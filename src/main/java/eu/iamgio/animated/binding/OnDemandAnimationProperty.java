package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.PropertyWrapper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

import java.util.function.Function;

/**
 *
 */
public abstract class OnDemandAnimationProperty<T> extends AnimationProperty<T> implements BindableContextNode {

    private final Function<Node, PropertyWrapper<T>> propertyRetriever;
    private final ObjectProperty<Node> targetNode;

    public OnDemandAnimationProperty(Function<Node, PropertyWrapper<T>> propertyRetriever) {
        super(null);
        this.propertyRetriever = propertyRetriever;
        this.targetNode = new SimpleObjectProperty<>();
    }

    @Override
    public ObjectProperty<Node> targetNodeProperty() {
        return this.targetNode;
    }

    public AnimationProperty<T> requestProperty() {
        final AnimationProperty<T> property = new AnimationProperty<>(propertyRetriever.apply(targetNode.get()));
        property.pausedProperty().bindBidirectional(pausedProperty());
        return property.withSettings(this.getSettings());
    }

    @Override
    public void register(Node target) {

    }
}
