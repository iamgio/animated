package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.animation.OnDemandAnimationProperty;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.scene.Node;

/**
 * Node that animates its child's opacity.
 * @author Giorgio Garofalo
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
