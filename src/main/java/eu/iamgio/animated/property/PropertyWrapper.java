package eu.iamgio.animated.property;

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
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <A extends PropertyWrapper<T>> A withSettings(AnimationSettings settings) {
        this.settings = settings;
        return (A) this;
    }

    /**
     * Gets a {@link PropertyWrapper} for the given property.
     * If no match is found, {@link ObjectPropertyWrapper} is used.
     * @see eu.iamgio.animated.property
     * @param property JavaFX property to wrap
     * @param <T> property type
     * @return an instance of the proper sub-class of {@link PropertyWrapper} that wraps <tt>property</tt>.
     */
    @SuppressWarnings("unchecked")
    public static <T> PropertyWrapper<T> of(Property<T> property) {
        if(property instanceof DoubleProperty) {
            return (PropertyWrapper<T>) new DoublePropertyWrapper((DoubleProperty) property);
        }
        return new ObjectPropertyWrapper<>((ObjectProperty<T>) property);
    }
}
