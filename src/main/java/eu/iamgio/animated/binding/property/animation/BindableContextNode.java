package eu.iamgio.animated.binding.property.animation;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

/**
 * A container for a writable node property.
 */
public interface BindableContextNode<N extends Node> {

    /**
     * @return the target node for the context
     */
    ObjectProperty<N> targetNodeProperty();
}
