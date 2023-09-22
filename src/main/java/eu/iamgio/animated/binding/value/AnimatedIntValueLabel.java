package eu.iamgio.animated.binding.value;

import javafx.beans.NamedArg;

/**
 * An {@link AnimatedValueLabel} that wraps an integer value.
 */
public class AnimatedIntValueLabel extends AnimatedValueLabel<Integer> {

    /**
     * Instantiates an {@link AnimatedIntValueLabel}.
     * @param value initial wrapped value
     */
    public AnimatedIntValueLabel(@NamedArg("value") int value) {
        super(value);
    }
}
