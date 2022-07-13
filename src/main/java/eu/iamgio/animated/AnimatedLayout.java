package eu.iamgio.animated;

import eu.iamgio.animated.property.DoublePropertyWrapper;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Region;

/**
 * Node that animates its child's position based on an alignment relative to an anchor root.
 *
 * @author Giorgio Garofalo
 */
public class AnimatedLayout extends AnimatedMulti {

    private final Node child;
    private final Region root;
    private final Pos alignment;

    // The latest registered child size
    private Bounds bounds;

    // TODO allow dynamic parent, reapply bindings on parent change

    /**
     * Instantiates an {@link AnimatedLayout} node.
     * @param child the node to wrap, whose layout should be animated
     * @param root root to rely relayouts on
     * @param alignment position of the node relative to its root
     */
    public AnimatedLayout(Node child, Region root, Pos alignment) {
        super(child, new DoublePropertyWrapper(child.layoutXProperty()), new DoublePropertyWrapper(child.layoutYProperty()));

        this.child = child;
        this.root = root;
        this.alignment = alignment;
        this.bounds = child.getLayoutBounds();

        HPos hPos = alignment.getHpos();
        VPos vPos = alignment.getVpos();

        // Update the coordinates whenever the root gets resized
        bindX(hPos);
        bindY(vPos);

        // Register child size
        registerBoundsListener(hPos, vPos);
    }

    /**
     * @return alignment of the child relative to the root
     */
    public Pos getAlignment() {
        return alignment;
    }

    /**
     * Whenever the size of the node changes, it gets saved to a local variable
     * @param hPos horizontal position of the alignment
     * @param vPos vertical position of the alignment
     */
    private void registerBoundsListener(HPos hPos, VPos vPos) {
        child.layoutBoundsProperty().addListener((o, oldValue, newValue) -> {
            if(oldValue != newValue || newValue != bounds) {
                bounds = newValue;

                // Don't animate if this is the first update
                boolean wasEmpty = oldValue.getWidth() == 0 && oldValue.getHeight() == 0;
                if(wasEmpty) setActive(false);

                // Update coordinates
                if(requiresBinding(hPos)) updateX(isCenter(hPos));
                if(requiresBinding(vPos)) updateY(isCenter(vPos));

                if(wasEmpty) setActive(true);
            }
        });
    }

    /**
     * Updates the layout X coordinate of the node: either to the end of the root or to its center.
     * @param center whether the node should be centered to the root
     */
    private void updateX(boolean center) {
        if(bounds == null) return;

        double x = root.getPrefWidth() - bounds.getWidth();
        child.setLayoutX(center ? x / 2 : x);
    }

    /**
     * Updates the layout Y coordinate of the node: either to the end of the root or to its center.
     * @param center whether the node should be centered to the root
     */
    private void updateY(boolean center) {
        if(bounds == null) return;

        double y = root.getPrefHeight() - bounds.getHeight();
        child.setLayoutY(center ? y / 2 : y);
    }

    /**
     * Updates the layout X coordinate of the node whenever the width of the root changes.
     * @param hPos horizontal position of the alignment
     */
    private void bindX(HPos hPos) {
        if(requiresBinding(hPos)) {
            root.prefWidthProperty().addListener((o) -> updateX(isCenter(hPos)));
        }
    }

    /**
     * Updates the layout Y coordinate of the node whenever the height of the root changes.
     * @param vPos vertical position of the alignment
     */
    private void bindY(VPos vPos) {
        if(requiresBinding(vPos)) {
            boolean center = vPos == VPos.CENTER;
            root.prefHeightProperty().addListener((o) -> updateY(isCenter(vPos)));
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
