package eu.iamgio.animated;

import eu.iamgio.animated.property.PropertyWrapper;
import javafx.animation.Animation;
import javafx.animation.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

/**
 * Class that animates of a JavaFX property wrapped inside of a {@link PropertyWrapper}.
 * @author Giorgio Garofalo
 */
public class AnimationProperty<T> implements CustomizableAnimation<AnimationProperty<T>> {

    // The target property
    private final PropertyWrapper<T> property;

    // The parallel property is used to check if the changes are applied by the animation or by external sources
    private final ObjectProperty<T> parallelProperty;

    // Animation timeline
    private final Timeline timeline;

    // Whether the property should be animated
    private boolean isActive = true;

    // Whether the changes should be handled (internally handled)
    private boolean handleChanges = false;

    /**
     * Instantiates an implicitly animated property
     * @param property target property
     * @param settings animation settings
     */
    public AnimationProperty(PropertyWrapper<T> property, AnimationSettings settings) {
        this.property = property.withSettings(settings);
        this.parallelProperty = new SimpleObjectProperty<>();
        this.timeline = new Timeline();
    }

    /**
     * Instantiates an implicitly animated property
     * @param property target property
     */
    public AnimationProperty(PropertyWrapper<T> property) {
        this(property, property.getSettings());
    }

    /**
     * Plays the animation
     * @param value new property value
     */
    private void handleChanges(T value) {
        AnimationSettings settings = property.getSettings();

        // The parallel property is used to check if the changes are applied by the animation or by external sources
        parallelProperty.set(property.getProperty().getValue());

        Interpolator interpolator = settings.getCurve().toInterpolator();

        timeline.getKeyFrames().setAll(
                new KeyFrame(settings.getDuration(), new KeyValue(property.getProperty(), value, interpolator)),
                new KeyFrame(settings.getDuration(), new KeyValue(parallelProperty, value, interpolator))
        );
        timeline.playFromStart();
    }

    /**
     * @return whether the current property change was fired by the animation or by an external source
     */
    private boolean isAnimationFrame(T oldPropertyValue, T newPropertyValue) {
        Object parallelValue = parallelProperty.getValue();
        return parallelValue != null && (parallelValue.equals(oldPropertyValue) || parallelValue.equals(newPropertyValue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimationSettings getSettings() {
        return property.getSettings();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A extends AnimationProperty<T>> A withSettings(AnimationSettings settings) {
        this.property.withSettings(settings);
        return (A) this;
    }

    /**
     * Registers the listener
     * @param target nullable target {@link Node}. If present, the animation is not played when it is not in scene.
     */
    public void register(Node target) {
        property.addListener(((observable, oldValue, newValue) -> {
            if(!isActive || (target != null && target.getScene() == null)) return;
            if(timeline.getStatus() != Animation.Status.RUNNING || !isAnimationFrame(oldValue, newValue)) {
                if(handleChanges ^= true) {
                    property.set(oldValue);
                    handleChanges(newValue);
                }
            }
        }));
    }

    /**
     * Registers the listener
     */
    public void register() {
        register(null);
    }

    /**
     * @return target property
     */
    public PropertyWrapper<T> getProperty() {
        return property;
    }

    /**
     * @return whether the property should be animated
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param active whether the property should be animated
     */
    public void setActive(boolean active) {
        isActive = active;
    }
}
