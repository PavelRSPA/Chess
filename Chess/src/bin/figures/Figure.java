package bin.figures;

import bin.field.Node;

import java.util.ArrayList;

public abstract class Figure {

    public final Color color;
    public final FigureType type;
    protected boolean isEaten = false;
    protected Node node;
    public final Character character;

    public void move(Node toNode) {
        if (toNode.getData() != null && toNode.getData().color != color) {
            toNode.getData().isEaten = true;
        }
        this.node.setData(null);
        toNode.setData(this);
        this.node = toNode;
    }

    public Figure(Color color, Node node, FigureType figureType, Character character) {
        this.color = color;
        this.node = node;
        this.type = figureType;
        this.character = character;
    }

    public ArrayList<Node> getPossibleMoves() {
        System.err.println("Wrong generate moves method");
        return null;
    }

    public final boolean isEaten() { return isEaten; }
    public final boolean isPawn() { return this.type == FigureType.PAWN; }
    public final boolean isKing() { return this.type == FigureType.KING; }
    public final boolean isRook() { return this.type == FigureType.ROOK; }
    public final boolean isBishop() { return this.type == FigureType.BISHOP; }
    public final boolean isQueen() { return this.type == FigureType.QUEEN; }
    public final boolean isHORSE() { return this.type == FigureType.HORSE; }

    public final String toString() {
        return character.toString();
    }
}
