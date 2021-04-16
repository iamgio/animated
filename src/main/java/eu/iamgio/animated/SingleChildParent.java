package eu.iamgio.animated;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * Parent node that has only one child
 * @author Giorgio Garofalo
 */
class SingleChildParent extends Parent {

    protected final SimpleObjectProperty<Node> child = new SimpleObjectProperty<>();

    protected SingleChildParent(Node child) {
        // Registers child listener
        this.child.addListener((observable, oldChild, newChild) -> {
            if(newChild != null) {
                getChildren().setAll(newChild);
            } else {
                getChildren().clear();
            }
        });
        this.child.set(child);
    }

    /**
     * @return current child
     */
    public Node getChild() {
        return child.get();
    }

    /**
     * @param child child to set
     */
    public void setChild(Node child) {
        this.child.set(child);
    }
}
