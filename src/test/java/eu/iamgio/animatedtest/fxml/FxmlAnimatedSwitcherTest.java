package eu.iamgio.animatedtest.fxml;

import animatefx.animation.SlideOutUp;
import eu.iamgio.animated.transition.AnimatedSwitcher;
import eu.iamgio.animated.transition.Animation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;

public class FxmlAnimatedSwitcherTest extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AnimatedSwitcher.fxml")));
        Scene scene = new Scene(root, 650, 500);

        primaryStage.setTitle("FXML");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button switchButton = (Button) Objects.requireNonNull(root.lookup("#button"));
        AnimatedSwitcher switcher = (AnimatedSwitcher) Objects.requireNonNull(root.lookup("#animated-switcher"));
        switcher.setOut(new Animation(new SlideOutUp()));

        switchButton.setOnAction(e -> switcher.setChild(new Rectangle(50, 50)));
    }
}