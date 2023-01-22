package bin.field;

public enum Path {
    top,
    topRight,
    right,
    bottomRight,
    bottom,
    bottomLeft,
    left,
    topLeft;
    public static boolean isStraightPath(Path path) {
        return (path == top || path == right || path == bottom || path == left);
    }
}
