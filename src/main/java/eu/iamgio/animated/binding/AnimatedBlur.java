package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.wrapper.DoublePropertyWrapper;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;

/**
 * Node that animates its child's {@link GaussianBlur} radius.
 * @author Giorgio Garofalo
 */
public class AnimatedBlur extends Animated<Double> {

    /**
     * If <tt>child</tt> has either no effect or {@link GaussianBlur} at initialization time,
     * a new {@link GaussianBlur} with default radius will be set as its effect.
     */
    public AnimatedBlur(Node child) {
        super(child, new DoublePropertyWrapper(getEffect(child).radiusProperty()));
    }

    private static GaussianBlur getEffect(Node child) {
        if(child.getEffect() == null || !(child.getEffect() instanceof GaussianBlur)) {
            GaussianBlur blur = new GaussianBlur();
            child.setEffect(blur);
            return blur;
        } else {
            return (GaussianBlur) child.getEffect();
        }
    }
}
