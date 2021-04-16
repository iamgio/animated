package eu.iamgio.animated.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

/**
 * @author Giorgio Garofalo
 */
public class DoublePropertyWrapper implements PropertyWrapper<Double> {

    private final DoubleProperty property;

    public DoublePropertyWrapper(DoubleProperty property) {
        this.property = property;
    }

    @Override
    public Property<Double> getProperty() {
        return property.asObject();
    }

    @Override
    public void set(Double value) {
        property.set(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addListener(ChangeListener<? super Double> listener) {
        property.addListener((ChangeListener<? super Number>) listener);
    }
}
