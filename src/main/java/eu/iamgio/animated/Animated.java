package eu.iamgio.animated;

import eu.iamgio.animated.property.PropertyWrapper;
import javafx.animation.Animation;
import javafx.animation.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

/**
 * A node that automatically animates a property of its child.
 * @param <T> type of the target property
 * @author Giorgio Garofalo
 */
public class Animated<T> extends SingleChildParent implements CustomizableAnimation<Animated<T>> {

    // The target property
    private final PropertyWrapper<T> property;

    // The parallel property is used to check if the changes are applied by the animation or by external sources
    private final ObjectProperty<T> parallelProperty;

    // Animation timeline
    private final Timeline timeline;

    // Whether the changes should be handled
    private boolean handleChanges = false;

    /**
     * Plays the animation
     * @param value new property value
     */
    private void handleChanges(T value) {
        AnimationSettings settings = property.getSettings();

        timeline.stop();

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
     * Registers the listener
     */
    private void registerHandler() {
        property.addListener(((observable, oldValue, newValue) -> {
            if(getScene() == null) return;
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
        this.property = property.withSettings(settings);
        this.parallelProperty = new SimpleObjectProperty<>();
        this.timeline = new Timeline();

        // Registers property listener
        registerHandler();
    }

    /**
     * Instantiates an {@link Animated} node
     * @param child initial child
     * @param property target property
     */
    public Animated(Node child, PropertyWrapper<T> property) {
        this(child, property, property.getSettings());
    }

    /**
     * Instantiates an {@link Animated} node
     * @param property target property
     */
    public Animated(PropertyWrapper<T> property) {
        this(null, property);
    }

    /**
     * @return target property
     */
    PropertyWrapper<T> getProperty() {
        return property;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimationSettings getSettings() {
        return property.getSettings();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends Animated<T>> A withSettings(AnimationSettings settings) {
        this.property.withSettings(settings);
        return (A) this;
    }
}
