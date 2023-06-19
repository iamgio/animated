package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.animation.OnDemandAnimationPropertyGroup;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.scene.layout.Region;

import java.util.Arrays;

/**
 * Property that animates its child's preferred size (pref size for {@link Region}.
 * @author Giorgio Garofalo
 */
public class AnimatedPrefSize extends OnDemandAnimationPropertyGroup<Region, Double> {

    public AnimatedPrefSize() {
        super(Arrays.asList(
                node -> PropertyWrapper.of(node.prefWidthProperty()),
                node -> PropertyWrapper.of(node.prefHeightProperty())
        ));
    }

    public AnimatedPrefSize(Region child) {
        this();
        targetNodeProperty().set(child);
    }
}
