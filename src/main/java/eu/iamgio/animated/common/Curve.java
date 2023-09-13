package eu.iamgio.animated.common;

import eu.iamgio.animated.binding.AnimationSettings;
import javafx.animation.Interpolator;

import java.util.function.Function;

import static java.lang.Math.*;

/**
 * A group of curves that affect the interpolation of an animation.
 * @author Giorgio Garofalo
 * @see AnimationSettings#withCurve(Curve)
 */
public enum Curve {

    /**
     * A curve that keeps the same value throughout its execution.
     */
    LINEAR(t -> t),


    //
    // EASE OUT CURVES
    //


    /**
     * @see <a href="https://easings.net/#easeOutCubic">on easings.net</a>
     */
    EASE_OUT(t -> 1 - (1 - t) * (1 - t) * (1 - t)),

    /**
     * @see <a href="https://easings.net/#easeOutSine">on easings.net</a>
     */
    EASE_OUT_SINE(t -> sin((t * PI) / 2)),

    /**
     * @see <a href="https://easings.net/#easeOutExpo">on easings.net</a>
     */
    EASE_OUT_EXPO(t -> t == 1 ? 1 : 1 - pow(2, -10 * t)),

    /**
     * @see <a href="https://easings.net/#easeOutBack">on easings.net</a>
     */
    EASE_OUT_BACK(t -> {
        double c1 = 1.70158;
        double c3 = c1 + 1;

        return 1 + c3 * pow(t - 1, 3) + c1 * pow(t - 1, 2);
    }),

    /**
     * @see <a href="https://easings.net/#easeOutBounce">on easings.net</a>
     */
    EASE_OUT_BOUNCE(t -> {
        double n = 7.5625;
        double d = 2.75;
        if (t < 1 / d) {
            return n * t * t;
        } else if (t < 2 / d) {
            return n * (t -= 1.5 / d) * t + 0.75;
        } else if (t < 2.5 / d) {
            return n * (t -= 2.25 / d) * t + 0.9375;
        } else {
            return n * (t -= 2.625 / d) * t + 0.984375;
        }
    }),

    /**
     * @see <a href="https://easings.net/#easeOutElastic">on easings.net</a>
     */
    EASE_OUT_ELASTIC(t -> {
        double c = (2 * Math.PI) / 3;

        return t == 0
                ? 0
                : t == 1
                ? 1
                : pow(2, -10 * t) * sin((t * 10 - 0.75) * c) + 1;
    }),


    //
    // EASE IN CURVES
    //


    /**
     * @see <a href="https://easings.net/#easeInCubic">on easings.net</a>
     */
    EASE_IN(t -> t * t * t),

    /**
     * @see <a href="https://easings.net/#easeInSine">on easings.net</a>
     */
    EASE_IN_SINE(t -> 1 - cos((t * PI) / 2)),

    /**
     * @see <a href="https://easings.net/#easeInExpo">on easings.net</a>
     */
    EASE_IN_EXPO(t -> t == 0 ? 0 : pow(2, 10 * t - 10)),

    /**
     * @see <a href="https://easings.net/#easeInBack">on easings.net</a>
     */
    EASE_IN_BACK(t -> {
        double c1 = 1.70158;
        double c3 = c1 + 1;

        return c3 * t * t * t - c1 * t * t;
    }),

    /**
     * @see <a href="https://easings.net/#easeInBounce">on easings.net</a>
     */
    EASE_IN_BOUNCE(t -> 1 - EASE_OUT_BOUNCE.curve.apply(1 - t)),

    /**
     * @see <a href="https://easings.net/#easeInElastic">on easings.net</a>
     */
    EASE_IN_ELASTIC(t -> {
        double c = (2 * PI) / 3;

        return t == 0
                ? 0
                : t == 1
                ? 1
                : -pow(2, 10 * t - 10) * sin((t * 10 - 10.75) * c);
    }),


    //
    // EASE IN OUT CURVES
    //


    /**
     * @see <a href="https://easings.net/#easeInOutCubic">on easings.net</a>
     */
    EASE_IN_OUT(t -> t < 0.5 ? 4 * t * t * t : 1 - pow(-2 * t + 2, 3) / 2),

    /**
     * @see <a href="https://easings.net/#easeInOutSine">on easings.net</a>
     */
    EASE_IN_OUT_SINE(t -> -(cos(PI * t) - 1) / 2),

    /**
     * @see <a href="https://easings.net/#easeInOutExpo">on easings.net</a>
     */
    EASE_IN_OUT_EXPO(t -> t == 0 ? 0 : t == 1 ? 1 : t < 0.5 ? pow(2, 20 * t - 10) / 2 : (2 - pow(2, -20 * t + 10)) / 2),

    /**
     * @see <a href="https://easings.net/#easeInOutBack">on easings.net</a>
     */
    EASE_IN_OUT_BACK(t -> {
        double c1 = 1.70158;
        double c2 = c1 * 1.525;

        return t < 0.5 ? (pow(2 * t, 2) * ((c2 + 1) * 2 * t - c2)) / 2 : (pow(2 * t - 2,
                2) * ((c2 + 1) * (t * 2 - 2) + c2) + 2) / 2;
    }),

    /**
     * @see <a href="https://easings.net/#easeInOutBounce">on easings.net</a>
     */
    EASE_IN_OUT_BOUNCE(t -> t < 0.5
            ? (1 - EASE_OUT_BOUNCE.curve.apply(1 - 2 * t)) / 2
            : (1 + EASE_IN_BOUNCE.curve.apply(2 * t - 1)) / 2),

    /**
     * @see <a href="https://easings.net/#easeInOutElastic">on easings.net</a>
     */
    EASE_IN_OUT_ELASTIC(t -> {
        double c = (2 * PI) / 4.5;
        double sin = sin((20 * t - 11.125) * c);

        return t == 0
                ? 0
                : t == 1
                ? 1
                : t < 0.5
                ? -(pow(2, 20 * t - 10) * sin) / 2
                : (pow(2, -20 * t + 10) * sin) / 2 + 1;
    });


    //
    // END OF CURVES
    //

    private final Function<Double, Double> curve;

    Curve(Function<Double, Double> curve) {
        this.curve = curve;
    }

    /**
     * @return the function that describes this curve
     */
    public Function<Double, Double> getCurveFunction() {
        return curve;
    }

    /**
     * @return this curve to a JavaFX timeline interpolator
     */
    public Interpolator toInterpolator() {
        return new Interpolator() {
            @Override
            protected double curve(double t) {
                return curve.apply(t);
            }
        };
    }
}
