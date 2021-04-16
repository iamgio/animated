package eu.iamgio.animated.property;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

/**
 * Interface that allows wrapping object and primitive JavaFX properties without the explicit <pre>asObject()</pre> call.
 * @param <T>
 * @author Giorgio Garofalo
 */
public interface PropertyWrapper<T> {

    Property<T> getProperty();
    void set(T value);
    void addListener(ChangeListener<? super T> listener);
}
