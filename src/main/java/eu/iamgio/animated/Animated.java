package eu.iamgio.animated;

import eu.iamgio.animated.property.AnimatedProperty;
import eu.iamgio.animated.property.PropertyWrapper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * A node that automatically animates a property of its child.
 * @param <T> type of the target property
 * @author Giorgio Garofalo
 */
public class Animated<T> extends SingleChildParent {

    private final PropertyWrapper<T> property;

    // Animation timeline
    private final Timeline timeline;

    // Customizable animation properties
    private AnimationSettings settings;

    // Whether the changes should be handled
    private boolean handleChanges = false;
    // Whether the animation is playing
    private boolean isPlaying = false;

    /**
     * Plays the animation
     * @param newValue new property value
     */
    private void handleChanges(T newValue) {
        isPlaying = true;
        timeline.getKeyFrames().setAll(new KeyFrame(settings.getDuration(), new KeyValue(property.getProperty(), newValue)));
        timeline.setOnFinished(e -> isPlaying = false);
        timeline.playFromStart();
    }

    /**
     * Registers the listener
     */
    private void registerHandler() {
        property.addListener(((observable, oldValue, newValue) -> {
            if(!isPlaying) {
                handleChanges ^= true;
                if(handleChanges) {
                    property.set(oldValue);
                    handleChanges(newValue);
                }
            }
        }));
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
