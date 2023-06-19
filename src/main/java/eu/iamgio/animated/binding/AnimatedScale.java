package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.wrapper.DoublePropertyWrapper;
import javafx.scene.Node;

/**
 * Node that animates its child's scale X and Y.
 * @author Giorgio Garofalo
 */
public class AnimatedScale extends AnimatedMulti {

    public AnimatedScale(Node child) {
        super(child, new DoublePropertyWrapper(child.scaleXProperty()), new DoublePropertyWrapper(child.scaleYProperty()));
    }
}
