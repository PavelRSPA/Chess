package bin.figures;

import bin.field.Field;
import bin.field.Node;
import bin.field.Path;

import java.util.ArrayList;

public class Horse extends Figure {

    public Horse(Color color, Node node, FigureType figureType, Character character) {
        super(color, node, figureType, character);
    }

    private void method(Path[] paths1, Path[] paths2, Node node, ArrayList<Node> result){
        for (Path path : paths1) {
            Node temp = node.getNode(path);
            if (temp != null) {
                temp = node.getNode(path);
                if (temp != null) {
                    for (Path path1 : paths2) {
                        Node temp1 = temp.getNode(path1);
                        if (temp1 != null && temp1.getData() != null && temp1.getData().color != color) {
                            result.add(temp1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public ArrayList<Node> getPossibleMoves() {
        Node node = this.node;
        ArrayList<Node> result = new ArrayList<>();
        Path[] paths1 = new Path[] {Path.top, Path.bottom};
        Path[] paths2 = new Path[] {Path.left, Path.right};
        method(paths1, paths2, node, result);
        method(paths2, paths1, node, result);
        return result;
    }
}
