package eu.iamgio.animated.binding.presets;

import eu.iamgio.animated.binding.property.animation.OnDemandAnimationPropertyGroup;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Region;

import java.util.Arrays;

/**
 * Node that animates its child's position based on an alignment relative to an anchor root.
 *
 * @author Giorgio Garofalo
 */
public class AnimatedLayout extends OnDemandAnimationPropertyGroup<Node, Double> {

    private final Node child;
    private final Region root;
    private final Pos alignment;

    private final BooleanProperty animateShrinking = new SimpleBooleanProperty();

    // The latest registered child size
    private Bounds bounds;

    /**
     * Instantiates an {@link AnimatedLayout} node.
     * @param child the node to wrap, whose layout should be animated
     * @param root root to rely relayouts on
     * @param alignment position of the node relative to its root
     * @param animateShrinking whether the animation should be played when the root is shrunk
     */
    public AnimatedLayout(Node child, Region root, Pos alignment, boolean animateShrinking) {
        super(Arrays.asList(
                node -> PropertyWrapper.of(node.layoutXProperty()),
                node -> PropertyWrapper.of(node.layoutYProperty())
        ));

        targetNodeProperty().set(child);

        this.child = child;
        this.root = root;
        this.alignment = alignment;
        this.bounds = child.getLayoutBounds();
        this.animateShrinking.set(animateShrinking);

        HPos hPos = alignment.getHpos();
        VPos vPos = alignment.getVpos();

        // Update the coordinates whenever the root gets resized
        bindX(hPos);
        bindY(vPos);

        // Register child size
        registerBoundsListener(hPos, vPos);
    }

    /**
     * Instantiates an {@link AnimatedLayout} node.
     * @param child the node to wrap, whose layout should be animated
     * @param root root to rely relayouts on
     * @param alignment position of the node relative to its root
     */
    public AnimatedLayout(Node child, Region root, Pos alignment) {
        this(child, root, alignment, false);
    }

    /**
     * @return alignment of the child relative to the root
     */
    public Pos getAlignment() {
        return alignment;
    }

    /**
     * @return whether the animation should be played when the root is shrunk
     */
    public BooleanProperty animateShrinkingProperty() {
        return animateShrinking;
    }

    /**
     * @return whether the animation should be played when the root is shrunk. Does not affect centered alignments
     */
    public boolean isAnimateShrinking() {
        return animateShrinking.get();
    }

    /**
     * Enables or disables shrinking animation.
     * @param animateShrinking whether the animation should be played when the root is shrunk
     */
    public void setAnimateShrinking(boolean animateShrinking) {
        this.animateShrinking.set(animateShrinking);
    }

    /**
     * Whenever the size of the node changes, it gets saved to a local variable
     * @param hPos horizontal position of the alignment
     * @param vPos vertical position of the alignment
     */
    private void registerBoundsListener(HPos hPos, VPos vPos) {
        child.layoutBoundsProperty().addListener((o, oldValue, newValue) -> {
            if (oldValue != newValue || newValue != bounds) {
                bounds = newValue;

                // Don't animate if this is the first update
                boolean wasEmpty = oldValue.getWidth() == 0 && oldValue.getHeight() == 0;
                if (wasEmpty) {
                    pause();
                }

                // Update coordinates
                if (requiresBinding(hPos)) {
                    updateX(isCenter(hPos));
                }
                if (requiresBinding(vPos)) {
                    updateY(isCenter(vPos));
                }

                if (wasEmpty) {
                    resume();
                }
            }
        });
    }

    /**
     * Updates the layout X coordinate of the node: either to the end of the root or to its center.
     * @param center whether the node should be centered to the root
     */
    private void updateX(boolean center) {
        if (bounds == null) {
            return;
        }

        double x = root.getPrefWidth() - bounds.getWidth();
        child.setLayoutX(center ? x / 2 : x);
    }

    /**
     * Updates the layout Y coordinate of the node: either to the end of the root or to its center.
     * @param center whether the node should be centered to the root
     */
    private void updateY(boolean center) {
        if (bounds == null) {
            return;
        }

        double y = root.getPrefHeight() - bounds.getHeight();
        child.setLayoutY(center ? y / 2 : y);
    }

    /**
     * Updates the layout X coordinate of the node whenever the width of the root changes.
     * @param hPos horizontal position of the alignment
     */
    private void bindX(HPos hPos) {
        if (requiresBinding(hPos)) {
            root.prefWidthProperty().addListener((observable, oldValue, newValue) -> {
                boolean isShrunk = !isAnimateShrinking() && (double) newValue < (double) oldValue && !isCenter(hPos);

                if (isShrunk) {
                    pause();
                }

                updateX(isCenter(hPos));

                if (isShrunk) {
                    resume();
                }
            });
        }
    }

    /**
     * Updates the layout Y coordinate of the node whenever the height of the root changes.
     * @param vPos vertical position of the alignment
     */
    private void bindY(VPos vPos) {
        if (requiresBinding(vPos)) {
            boolean center = vPos == VPos.CENTER;
            root.prefHeightProperty().addListener((observable, oldValue, newValue) -> {
                boolean isShrunk = !isAnimateShrinking() && (double) newValue < (double) oldValue && !isCenter(vPos);
                if (isShrunk) {
                    pause();
                }

                updateY(isCenter(vPos));

                if (isShrunk) {
                    resume();
                }
            });
        }
    }

    private boolean requiresBinding(HPos hPos) {
        return child != null && root != null && hPos != HPos.LEFT;
    }

    private boolean requiresBinding(VPos vPos) {
        return child != null && root != null && vPos != VPos.TOP;
    }

    private boolean isCenter(HPos hPos) {
        return hPos == HPos.CENTER;
    }

    private boolean isCenter(VPos vPos) {
        return vPos == VPos.CENTER;
    }
}
