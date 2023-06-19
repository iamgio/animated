package eu.iamgio.animated.binding.presets;

import eu.iamgio.animated.binding.property.animation.OnDemandAnimationPropertyGroup;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.scene.Node;
import javafx.scene.layout.Region;

import java.util.Arrays;

/**
 * Property that animates its child's scale X/Y.
 */
public class AnimatedScale extends OnDemandAnimationPropertyGroup<Node, Double> {

    public AnimatedScale() {
        super(Arrays.asList(
                node -> PropertyWrapper.of(node.scaleXProperty()),
                node -> PropertyWrapper.of(node.scaleYProperty())
        ));
    }

    public AnimatedScale(Region child) {
        this();
        targetNodeProperty().set(child);
    }
}
