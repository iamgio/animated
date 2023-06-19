package eu.iamgio.animated.binding.presets;

import eu.iamgio.animated.binding.property.animation.OnDemandAnimationProperty;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/**
 * Property that animates its child's color, for {@link Shape} nodes.
 * @author Giorgio Garofalo
 */
public class AnimatedColor extends OnDemandAnimationProperty<Shape, Paint> {

    public AnimatedColor() {
        super(node -> PropertyWrapper.of(node.fillProperty()));
    }

    public AnimatedColor(Shape child) {
        this();
        targetNodeProperty().set(child);
    }
}
