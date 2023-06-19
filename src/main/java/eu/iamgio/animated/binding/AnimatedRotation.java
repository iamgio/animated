package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.wrapper.DoublePropertyWrapper;
import javafx.scene.Node;

/**
 * Node that animates its child's rotation.
 * @author Giorgio Garofalo
 */
public class AnimatedRotation extends Animated<Double> {

    public AnimatedRotation(Node child) {
        super(child, new DoublePropertyWrapper(child.rotateProperty()));
    }
}
