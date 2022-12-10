package eu.iamgio.animated.property;

import eu.iamgio.animated.AnimationProperty;
import eu.iamgio.animated.AnimationSettings;
import eu.iamgio.animated.internal.CustomizableAnimation;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

/**
 * Abstract class that allows wrapping object and primitive JavaFX properties without the explicit <pre>asObject()</pre> call ands allows animation customization.
 * @param <T> property type
 * @author Giorgio Garofalo
 */
public abstract class PropertyWrapper<T> implements CustomizableAnimation<PropertyWrapper<T>> {

    private AnimationSettings settings = new AnimationSettings();

    /**
     * @return the wrapped JavaFX property
     */
    public abstract Property<T> getProperty();

    /**
     * @return the wrapped value
     */
    public abstract T getValue();

    /**
     * Changes the value of the wrapped property
     * @param value new value to set
     */
    public abstract void set(T value);

    /**
     * Registers a listener
     * @param listener listener to register
     */
    public abstract void addListener(ChangeListener<? super T> listener);

    /**
     * @return an empty property of the same type
     * @deprecated Currently unused and will be likely removed.
     */
    public abstract PropertyWrapper<T> createParallelProperty();

    /**
     * {@inheritDoc}
     */
    public AnimationSettings getSettings() {
        return settings;
    }

    /**
     * Creates a new implicitly animated binding for this property and registers a listener for it.
     * @return new animation property linked to this property
     */
    public AnimationProperty<T> registerAnimation() {
        AnimationProperty<T> property = new AnimationProperty<>(this, settings);
        property.register();
        return property;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <A extends PropertyWrapper<T>> A withSettings(AnimationSettings settings) {
        this.settings = settings;
        return (A) this;
    }

    /**
     * Creates an {@link ObjectPropertyWrapper} for the given {@link ObjectProperty}.
     * @see eu.iamgio.animated.property
     * @param property JavaFX property to wrap
     * @param <T> property type
     * @return an instance of the proper subclass of {@link PropertyWrapper} that wraps <tt>property</tt>.
     */
    public static <T> PropertyWrapper<T> of(ObjectProperty<T> property) {
        return new ObjectPropertyWrapper<>(property);
    }

    /**
     * Creates a {@link DoublePropertyWrapper} for the given {@link DoubleProperty}.
     * @see eu.iamgio.animated.property
     * @param property JavaFX property to wrap
     * @return an instance of the proper subclass of {@link PropertyWrapper} that wraps <tt>property</tt>.
     */
    public static PropertyWrapper<Double> of(DoubleProperty property) {
        return new DoublePropertyWrapper(property);
    }
}
