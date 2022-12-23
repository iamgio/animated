package eu.iamgio.animated.binding.property;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

/**
 * Wraps a non-primitive property.
 * @author Giorgio Garofalo
 */
public class ObjectPropertyWrapper<T> extends PropertyWrapper<T> {

    private final ObjectProperty<T> property;

    /**
     * Instantiates a wrapper of a non-primitive property
     * @param property property to wrap
     */
    public ObjectPropertyWrapper(ObjectProperty<T> property) {
        this.property = property;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<T> getProperty() {
        return property;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getValue() {
        return property.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(T value) {
        property.set(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyWrapper<T> createParallelProperty() {
        return new ObjectPropertyWrapper<>(new SimpleObjectProperty<>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addListener(ChangeListener<? super T> listener) {
        property.addListener(listener);
    }
}
