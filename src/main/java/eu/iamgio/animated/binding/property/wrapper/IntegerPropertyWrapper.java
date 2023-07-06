package eu.iamgio.animated.binding.property.wrapper;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

/**
 * Wrapper of an {@link Integer} property.
 * @author Giorgio Garofalo
 */
public class IntegerPropertyWrapper implements PropertyWrapper<Integer> {

    private final IntegerProperty property;

    /**
     * Instantiates a wrapper of an {@link Integer} property
     * @param property property to wrap
     */
    public IntegerPropertyWrapper(IntegerProperty property) {
        this.property = property;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<Integer> getProperty() {
        return property.asObject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getValue() {
        return property.intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(Integer value) {
        property.set(value);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addListener(ChangeListener<? super Integer> listener) {
        property.addListener((ChangeListener<? super Number>) listener);
    }
}
