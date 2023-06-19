package eu.iamgio.animated.binding.property.animation;

import eu.iamgio.animated.binding.NewAnimated;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.scene.Node;

import java.util.List;
import java.util.function.Function;

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

    /**
     * {@inheritDoc}
     * @implNote all the sub-properties are applied to the target animated node
     */
    @Override
    public void attachTo(NewAnimated animated) {
        for (Function<N, PropertyWrapper<T>> propertyRetriever : this.propertyRetrievers) {
            final OnDemandAnimationProperty<N, T> property = new OnDemandAnimationProperty<>(propertyRetriever);
            property.targetNodeProperty().bind(targetNodeProperty());
            super.copyAttributesTo(property);
            property.attachTo(animated);
        }
    }
}
