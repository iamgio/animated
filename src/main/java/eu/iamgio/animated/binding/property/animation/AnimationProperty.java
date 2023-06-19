package eu.iamgio.animated.binding.property.animation;

import eu.iamgio.animated.binding.AnimationSettings;
import eu.iamgio.animated.binding.CustomizableAnimation;
import eu.iamgio.animated.binding.NewAnimated;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import eu.iamgio.animated.transition.Pausable;
import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
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

    // Animation settings
    private AnimationSettings settings;

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
        this.property = property;
        this.settings = settings;
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
        this(property, new AnimationSettings());
    }

    /**
     * Plays the animation
     * @param value new property value
     */
    private void handleChanges(T value) {
        // Temporarily stop the timeline in case it is currently running
        timeline.stop();

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
        return this.settings;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <A extends AnimationProperty<T>> A withSettings(AnimationSettings settings) {
        this.settings = settings;
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
     * Attaches this property to an {@link NewAnimated} node.
     * @param animated animated node to link this property to
     */
    public void attachTo(NewAnimated animated) {
        this.register(animated.getChild());
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

    /**
     * Copies settings and other attributes (pause status) from this property to another (and overrides existing values).
     * @param to property to copy attributes to
     */
    void copyAttributesTo(AnimationProperty<?> to) {
        to.pausedProperty().bindBidirectional(pausedProperty());
        to.withSettings(this.getSettings());
    }

    /**
     * Creates an {@link AnimationProperty} that wraps the given {@link ObjectProperty}.
     * @see eu.iamgio.animated.binding.property
     * @param property JavaFX property to wrap
     * @param <T> property type
     * @return instance of a new animation property that wraps <tt>property</tt>.
     */
    public static <T> AnimationProperty<T> of(ObjectProperty<T> property) {
        return new AnimationProperty<>(PropertyWrapper.of(property));
    }

    /**
     * Creates an {@link AnimationProperty} that wraps the given {@link DoubleProperty}.
     * @see eu.iamgio.animated.binding.property
     * @param property JavaFX property to wrap
     * @return instance of a new animation property that wraps <tt>property</tt>.
     */
    public static AnimationProperty<Double> of(DoubleProperty property) {
        return new AnimationProperty<>(PropertyWrapper.of(property));
    }
}
