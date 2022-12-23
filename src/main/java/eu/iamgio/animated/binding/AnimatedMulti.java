package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.PropertyWrapper;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * A node that automatically animates a list of properties of its child.
 * @see Animated
 * @author Giorgio Garofalo
 */
public class AnimatedMulti extends SingleChildParent implements CustomizableAnimation<AnimatedMulti> {

    private final AnimationProperty<?>[] properties;
    private boolean isActive;

    /**
     * Instantiates a group of animated properties
     * @param child initial child
     * @param properties target properties
     */
    public AnimatedMulti(Node child, AnimationProperty<?>... properties) {
        super(child);
        this.properties = properties;
        for(AnimationProperty<?> property : properties) {
            property.register(child);
        }
    }

    /**
     * Instantiates a group of animated properties
     * @param child initial child
     * @param properties target properties
     */
    public AnimatedMulti(Node child, PropertyWrapper<?>... properties) {
        super(child);
        this.properties = new AnimationProperty[properties.length];
        for(int i = 0; i < properties.length; i++) {
            AnimationProperty<?> property = new AnimationProperty<>(properties[i]);
            this.properties[i] = property;
            property.register(child);
        }
    }

    /**
     * Instantiates a group of animated properties
     * @param child initial child
     * @param animated animated nodes (with no children)
     */
    public AnimatedMulti(Node child, Animated<?>... animated) {
        super(child);
        this.properties = new AnimationProperty[animated.length];
        for(int i = 0; i < animated.length; i++) {
            Animated<?> anim = animated[i];
            if(anim.getChild() != null) {
                System.err.println("Animated arguments of AnimatedMulti should not have any children.");
            }
            if(anim.getScene() != null) {
                System.err.println("Animated arguments of AnimatedMulti should not be already in scene.");
            }
            this.properties[i] = anim.getTargetProperty();
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
        for(AnimationProperty<?> property : properties) {
            property.withSettings(settings);
        }
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends AnimatedMulti> A custom(Function<AnimationSettings, AnimationSettings> settings) {
        for(AnimationProperty<?> property : properties) {
            property.custom(settings);
        }
        return (A) this;
    }

    /**
     * @return target properties
     */
    public AnimationProperty<?>[] getTargetProperties() {
        return properties;
    }

    /**
     * @return whether the property should be animated
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param active whether the property should be animated
     */
    public void setActive(boolean active) {
        isActive = active;
        for(AnimationProperty<?> property : properties) {
            property.setActive(active);
        }
    }
}
