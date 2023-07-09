package eu.iamgio.animated.transition;

import animatefx.animation.AnimationFX;
import animatefx.animation.FadeOut;
import eu.iamgio.animated.common.Pausable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;

/**
 * An {@link AnimatedThemeSwitcher} provides animated transitions that are played when changing the stylesheets of a {@link Scene}.
 * @author Giorgio Garofalo
 */
public class AnimatedThemeSwitcher implements Pausable, ExitAnimationCompatible {

    private final Scene scene;

    private final ObjectProperty<Animation> out;
    private final BooleanProperty pausedProperty = new SimpleBooleanProperty(false);

    // Whether the changes to the stylesheets should be handled
    private boolean handleChanges = true;

    /**
     * Instantiates an {@link AnimatedThemeSwitcher}.
     * In order for this switcher to work, {@link #init()} has to be called.
     * @param scene JavaFX scene to register the hook onto
     * @param animationOut non-null exit animation
     */
    public AnimatedThemeSwitcher(Scene scene, Animation animationOut) {
        this.scene = scene;
        this.out = new SimpleObjectProperty<>(Animation.requireNonNull(animationOut));
    }

    /**
     * Instantiates an {@link AnimatedThemeSwitcher}.
     * In order for this switcher to work, {@link #init()} has to be called.
     * @param scene JavaFX scene to register the hook onto
     * @param animationOut non-null raw exit animation
     */
    public AnimatedThemeSwitcher(Scene scene, AnimationFX animationOut) {
        this(scene, new Animation(animationOut));
    }

    /**
     * Instantiates an {@link AnimatedThemeSwitcher} with a default animation.
     * In order for this switcher to work, {@link #init()} has to be called.
     * @param scene JavaFX scene to register the hook onto
     */
    public AnimatedThemeSwitcher(Scene scene) {
        this(scene, new Animation(new FadeOut()));
    }

    /**
     * Registers the stylesheets hook on the wrapped scene.
     *
     * @throws IllegalStateException if the root of the scene is not suitable for the transition
     *                               (i.e. containers like <tt>VBox</tt> and <tt>HBox</tt> are not supported)
     */
    public void init() throws IllegalStateException {
        final Parent root = scene.getRoot();

        if (root == null) {
            throw new IllegalStateException("The given scene does not have a root node.");
        }
        if (!(root instanceof Pane)) {
            throw new IllegalStateException("The root node is not a Pane (or subclass).");
        }
        if (root instanceof VBox || root instanceof HBox) {
            throw new IllegalStateException("The root node cannot be a VBox or HBox.");
        }

        this.register();
    }

    /**
     * Registers the listener that watches the stylesheets of the scene.
     * Every time they change, an image of the 'old' scene is put on top and disappears via an animation.
     */
    private void register() {
        // Set-up stylesheets listener
        scene.getStylesheets().addListener((ListChangeListener<? super String>) change -> {
            final ObservableList<String> stylesheets = scene.getStylesheets();
            while (change.next() && handleChanges && !isPaused()) {
                // Copy changes (to avoid ConcurrentModificationException)
                final List<? extends String> added = new LinkedList<>(change.getAddedSubList());
                final List<? extends String> removed = new LinkedList<>(change.getRemoved());

                handleChanges = false; // Puts the listener on hold

                // Revert changes
                stylesheets.removeAll(added);
                stylesheets.addAll(change.getFrom(), removed);

                // Screenshot the scene with the old theme applied and play the out animation
                overlapSnapshot();

                // Reapply changes
                stylesheets.addAll(change.getFrom(), added);
                stylesheets.removeAll(removed);

                handleChanges = true; // Resumes the listener
            }
        });
    }

    /**
     * Takes a snapshot/screenshot of the scene, puts it on top and plays the exit animation on it.
     */
    private void overlapSnapshot() {
        // Takes a screenshot
        Image snapshot = scene.snapshot(null);
        Pane root = (Pane) scene.getRoot();

        // Adds the image on top of the root
        ImageView imageView = new ImageView(snapshot);
        root.getChildren().add(imageView);

        // Plays the exit animation and removes the image after the transition ends.
        getOut().playOut(imageView, root.getChildren());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<Animation> animationOutProperty() {
        return this.out;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty pausedProperty() {
        return this.pausedProperty;
    }
}
