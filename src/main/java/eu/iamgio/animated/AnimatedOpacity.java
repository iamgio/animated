package eu.iamgio.animated;

import eu.iamgio.animated.property.DoublePropertyWrapper;
import javafx.scene.Node;

/**
 * @author Giorgio Garofalo
 */
public class AnimatedOpacity extends Animated<Double> {

    public AnimatedOpacity(Node child) {
        super(new DoublePropertyWrapper(child.opacityProperty()), child);
    }
}
