package eu.iamgio.animated.transition.animations;

/**
 * A tag interface that, when implemented by {@link animatefx.animation.AnimationFX} subclasses,
 * lets animations play after adding the target node to the scene.
 * This is required when the animation needs to access information about the scene,
 * but doing so may alter the visual results.
 */
public interface RequiresScene {
}
