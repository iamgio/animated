package eu.iamgio.animated;

import eu.iamgio.animated.property.PropertyWrapper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Duration;

/**
 * A node that automatically animates a property of its child.
 * @param <T> type of the target property
 * @author Giorgio Garofalo
 */
public class Animated<T> extends Parent {

    private final PropertyWrapper<T> property;
    private final SimpleObjectProperty<Node> child = new SimpleObjectProperty<>();

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
     * @param property target property
     * @param child initial child
     */
    public Animated(PropertyWrapper<T> property, Node child) {
        this.property = property;
        this.timeline = new Timeline();

        // Registers child listener
        this.child.addListener((observable, oldChild, newChild) -> {
            if(newChild != null) {
                getChildren().setAll(newChild);
            } else {
                getChildren().clear();
            }
        });
        this.child.set(child);

        // Registers property listener
        registerHandler();
    }

    /**
     * Instantiates an {@link Animated} node
     * @param property target property
     */
    public Animated(PropertyWrapper<T> property) {
        this(property, null);
    }

    /**
     * @return current child
     */
    public Node getChild() {
        return child.get();
    }

    /**
     * @param child child to set
     */
    public void setChild(Node child) {
        this.child.set(child);
    }
}
