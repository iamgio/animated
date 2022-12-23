package eu.iamgio.animated.transition.container;

import eu.iamgio.animated.binding.Curve;
import eu.iamgio.animated.transition.Animation;
import eu.iamgio.animated.transition.Pausable;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
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
 * Interface implemented by containers whose children should be animated.
 * @see AnimatedVBox
 * @see AnimatedHBox
 * @author Giorgio Garofalo
 */
public interface AnimatedContainer extends Pausable {

    /**
     * @return target container's children
     */
    ObservableList<Node> getChildren();

    /**
     * @return entrance animation
     */
    Animation getIn();

    /**
     * @return exit animation
     */
    Animation getOut();

    /**
     * @return spacing between children
     */
    double getSpacing();

    /**
     * @return whether the children are displayed horizontally (HBox) or vertically (VBox)
     */
    Direction getDirection();

    /**
     * @return curve used by the animation while relocating other nodes after a change
     */
    SimpleObjectProperty<Curve> relocationCurveProperty();

    /**
     * @return curve used by the animation while relocating other nodes after a change. Defaults to {@link Curve#EASE_IN_OUT}
     */
    default Curve getRelocationCurve() {
        return relocationCurveProperty().get();
    }

    /**
     * Changes the curve used by the animation while relocating other nodes after a change.
     * @param curve relocation curve to set
     */
    default void setRelocationCurve(Curve curve) {
        relocationCurveProperty().set(curve);
    }

    /**
     * Registers the handler.
     */
    default void register() {
        Handler.register(this);
    }

    enum Direction {
        HORIZONTAL, VERTICAL
    }

    /**
     * Utility class that handles {@link AnimatedContainer}'s features.
     */
    class Handler {

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
            for(Node child : change.getRemoved()) {
                if(skipped.contains(child)) {
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
        private static void setTranslate(Node node, Direction direction, double translate) {
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
            ObservableList<Node> children = container.getChildren();
            double spacing = container.getSpacing();
            Direction direction = container.getDirection();
            Curve relocationCurve = container.getRelocationCurve();

            Timeline timeline = new Timeline();

            // Affected nodes
            List<Node> toReset = new ArrayList<>();

            // Iterate next nodes
            for(int i = index + 1; i < children.size(); i++) {
                // Target node
                Node next = children.get(i);
                // Bounds of the affected node
                Bounds affectedBounds = children.get(index).getBoundsInLocal();

                // Determine start (or end if reverse is true) value, based off direction and spacing
                double start = -((direction == Direction.HORIZONTAL ? affectedBounds.getWidth() : affectedBounds.getHeight()) + spacing);
                setTranslate(next, direction, reverse ? 0 : start);

                // Animate translate X/Y
                Duration duration = animation.getAnimationFX().getTimeline().getCycleDuration().divide(animation.getSpeed());
                timeline.getKeyFrames().add(direction == Direction.HORIZONTAL
                        ? new KeyFrame(duration, new KeyValue(next.translateXProperty(), reverse ? start : 0, relocationCurve.toInterpolator()))
                        : new KeyFrame(duration, new KeyValue(next.translateYProperty(), reverse ? start : 0, relocationCurve.toInterpolator()))
                );

                // This node position will be reset after the animation is complete
                toReset.add(next);
            }

            // Reset positions
            timeline.setOnFinished(e -> {
                for(Node node : toReset) {
                    setTranslate(node, direction, 0);
                }
            });

            timeline.playFromStart();
        }
    }
}