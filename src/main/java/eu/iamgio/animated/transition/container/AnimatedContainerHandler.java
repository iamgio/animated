package eu.iamgio.animated.transition.container;

import eu.iamgio.animated.binding.Curve;
import eu.iamgio.animated.transition.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class that handles {@link AnimatedContainer}'s features.
 */
class AnimatedContainerHandler {

    // Nodes whose animation should be temporarily skipped.
    private static final Set<Node> skipped = new HashSet<>();

    /**
     * Registers the listener.
     * @param container container to animate
     */
    static void register(AnimatedContainer container) {
        container.getChildren().addListener((ListChangeListener<? super Node>) change -> {
            while(!container.isPaused() && change.next()) {
                // Animate inserted nodes
                if(container.getIn().getAnimationFX() != null) {
                    playIn(change, container);
                }
                // Animate removed nodes
                if(container.getOut().getAnimationFX() != null) {
                    playOut(change, container);
                }
            }
        });
    }

    /**
     * Plays the entrance animation for each added node.
     * @param change change that affected the children
     * @param container animated container to handle
     */
    private static void playIn(ListChangeListener.Change<? extends Node> change, AnimatedContainer container) {
        for(Node child : change.getAddedSubList()) {
            if(skipped.contains(child)) {
                skipped.remove(child);
                continue;
            }

            container.getIn().playIn(child, null);
            Platform.runLater(() -> relocate(
                    container,
                    container.getIn(),
                    change.getFrom(),
                    false
            ));
        }
    }

    /**
     * Plays the exit animation for each removed node.
     * @param change change that affected the children
     * @param container animated container to handle
     */
    private static void playOut(ListChangeListener.Change<? extends Node> change, AnimatedContainer container) {
        for (Node child : change.getRemoved()) {
            if (skipped.contains(child)) {
                skipped.remove(child);
                continue;
            }

            skipped.add(child);
            Platform.runLater(() -> {
                container.getChildren().add(change.getFrom(), child);
                skipped.add(child);
                container.getOut().playOut(child, container.getChildren());
                relocate(
                        container,
                        container.getOut(),
                        change.getFrom(),
                        true
                );
            });
        }
    }

    /**
     * Updates the node's translate X or Y depending on the direction of the container.
     * @param node target node
     * @param direction direction of the container
     * @param translate new translation value
     */
    private static void setTranslate(Node node, AnimatedContainer.Direction direction, double translate) {
        switch(direction) {
            case HORIZONTAL:
                node.setTranslateX(translate);
                break;
            case VERTICAL:
                node.setTranslateY(translate);
                break;
        }
    }

    /**
     * Animates next nodes' position in order to have a smooth animation.
     * @param container animated container to handle
     * @param animation last animation to relocate from
     * @param index start index
     * @param reverse whether the animation should be reversed (exit) or not (entrance)
     */
    private static void relocate(AnimatedContainer container, Animation animation, int index, boolean reverse) {
        final ObservableList<Node> children = container.getChildren();
        final double spacing = container.getSpacing();
        final AnimatedContainer.Direction direction = container.getDirection();
        final Curve relocationCurve = container.getRelocationCurve();

        final Timeline timeline = new Timeline();

        // Affected nodes
        final List<Node> toReset = new ArrayList<>();

        // Iterate next nodes
        for (int i = index + 1; i < children.size(); i++) {
            // Target node
            final Node next = children.get(i);
            // Bounds of the affected node
            final Bounds affectedBounds = children.get(index).getBoundsInLocal();

            // Determine start (or end if reverse is true) value, based off direction and spacing
            final double start = -((direction == AnimatedContainer.Direction.HORIZONTAL ? affectedBounds.getWidth() : affectedBounds.getHeight()) + spacing);
            setTranslate(next, direction, reverse ? 0 : start);

            // Animate translate X/Y
            final Duration duration = animation.getAnimationFX().getTimeline().getCycleDuration().divide(animation.getSpeed());
            final DoubleProperty targetProperty = direction == AnimatedContainer.Direction.HORIZONTAL ? next.translateXProperty() : next.translateYProperty();
            timeline.getKeyFrames().add(
                    new KeyFrame(duration, new KeyValue(targetProperty, reverse ? start : 0, relocationCurve.toInterpolator()))
            );

            // This node position will be reset after the animation is complete
            toReset.add(next);
        }

        // Reset positions
        timeline.setOnFinished(e -> {
            for (Node node : toReset) {
                setTranslate(node, direction, 0);
            }
        });

        timeline.playFromStart();
    }
}
