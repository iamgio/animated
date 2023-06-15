package eu.iamgio.animated.transition;

import animatefx.animation.AnimationFX;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * This node plays a transition when its child node changes.
 * @author Giorgio Garofalo
 */
public class AnimatedSwitcher extends Parent implements Pausable, EntranceAndExitAnimationCompatible {

    private final ObjectProperty<Animation> in;
    private final ObjectProperty<Animation> out;

    private final ObjectProperty<Node> child = new SimpleObjectProperty<>();
    private final BooleanProperty pausedProperty = new SimpleBooleanProperty(false);

    // Whether the handler has been set
    private boolean isHandlerRegistered;

    private void handleChanges(Node oldChild, Node newChild) {
        if (isPaused()) {
            getChildren().setAll(newChild);
            return;
        }
        if (newChild != null) {
            in.get().playIn(newChild, getChildren());
        }
        if (oldChild != null) {
            out.get().playOut(oldChild, getChildren());
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
        this.in = new SimpleObjectProperty<>(Animation.requireNonNull(animationIn));
        this.out = new SimpleObjectProperty<>(Animation.requireNonNull(animationOut));
    }

    /**
     * Instantiates an {@link AnimatedSwitcher}.
     * @param animation a pair of in and out animations
     */
    public AnimatedSwitcher(AnimationPair animation) {
        this(animation.getIn(), animation.getOut());
    }

    /**
     * Instantiates an {@link AnimatedSwitcher}.
     * @param animationIn non-null raw entrance animation
     * @param animationOut non-null raw exit animation
     */
    public AnimatedSwitcher(AnimationFX animationIn, AnimationFX animationOut) {
        this(new Animation(animationIn), new Animation(animationOut));
    }

    /**
     * Instantiates an {@link AnimatedSwitcher} with default animations.
     */
    public AnimatedSwitcher() {
        this(AnimationPair.fade());
    }

    /**
     * Sets the initial child without playing the animation.
     * @param child initial child
     * @return this for concatenation
     * @throws IllegalAccessError if child is already set
     */
    public AnimatedSwitcher of(Node child) throws IllegalAccessError {
        if (getChild() != null) {
            throw new IllegalAccessError("Cannot use AnimatedSwitcher#of: child is already set. Use setChild instead.");
        }
        if (child != null) {
            getChildren().add(child);
        }

        this.child.set(child);
        if (!isHandlerRegistered) {
            registerHandler();
        }

        return this;
    }

    /**
     * @return current child
     */
    public ObjectProperty<Node> childProperty() {
        return this.child;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<Animation> animationInProperty() {
        return this.in;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<Animation> animationOutProperty() {
        return this.out;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty pausedProperty() {
        return pausedProperty;
    }
}
