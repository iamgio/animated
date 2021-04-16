package eu.iamgio.animated.property;

import eu.iamgio.animated.AnimationSettings;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

/**
 * Property that allows animation customization.
 * @author Giorgio Garofalo
 */
public class AnimatedProperty<T> implements PropertyWrapper<T> {

    private final PropertyWrapper<T> property;
    private final AnimationSettings settings;

    public AnimatedProperty(PropertyWrapper<T> property, AnimationSettings settings) {
        this.property = property;
        this.settings = settings;
    }

    public PropertyWrapper<T> getPropertyWrapper() {
        return property;
    }

    public AnimationSettings getSettings() {
        return settings;
    }

    @Override
    public Property<T> getProperty() {
        return property.getProperty();
    }

    @Override
    public void set(T value) {
        property.set(value);
    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        property.addListener(listener);
    }
}
