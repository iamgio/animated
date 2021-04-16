package eu.iamgio.animated;

import eu.iamgio.animated.property.PropertyWrapper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * A node that automatically animates a property of its child.
 * @param <T> type of the target property
 * @author Giorgio Garofalo
 */
public class Animated<T> extends SingleChildParent {

    private final PropertyWrapper<T> property;

    // Animation timeline
    private final Timeline timeline;

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
        timeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(1), new KeyValue(property.getProperty(), newValue)));
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
     */
    public Animated(Node child, PropertyWrapper<T> property) {
        super(child);
        this.property = property;
        this.timeline = new Timeline();

        // Registers property listener
        registerHandler();
    }

    /**
     * Instantiates an {@link Animated} node
     * @param property target property
     */
    public Animated(PropertyWrapper<T> property) {
        this(null, property);
    }
}
