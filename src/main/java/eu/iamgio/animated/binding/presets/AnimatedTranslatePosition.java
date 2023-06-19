package eu.iamgio.animated.binding.presets;

import eu.iamgio.animated.binding.property.animation.OnDemandAnimationPropertyGroup;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.scene.Node;
import javafx.scene.layout.Region;

import java.util.Arrays;

/**
 * Property that animates its child's translate X/Y coordinates.
 */
public class AnimatedTranslatePosition extends OnDemandAnimationPropertyGroup<Node, Double> {

    public AnimatedTranslatePosition() {
        super(Arrays.asList(
                node -> PropertyWrapper.of(node.translateXProperty()),
                node -> PropertyWrapper.of(node.translateYProperty())
        ));
    }

    public AnimatedTranslatePosition(Region child) {
        this();
        targetNodeProperty().set(child);
    }
}
