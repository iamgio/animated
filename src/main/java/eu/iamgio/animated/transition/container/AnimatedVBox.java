package eu.iamgio.animated.transition.container;

import animatefx.animation.AnimationFX;
import eu.iamgio.animated.common.Curve;
import eu.iamgio.animated.transition.Animation;
import eu.iamgio.animated.transition.AnimationPair;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.VBox;

/**
 * A {@link VBox} with animated children.
 * @author Giorgio Garofalo
 */
public class AnimatedVBox extends VBox implements AnimatedContainer {

    private final ObjectProperty<Animation> in;
    private final ObjectProperty<Animation> out;

    private final BooleanProperty pausedProperty = new SimpleBooleanProperty(false);
    private final ObjectProperty<Curve> relocationCurveProperty = new SimpleObjectProperty<>(Curve.EASE_IN_OUT);

    /**
     * Instantiates an {@link AnimatedVBox}. {@link Animation} wraps an {@link AnimationFX}, allowing customization.
     * @param animationIn non-null entrance animation
     * @param animationOut non-null exit animation
     */
    public AnimatedVBox(Animation animationIn, Animation animationOut) {
        this.in = new SimpleObjectProperty<>(Animation.requireNonNull(animationIn));
        this.out = new SimpleObjectProperty<>(Animation.requireNonNull(animationOut));
        register();
    }

    /**
     * Instantiates an {@link AnimatedVBox}.
     * @param animation a pair of in and out animations
     */
    public AnimatedVBox(AnimationPair animation) {
        this(animation.getIn(), animation.getOut());
    }

    /**
     * Instantiates an {@link AnimatedVBox}.
     * @param in entrance animation
     * @param out exit animation
     */
    public AnimatedVBox(AnimationFX in, AnimationFX out) {
        this(new Animation(in), new Animation(out));
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
        return Direction.VERTICAL;
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
