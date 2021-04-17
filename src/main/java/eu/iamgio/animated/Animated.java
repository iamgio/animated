package eu.iamgio.animated;

import eu.iamgio.animated.property.AnimatedProperty;
import eu.iamgio.animated.property.PropertyWrapper;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * A node that automatically animates a property of its child.
 * @param <T> type of the target property
 * @author Giorgio Garofalo
 */
public class Animated<T> extends SingleChildParent {

    // The target property
    private final PropertyWrapper<T> property;

    // The parallel property is used to check if the changes are applied by the animation or by external sources
    private final ObjectProperty<T> parallelProperty;

    // Animation timeline
    private final Timeline timeline;

    // Customizable animation properties
    private AnimationSettings settings;

    // Whether the changes should be handled
    private boolean handleChanges = false;

    /**
     * Plays the animation
     * @param value new property value
     */
    private void handleChanges(T value) {
        timeline.stop();

        // The parallel property is used to check if the changes are applied by the animation or by external sources
        parallelProperty.set(property.getProperty().getValue());

        timeline.getKeyFrames().setAll(
                new KeyFrame(settings.getDuration(), new KeyValue(property.getProperty(), value)),
                new KeyFrame(settings.getDuration(), new KeyValue(parallelProperty, value))
        );
        timeline.playFromStart();
    }

    /**
     * Registers the listener
     */
    private void registerHandler() {
        property.addListener(((observable, oldValue, newValue) -> {
            if(timeline.getStatus() != Animation.Status.RUNNING) {
                handleChanges ^= true;
                if(handleChanges) {
                    property.set(oldValue);
                    handleChanges(newValue);
                }
            } else if(!isAnimationFrame(oldValue, newValue)) {
                handleChanges ^= true;
                if(handleChanges) {
                    property.set(newValue);
                    handleChanges(oldValue);
                }
            }
        }));
    }

    /**
     * @return whether the current property change was fired by the animation or by an external source
     */
    private boolean isAnimationFrame(T oldPropertyValue, T newPropertyValue) {
        Object parallelValue = parallelProperty.getValue();
        return parallelValue != null && (parallelValue.equals(oldPropertyValue) || parallelValue.equals(newPropertyValue));
    }

    /**
     * Instantiates an {@link Animated} node
     * @param child initial child
     * @param property target property
     * @param settings animation settings
     */
    public Animated(Node child, PropertyWrapper<T> property, AnimationSettings settings) {
        super(child);
        this.property = property;
        this.parallelProperty = new SimpleObjectProperty<>();
        this.settings = settings;
        this.timeline = new Timeline();

        // Registers property listener
        registerHandler();
    }

    public Animated(Node child, AnimatedProperty<T> property) {
        this(child, property.getPropertyWrapper(), property.getSettings());
    }

    /**
     * Instantiates an {@link Animated} node
     * @param child initial child
     * @param property target property
     */
    public Animated(Node child, PropertyWrapper<T> property) {
        this(child, property, new AnimationSettings());
    }

    /**
     * Instantiates an {@link Animated} node
     * @param property target property
     */
    public Animated(PropertyWrapper<T> property) {
        this(null, property, new AnimationSettings());
    }

    /**
     * Applies custom animation settings
     * @param settings animation settings to set
     * @param <A> either {@link Animated} or subclass
     * @return this for concatenation
     */
    @SuppressWarnings("unchecked")
    public <A extends Animated<T>> A withSettings(AnimationSettings settings) {
        this.settings = settings;
        return (A) this;
    }

    /**
     * Applies custom animation settings
     * @param settings settings to update. Example: <pre>custom(settings -> settings.withDuration(...))</pre>
     * @param <A> either {@link Animated} or subclass
     * @return this for concatenation
     */
    public <A extends Animated<T>> A custom(Function<AnimationSettings, AnimationSettings> settings) {
        return withSettings(settings.apply(new AnimationSettings()));
    }
}
