package bin.figures;

import bin.field.Node;

import bin.field.Path;

import java.util.ArrayList;

public class Pawn extends Figure {
    private final Path path;
    private boolean isFirstMove = true;
    public Pawn(Color color, Node node, FigureType figureType, Character character) {
        super(color, node, figureType, character);
        if (color == Color.BLACK) {
            path = Path.bottom;
        } else {
            path = Path.top;
        }
    }

    @Override
    public void move(Node toNode) {
        super.move(toNode);
        isFirstMove = false;
    }

    @Override
    public ArrayList<Node> getPossibleMoves() {
        Node node = this.node.getNode(path);
        ArrayList<Node> result = new ArrayList<>();
        if (node != null) {
            result.add(node);
            for (Node temp : new Node[] { node.getNode(Path.left), node.getNode(Path.right)}) {
                if (temp != null) {
                    if (temp.getData() != null && temp.getData().color != color) {
                        result.add(temp);
                    }
                }
            }
            if (isFirstMove) {
                node = node.getNode(path);
                if (node != null) {
                    result.add(node);
                }
            }
        }
        return result;
    }
}
