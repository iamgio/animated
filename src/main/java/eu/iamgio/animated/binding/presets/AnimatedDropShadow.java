package eu.iamgio.animated.binding.presets;

import eu.iamgio.animated.binding.property.animation.OnDemandAnimationProperty;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;

/**
 * Container of different drop shadow properties.
 * If the target node does not have an {@link javafx.scene.effect.Effect}
 * or if its effect is not a {@link DropShadow} at initialization time,
 * a new {@link DropShadow} with default color and radius will be set as its new effect.
 */
public class AnimatedDropShadow {

    /**
     * Property that animates its child's {@link DropShadow} color.
     * If the target node does not have an {@link javafx.scene.effect.Effect}
     * or if its effect is not a {@link DropShadow} at initialization time,
     * a new {@link DropShadow} with default color and radius will be set as its new effect.
     */
    public static class Color extends OnDemandAnimationProperty<Node, javafx.scene.paint.Color> {

        public Color() {
            super(node -> PropertyWrapper.of(getEffectOrCreate(node).colorProperty()));
        }

        public Color(Node child) {
            this();
            targetNodeProperty().set(child);
        }
    }

    /**
     * Property that animates its child's {@link DropShadow} radius.
     * If the target node does not have an {@link javafx.scene.effect.Effect}
     * or if its effect is not a {@link DropShadow} at initialization time,
     * a new {@link DropShadow} with default color and radius will be set as its new effect.
     */
    public static class Radius extends OnDemandAnimationProperty<Node, Double> {

        public Radius() {
            super(node -> PropertyWrapper.of(getEffectOrCreate(node).radiusProperty()));
        }

        public Radius(Node child) {
            this();
            targetNodeProperty().set(child);
        }
    }

    private static DropShadow getEffectOrCreate(Node node) {
        if (node.getEffect() == null || !(node.getEffect() instanceof DropShadow)) {
            final DropShadow shadow = new DropShadow();
            node.setEffect(shadow);
            return shadow;
        } else {
            return (DropShadow) node.getEffect();
        }
    }
}

