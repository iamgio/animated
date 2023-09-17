package eu.iamgio.animated.util;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;

/**
 * Position and alignment utilities.
 */
public final class PositionUtils {

    private PositionUtils() {
    }

    /**
     * @param alignment position to calculate the X coordinate for
     * @param areaWidth width of the available area
     * @return the X coordinate at the given position within an area
     */
    public static double getX(Pos alignment, double areaWidth) {
        switch (alignment.getHpos()) {
            case CENTER:
                return areaWidth / 2;
            case RIGHT:
                return areaWidth;
            default:
                return 0;
        }
    }

    /**
     * @param alignment position to calculate the Y coordinate for
     * @param areaHeight height of the available area
     * @return the Y coordinate at the given position within a scene
     */
    public static double getY(Pos alignment, double areaHeight) {
        switch (alignment.getVpos()) {
            case CENTER:
                return areaHeight / 2;
            case BOTTOM:
                return areaHeight;
            default:
                return 0;
        }
    }

    /**
     * @return whether the horizontal alignment requires position binding
     */
    public static boolean requiresXBinding(Pos alignment) {
        return alignment.getHpos() != HPos.LEFT;
    }

    /**
     * @return whether the vertical alignment requires position binding
     */
    public static boolean requiresYBinding(Pos alignment) {
        return alignment.getVpos() != VPos.TOP;
    }

    /**
     * Binds X and Y coordinate properties to a position that guarantees the given alignment within the given area.
     * @param xProperty X coordinate property to bind
     * @param yProperty Y coordinate property to bind
     * @param areaProperty available area property
     * @param alignment position to bind to, relative to the scene
     */
    public static void bindAlignmentToArea(DoubleProperty xProperty, DoubleProperty yProperty,
                                           ReadOnlyObjectProperty<Bounds> areaProperty,
                                           Pos alignment) {
        if (requiresXBinding(alignment)) {
            xProperty.bind(Bindings.createObjectBinding(
                    () -> getX(alignment, areaProperty.get().getWidth()),
                    areaProperty
            ));
        }
        if (requiresYBinding(alignment)) {
            yProperty.bind(Bindings.createObjectBinding(
                    () -> getY(alignment, areaProperty.get().getHeight()),
                    areaProperty
            ));
        }
    }

    /**
     * Binds X and Y properties to a position that anchors the selected corner to the given alignment.
     * For example, a rectangle with a <tt>TOP_RIGHT</tt> alignment sets its top right corner as
     * its origin point.
     * @param xProperty X coordinate property to bind
     * @param yProperty Y coordinate property to bind
     * @param widthProperty width property of the node
     * @param heightProperty height property of the node
     * @param alignment position to bind to, relative to the scene
     */
    public static void bindCornerAlignment(DoubleProperty xProperty, DoubleProperty yProperty,
                                           ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty,
                                           Pos alignment) {
        switch (alignment.getHpos()) {
            case CENTER:
                xProperty.bind(widthProperty.divide(-2));
                break;
            case RIGHT:
                xProperty.bind(widthProperty.negate());
                break;
        }

        switch (alignment.getVpos()) {
            case CENTER:
                yProperty.bind(heightProperty.divide(-2));
                break;
            case BOTTOM:
                yProperty.bind(heightProperty.negate());
                break;
        }
    }
}
