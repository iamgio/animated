package eu.iamgio.animated.transition.container;

import animatefx.animation.AnimationFX;
import eu.iamgio.animated.binding.Curve;
import eu.iamgio.animated.transition.Animation;
import eu.iamgio.animated.transition.AnimationPair;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.VBox;

/**
 * A {@link VBox} with animated children.
 * @author Giorgio Garofalo
 */
public class AnimatedVBox extends VBox implements AnimatedContainer {

    private final Animation in;
    private final Animation out;
    private final SimpleBooleanProperty pausedProperty = new SimpleBooleanProperty(false);
    private final SimpleObjectProperty<Curve> relocationCurveProperty = new SimpleObjectProperty<>(Curve.EASE_IN_OUT);

    /**
     * Instantiates an {@link AnimatedVBox}. {@link Animation} wraps an {@link AnimationFX}, allowing customization.
     * @param animationIn non-null entrance animation
     * @param animationOut non-null exit animation
     */
    public AnimatedVBox(Animation animationIn, Animation animationOut) {
        this.in = Animation.requireNonNull(animationIn);
        this.out = Animation.requireNonNull(animationOut);
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
    public Animation getIn() {
        return in;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Animation getOut() {
        return out;
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
    public SimpleBooleanProperty pausedProperty() {
        return pausedProperty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SimpleObjectProperty<Curve> relocationCurveProperty() {
        return relocationCurveProperty;
    }
}
