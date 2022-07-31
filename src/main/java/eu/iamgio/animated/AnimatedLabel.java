package eu.iamgio.animated;

import animatefx.animation.AnimationFX;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * A node that wraps a text string that, whenever changed, plays an animation between the old and current state.
 *
 * @author Giorgio Garofalo
 */
public class AnimatedLabel extends Parent {
    private final SimpleStringProperty textProperty = new SimpleStringProperty();
    private final AnimationPair animation;

    private LabelFactory labelFactory;
    private Label currentLabel;

    /**
     * Instantiates an {@link AnimatedLabel}.
     * @param text initial text, displayed without animations
     * @param animation a pair of in and out animations
     * @param labelFactory label generator - can be <tt>null</tt>
     */
    public AnimatedLabel(String text, AnimationPair animation, LabelFactory labelFactory) {
        this.textProperty.set(text);
        this.animation = animation;
        setLabelFactory(labelFactory);
        register();
    }

    /**
     * Instantiates an {@link AnimatedLabel}.
     * @param text initial text, displayed without animations
     * @param animation a pair of in and out animations
     */
    public AnimatedLabel(String text, AnimationPair animation) {
        this(text, animation, LabelFactory.DEFAULT);
    }

    /**
     * Instantiates an {@link AnimatedLabel}.
     * @param animation a pair of in and out animations
     */
    public AnimatedLabel(AnimationPair animation) {
        this("", animation, LabelFactory.DEFAULT);
    }

    /**
     * Instantiates an {@link AnimatedLabel}. {@link Animation} wraps an {@link AnimationFX} allowing customization.
     * @param animationIn entrance animation
     * @param animationOut exit animation
     */
    public AnimatedLabel(Animation animationIn, Animation animationOut) {
        this(new AnimationPair(animationIn, animationOut));
    }

    /**
     * Instantiates an {@link AnimatedLabel}.
     * @param animationIn entrance animation
     * @param animationOut exit animation
     */
    public AnimatedLabel(AnimationFX animationIn, AnimationFX animationOut) {
        this(new Animation(animationIn), new Animation(animationOut));
    }

    /**
     * Registers the text listener.
     */
    private void register() {
        // Initialization
        currentLabel = labelFactory.newLabel(getText());

        // AnimatedLabel works via this AnimatedSwitcher,
        // which simply overlaps the old and new labels.
        AnimatedSwitcher switcher = new AnimatedSwitcher(animation).of(currentLabel);
        getChildren().add(switcher);

        // Every time the text changes, a new label is placed on top and the switch is animated.
        textProperty.addListener(((observable, oldValue, newValue) -> {
            currentLabel = labelFactory.newLabel(newValue);
            switcher.setChild(currentLabel);
        }));
    }

    /**
     * @return the displayed text
     */
    public SimpleStringProperty textProperty() {
        return textProperty;
    }

    /**
     * @return the displayed text
     */
    public String getText() {
        return textProperty.get();
    }

    /**
     * Sets the displayed text.
     * @param text text to display
     */
    public void setText(String text) {
        this.textProperty.set(text);
    }

    /**
     * @return the latest {@link Label} initialized
     */
    public Label getCurrentLabel() {
        return currentLabel;
    }

    /**
     * @return the in-out animation used to switch labels
     */
    public AnimationPair getAnimation() {
        return animation;
    }

    /**
     * @return the non-null active label generator
     */
    public LabelFactory getLabelFactory() {
        return labelFactory;
    }

    /**
     * Sets the active label generator.
     *
     * Example:
     * <pre>
     *     animatedLabel.setLabelFactory(text{@literal ->} {
     *        Label label = new Label(text);
     *        // Apply label properties...
     *        return label;
     *     });
     * </pre>
     *
     * @param labelFactory the way new labels will be generated. If <tt>null</tt>, the default one will be used
     */
    public void setLabelFactory(LabelFactory labelFactory) {
        this.labelFactory = labelFactory != null ? labelFactory : LabelFactory.DEFAULT;
    }

    /**
     * A factory that generates new {@link Label}s every time the {@link AnimatedLabel}'s text is updated.
     */
    @FunctionalInterface
    public interface LabelFactory {

        /**
         * The default factory used, which returns a standard {@link Label} with the given text.
         */
        LabelFactory DEFAULT = Label::new;

        /**
         * @param text the updated text
         * @return a new label
         */
        Label newLabel(String text);
    }
}
