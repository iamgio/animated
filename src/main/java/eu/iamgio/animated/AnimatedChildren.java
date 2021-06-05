package eu.iamgio.animated;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility interface that represents a container whose children should be animated.
 * @author Giorgio Garofalo
 */
public interface AnimatedChildren {

    // This set contains hash code of nodes whose animation should be skipped.
    Set<Integer> skipNodesHash = new HashSet<>();

    /**
     * Registers the listener.
     * @param children target observable list
     * @param animationIn in animation
     * @param animationOut out animation
     */
    static void register(ObservableList<Node> children, Animation animationIn, Animation animationOut) {
        children.addListener((ListChangeListener<? super Node>) change -> {
            while(change.next()) {
                change.getAddedSubList().forEach(child -> {
                    if(!skipNodesHash.contains(child.hashCode())) {
                        animationIn.playIn(child, null);
                    } else {
                        skipNodesHash.remove(child.hashCode());
                    }
                });
                change.getRemoved().forEach(child -> {
                    if(skipNodesHash.contains(child.hashCode())) {
                        skipNodesHash.remove(child.hashCode());
                        return;
                    }
                    skipNodesHash.add(child.hashCode());
                    Platform.runLater(() -> {
                        children.add(child);
                        skipNodesHash.add(child.hashCode());
                        animationOut.playOut(child, children);
                    });
                });
            }
        });
    }
}