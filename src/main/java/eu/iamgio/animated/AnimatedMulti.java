package eu.iamgio.animated;

import eu.iamgio.animated.property.PropertyWrapper;
import javafx.scene.Node;

/**
 * A node that automatically animates a list of properties of its child.
 * @see Animated
 * @author Giorgio Garofalo
 */
public class AnimatedMulti extends SingleChildParent {

    public AnimatedMulti(Node child, PropertyWrapper<?>... properties) {
        super(child);
        for(PropertyWrapper<?> property : properties) {
            getChildren().add(new Animated<>(property));
        }
    }

    public AnimatedMulti(Node child, Animated<?>... animated) {
        super(child);
        for(Animated<?> anim : animated) {
            if(anim.getChild() != null) {
                System.err.println("Animated arguments of AnimatedMulti should not have any children.");
            }
            getChildren().add(anim);
        }
    }
}
