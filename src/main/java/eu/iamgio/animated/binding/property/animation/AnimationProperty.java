package eu.iamgio.animated.binding.property.animation;

import eu.iamgio.animated.binding.Animated;
import eu.iamgio.animated.binding.AnimationSettings;
import eu.iamgio.animated.binding.CustomizableAnimation;
import eu.iamgio.animated.binding.event.AnimationEvent;
import eu.iamgio.animated.binding.event.ListenableAnimation;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import eu.iamgio.animated.common.Pausable;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * An animation property wraps a JavaFX property and describes its behavior when it is affected by a change.
 * The property has to be wrapped inside a {@link PropertyWrapper}.
 * @param <T> type of the wrapped value
 * @author Giorgio Garofalo
 */
public abstract class AnimationProperty<T> implements CustomizableAnimation<AnimationProperty<T>>, ListenableAnimation, Pausable {

    // The target property
    private final PropertyWrapper<T> property;

    // Whether the property should be animated
    private final BooleanProperty paused = new SimpleBooleanProperty(false);

    // Animation settings
    private final ObjectProperty<AnimationSettings> settings;

    // Event handlers
    private final ObjectProperty<EventHandler<AnimationEvent>> onAnimationStarted = new SimpleObjectProperty<>();
    private final ObjectProperty<EventHandler<AnimationEvent>> onAnimationEnded = new SimpleObjectProperty<>();

    /**
     * Instantiates an implicitly animated property
     * @param property target property
     * @param settings animation settings
     */
    public AnimationProperty(PropertyWrapper<T> property, AnimationSettings settings) {
        this.property = property;
        this.settings = new SimpleObjectProperty<>(settings);
    }

    /**
     * Instantiates an implicitly animated property with default settings.
     * @param property target property
     */
    public AnimationProperty(PropertyWrapper<T> property) {
        this(property, new AnimationSettings());
    }

    /**
     * @return the current animation settings
     */
    public ObjectProperty<AnimationSettings> settingsProperty() {
        return this.settings;
    }

    /**
     * @return the current animation settings
     */
    public AnimationSettings getSettings() {
        return this.settings.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSettings(AnimationSettings settings) {
        this.settings.set(settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimationProperty<T> custom(Function<AnimationSettings, AnimationSettings> settings) {
        return withSettings(settings.apply(getSettings()));
    }

    /**
     * Registers the listener
     * @param target nullable target {@link Node}. If present, the animation is not played when it is not in scene.
     */
    public abstract void register(Node target);

    /**
     * Registers the listener
     */
    public void register() {
        register(null);
    }

    /**
     * Attaches this property to an {@link Animated} node.
     * @param animated animated node to link this property to
     */
    public abstract void attachTo(Animated animated);

    /**
     * Adds a binding to a target property: when the value of the wrapped property changes,
     * the target property is updated too, based on a mapper function.
     * The following example binds a label text to this property, converted to a string:
     * <pre>
     *     property.addBinding(label.textProperty(), String::valueOf);
     * </pre>
     *
     * @param targetProperty property to bind
     * @param mapper function that takes the value of the wrapped property as input,
     *               and returns the value the target property should get
     * @return this for concatenation
     * @param <V> type of the target property
     */
    public abstract <V> AnimationProperty<T> addBinding(Property<V> targetProperty, Function<T, V> mapper);

    /**
     * Adds a binding to a target property: when the value of the wrapped property changes,
     * the target property is updated too.
     * @param targetProperty property to bind
     * @return this for concatenation
     */
    public AnimationProperty<T> addBinding(Property<T> targetProperty) {
        return this.addBinding(targetProperty, Function.identity());
    }

    /**
     * @return target property
     */
    public PropertyWrapper<T> getProperty() {
        return this.property;
    }

    /**
     * Fluent setter for {@link ListenableAnimation#setOnAnimationStarted(EventHandler)}
     * @param handler the action to run when an animation begins
     * @return this for concatenation
     */
    public AnimationProperty<T> onAnimationStarted(EventHandler<AnimationEvent> handler) {
        setOnAnimationStarted(handler);
        return this;
    }

    /**
     * Fluent setter for {@link ListenableAnimation#setOnAnimationEnded(EventHandler)}
     * @param handler the action to run when an animation finishes
     * @return this for concatenation
     */
    public AnimationProperty<T> onAnimationEnded(EventHandler<AnimationEvent> handler) {
        setOnAnimationEnded(handler);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty pausedProperty() {
        return this.paused;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<EventHandler<AnimationEvent>> onAnimationStartedProperty() {
        return this.onAnimationStarted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<EventHandler<AnimationEvent>> onAnimationEndedProperty() {
        return this.onAnimationEnded;
    }

    /**
     * Copies settings and other attributes (pause status) from this property to another (and overrides existing values).
     * @param to property to copy attributes to
     */
    void copyAttributesTo(AnimationProperty<?> to) {
        to.pausedProperty().bindBidirectional(pausedProperty());

        to.onAnimationStartedProperty().bindBidirectional(onAnimationStartedProperty());
        to.onAnimationEndedProperty().bindBidirectional(onAnimationEndedProperty());

        to.withSettings(this.getSettings());
    }

    /**
     * Creates an {@link AnimationProperty} that wraps the given {@link ObjectProperty}.
     * @see eu.iamgio.animated.binding.property.wrapper.ObjectPropertyWrapper
     * @param property JavaFX property to wrap
     * @param <T> property type
     * @return instance of a new animation property that wraps <tt>property</tt>.
     */
    public static <T> AnimationProperty<T> of(ObjectProperty<T> property) {
        return new SimpleAnimationProperty<>(PropertyWrapper.of(property));
    }

    /**
     * Creates an {@link AnimationProperty} that wraps the given {@link DoubleProperty}.
     * @see eu.iamgio.animated.binding.property.wrapper.DoublePropertyWrapper
     * @param property JavaFX property to wrap
     * @return instance of a new animation property that wraps <tt>property</tt>.
     */
    public static AnimationProperty<Double> of(DoubleProperty property) {
        return new SimpleAnimationProperty<>(PropertyWrapper.of(property));
    }

    /**
     * Creates an {@link AnimationProperty} that wraps the given {@link IntegerProperty}.
     * @see eu.iamgio.animated.binding.property.wrapper.IntegerPropertyWrapper
     * @param property JavaFX property to wrap
     * @return instance of a new animation property that wraps <tt>property</tt>.
     */
    public static AnimationProperty<Integer> of(IntegerProperty property) {
        return new SimpleAnimationProperty<>(PropertyWrapper.of(property));
    }
}
