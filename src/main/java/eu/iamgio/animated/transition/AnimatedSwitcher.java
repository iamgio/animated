package eu.iamgio.animated.transition;

import animatefx.animation.AnimationFX;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * This node plays a transition when its child node changes.
 * @author Giorgio Garofalo
 */
public class AnimatedSwitcher extends Parent {

    private final Animation in;
    private final Animation out;
    private final SimpleObjectProperty<Node> child = new SimpleObjectProperty<>();

    // Whether the handler has been set
    private boolean isHandlerRegistered;

    private void handleChanges(Node oldChild, Node newChild) {
        if(newChild != null) {
            in.playIn(newChild, getChildren());
        }
        if(oldChild != null) {
            out.playOut(oldChild, getChildren());
        }
    }

    /**
     * Registers the listener
     */
    private void registerHandler() {
        isHandlerRegistered = true;
        child.addListener((observable, oldChild, newChild) -> handleChanges(oldChild, newChild));
    }

    /**
     * Instantiates an {@link AnimatedSwitcher}. {@link Animation} wraps an {@link AnimationFX} allowing customization.
     * @param animationIn non-null entrance animation
     * @param animationOut non-null exit animation
     */
    public AnimatedSwitcher(Animation animationIn, Animation animationOut) {
        this.in = Animation.requireNonNull(animationIn);
        this.out = Animation.requireNonNull(animationOut);
    }

    /**
     * Instantiates an {@link AnimatedSwitcher}.
     * @param animationIn non-null entrance animation
     * @param animationOut non-null exit animation
     */
    public AnimatedSwitcher(AnimationFX animationIn, AnimationFX animationOut) {
        this(new Animation(animationIn), new Animation(animationOut));
    }

    /**
     * Instantiates an {@link AnimatedSwitcher}.
     * @param animation a pair of in and out animations
     */
    public AnimatedSwitcher(AnimationPair animation) {
        this(animation.getIn(), animation.getOut());
    }

    /**
     * Sets the initial child without playing the animation.
     * @param child initial child
     * @return this for concatenation
     * @throws IllegalAccessError if child is already set
     */
    public AnimatedSwitcher of(Node child) throws IllegalAccessError {
        if(getChild() != null) {
            throw new IllegalAccessError("Cannot use AnimatedSwitcher#of: child is already set. Use setChild instead.");
        }
        if(child != null) getChildren().add(child);

        this.child.set(child);
        if(!isHandlerRegistered) registerHandler();

        return this;
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
        if(!isHandlerRegistered) registerHandler();
        this.child.set(child);
    }
}
