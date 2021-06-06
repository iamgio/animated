package eu.iamgio.animated;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
public interface AnimatedContainer {

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
    Handler.Direction getDirection();

    /**
     * Registers the handler.
     */
    default void register() {
        Handler.register(this);
    }

    /**
     * Utility class that handles {@link AnimatedContainer}'s features.
     */
    class Handler {

        // This set contains hash code of nodes whose animation should be skipped.
        private static final Set<Integer> skipNodesHash = new HashSet<>();

        /**
         * Registers the listener.
         * @param container container to animate
         */
        static void register(AnimatedContainer container) {
            ObservableList<Node> children = container.getChildren();
            Animation animationIn = container.getIn();
            Animation animationOut = container.getOut();
            double spacing = container.getSpacing();
            Direction direction = container.getDirection();

            children.addListener((ListChangeListener<? super Node>) change -> {
                while(change.next()) {

                    // Animate inserted nodes
                    change.getAddedSubList().forEach(child -> {
                        if(!skipNodesHash.contains(child.hashCode())) {
                            animationIn.playIn(child, null);
                            Platform.runLater(() -> relocate(children, animationIn, spacing, direction, change.getFrom(), false));
                        } else {
                            skipNodesHash.remove(child.hashCode());
                        }
                    });

                    // Animate removed nodes
                    change.getRemoved().forEach(child -> {
                        if(skipNodesHash.contains(child.hashCode())) {
                            skipNodesHash.remove(child.hashCode());
                            return;
                        }
                        skipNodesHash.add(child.hashCode());
                        Platform.runLater(() -> {
                            children.add(change.getFrom(), child);
                            skipNodesHash.add(child.hashCode());
                            animationOut.playOut(child, children);
                            relocate(children, animationOut, spacing, direction, change.getFrom(), true);
                        });
                    });
                }
            });
        }

        /**
         * Animates next nodes' position in order to have a smooth animation.
         * @param index start index
         * @param reverse whether the animation should be reversed (exit) or not (entrance)
         */
        private static void relocate(ObservableList<Node> children, Animation animation, double spacing, Direction direction, int index, boolean reverse) {
            Timeline timeline = new Timeline();
            List<Node> toReset = new ArrayList<>();

            for(int i = index + 1; i < children.size(); i++) {
                Node next = children.get(i);
                Bounds nextBounds = next.localToScene(next.getBoundsInLocal());

                double start = -((direction == Direction.HORIZONTAL ? nextBounds.getWidth() : nextBounds.getHeight()) + spacing);
                next.setTranslateY(reverse ? 0 : start);

                Duration duration = animation.getAnimationFX().getTimeline().getCycleDuration().divide(animation.getSpeed());
                timeline.getKeyFrames().add(direction == Direction.HORIZONTAL
                        ? new KeyFrame(duration, new KeyValue(next.translateXProperty(), reverse ? start : 0, Curve.EASE_IN_OUT.toInterpolator()))
                        : new KeyFrame(duration, new KeyValue(next.translateYProperty(), reverse ? start : 0, Curve.EASE_IN_OUT.toInterpolator()))
                );

                toReset.add(next);
            }

            timeline.setOnFinished(e -> {
                for(Node node : toReset) {
                    switch(direction) {
                        case HORIZONTAL:
                            node.setTranslateX(0);
                            break;
                        case VERTICAL:
                            node.setTranslateY(0);
                            break;
                    }
                }
            });

            timeline.playFromStart();
        }

        enum Direction {
            HORIZONTAL, VERTICAL
        }
    }
}