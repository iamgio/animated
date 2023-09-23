package eu.iamgio.animatedtest.fxml;

import eu.iamgio.animated.transition.container.AnimatedContainer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

import static eu.iamgio.animatedtest.TestUtil.SCENE_HEIGHT;
import static eu.iamgio.animatedtest.TestUtil.SCENE_WIDTH;

public class FxmlAnimatedContainerTest extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AnimatedContainer.fxml")));
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setTitle("FXML");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button removeButton = (Button) Objects.requireNonNull(root.lookup("#button"));
        AnimatedContainer container = (AnimatedContainer) Objects.requireNonNull(root.lookup("#animated-container"));

        removeButton.setOnAction(e -> container.getChildren().remove(container.getChildren().size() - 1));
    }
}