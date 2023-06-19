package eu.iamgio.animated.binding.presets;

import eu.iamgio.animated.binding.property.animation.OnDemandAnimationProperty;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.scene.Node;

/**
 * Property that animates its child's rotation.
 */
public class AnimatedRotation extends OnDemandAnimationProperty<Node, Double> {

    public AnimatedRotation() {
        super(node -> PropertyWrapper.of(node.opacityProperty()));
    }

    public AnimatedRotation(Node child) {
        this();
        targetNodeProperty().set(child);
    }
}
