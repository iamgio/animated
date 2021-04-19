package eu.iamgio.animated;

import eu.iamgio.animated.property.PropertyWrapper;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * A node that automatically animates a list of properties of its child.
 * @see Animated
 * @author Giorgio Garofalo
 */
public class AnimatedMulti extends SingleChildParent {

    private final PropertyWrapper<?>[] wrappers;

    /**
     * Instantiates a group of animated properties
     * @param child initial child
     * @param properties target properties
     */
    public AnimatedMulti(Node child, PropertyWrapper<?>... properties) {
        super(child);
        this.wrappers = properties;
        for(PropertyWrapper<?> property : properties) {
            getChildren().add(new Animated<>(null,
                    property,
                    property.getSettings()
            ));
        }
    }

    /**
     * Instantiates a group of animated properties
     * @param child initial child
     * @param animated animated nodes (with no children)
     */
    public AnimatedMulti(Node child, Animated<?>... animated) {
        super(child);
        this.wrappers = new PropertyWrapper[animated.length];
        for(int i = 0; i < animated.length; i++) {
            Animated<?> anim = animated[i];
            if(anim.getChild() != null) {
                System.err.println("Animated arguments of AnimatedMulti should not have any children.");
            }
            if(anim.getScene() != null) {
                System.err.println("Animated arguments of AnimatedMulti should not be already in scene.");
            }
            wrappers[i] = anim.getProperty();
            getChildren().add(anim);
        }
    }

    /**
     * Applies (and overrides) custom animation settings to all the properties
     * @param settings animation settings to set
     * @param <A> either {@link AnimatedMulti} or subclass
     * @return this for concatenation
     */
    @SuppressWarnings("unchecked")
    public <A extends AnimatedMulti> A withSettings(AnimationSettings settings) {
        for(PropertyWrapper<?> wrapper : wrappers) {
            wrapper.withSettings(settings);
        }
        return (A) this;
    }

    /**
     * Applies custom animation settings
     * @param settings settings to update. Example: <pre>custom(settings -> settings.withDuration(...))</pre>
     * @param <A> either {@link AnimatedMulti} or subclass
     * @return this for concatenation
     */
    @SuppressWarnings("unchecked")
    public <A extends AnimatedMulti> A custom(Function<AnimationSettings, AnimationSettings> settings) {
        for(PropertyWrapper<?> wrapper : wrappers) {
            wrapper.custom(settings);
        }
        return (A) this;
    }
}
