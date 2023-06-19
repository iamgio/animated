package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.animation.AnimationProperty;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import eu.iamgio.animated.transition.Pausable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * A node that automatically animates a list of properties of its child.
 * @see Animated
 * @author Giorgio Garofalo
 */
public class AnimatedMulti extends SingleChildParent implements CustomizableAnimation<AnimatedMulti>, Pausable {

    private final AnimationProperty<?>[] properties;

    // Whether the property should be animated
    private final BooleanProperty pausedProperty = new SimpleBooleanProperty(false);

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

        registerPause();
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

        registerPause();
    }

    /**
     * Instantiates a group of animated properties
     * @param child initial child
     * @param animated animated nodes (with no children)
     */
    public AnimatedMulti(Node child, Animated<?>... animated) {
        super(child);
        this.properties = new AnimationProperty[animated.length];
        for (int i = 0; i < animated.length; i++) {
            Animated<?> anim = animated[i];
            if (anim.getChild() != null) {
                System.err.println("Animated arguments of AnimatedMulti should not have any children.");
            }
            if (anim.getScene() != null) {
                System.err.println("Animated arguments of AnimatedMulti should not be already in scene.");
            }
            this.properties[i] = anim.getTargetProperty();
            getChildren().add(anim);
        }

        registerPause();
    }

    private void registerPause() {
        for (AnimationProperty<?> property : properties) {
            property.pausedProperty().bind(this.pausedProperty);
        }
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
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty pausedProperty() {
        return this.pausedProperty;
    }
}
