package eu.iamgio.animated.binding.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

/**
 * Abstract class that allows wrapping object and primitive JavaFX properties without the explicit <pre>asObject()</pre> call ands allows animation customization.
 * @param <T> property type
 * @author Giorgio Garofalo
 */
public abstract class PropertyWrapper<T> {

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
     * Creates an {@link ObjectPropertyWrapper} for the given {@link ObjectProperty}.
     * @see eu.iamgio.animated.binding.property
     * @param property JavaFX property to wrap
     * @param <T> property type
     * @return an instance of the proper subclass of {@link PropertyWrapper} that wraps <tt>property</tt>.
     */
    public static <T> PropertyWrapper<T> of(ObjectProperty<T> property) {
        return new ObjectPropertyWrapper<>(property);
    }

    /**
     * Creates a {@link DoublePropertyWrapper} for the given {@link DoubleProperty}.
     * @see eu.iamgio.animated.binding.property
     * @param property JavaFX property to wrap
     * @return an instance of the proper subclass of {@link PropertyWrapper} that wraps <tt>property</tt>.
     */
    public static PropertyWrapper<Double> of(DoubleProperty property) {
        return new DoublePropertyWrapper(property);
    }
}
