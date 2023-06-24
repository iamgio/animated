package eu.iamgio.animated.binding.property.animation;

import eu.iamgio.animated.binding.Animated;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.scene.Node;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A group of multiple {@link OnDemandAnimationProperty}.
 * @param <N> type of the JavaFX node to extract the properties from
 * @param <T> type of the wrapped values
 */
public class OnDemandAnimationPropertyGroup<N extends Node, T> extends OnDemandAnimationProperty<N, T> {

    private final List<Function<N, PropertyWrapper<T>>> propertyRetrievers;

    /**
     * Instantiates a new group of on-demand animation property.
     * @param propertyRetrievers list of function that generate wrapped JavaFX properties when applied to a node.
     */
    public OnDemandAnimationPropertyGroup(List<Function<N, PropertyWrapper<T>>> propertyRetrievers) {
        super(null);
        this.propertyRetrievers = propertyRetrievers;
    }

    private Stream<OnDemandAnimationProperty<N, T>> retrieveOnDemandProperties() {
        return this.propertyRetrievers.stream()
                .map(OnDemandAnimationProperty::new)
                .peek(property -> property.targetNodeProperty().bind(targetNodeProperty()))
                .peek(super::copyAttributesTo);
    }

    /**
     * {@inheritDoc}
     * All the sub-properties are registered.
     */
    @Override
    public void register(Node target) {
        this.retrieveOnDemandProperties().forEach(property -> property.register(target));
    }

    /**
     * {@inheritDoc}
     * All the sub-properties are applied to the target animated node.
     */
    @Override
    public void attachTo(Animated animated) {
        this.retrieveOnDemandProperties().forEach(property -> property.attachTo(animated));
    }
}
