package eu.iamgio.animated.transition.container;

import eu.iamgio.animated.binding.Curve;
import eu.iamgio.animated.common.Pausable;
import eu.iamgio.animated.transition.EntranceAndExitAnimationCompatible;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * Interface implemented by containers whose children should be animated.
 * @see AnimatedVBox
 * @see AnimatedHBox
 * @author Giorgio Garofalo
 */
public interface AnimatedContainer extends Pausable, EntranceAndExitAnimationCompatible {

    /**
     * @return target container's children
     */
    ObservableList<Node> getChildren();

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
    ObjectProperty<Curve> relocationCurveProperty();

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
        AnimatedContainerHandler.register(this);
    }

    enum Direction {
        HORIZONTAL, VERTICAL
    }
}