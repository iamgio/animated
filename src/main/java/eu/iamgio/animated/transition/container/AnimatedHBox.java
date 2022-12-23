package eu.iamgio.animated.transition.container;

import animatefx.animation.AnimationFX;
import eu.iamgio.animated.binding.Curve;
import eu.iamgio.animated.transition.Animation;
import eu.iamgio.animated.transition.AnimationPair;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;

/**
 * An {@link HBox} with animated children.
 * @author Giorgio Garofalo
 */
public class AnimatedHBox extends HBox implements AnimatedContainer {

    private final Animation in;
    private final Animation out;
    private final SimpleBooleanProperty pausedProperty = new SimpleBooleanProperty(false);
    private final SimpleObjectProperty<Curve> relocationCurveProperty = new SimpleObjectProperty<>(Curve.EASE_IN_OUT);

    /**
     * Instantiates an {@link AnimatedHBox}. {@link Animation} wraps an {@link AnimationFX}, allowing customization.
     * @param animationIn non-null entrance animation
     * @param animationOut non-null exit animation
     */
    public AnimatedHBox(Animation animationIn, Animation animationOut) {
        this.in = Animation.requireNonNull(animationIn);
        this.out = Animation.requireNonNull(animationOut);
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
        return Direction.HORIZONTAL;
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
