package bin;

import bin.figures.Figure;

import java.util.List;

public class Player {
    protected List<Figure> figures;

    public Figure getFigure() {
        int n = GameLogic.random.nextInt(figures.size());
        //System.out.println(n);
        Figure figure = getFigure(n);
        while (figure.getPossibleMoves().size() == 0) {
            n = GameLogic.random.nextInt(figures.size());
            figure = getFigure(n);
        }
        return figure;
    }

    public Figure getFigure(int n) {
        return figures.get(n);
    }

    public Player(List<Figure> figures) {
        this.figures = figures;
    }
}

