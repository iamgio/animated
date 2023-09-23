package eu.iamgio.animatedtest;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * @author Giorgio Garofalo
 */
public class TestUtil {

    public static final double SCENE_WIDTH = 650;
    public static final double SCENE_HEIGHT = 500;

    /**
     * Centers a node (rectangles wrapped in Panes)
     * @param parent parent of the rectangle
     * @param scene scene
     */
    public static void center(Region parent, Scene scene) {
        parent.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(parent.widthProperty().divide(2)));
        parent.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(parent.heightProperty().divide(2)));
    }

    /**
     * @return randomly generated rectangle
     */
    public static Rectangle randomRectangle() {
        Random random = new Random();
        // Width and height go from 100 to 300
        double width = random.nextInt(200) + 100;
        double height = random.nextInt(200) + 100;
        // R, G and B values in 0.5-1 range
        Color color = randomColor(random);
        return new Rectangle(width, height, color);
    }

    /**
     * @param random {@link Random} object
     * @return randomly generated color in 0.5-1 range
     */
    public static Color randomColor(Random random) {
        return new Color((random.nextInt(5) + 5) / 10F, (random.nextInt(5) + 5) / 10F, (random.nextInt(5) + 5) / 10F, 1);
    }

    /**
     * @return randomly generated color in 0.5-1 range
     */
    public static Color randomColor() {
        return randomColor(new Random());
    }

    /**
     * @param color base color
     * @return complementary (opposite) color
     */
    public static Color complementaryColor(Color color) {
        return Color.color(1 - color.getRed(), 1 - color.getGreen(), 1 - color.getBlue());
    }
}
