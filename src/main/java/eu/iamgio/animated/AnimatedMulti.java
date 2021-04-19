package eu.iamgio.animated;

import eu.iamgio.animated.property.PropertyWrapper;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * A node that automatically animates a list of properties of its child.
 * @see Animated
 * @author Giorgio Garofalo
 */
public class AnimatedMulti extends SingleChildParent implements CustomizableAnimation<AnimatedMulti> {

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
     * @throws UnsupportedOperationException {@link AnimatedMulti} does not have global settings
     */
    @Override
    public AnimationSettings getSettings() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <A extends AnimatedMulti> A withSettings(AnimationSettings settings) {
        for(PropertyWrapper<?> wrapper : wrappers) {
            wrapper.withSettings(settings);
        }
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends AnimatedMulti> A custom(Function<AnimationSettings, AnimationSettings> settings) {
        for(PropertyWrapper<?> wrapper : wrappers) {
            wrapper.custom(settings);
        }
        return (A) this;
    }
}
