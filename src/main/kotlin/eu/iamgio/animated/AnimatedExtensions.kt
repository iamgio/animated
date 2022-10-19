package eu.iamgio.animated

import animatefx.animation.AnimationFX
import eu.iamgio.animated.property.PropertyWrapper
import javafx.beans.property.DoubleProperty
import javafx.beans.property.ObjectProperty
import javafx.util.Duration

// Utility extension functions for Kotlin

/**
 * Wraps a JavaFX double property into a [PropertyWrapper].
 */
fun DoubleProperty.animated(): PropertyWrapper<Double> = PropertyWrapper.of(this)

/**
 * Wraps a JavaFX object property into a [PropertyWrapper].
 */
fun <T> ObjectProperty<T>.animated(): PropertyWrapper<T> = PropertyWrapper.of(this)

/**
 * Wraps an AnimateFX animation into an Animated animation that supports customization.
 */
fun AnimationFX.wrapped(): Animation = Animation(this)

/**
 * Wraps an AnimateFX animation into an Animated animation that supports customization.
 * @param speed speed multiplier
 * @param delay delay before the animation is played
 * @param cycleCount times the animation should be played
 */
fun AnimationFX.options(speed: Double = 1.0, delay: Duration? = null, cycleCount: Int = 1): Animation =
        this.wrapped().setSpeed(speed).setDelay(delay).setCycleCount(cycleCount)

/**
 * Creates an [AnimationPair] from [this] to [animationOut].
 */
infix fun Animation.outTo(animationOut: Animation) = AnimationPair(this, animationOut)

/**
 * Creates an [AnimationPair] from [this] to [animationOut].
 */
infix fun Animation.outTo(animationOut: AnimationFX) = AnimationPair(this, animationOut.wrapped())

/**
 * Creates an [AnimationPair] from [this] to [animationOut].
 */
infix fun AnimationFX.outTo(animationOut: Animation) = AnimationPair(this.wrapped(), animationOut)

/**
 * Creates an [AnimationPair] from [this] to [animationOut].
 */
infix fun AnimationFX.outTo(animationOut: AnimationFX) = AnimationPair(this, animationOut)