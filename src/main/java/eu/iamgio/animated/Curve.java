package eu.iamgio.animated;

import javafx.animation.Interpolator;

import java.util.function.Function;

import static java.lang.Math.*;

/**
 * @author Giorgio Garofalo
 */
public enum Curve {

    LINEAR          (t -> t),
    EASE_IN         (t -> t * t * t),
    EASE_IN_SINE    (t -> 1 - cos((t * PI) / 2)),
    EASE_OUT        (t -> 1 - (1 - t) * (1 - t) * (1 - t)),
    EASE_OUT_SINE   (t -> sin((t * PI) / 2)),
    EASE_IN_OUT     (t -> t < 0.5 ? 4 * t * t * t : 1 - pow(-2 * t + 2, 3) / 2),
    EASE_IN_OUT_SINE(t -> -(cos(PI * t) - 1) / 2);

    private final Function<Double, Double> curve;

    Curve(Function<Double, Double> curve) {
        this.curve = curve;
    }

    public Interpolator toInterpolator() {
        return new Interpolator() {
            @Override
            protected double curve(double t) {
                return curve.apply(t);
            }
        };
    }


}
