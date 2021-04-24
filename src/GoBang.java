import java.io.IOException;

import board.Board;

public class GoBang {
    public static void main(String[] args) throws IOException, ArrayIndexOutOfBoundsException {
        Board gb = new Board(false);// 新建一个尺寸为9的棋盘
        gb.startGame();// 开始游戏
    }
}


