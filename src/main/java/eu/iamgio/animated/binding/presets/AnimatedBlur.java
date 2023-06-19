package eu.iamgio.animated.binding.presets;

import eu.iamgio.animated.binding.property.animation.OnDemandAnimationProperty;
import eu.iamgio.animated.binding.property.wrapper.PropertyWrapper;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;

/**
 * Property that animates its child's {@link GaussianBlur} radius.
 * If the target node does not have an {@link javafx.scene.effect.Effect}
 * or if its effect is not a {@link GaussianBlur} at initialization time,
 * a new {@link GaussianBlur} with default radius will be set as its new effect.
 */
public class AnimatedBlur extends OnDemandAnimationProperty<Node, Double> {

    public AnimatedBlur() {
        super(node -> PropertyWrapper.of(getEffectOrCreate(node).radiusProperty()));
    }

    public AnimatedBlur(Node child) {
        this();
        targetNodeProperty().set(child);
    }

    private static GaussianBlur getEffectOrCreate(Node node) {
        if (node.getEffect() == null || !(node.getEffect() instanceof GaussianBlur)) {
            GaussianBlur blur = new GaussianBlur();
            node.setEffect(blur);
            return blur;
        } else {
            return (GaussianBlur) node.getEffect();
        }
    }
}
