package bin.field;

import bin.figures.Figure;
import bin.figures.FigureType;

public class Node {
    protected Node[] nodes;
    protected Figure data;

    public Figure getData() {
        return data;
    }

    public void setData(Figure data) {
        this.data = data;
    }

    public Node getNode(Path path) {
        return this.nodes[path.ordinal()];
    }

    public Node(Figure data) {
        this.nodes = new Node[8];
        this.data = data;
    }
    public String toString() {
        if (data == null) {
            return '\u26C6'+" ";
        }
        return data+" ";
    }
}
