package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.DoublePropertyWrapper;
import javafx.scene.Node;

/**
 * Node that animates its child's opacity.
 * @author Giorgio Garofalo
 */
public class AnimatedOpacity extends Animated<Double> {

    public AnimatedOpacity(Node child) {
        super(child, new DoublePropertyWrapper(child.opacityProperty()));
    }
}
