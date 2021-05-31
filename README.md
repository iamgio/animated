# animated

**animated** introduces **implicit animations**, a completely new concept in JavaFX strongly inspired by [Flutter's animations and motion widgets](https://flutter.dev/docs/development/ui/widgets/animation).

## Getting started

Maven:
```xml
<repositories>
    <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
</repositories>
...
<dependency>
    <groupId>com.github.iAmGio</groupId>
    <artifactId>animated</artifactId>
    <version>v0.0.2</version>
</dependency>
```

Gradle:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    implementation 'com.github.iAmGio:animated:v0.0.2'
}
```

## Implicit animations

Forget about timelines, explicit animations and other stuff that pollutes your code. This animation system will provide versatility to your code and interface.

![Demo](https://i.imgur.com/TKXA8de.gif)  
**[Code](https://github.com/iAmGio/animated/blob/master/src/test/java/eu/iamgio/animatedtest/AnimatedTest.java)**

```java
Animated<Double> animated = new Animated<>(child, new DoublePropertyWrapper(child.opacityProperty()));
root.getChildren().add(animated);

// Later...
child.setOpacity(0.5); // Plays the transition
```  

This approach instantiates an `Animated` node that contains one child and is bound to a property.  
Now that we have set an animated bound, we'll see that `child.setOpacity(someValue)` creates a transition between the initial and final value.  

There are some pre-made animated nodes that take the child as an argument as well (list will expand):
- `AnimatedBlur`
- `AnimatedDropShadow`
- `AnimatedColor` (shapes only)
- `AnimatedOpacity`
- `AnimatedPosition`
- `AnimatedRotation`
- `AnimatedSize`
- `AnimatedScale`

### Multiple animations at once

In case you need to animate more than one property of a single node, `AnimatedMulti` comes to the rescue.  
Unfortunately, at this time it only takes properties as arguments, so it won't be possible to use pre-made nodes (list above).

```java
AnimatedMulti animated = new AnimatedMulti(child,
    new DoublePropertyWrapper(child.opacityProperty()),
    new DoublePropertyWrapper(child.prefWidthProperty()),
    new DoublePropertyWrapper(child.prefHeightProperty())
);
root.getChildren().add(animated);

// Later...
child.setOpacity(0.5);   // Plays the transition
child.setPrefWidth(100); // Plays the transition
child.setPrefHeight(50); // Plays the transition
```  

### Custom animations

The default animation is linear and lasts 1 second. It can be customized by calling either `withSettings(AnimationSettings settings)` or `custom(Function<AnimationSettings, AnimationSettings> settings)`, both methods available on property wrappers and animated nodes.

Examples:
```java
AnimatedOpacity animated = new AnimatedOpacity(child)
    .custom(settings -> settings.withDuration(Duration.seconds(.5)).withCurve(Curve.EASE_IN_OUT));
```  

```java
AnimatedMulti animated = new AnimatedMulti(child,
    new DoublePropertyWrapper(child.opacityProperty())
        .custom(settings -> settings.withDuration(Duration.seconds(.8))),
    new DoublePropertyWrapper(child.rotateProperty())
        .custom(settings -> settings.withDuration(Duration.seconds(.5)),
).custom(settings -> settings.withCurve(Curve.EASE_OUT)); // 'custom' applies only these settings to the properties.
                                                          // 'withSettings' overrides all instead.
root.getChildren().add(animated);
```

## Animated switchers

The library also provides an `AnimatedSwitcher` node that creates a transition whenever its child changes.  
This feature is based on animations from [AnimateFX](https://github.com/Typhon0/AnimateFX).

![Demo](https://i.imgur.com/8v2Wn0a.gif)  
**[Code](https://github.com/iAmGio/animated/blob/master/src/test/java/eu/iamgio/animatedtest/AnimatedSwitcherTest.java)**

The node can be instantiated these ways:
- `new AnimatedSwitcher(Animation in, Animation out)` wraps two `AnimateFX` objects into customizable `animated` objects;
- `new AnimatedSwitcher(AnimationFX in, AnimationFX out)` takes two raw AnimateFX animations that cannot be customized;
- `new AnimatedSwitcher(SwitchAnimation animation)` takes a pair of animations, mostly used with pre-made pairs (e.g. `SwitchAnimation.fade()`).

Right after the instantiation, calling `of(Node child)` will set the initial child without any animation played.

Example:
```java
AnimatedSwitcher switcher = new AnimatedSwitcher(
    new Animation(new FadeInDown()).setSpeed(2), 
    new Animation(new FadeOutDown()).setSpeed(1.5)
).of(firstChild);
root.getChildren().add(switcher);

// Later...
switcher.setChild(secondChild); // Plays the transition
```

## Other examples

![Shadow](https://i.imgur.com/jd8Bbr4.gif)  
[Drop shadows + switcher](https://github.com/iAmGio/animated/blob/master/src/test/java/eu/iamgio/animatedtest/AnimatedShadowTest.java)