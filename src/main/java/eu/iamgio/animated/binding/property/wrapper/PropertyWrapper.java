package eu.iamgio.animated.binding.property.wrapper;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

import java.util.function.Function;

/**
 * Wrapper of object and primitive JavaFX properties.
 * @param <T> wrapped property type
 */
public interface PropertyWrapper<T> {

    /**
     * @return the wrapped JavaFX property
     */
    Property<T> getProperty();

    /**
     * @return the wrapped value
     */
    T getValue();

    /**
     * Changes the value of the wrapped property.
     * @param value new value to set
     */
    void set(T value);

    /**
     * Registers a listener.
     * @param listener listener to register
     */
    void addListener(ChangeListener<? super T> listener);

    /**
     * Adds a binding to a target property: when the value of the wrapped property changes,
     * the target property is updated too, based on a mapper function.
     * @param targetProperty property to bind
     * @param mapper function that takes the value of the wrapped property as input,
     *               and returns the value the target property should get
     * @param <V> type of the target property
     */
    default  <V> void bindMapped(Property<V> targetProperty, Function<T, V> mapper) {
        targetProperty.bind(Bindings.createObjectBinding(() -> mapper.apply(getValue()), getProperty()));
    }

    /**
     * Creates an {@link ObjectPropertyWrapper} for the given {@link ObjectProperty}.
     * @see eu.iamgio.animated.binding.property
     * @param property JavaFX property to wrap
     * @param <T> property type
     * @return an instance of the proper subclass of {@link PropertyWrapper} that wraps <tt>property</tt>.
     */
    static <T> PropertyWrapper<T> of(ObjectProperty<T> property) {
        return new ObjectPropertyWrapper<>(property);
    }

    /**
     * Creates a {@link DoublePropertyWrapper} for the given {@link DoubleProperty}.
     * @see eu.iamgio.animated.binding.property
     * @param property JavaFX property to wrap
     * @return an instance of the proper subclass of {@link PropertyWrapper} that wraps <tt>property</tt>.
     */
    static PropertyWrapper<Double> of(DoubleProperty property) {
        return new DoublePropertyWrapper(property);
    }
}
