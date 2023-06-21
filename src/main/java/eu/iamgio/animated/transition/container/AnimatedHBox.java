package eu.iamgio.animated.transition.container;

import animatefx.animation.AnimationFX;
import eu.iamgio.animated.binding.Curve;
import eu.iamgio.animated.transition.Animation;
import eu.iamgio.animated.transition.AnimationPair;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;

/**
 * An {@link HBox} with animated children.
 * @author Giorgio Garofalo
 */
public class AnimatedHBox extends HBox implements AnimatedContainer {

    private final ObjectProperty<Animation> in;
    private final ObjectProperty<Animation> out;

    private final BooleanProperty pausedProperty = new SimpleBooleanProperty(false);
    private final ObjectProperty<Curve> relocationCurveProperty = new SimpleObjectProperty<>(Curve.EASE_IN_OUT);

    /**
     * Instantiates an {@link AnimatedHBox}. {@link Animation} wraps an {@link AnimationFX}, allowing customization.
     * @param animationIn non-null entrance animation
     * @param animationOut non-null exit animation
     */
    public AnimatedHBox(Animation animationIn, Animation animationOut) {
        this.in = new SimpleObjectProperty<>(Animation.requireNonNull(animationIn));
        this.out = new SimpleObjectProperty<>(Animation.requireNonNull(animationOut));
        register();
    }

    /**
     * Instantiates an {@link AnimatedHBox}.
     * @param animation a pair of in and out animations
     */
    public AnimatedHBox(AnimationPair animation) {
        this(animation.getIn(), animation.getOut());
    }

    /**
     * Instantiates an {@link AnimatedHBox}.
     * @param animationIn entrance animation
     * @param animationOut exit animation
     */
    public AnimatedHBox(AnimationFX animationIn, AnimationFX animationOut) {
        this(new Animation(animationIn), new Animation(animationOut));
    }

    /**
     * Instantiates an {@link AnimatedHBox} with default animations.
     */
    public AnimatedHBox() {
        this(AnimationPair.fade());
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
    public Animation getIn() {
        // This overriden method is required for FXML compatibility.
        return AnimatedContainer.super.getIn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIn(Animation in) {
        // This overriden method is required for FXML compatibility.
        AnimatedContainer.super.setIn(in);
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
    public Animation getOut() {
        // This overriden method is required for FXML compatibility.
        return AnimatedContainer.super.getOut();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOut(Animation out) {
        // This overriden method is required for FXML compatibility.
        AnimatedContainer.super.setOut(out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getDirection() {
        return Direction.HORIZONTAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty pausedProperty() {
        return pausedProperty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<Curve> relocationCurveProperty() {
        return relocationCurveProperty;
    }
}
