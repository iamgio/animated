package eu.iamgio.animated.binding.property.animation;

import eu.iamgio.animated.binding.Animated;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * An on-demand animation property is a 'lazy evaluation' of an {@link AnimationProperty}:
 * its wrapped property is defined by a {@link Function} that is applied to the child
 * of the attached {@link Animated} node only when it changes.
 * Optionally, a constant target node can be specified instead of using the bound one from the animated node.
 * @param <N> type of the JavaFX node to extract the property from
 * @param <T> type of the wrapped value
 */
public class OnDemandAnimationProperty<N extends Node, T> extends AnimationProperty<T> implements BindableContextNode<N> {

    private final Function<N, PropertyWrapper<T>> propertyRetriever;
    private final ObjectProperty<N> targetNode;

    /**
     * Instantiates a new on-demand animation property.
     * @param propertyRetriever function that generates a wrapped JavaFX property when applied to a node.
     */
    public OnDemandAnimationProperty(Function<N, PropertyWrapper<T>> propertyRetriever) {
        super(null);
        this.propertyRetriever = propertyRetriever;
        this.targetNode = new SimpleObjectProperty<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<N> targetNodeProperty() {
        return this.targetNode;
    }

    /**
     * @return a new {@link AnimationProperty} that wraps the output of the function applied to the current target node.
     * @throws IllegalStateException if the target node is not set
     */
    private AnimationProperty<T> requestProperty() {
        if (targetNode.get() == null) {
            throw new IllegalStateException("The on-demand property was trying to access its status, " +
                    "but its target node is not set.");
        }

        final AnimationProperty<T> property = new SimpleAnimationProperty<>(propertyRetriever.apply(targetNode.get()));
        super.copyAttributesTo(property);
        return property;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(Node target) {
        final AnimationProperty<?> requested = requestProperty();
        requested.register(target != null ? target : targetNode.get());
    }

    /**
     * {@inheritDoc}
     * A hook is set up so that the child of the animated node generates
     * and registers a new {@link SimpleAnimationProperty} when it changes.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void attachTo(Animated animated) {
        if (targetNode.get() == null) {
            // Not a beautiful way to achieve this.
            // Casting is a workaround and should be handled better in the future.
            targetNode.bind((Property<N>) animated.childProperty());
        }

        // Whenever the wrapped child of an animated node changes,
        // the animation property is evaluated and registered.

        final InvalidationListener listener = o -> this.register(targetNode.get());

        // Calling the listener if a child is already present.
        if (animated.getChild() != null) {
            listener.invalidated(null);
        }

        animated.childProperty().addListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <V> AnimationProperty<T> addBinding(Property<V> targetProperty, Function<T, V> mapper) {
        requestProperty().addBinding(targetProperty, mapper);
        return this;
    }
}
