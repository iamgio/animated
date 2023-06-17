package eu.iamgio.animated.binding;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * Parent node that has only one child
 * @author Giorgio Garofalo
 */
public class SingleChildParent extends Parent {

    protected final ObjectProperty<Node> child = new SimpleObjectProperty<>();

    protected SingleChildParent() {
        // Registers child listener
        this.child.addListener((observable, oldChild, newChild) -> {
            if (newChild != null) {
                getChildren().setAll(newChild);
            } else {
                getChildren().clear();
            }
        });
    }

    protected SingleChildParent(Node child) {
        this();
        this.child.set(child);
    }

    /**
     * @return the current child
     */
    public ObjectProperty<Node> childProperty() {
        return this.child;
    }

    /**
     * @return the current child
     */
    public Node getChild() {
        return this.child.get();
    }

    /**
     * Sets the new child.
     * @param child new child. If <tt>null</tt>, the current child is removed and nothing is added.
     */
    public void setChild(Node child) {
        this.childProperty().set(child);
    }
}
