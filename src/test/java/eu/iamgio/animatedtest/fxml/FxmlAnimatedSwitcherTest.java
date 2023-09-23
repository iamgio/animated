package eu.iamgio.animatedtest.fxml;

import eu.iamgio.animated.transition.AnimatedLabel;
import eu.iamgio.animated.transition.AnimatedSwitcher;
import eu.iamgio.animatedtest.TestUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;

import static eu.iamgio.animatedtest.TestUtil.SCENE_HEIGHT;
import static eu.iamgio.animatedtest.TestUtil.SCENE_WIDTH;

public class FxmlAnimatedSwitcherTest extends Application {

    private int times = 0;

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AnimatedSwitcher.fxml")));
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setTitle("FXML");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button switchButton = (Button) Objects.requireNonNull(root.lookup("#button"));
        AnimatedSwitcher switcher = (AnimatedSwitcher) Objects.requireNonNull(root.lookup("#animated-switcher"));

        AnimatedLabel label = (AnimatedLabel) Objects.requireNonNull(root.lookup("#animated-label"));

        switchButton.setOnAction(e -> {
            final Rectangle rectangle = new Rectangle(50, 50);
            rectangle.setFill(TestUtil.randomColor());
            switcher.setChild(rectangle);
            label.setText(String.valueOf(++times));
        });
    }
}