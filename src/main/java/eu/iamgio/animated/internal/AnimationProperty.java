package eu.iamgio.animated.internal;

import eu.iamgio.animated.AnimationSettings;
import eu.iamgio.animated.property.PropertyWrapper;
import javafx.animation.*;
import javafx.scene.Node;

/**
 * Class that animates a JavaFX property wrapped inside of a {@link PropertyWrapper}.
 * @author Giorgio Garofalo
 */
public class AnimationProperty<T> implements CustomizableAnimation<AnimationProperty<T>> {

    // The target property
    private final PropertyWrapper<T> property;

    // Animation timeline
    private final Timeline timeline;

    // Last time an animation frame was played (in millis)
    private double lastUpdate;

    // Last value the timeline changed
    private T lastValue;

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
        this.timeline = new Timeline();

        timeline.currentTimeProperty().addListener(o -> {
            lastUpdate = timeline.getCurrentTime().toMillis();
            lastValue = property.getValue();
        });
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
        // Temporarily stop the timeline in case it is currently running
        timeline.stop();

        // Retrieve settings
        AnimationSettings settings = property.getSettings();
        Interpolator interpolator = settings.getCurve().toInterpolator();

        // Set keyframes
        timeline.getKeyFrames().setAll(
                new KeyFrame(settings.getDuration(), new KeyValue(property.getProperty(), value, interpolator))
        );

        // Play the animation
        timeline.play();
    }

    /**
     * @return whether the current property change was fired by the animation or by an external source
     */
    private boolean isAnimationFrame(T oldPropertyValue, T newPropertyValue) {
        return timeline.getCurrentTime().toMillis() == lastUpdate && (newPropertyValue.equals(lastValue) || oldPropertyValue.equals(lastValue));
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
