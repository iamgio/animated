package eu.iamgio.animated;

import eu.iamgio.animated.property.DoublePropertyWrapper;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Node that animates its child's size (pref size for {@link Region}, size for {@link Rectangle}, radius for {@link Circle}).
 * @author Giorgio Garofalo
 */
public class AnimatedSize extends AnimatedMulti {

    public AnimatedSize(Region child) {
        super(child, new DoublePropertyWrapper(child.prefWidthProperty()), new DoublePropertyWrapper(child.prefHeightProperty()));
    }

    public AnimatedSize(Rectangle child) {
        super(child, new DoublePropertyWrapper(child.widthProperty()), new DoublePropertyWrapper(child.heightProperty()));
    }

    public AnimatedSize(Circle child) {
        super(child, new DoublePropertyWrapper(child.radiusProperty()));
    }
}
