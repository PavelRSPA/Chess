package bin.field;

import bin.figures.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Field {
    public static final Path[] straightPaths = new Path[]{ Path.top, Path.right, Path.bottom, Path.left };
    public static final Path[] diagonalPaths = new Path[]{ Path.topRight, Path.bottomRight,
            Path.bottomLeft, Path.topLeft };
    public static final Path[] paths = new Path[] { Path.top, Path.right, Path.bottom, Path.left,
            Path.topRight, Path.bottomRight, Path.bottomLeft, Path.topLeft };

    private final Node topLeftNode;
    public static final ArrayList<Node> clearNodes = new ArrayList<>();

    private void connectNodes (Node pred, Node temp, Path path1, Path path2, Path path3) {
        if (pred.nodes[path1.ordinal()] != null) {
            temp.nodes[path2.ordinal()] = pred.nodes[path1.ordinal()];
            pred.nodes[path1.ordinal()].nodes[path3.ordinal()] = temp;
        }
    }

    public Field() {
        this.topLeftNode = new Node(null);
        Node pred = this.topLeftNode;

        for (int i = 1; i < 8; i++) {
            pred.nodes[Path.right.ordinal()] = new Node(null);
            Node temp = pred.nodes[Path.right.ordinal()];
            temp.nodes[Path.left.ordinal()] = pred;
            pred = temp;
        }

        Node node = this.topLeftNode;
        for (int i = 1; i < 8; i++) {
            Node predpred = null;
            pred = node;
            for (int j = 0; j < 8; j++) {
                Node temp = new Node(null);

                pred.nodes[Path.bottom.ordinal()] = temp;
                temp.nodes[Path.top.ordinal()] = pred;

                connectNodes(pred, temp, Path.left, Path.topLeft, Path.bottomRight);
                connectNodes(pred, temp, Path.right, Path.topRight, Path.bottomLeft);

                temp.nodes[Path.left.ordinal()] = predpred;
                if (predpred != null) {
                    predpred.nodes[Path.right.ordinal()] = temp;
                }
                predpred = temp;
                pred = pred.nodes[Path.right.ordinal()];
            }
            node = node.nodes[Path.bottom.ordinal()];
        }
    }

    public Node getNode(int i1, int j1) {
        Node node = this.topLeftNode;
        for (int j = 0; j < 8; j++) {
            Node temp = node;
            for (int i = 0; i < 8; i++) {
                if (i == i1 && j == j1) {
                    return temp;
                }
                temp = temp.nodes[Path.right.ordinal()];
            }
            node = node.nodes[Path.bottom.ordinal()];
        }
        return null;
    }

    private static HashMap<Color, HashMap<FigureType, Character>> getMap() {
        HashMap<FigureType, Character> blackMap = new HashMap<>();
        HashMap<FigureType, Character> whiteMap = new HashMap<>();
        HashMap<Color, HashMap<FigureType, Character>> map = new HashMap<>();
        map.put(Color.BLACK, blackMap);
        map.put(Color.WHITE, whiteMap);
        blackMap.put(FigureType.PAWN, '\u2659');
        blackMap.put(FigureType.BISHOP, '\u2657');
        blackMap.put(FigureType.ROOK, '\u2656');
        blackMap.put(FigureType.QUEEN, '\u2655');
        blackMap.put(FigureType.KING, '\u2654');
        blackMap.put(FigureType.HORSE, '\u2658');
        whiteMap.put(FigureType.PAWN, '\u265F');
        whiteMap.put(FigureType.BISHOP, '\u265D');
        whiteMap.put(FigureType.ROOK, '\u265C');
        whiteMap.put(FigureType.QUEEN, '\u265B');
        whiteMap.put(FigureType.KING, '\u265A');
        whiteMap.put(FigureType.HORSE, '\u265E');
        return map;
    }

    public void setStartField(List<Figure> white, List<Figure> black){
        HashMap<Color, HashMap<FigureType, Character>> map = getMap();
        try (Scanner sc = new Scanner(new File("src/bin/field/startField.txt"), StandardCharsets.UTF_8)) {
            Node node = this.topLeftNode;
            for (int i = 0; i < 8; i++) {
                Node temp = node;
                for (int j = 0; j < 8; j++) {
                    int n = sc.nextInt();
                    Figure figure = null;
                    Color color = (i > 3) ? Color.WHITE : Color.BLACK;
                    HashMap<FigureType, Character> map1 = map.get(color);
                    switch (n) {
                        case 1 -> figure = new Rook(color, temp, FigureType.ROOK, map1.get(FigureType.ROOK));
                        case 2 -> figure = new Horse(color, temp, FigureType.HORSE, map1.get(FigureType.HORSE));
                        case 3 -> figure = new Bishop(color, temp, FigureType.BISHOP, map1.get(FigureType.BISHOP));
                        case 4 -> figure = new King(color, temp, FigureType.KING, map1.get(FigureType.KING));
                        case 5 -> figure = new Queen(color, temp, FigureType.QUEEN, map1.get(FigureType.QUEEN));
                        case 6 -> figure = new Pawn(color, temp, FigureType.PAWN, map1.get(FigureType.PAWN));
                    }
                    if (figure != null) {
                        if (i < 4) {
                            black.add(figure);
                        } else {
                            white.add(figure);
                        }
                    }
                    temp.setData(figure);
                    temp = temp.nodes[Path.right.ordinal()];
                }
                node = node.nodes[Path.bottom.ordinal()];
            }
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }

    public void renderPath(ArrayList<Node> nodes, Figure figure) {
        Node n = topLeftNode;
        while (n != null) {
            Node t = n;
            while (t != null) {
                if (t.getData() != null && t.getData().equals(figure)) {
                    System.out.print("\033[0;93m" + t.toString() + "\u001B[0m");
                } else if (nodes.contains(t)) {
                    System.out.print("\033[0;92m" + t.toString() + "\u001B[0m");
                } else {
                    System.out.print(t);
                }
                t = t.nodes[Path.right.ordinal()];
            }
            System.out.println();
            n = n.nodes[Path.bottom.ordinal()];
        }
        System.out.println();
    }

    public void renderMoves(Figure figure) {
        Node n = topLeftNode;
        while (n != null) {
            Node t = n;
            while (t != null) {
                if (t.getData() != null && t.getData().equals(figure)) {
                    System.out.print("\033[0;93m" + t.toString() + "\u001B[0m");
                } else {
                    System.out.print(t);
                }
                t = t.nodes[Path.right.ordinal()];
            }
            System.out.println();
            n = n.nodes[Path.bottom.ordinal()];
        }
        System.out.println();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node n = topLeftNode;
        while (n != null) {
            Node t = n;
            while (t != null) {
                sb.append(t);
                t = t.nodes[Path.right.ordinal()];
            }
            sb.append('\n');
            n = n.nodes[Path.bottom.ordinal()];
        }
        return sb.toString();
    }
}
