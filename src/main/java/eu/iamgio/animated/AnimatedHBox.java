package eu.iamgio.animated;

import animatefx.animation.AnimationFX;
import eu.iamgio.animated.internal.AnimatedContainer;
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
     * @param in entrance animation
     * @param out exit animation
     */
    public AnimatedHBox(Animation in, Animation out) {
        this.in = in;
        this.out = out;
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
     * @param in entrance animation
     * @param out exit animation
     */
    public AnimatedHBox(AnimationFX in, AnimationFX out) {
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
