package eu.iamgio.animated.binding.presets;

import eu.iamgio.animated.binding.property.animation.OnDemandAnimationProperty;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.scene.Node;

/**
 * Property that animates its child's opacity.
 */
public class AnimatedOpacity extends OnDemandAnimationProperty<Node, Double> {

    public AnimatedOpacity() {
        super(node -> PropertyWrapper.of(node.opacityProperty()));
    }

    public AnimatedOpacity(Node child) {
        this();
        targetNodeProperty().set(child);
    }
}
