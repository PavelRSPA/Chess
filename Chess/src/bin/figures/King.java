package bin.figures;

import bin.field.Field;
import bin.field.Node;
import bin.field.Path;

import java.util.ArrayList;

public class King extends Figure {
    public King(Color color, Node node, FigureType figureType, Character character) {
        super(color, node, figureType, character);
    }
    @Override
    public ArrayList<Node> getPossibleMoves() {
        ArrayList<Node> result = new ArrayList<>();
        for (Path path : Field.straightPaths) {
            Node node = this.node.getNode(path);
            if (node != null && node.getData() == null) {
                result.add(node);
            } else if (node != null && node.getData() != null && node.getData().color != color) {
                result.add(node);
            }
        }
        for (Path path : Field.diagonalPaths) {
            Node node = this.node.getNode(path);
            if (node != null && node.getData() == null) {
                result.add(node);
            } else if (node != null && node.getData() != null && node.getData().color != color) {
                result.add(node);
            }
        }
        return result;
    }
}
