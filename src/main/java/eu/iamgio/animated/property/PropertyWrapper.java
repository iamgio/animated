package eu.iamgio.animated.property;

import eu.iamgio.animated.AnimationSettings;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

import java.util.function.Function;

/**
 * Abstract class that allows wrapping object and primitive JavaFX properties without the explicit <pre>asObject()</pre> call ands allows animation customization.
 * @param <T> property type
 * @author Giorgio Garofalo
 */
public abstract class PropertyWrapper<T> {

    public abstract Property<T> getProperty();
    public abstract void set(T value);
    public abstract void addListener(ChangeListener<? super T> listener);

    private AnimationSettings settings = new AnimationSettings();

    /**
     * @return animation settings
     */
    public AnimationSettings getSettings() {
        return settings;
    }

    /**
     * Applies custom animation settings
     * @param settings animation settings to set
     * @return this for concatenation
     */
    public PropertyWrapper<T> withSettings(AnimationSettings settings) {
        this.settings = settings;
        return this;
    }

    /**
     * Applies custom animation settings
     * @param settings settings to update. Example: <pre>custom(settings -> settings.withDuration(...))</pre>
     * @return this for concatenation
     */
    public PropertyWrapper<T> custom(Function<AnimationSettings, AnimationSettings> settings) {
        return withSettings(settings.apply(getSettings()));
    }
}
