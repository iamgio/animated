package eu.iamgio.animated;

import animatefx.animation.AnimationFX;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * This node plays a transition when its child node changes.
 * @author Giorgio Garofalo
 */
public class AnimatedSwitcher extends Parent {

    private final SwitchAnimation animation;
    private final SimpleObjectProperty<Node> child = new SimpleObjectProperty<>();

    // Whether the handler has been set
    private boolean isHandlerRegistered;

    private void handleChanges(Node oldChild, Node newChild) {
        if(newChild != null) {
            AnimationFX in = animation.getIn().getAnimationFX();
            if(in != null) {
                in.setNode(newChild);
                animation.getIn().applyProperties();
                in.play();
            }
            Platform.runLater(() -> getChildren().add(newChild));
        }
        if(oldChild != null) {
            AnimationFX out = animation.getOut().getAnimationFX();
            if(out != null) {
                out.setNode(oldChild);
                animation.getOut().applyProperties();
                out.setOnFinished(e -> getChildren().remove(oldChild));
                out.play();
            }
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
     * @param animationIn entrance animation
     * @param animationOut exit animation
     */
    public AnimatedSwitcher(Animation animationIn, Animation animationOut) {
        this.animation = new SwitchAnimation(animationIn, animationOut);
    }

    /**
     * Instantiates an {@link AnimatedSwitcher}.
     * @param animationIn entrance animation
     * @param animationOut exit animation
     */
    public AnimatedSwitcher(AnimationFX animationIn, AnimationFX animationOut) {
        this(new Animation(animationIn), new Animation(animationOut));
    }

    /**
     * Instantiates an {@link AnimatedSwitcher}.
     * @param animation a pair of in and out animations
     */
    public AnimatedSwitcher(SwitchAnimation animation) {
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