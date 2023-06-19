package eu.iamgio.animated.binding;

import eu.iamgio.animated.transition.Pausable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.function.Function;

/**
 * A node that automatically animates multiple properties related to its child.
 * @author Giorgio Garofalo
 */
public class NewAnimated extends SingleChildParent implements CustomizableAnimation<NewAnimated>, Pausable {

    private final ObservableList<AnimationProperty<?>> properties = FXCollections.observableArrayList();
    private final BooleanProperty paused = new SimpleBooleanProperty(false);

    public NewAnimated() {
        registerPropertyListeners();
    }

    public NewAnimated(Node child, AnimationProperty<?>... properties) {
        super(child);
        registerPropertyListeners();
        this.properties.addAll(properties);
    }

    public NewAnimated(Node child) {
        super(child);
        registerPropertyListeners();
    }

    public NewAnimated(AnimationProperty<?>... properties) {
        registerPropertyListeners();
        this.properties.addAll(properties);
    }

    private void registerPropertyListeners() {
        properties.addListener((ListChangeListener<? super AnimationProperty<?>>) change -> {
            while (change.next()) {
                change.getAddedSubList().forEach(property -> {
                    if (property instanceof OnDemandAnimationProperty) {
                        handleOnDemandProperty((OnDemandAnimationProperty<?>) property);
                    }
                    property.register(getChild());
                    property.pausedProperty().bind(this.paused);
                });
                // TODO unbind on remove
            }
        });
    }

    private void handleOnDemandProperty(OnDemandAnimationProperty<?> property) {
        if (property.targetNodeProperty().get() == null) {
            property.targetNodeProperty().bind(childProperty());
        }

        final ChangeListener<Node> listener = (observable, oldChild, newChild) -> {
            final AnimationProperty<?> requested = property.requestProperty();
            requested.register(property.targetNodeProperty().get());
        };

        if (getChild() != null) {
            listener.changed(null, null, property.targetNodeProperty().get());
        }

        this.child.addListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends NewAnimated> A withSettings(AnimationSettings settings) {
        this.properties.forEach(property -> property.withSettings(settings));
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends NewAnimated> A custom(Function<AnimationSettings, AnimationSettings> settings) {
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

    public ObservableList<AnimationProperty<?>> getTargetProperties() {
        return this.properties;
    }
}
