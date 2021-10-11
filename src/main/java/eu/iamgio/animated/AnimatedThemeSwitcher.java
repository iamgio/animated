package eu.iamgio.animated;

import animatefx.animation.AnimationFX;
import animatefx.animation.FadeOut;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * An {@link AnimatedThemeSwitcher} provides animated transitions that are played when changing the stylesheets of a {@link Scene}.
 * @author Giorgio Garofalo
 */
public class AnimatedThemeSwitcher {

    private final Scene scene;
    private final SimpleObjectProperty<Animation> animation = new SimpleObjectProperty<>(new Animation(new FadeOut()));

    private String stylesheet;

    private AnimatedThemeSwitcher(Scene scene) {
        this.scene = scene;
    }

    /**
     * Gets an {@link AnimatedThemeSwitcher} that wraps a {@link Scene}.
     * @param scene JavaFX scene to affect
     * @return new {@link AnimatedThemeSwitcher} for the given {@link Scene}
     * @throws IllegalStateException if the root of the scene is not suitable for the transition (e.g. VBox and HBox)
     */
    public static AnimatedThemeSwitcher of(Scene scene) {
        Parent root = scene.getRoot();
        if(!(root instanceof Pane)) {
            throw new IllegalStateException("The root node is not a Pane (or subclass).");
        }
        if(root instanceof VBox || root instanceof HBox) {
            throw new IllegalStateException("The root node cannot be a VBox or HBox.");
        }
        return new AnimatedThemeSwitcher(scene);
    }

    /**
     * @return the animation played during the transition
     */
    public SimpleObjectProperty<Animation> animationProperty() {
        return animation;
    }

    /**
     * @return the exit animation played during the transition, defaults to {@link FadeOut}
     */
    public Animation getAnimation() {
        return animation.get();
    }

    /**
     * @param animationOut exit animation to be played during the transition.
     * @throws IllegalArgumentException if the animation is not an exit animation.
     */
    public void setAnimation(Animation animationOut) {
        if(!animationOut.getAnimationFX().toString().contains("Out")) {
            throw new IllegalArgumentException("AnimatedThemeSwitcher should feature an exit animation, such as FadeOut.");
        }
        this.animation.set(animationOut);
    }

    /**
     * @param animationOut raw exit animation to be played during the transition.
     * @throws IllegalArgumentException if the animation is not an exit animation.
     */
    public void setAnimation(AnimationFX animationOut) {
        setAnimation(new Animation(animationOut));
    }

    /**
     * Changes the stylesheet of the scene without playing a transition.
     * @param stylesheet path of the stylesheet
     */
    public void setTheme(String stylesheet) {
        ObservableList<String> stylesheets = scene.getStylesheets();
        int index = stylesheets.indexOf(this.stylesheet);
        if(index != -1) {
            stylesheets.set(index, stylesheet);
        } else {
            stylesheets.add(stylesheet);
        }
        this.stylesheet = stylesheet;
    }

    /**
     * Changes the stylesheet of the scene and plays the exit animation.
     * @param stylesheet path of the stylesheet
     */
    public void animateTheme(String stylesheet) {
        // Takes a screenshot
        Image snapshot = scene.snapshot(null);
        Pane root = (Pane) scene.getRoot();

        // Adds the image on top of the root
        ImageView imageView = new ImageView(snapshot);
        root.getChildren().add(imageView);
        setTheme(stylesheet);

        // Plays the exit animation and removes the image after the transition ends.
        getAnimation().playOut(imageView, root.getChildren());
    }
}
