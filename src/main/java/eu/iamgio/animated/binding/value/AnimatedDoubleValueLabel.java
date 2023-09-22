package eu.iamgio.animated.binding.value;

import javafx.beans.NamedArg;

/**
 * An {@link AnimatedValueLabel} that wraps a double value.
 */
public class AnimatedDoubleValueLabel extends AnimatedValueLabel<Double> {

    /**
     * Instantiates an {@link AnimatedDoubleValueLabel}.
     * @param value initial wrapped value
     */
    public AnimatedDoubleValueLabel(@NamedArg("value") double value) {
        super(value);
    }
}
