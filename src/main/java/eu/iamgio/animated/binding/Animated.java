package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.PropertyWrapper;
import eu.iamgio.animated.transition.Pausable;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * A node that automatically animates a property of its child.
 * @param <T> type of the target property
 * @author Giorgio Garofalo
 */
public class Animated<T> extends SingleChildParent implements CustomizableAnimation<Animated<T>>, Pausable {

    private final AnimationProperty<T> property;

    /**
     * Instantiates an {@link Animated} node
     * @param child initial child
     * @param property target property
     * @param settings animation settings
     */
    public Animated(Node child, AnimationProperty<T> property, AnimationSettings settings) {
        super(child);
        this.property = property.withSettings(settings);
        this.property.register(child);
    }

    /**
     * Instantiates an {@link Animated} node
     * @param child initial child
     * @param property target property
     * @param settings animation settings
     */
    public Animated(Node child, PropertyWrapper<T> property, AnimationSettings settings) {
        this(child, new AnimationProperty<>(property), settings);
    }

    /**
     * Instantiates an {@link Animated} node
     * @param child initial child
     * @param property target property
     */
    public Animated(Node child, PropertyWrapper<T> property) {
        this(child, property, new AnimationSettings());
    }

    /**
     * Instantiates an {@link Animated} node
     * @param property target property
     */
    public Animated(PropertyWrapper<T> property) {
        this(null, property);
    }

    /**
     * @return target property
     */
    public AnimationProperty<T> getTargetProperty() {
        return property;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends Animated<T>> A withSettings(AnimationSettings settings) {
        this.property.withSettings(settings);
        return (A) this;
    }

    @Override
    public <A extends Animated<T>> A custom(Function<AnimationSettings, AnimationSettings> settings) {
        return withSettings(settings.apply(property.getSettings()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty pausedProperty() {
        return this.property.pausedProperty();
    }
}
