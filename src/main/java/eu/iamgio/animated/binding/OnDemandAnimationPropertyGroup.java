package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.PropertyWrapper;
import javafx.scene.Node;

import java.util.List;
import java.util.function.Function;

/**
 *
 */
public class OnDemandAnimationPropertyGroup<N extends Node, T> extends OnDemandAnimationProperty<N, T> {

    private final List<Function<N, PropertyWrapper<T>>> propertyRetrievers;

    public OnDemandAnimationPropertyGroup(List<Function<N, PropertyWrapper<T>>> propertyRetrievers) {
        super(null);
        this.propertyRetrievers = propertyRetrievers;
    }

    @Override
    public void attachTo(NewAnimated animated) {
        for (Function<N, PropertyWrapper<T>> propertyRetriever : this.propertyRetrievers) {
            final OnDemandAnimationProperty<N, T> property = new OnDemandAnimationProperty<>(propertyRetriever);
            property.targetNodeProperty().bind(targetNodeProperty());
            property.pausedProperty().bindBidirectional(pausedProperty());
            property.withSettings(this.getSettings());
            property.attachTo(animated);
        }
    }
}
