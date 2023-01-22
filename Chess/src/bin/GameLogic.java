package bin;

import bin.field.Field;
import bin.field.Node;
import bin.figures.Color;
import bin.figures.Figure;
import bin.figures.FigureType;
import bin.figures.Pawn;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class GameLogic {
    Field field;
    Player black;
    Player white;

    Player currentPlayer;
    Player nextPlayer;
    protected static final Random random = new Random();
    private void turn(Player player) throws Exception {
        System.out.println(field);
        Figure figure = player.getFigure();
//        if (figure.isPawn()) {
//            Pawn pawn = (Pawn) figure;
//            pawn.move(2);
//        }
        ArrayList<Node> posMoves = figure.getPossibleMoves();
        //System.out.println(posMoves.size());
        int index = random.nextInt(posMoves.size());
        //System.out.println(index);
        figure.move(posMoves.get(index));
        this.field.renderMoves(figure);
        //this.field.renderPath(figure.getPossibleMoves(), figure);
    }

    private boolean afterTurn() {
        for (int i = 0; i < this.currentPlayer.figures.size(); i++) {
            Figure figure = this.currentPlayer.figures.get(i);
            if (figure.type == FigureType.KING) {
                if (figure.isEaten()) {
                    if (figure.color == Color.BLACK) {
                        System.out.println("Победили белые");
                    } else {
                        System.out.println("Победили чёрные");
                    }
                    return true;
                }
            }
        }

        for (int i = 0; i < this.nextPlayer.figures.size(); i++) {
            Figure figure = this.nextPlayer.figures.get(i);
            if (figure.isEaten()) {
                this.nextPlayer.figures.remove(i);
                i--;
            }
        }
        return false;
    }

    private void gameCycle() throws Exception {
        this.currentPlayer = this.white;
        this.nextPlayer = this.black;

        boolean game = true;
        Scanner sc = new Scanner(System.in);
        while (game) {

            turn(this.currentPlayer);
            if (afterTurn()) {
                break;
            }

            Player temp = this.currentPlayer;
            this.currentPlayer = this.nextPlayer;
            this.nextPlayer = temp;
            // System.out.println(this.field);
            String s = sc.next();
            if (Objects.equals(s, "stop")) {
                game = false;
            }
        }
    }

    private void startOptions() {
        this.black = new Player(new ArrayList<Figure>());
        this.white = new Player(new ArrayList<Figure>());
        this.field = new Field();
        this.field.setStartField(this.white.figures, this.black.figures);
    }

    public void start() throws Exception {
        gameCycle();
    }

    public GameLogic() {
        startOptions();
    }
}
