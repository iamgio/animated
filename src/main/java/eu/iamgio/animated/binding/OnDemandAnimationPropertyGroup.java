package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.PropertyWrapper;
import javafx.scene.Node;

import java.util.List;
import java.util.function.Function;

/**
 *
 */
public class OnDemandAnimationPropertyGroup<T> extends OnDemandAnimationProperty<T> {

    private final List<Function<Node, PropertyWrapper<T>>> propertyRetrievers;

    public OnDemandAnimationPropertyGroup(List<Function<Node, PropertyWrapper<T>>> propertyRetrievers) {
        super(null);
        this.propertyRetrievers = propertyRetrievers;
    }

    @Override
    public void attachTo(NewAnimated animated) {
        for (Function<Node, PropertyWrapper<T>> propertyRetriever : this.propertyRetrievers) {
            final OnDemandAnimationProperty<T> property = new OnDemandAnimationProperty<>(propertyRetriever);
            property.targetNodeProperty().bind(targetNodeProperty());
            property.pausedProperty().bindBidirectional(pausedProperty());
            property.attachTo(animated);
        }
    }
}
