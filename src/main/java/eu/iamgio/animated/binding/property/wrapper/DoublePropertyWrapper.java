package eu.iamgio.animated.binding.property.wrapper;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

/**
 * Wrapper of a {@link Double} property.
 * @author Giorgio Garofalo
 */
public class DoublePropertyWrapper implements PropertyWrapper<Double> {

    private final DoubleProperty property;

    /**
     * Instantiates a wrapper of a {@link Double} property
     * @param property property to wrap
     */
    public DoublePropertyWrapper(DoubleProperty property) {
        this.property = property;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<Double> getProperty() {
        return property.asObject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getValue() {
        return property.doubleValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(Double value) {
        property.set(value);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addListener(ChangeListener<? super Double> listener) {
        property.addListener((ChangeListener<? super Number>) listener);
    }
}
