# animated

**animated** introduces **implicit animations**, a completely new concept in JavaFX strongly inspired by [Flutter's animations and motion widgets](https://flutter.dev/docs/development/ui/widgets/animation).

### Index
1. [Getting started](#getting-started)
2. [Implicit animations](#implicit-animations)
3. [Animated containers](#animated-containers)
4. [Animated switchers](#animated-switchers)
5. [Animated theme switch](#animated-theme-switch)
6. [Other examples](#other-examples)

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
    <version>0.4.0</version>
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
    implementation 'com.github.iAmGio:animated:0.4.0'
}
```
<br/>

---

<br/>

## Implicit animations

Forget about timelines, explicit animations and other stuff that pollutes your code. This animation system will provide versatility to your code and interface.

![Demo](https://i.imgur.com/TKXA8de.gif)  
**[Code](src/test/java/eu/iamgio/animatedtest/AnimatedTest.java)**

```java
Animated<Double> animated = new Animated<>(child, PropertyWrapper.of(child.opacityProperty()));
root.getChildren().add(animated);

// Later...
child.setOpacity(0.5); // Plays the transition
```  

This approach instantiates an `Animated` node that contains one child and is bound to a property.  
Now that we have set an animated bound, we'll see that `child.setOpacity(someValue)` creates a transition between the initial and final value.

> `PropertyWrapper.of` automatically finds out the best kind of wrapper for a given property.  
> Currently supported wrappers are `DoublePropertyWrapper` and `ObjectPropertyWrapper<T>`.

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

In case you need to animate more than one property of a single node, `AnimatedMulti` comes to the rescue. At this time it only takes properties as arguments, so it won't be possible to use pre-made nodes (list above).

```java
AnimatedMulti animated = new AnimatedMulti(child,
    PropertyWrapper.of(child.opacityProperty()),
    PropertyWrapper.of(child.prefWidthProperty()),
    PropertyWrapper.of(child.prefHeightProperty())
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
    PropertyWrapper.of(child.opacityProperty())
        .custom(settings -> settings.withDuration(Duration.seconds(.8))),
    PropertyWrapper.of(child.rotateProperty())
        .custom(settings -> settings.withDuration(Duration.seconds(.5)),
).custom(settings -> settings.withCurve(Curve.EASE_OUT)); // 'custom' applies only these settings to the properties.
                                                          // 'withSettings' overrides all instead.
root.getChildren().add(animated);
```
<br/>

---

<br/>

## Animated containers

**animated** provides custom implementations of `VBox` and `HBox` that animate their content whenever their children are affected by a change.  
This feature is based on animations from [AnimateFX](https://github.com/Typhon0/AnimateFX).

![Demo](https://i.imgur.com/jqm9KDA.gif)  
**[Code](src/test/java/eu/iamgio/animatedtest/AnimatedContainerTest.java)**

**Constructors**:
- `Animation in, Animation out` wraps two `AnimateFX` objects into customizable `animated` objects;
- `AnimationFX in, AnimationFX out` takes two raw AnimateFX animations that cannot be customized;
- `AnimationPair animation` takes a pair of animations, mostly used with pre-made pairs (e.g. `AnimationPair.fade()`).

`pause()` and `resume()` allow disabling/enabling animations so that you can switch back to the regular implementation and forth.

Example:
```java
AnimatedVBox vBox = new AnimatedVBox(AnimationPair.fade());

// Later...
vBox.getChildren().add(someNode);    // someNode fades in
vBox.getChildren().remove(someNode); // someNode fades out
```

<br/>

---

<br/>

## Animated switchers

The library also provides an `AnimatedSwitcher` node that creates a transition whenever its child changes.  
As for animated containers, this feature relies on AnimateFX.

![Demo](https://i.imgur.com/8v2Wn0a.gif)  
**[Code](src/test/java/eu/iamgio/animatedtest/AnimatedSwitcherTest.java)**

See [animated containers](#animated-containers) for information about constructors.  
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
<br/>

---

<br/>

## Animated theme switch

It is possible to create a transition whenever the stylesheets of the scene change via `AnimatedThemeSwitcher`, based on AnimateFX.


![Theme](https://i.imgur.com/0PnOYbc.gif)  
**[Code](src/test/java/eu/iamgio/animatedtest/AnimatedThemeTest.java)**

```java
AnimatedThemeSwitcher themeSwitcher = AnimatedThemeSwitcher.of(scene);
themeSwitcher.setTheme("/light.css");         // Initial theme
theme.setAnimation(new Animation(/* ... */)); // Optional: defaults to FadeOut

// Later...
themeSwitcher.animateTheme("/dark.css"); // Plays the transition
```

> **Note** that not every type of root can be animated properly, such as `VBox` and `HBox`.
> Parents that allow overlapping children, i.e. `Pane`, are suggested. 

<br/>

---

<br/>

## Other examples

![Shadow](https://i.imgur.com/jd8Bbr4.gif)  
**[Drop shadows + switcher](src/test/java/eu/iamgio/animatedtest/AnimatedShadowTest.java)**

![Root switch](https://i.imgur.com/cYkSu9z.gif)  
**[Root switch](src/test/java/eu/iamgio/animatedtest/AnimatedShadowTest.java)**