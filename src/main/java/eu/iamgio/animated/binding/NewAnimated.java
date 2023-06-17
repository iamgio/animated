package eu.iamgio.animated.binding;

import eu.iamgio.animated.transition.Pausable;
import javafx.beans.property.BooleanProperty;
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
public class NewAnimated extends SingleChildParent implements CustomizableAnimation<NewAnimated>, Pausable {

    private final ObservableList<AnimationProperty<?>> properties = FXCollections.emptyObservableList();
    private final BooleanProperty paused = new SimpleBooleanProperty(false);

    public NewAnimated() {
        registerPause();
    }

    public NewAnimated(Node child, AnimationProperty<?>... properties) {
        super(child);
        registerPause();
        this.properties.addAll(properties);
    }

    public NewAnimated(Node child) {
        super(child);
        registerPause();
    }

    public NewAnimated(AnimationProperty<?>... properties) {
        registerPause();
        this.properties.addAll(properties);
    }

    private void registerPause() {
        properties.addListener((ListChangeListener<? super AnimationProperty<?>>) change -> {
            change.getAddedSubList().forEach(property -> property.pausedProperty().bind(this.paused));
            // TODO unbind on remove
        });
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
}
