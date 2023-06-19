package eu.iamgio.animated.binding;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

/**
 *
 */
public interface BindableContextNode<N extends Node> {

    ObjectProperty<N> targetNodeProperty();
}
