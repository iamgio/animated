package eu.iamgio.animated.internal;

import eu.iamgio.animated.AnimatedHBox;
import eu.iamgio.animated.AnimatedVBox;
import eu.iamgio.animated.Animation;
import eu.iamgio.animated.Curve;
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

        // This set contains hash code of nodes whose animation should be skipped.
        private static final Set<Integer> skipNodesHash = new HashSet<>();

        /**
         * Registers the listener.
         * @param container container to animate
         */
        static void register(AnimatedContainer container) {
            container.getChildren().addListener((ListChangeListener<? super Node>) change -> {
                while(!container.isPaused() && change.next()) {

                    ObservableList<Node> children = container.getChildren();
                    Animation animationIn = container.getIn();
                    Animation animationOut = container.getOut();
                    double spacing = container.getSpacing();
                    Direction direction = container.getDirection();
                    Curve relocationCurve = container.getRelocationCurve();

                    // Animate inserted nodes
                    change.getAddedSubList().forEach(child -> {
                        if(!skipNodesHash.contains(child.hashCode())) {
                            animationIn.playIn(child, null);
                            Platform.runLater(() -> relocate(children, animationIn, spacing, direction, change.getFrom(), false, relocationCurve));
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
                            relocate(children, animationOut, spacing, direction, change.getFrom(), true, relocationCurve);
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
        private static void relocate(ObservableList<Node> children, Animation animation, double spacing, Direction direction, int index, boolean reverse, Curve relocationCurve) {
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
                next.setTranslateY(reverse ? 0 : start);

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
    }
}