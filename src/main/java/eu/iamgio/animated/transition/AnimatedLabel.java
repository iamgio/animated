package eu.iamgio.animated.transition;

import animatefx.animation.AnimationFX;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * A node that wraps a text string that, whenever changed, plays an animation between the old and current state.
 *
 * @author Giorgio Garofalo
 */
public class AnimatedLabel extends Parent implements Pausable, EntranceAndExitAnimationCompatible {

    private final ObjectProperty<Animation> in;
    private final ObjectProperty<Animation> out;

    private final SimpleStringProperty textProperty = new SimpleStringProperty();

    private LabelFactory labelFactory = LabelFactory.DEFAULT;
    private Label currentLabel;

    private final SimpleBooleanProperty pausedProperty = new SimpleBooleanProperty();

    /**
     * Instantiates an {@link AnimatedLabel}.
     * @param text initial text, displayed without animations
     * @param animationIn non-null entrance animation
     * @param animationOut non-null exit animation
     */
    public AnimatedLabel(String text, Animation animationIn, Animation animationOut) {
        this.textProperty.set(text);
        this.in = new SimpleObjectProperty<>(Animation.requireNonNull(animationIn));
        this.out = new SimpleObjectProperty<>(Animation.requireNonNull(animationOut));
        register();
    }

    /**
     * Instantiates an initially empty {@link AnimatedLabel}.
     * @param animationIn non-null entrance animation
     * @param animationOut non-null exit animation
     */
    public AnimatedLabel(Animation animationIn, Animation animationOut) {
        this("", animationIn, animationOut);
    }

    /**
     * Instantiates an {@link AnimatedLabel}.
     * @param text initial text, displayed without animations
     * @param animation pair of non-null in and out animations
     */
    public AnimatedLabel(String text, AnimationPair animation) {
        this(text, animation.getIn(), animation.getOut());
    }

    /**
     * Instantiates an initially empty {@link AnimatedLabel}.
     * @param animation pair of non-null in and out animations
     */
    public AnimatedLabel(AnimationPair animation) {
        this("", animation.getIn(), animation.getOut());
    }

    /**
     * Instantiates an {@link AnimatedLabel}.
     * @param text initial text, displayed without animations
     * @param animationIn raw entrance animation
     * @param animationOut raw exit animation
     */
    public AnimatedLabel(String text, AnimationFX animationIn, AnimationFX animationOut) {
        this(text, new Animation(animationIn), new Animation(animationOut));
    }

    /**
     * Instantiates an initially empty {@link AnimatedLabel}.
     * @param animationIn raw entrance animation
     * @param animationOut raw exit animation
     */
    public AnimatedLabel(AnimationFX animationIn, AnimationFX animationOut) {
        this("", new Animation(animationIn), new Animation(animationOut));
    }

    /**
     * Instantiates an initially empty {@link AnimatedLabel} with default animations.
     */
    public AnimatedLabel() {
        this("", AnimationPair.fade());
    }

    /**
     * Registers the text listener.
     */
    private void register() {
        // Initialization
        currentLabel = labelFactory.newLabel(getText());

        // AnimatedLabel works via this AnimatedSwitcher,
        // which simply overlaps the old and new labels.
        AnimatedSwitcher switcher = new AnimatedSwitcher().of(currentLabel);
        this.in.bindBidirectional(switcher.animationInProperty());
        this.out.bindBidirectional(switcher.animationOutProperty());
        this.pausedProperty.bindBidirectional(switcher.pausedProperty());
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
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<Animation> animationInProperty() {
        return this.in;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectProperty<Animation> animationOutProperty() {
        return this.out;
    }

    /**
     * @return the non-null active label generator
     */
    public LabelFactory getLabelFactory() {
        return labelFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SimpleBooleanProperty pausedProperty() {
        return pausedProperty;
    }

    /**
     * Sets the active label generator.
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
