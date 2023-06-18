package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.PropertyWrapper;
import eu.iamgio.animated.transition.Pausable;
import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * Animation handler for a JavaFX property wrapped inside a {@link PropertyWrapper}.
 * @author Giorgio Garofalo
 */
public class AnimationProperty<T> implements CustomizableAnimation<AnimationProperty<T>>, Pausable {

    // The target property
    private final PropertyWrapper<T> property;

    // Animation timeline
    private final Timeline timeline;

    // Whether the property should be animated
    private final BooleanProperty paused = new SimpleBooleanProperty(false);

    // Last time an animation frame was played (in millis)
    private double lastUpdate;

    // Last value the timeline changed
    private T lastValue;

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
     * @return the current animation settings
     */
    public AnimationSettings getSettings() {
        return property.getSettings();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <A extends AnimationProperty<T>> A withSettings(AnimationSettings settings) {
        this.property.withSettings(settings);
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    public <A extends AnimationProperty<T>> A custom(Function<AnimationSettings, AnimationSettings> settings) {
        return withSettings(settings.apply(getSettings()));
    }

    /**
     * Registers the listener
     * @param target nullable target {@link Node}. If present, the animation is not played when it is not in scene.
     */
    public void register(Node target) {
        property.addListener(((observable, oldValue, newValue) -> {
            if (isPaused() || (target != null && target.getScene() == null)) {
                return;
            }
            if (timeline.getStatus() != Animation.Status.RUNNING || !isAnimationFrame(oldValue, newValue)) {
                if (handleChanges ^= true) {
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
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty pausedProperty() {
        return this.paused;
    }
}
