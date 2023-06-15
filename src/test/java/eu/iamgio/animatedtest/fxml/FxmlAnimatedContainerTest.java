package eu.iamgio.animatedtest.fxml;

import animatefx.animation.SlideOutUp;
import eu.iamgio.animated.transition.Animation;
import eu.iamgio.animated.transition.container.AnimatedContainer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

public class FxmlAnimatedContainerTest extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AnimatedContainer.fxml")));
        Scene scene = new Scene(root, 650, 500);

        primaryStage.setTitle("FXML");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button removeButton = (Button) Objects.requireNonNull(root.lookup("#button"));
        AnimatedContainer container = (AnimatedContainer) Objects.requireNonNull(root.lookup("#animated-container"));
        container.setOut(new Animation(new SlideOutUp()));

        removeButton.setOnAction(e -> container.getChildren().remove(container.getChildren().size() - 1));
    }
}