package eu.iamgio.animated.binding;

import eu.iamgio.animated.binding.property.animation.AnimationProperty;
import eu.iamgio.animated.transition.Pausable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * A node that automatically animates multiple properties related to its child.
 * @author Giorgio Garofalo
 */
public class Animated extends SingleChildParent implements CustomizableAnimation<Animated>, Pausable {

    private final ObservableList<AnimationProperty<?>> properties = FXCollections.observableArrayList();
    private final BooleanProperty paused = new SimpleBooleanProperty(false);

    /**
     * Instantiates a new {@link Animated} node without a child and with no target properties.
     */
    public Animated() {
        registerPropertyListeners();
    }

    /**
     * Instantiates a new {@link Animated} node.
     * @param child the target node that should be animated, to be wrapped by this {@link Animated} node
     * @param properties target properties that should be animated.
     *                   It is good practice to target properties that are related to this node's child
     * @see AnimationProperty#of(DoubleProperty)
     */
    public Animated(Node child, AnimationProperty<?>... properties) {
        super(child);
        registerPropertyListeners();
        this.properties.addAll(properties);
    }

    /**
     * Instantiates a new {@link Animated} node with no target properties.
     * @param child the target node that should be animated, to be wrapped by this {@link Animated} node
     */
    public Animated(Node child) {
        super(child);
        registerPropertyListeners();
    }

    /**
     * Instantiates a new {@link Animated} node without a child.
     * @param properties target properties that should be animated.
     *                   It is good practice to target properties that are related to this node's child
     * @see AnimationProperty#of(DoubleProperty)
     */
    public Animated(AnimationProperty<?>... properties) {
        registerPropertyListeners();
        this.properties.addAll(properties);
    }

    private void registerPropertyListeners() {
        properties.addListener((ListChangeListener<? super AnimationProperty<?>>) change -> {
            while (change.next()) {
                change.getAddedSubList().forEach(property -> {
                    property.attachTo(this);
                    property.pausedProperty().bindBidirectional(this.paused);
                });
                // TODO unbind on remove
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends Animated> A withSettings(AnimationSettings settings) {
        this.properties.forEach(property -> property.withSettings(settings));
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends Animated> A custom(Function<AnimationSettings, AnimationSettings> settings) {
        this.properties.forEach(property -> property.custom(settings));
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty pausedProperty() {
        return this.paused;
    }

    /**
     * @return a mutable list of the target properties that should be animated.
     *         It is good practice to target properties that are related to this node's child
     */
    public ObservableList<AnimationProperty<?>> getTargetProperties() {
        return this.properties;
    }
}
