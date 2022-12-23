package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.DoublePropertyWrapper;
import eu.iamgio.animated.binding.property.ObjectPropertyWrapper;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;

/**
 * Node that animates its child's {@link DropShadow} radius and color.
 * @author Giorgio Garofalo
 */
public class AnimatedDropShadow extends AnimatedMulti {

    /**
     * If <tt>child</tt> has either no effect or {@link DropShadow} at initialization time,
     * a new {@link DropShadow} with default values will be set as its effect.
     */
    public AnimatedDropShadow(Node child) {
        super(child, new DoublePropertyWrapper(getEffect(child).radiusProperty()), new ObjectPropertyWrapper<>(getEffect(child).colorProperty()));
    }

    private static DropShadow getEffect(Node child) {
        if(child.getEffect() == null || !(child.getEffect() instanceof DropShadow)) {
            DropShadow shadow = new DropShadow();
            child.setEffect(shadow);
            return shadow;
        } else {
            return (DropShadow) child.getEffect();
        }
    }
}

