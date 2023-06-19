package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.PropertyWrapper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;

import java.util.function.Function;

/**
 *
 */
public class OnDemandAnimationProperty<N extends Node, T> extends AnimationProperty<T> implements BindableContextNode<N> {

    private final Function<N, PropertyWrapper<T>> propertyRetriever;
    private final ObjectProperty<N> targetNode;

    public OnDemandAnimationProperty(Function<N, PropertyWrapper<T>> propertyRetriever) {
        super(null);
        this.propertyRetriever = propertyRetriever;
        this.targetNode = new SimpleObjectProperty<>();
    }

    @Override
    public ObjectProperty<N> targetNodeProperty() {
        return this.targetNode;
    }

    public AnimationProperty<T> requestProperty() {
        final AnimationProperty<T> property = new AnimationProperty<>(propertyRetriever.apply(targetNode.get()));
        super.copyAttributesTo(property);
        return property;
    }

    @Override
    public void register(Node target) {

    }

    @SuppressWarnings("unchecked")
    public void attachTo(NewAnimated animated) {
        if (targetNode.get() == null) {
            // Not a beautiful way to achieve this.
            // Casting is a workaround and should be handled better in the future.
            targetNode.bind((Property<N>) animated.childProperty());
        }

        final ChangeListener<Node> listener = (observable, oldChild, newChild) -> {
            final AnimationProperty<?> requested = requestProperty();
            requested.register(targetNode.get());
        };

        if (animated.getChild() != null) {
            listener.changed(null, null, targetNode.get());
        }

        animated.childProperty().addListener(listener);
    }
}
