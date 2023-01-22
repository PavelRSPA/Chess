package bin.figures;

import bin.field.Field;
import bin.field.Node;
import bin.field.Path;

import java.util.ArrayList;

public class Bishop extends Figure {
    public Bishop(Color color, Node node, FigureType figureType, Character character) {
        super(color, node, figureType, character);
    }
    @Override
    public ArrayList<Node> getPossibleMoves() {
        ArrayList<Node> result = new ArrayList<>();
        for (Path path : Field.diagonalPaths) {
            Node node = this.node.getNode(path);
            while (node != null && node.getData() == null) {
                result.add(node);
                node = node.getNode(path);
            }
            if (node != null) {
                if (node.getData().color != color) {
                    result.add(node);
                }
            }
        }
        return result;
    }
}
