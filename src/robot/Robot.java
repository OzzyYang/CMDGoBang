package robot;

public class Robot {
    private int level;
    private String[][] board;
    private int BOARD_SIZE;
    private String board_elements;
    private String[] piece;
    private int piece_tag;
    private int round_tag = 0;
    private boolean person_first;
    private int[] person_Pos = { 0, 0 };// 0,0表示用户没有下棋，即此时机器人先手

    public Robot(String[][] board, int BOARD_SIZE, String board_elements, String[] piece) {
        this.board = board;
        this.board_elements = board_elements;
        this.piece = piece;
        this.BOARD_SIZE = BOARD_SIZE;
    }

    public void setPersonFirst(boolean person_first) {
        this.person_first = person_first;
        if (person_first) {
            this.piece_tag = 0;
        } else {
            this.piece_tag = 1;
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRoundTag(int round_tag) {
        this.round_tag = round_tag;
    }

    public void inputPersonPos(int x, int y) {
        this.person_Pos[0] = x;
        this.person_Pos[1] = y;
    }

    public int[] playChess() throws ArrayIndexOutOfBoundsException {
        switch (level) {
            case 1: {
                return this.easyRobot();
            }
            case 2: {
                return this.normalRobot();
            }
            case 3: {
                return this.hardRobot();
            }
            default: {
                return null;
            }
        }
    }

    private int[] easyRobot() {
        int[] position = new int[2];
        while (true) {
            position[0] = (int) (Math.random() * (9 - 1) + 1);
            position[1] = (int) (Math.random() * (9 - 1) + 1);
            if (board[position[0]][position[1]].equals(board_elements)) {
                return position;
            }
        }

    }

    private int[] normalRobot() throws ArrayIndexOutOfBoundsException {
        int[] position = { 5, 5 };
        String[] bot_pos = { "" };

        if (person_Pos[0] == 0 & person_Pos[1] == 0) {// 如果机器人先手，则在在5,5下棋
            return position;
        } else {
            bot_pos = this.goThroughBoard(person_Pos[0], person_Pos[1]);
        }

        if (bot_pos[0] != "") {
            String[] str_arr = bot_pos[0].split(",");
            position[0] = Integer.parseInt(str_arr[0]);
            position[1] = Integer.parseInt(str_arr[1]);
            return position;
        }
        if (bot_pos[1] != "") {
            String[] str_arr = bot_pos[1].split(",");
            position[0] = Integer.parseInt(str_arr[0]);
            position[1] = Integer.parseInt(str_arr[1]);
            return position;
        }
        if (bot_pos[2] != "") {
            String[] str_arr = bot_pos[2].split(",");
            position[0] = Integer.parseInt(str_arr[0]);
            position[1] = Integer.parseInt(str_arr[1]);
            return position;
        }
        if (bot_pos[3] != "") {
            String[] str_arr = bot_pos[3].split(",");
            position[0] = Integer.parseInt(str_arr[0]);
            position[1] = Integer.parseInt(str_arr[1]);
            return position;
        }
        if (bot_pos[4] != "") {
            String[] str_arr = bot_pos[4].split(",");
            position[0] = Integer.parseInt(str_arr[0]);
            position[1] = Integer.parseInt(str_arr[1]);
            return position;
        }

        return position;

    }

    private int[] hardRobot() {
        int[] position = new int[2];
        return position;

    }

    private String[] goThroughBoard(int x, int y) throws ArrayIndexOutOfBoundsException {
        int comb_tag;
        String[] position = { "", "", "", "", "" };// 存储的坐标依次为四四，活三，活二，死三，其它

        // 遍历落子所在整行，看是否有连珠
        comb_tag = 0;
        for (int yPos = 1; yPos < BOARD_SIZE; yPos++) {

            if (board[x][yPos].equals(piece[piece_tag])) {
                comb_tag++;
            } else {
                comb_tag = 0;
            }

            // 两种跳四以及跳三
            if (comb_tag == 2 && board[x][yPos - 2] == board_elements && board[x][yPos + 1] == board_elements) {
                if (board[x][yPos + 2].equals(piece[piece_tag]) && board[x][yPos + 3].equals(piece[piece_tag])) {
                    position[0] = x + "," + (yPos + 1);
                }
                if (board[x][yPos - 3].equals(piece[piece_tag]) && board[x][yPos - 4].equals(piece[piece_tag])) {
                    position[0] = x + "," + (yPos - 2);
                }

            }
            if (comb_tag == 1 && board[x][yPos - 1] == board_elements && board[x][yPos + 1] == board_elements) {
                if (board[x][yPos + 2].equals(piece[piece_tag]) && board[x][yPos + 3].equals(piece[piece_tag])
                        && (board[x][yPos + 4].equals(piece[piece_tag]) || board[x][yPos + 4] == board_elements)) {
                    position[0] = x + "," + (yPos + 1);
                }
                if (board[x][yPos - 2].equals(piece[piece_tag]) && board[x][yPos - 3].equals(piece[piece_tag])
                        && (board[x][yPos - 4].equals(piece[piece_tag]) || board[x][yPos - 4] == board_elements)) {
                    position[0] = x + "," + (yPos - 1);
                }

            }

            // 冲四
            if (comb_tag == 4 && (board[x][yPos - 4] == board_elements || board[x][yPos + 1] == board_elements)) {
                if (board[x][yPos - 4] == board_elements) {
                    position[0] = x + "," + (yPos - 4);
                }
                if (board[x][yPos + 1] == board_elements) {
                    position[0] = x + "," + (yPos + 1);
                }

            }
            // 活三
            if (comb_tag == 3 && board[x][yPos - 3] == board_elements && board[x][yPos + 1] == board_elements) {
                position[1] = x + "," + (yPos + 1);
                // break;
            }
            // 活二
            if (comb_tag == 2 && board[x][yPos - 2] == board_elements && board[x][yPos + 1] == board_elements) {
                position[2] = x + "," + (yPos + 1);
                // break;
            }
            // 冲三
            if (comb_tag == 3 && (board[x][yPos - 3] == board_elements || board[x][yPos + 1] == board_elements)) {
                if (board[x][yPos - 3] == board_elements) {
                    position[3] = x + "," + (yPos - 3);
                }
                if (board[x][yPos + 1] == board_elements) {
                    position[3] = x + "," + (yPos + 1);
                }
                // break;
            }

        }

        // 遍历落子所在整列，看是否有连珠
        comb_tag = 0;
        for (int xPos = 1; xPos <= BOARD_SIZE; xPos++) {

            if (board[xPos][y].equals(piece[piece_tag])) {
                comb_tag++;
            } else {
                comb_tag = 0;
            }

            // 两种跳四以及连三
            if (comb_tag == 2 && board[xPos - 2][y] == board_elements && board[xPos + 1][y] == board_elements) {
                if (board[xPos + 2][y].equals(piece[piece_tag]) && board[xPos + 3][y].equals(piece[piece_tag])) {
                    position[0] = (xPos + 1) + "," + y;
                }
                if (board[xPos - 3][y].equals(piece[piece_tag]) && board[xPos - 4][y].equals(piece[piece_tag])) {
                    position[0] = (xPos - 2) + "," + y;
                }
            }
            if (comb_tag == 1 && board[xPos - 1][y] == board_elements && board[xPos + 1][y] == board_elements) {
                if (board[xPos + 2][y].equals(piece[piece_tag]) && board[xPos + 3][y].equals(piece[piece_tag])
                        && (board[xPos + 4][y].equals(piece[piece_tag]) || board[xPos + 4][y] == board_elements)) {
                    position[0] = (xPos + 1) + "," + y;
                }
                if (board[xPos - 2][y].equals(piece[piece_tag]) && board[xPos - 3][y].equals(piece[piece_tag])
                        && (board[xPos - 4][y].equals(piece[piece_tag]) || board[xPos - 4][y] == board_elements)) {
                    position[0] = (xPos - 1) + "," + y;
                }
            }
            // 冲四
            if (comb_tag == 4 && (board[xPos - 4][y] == board_elements || board[xPos + 1][y] == board_elements)) {
                if (board[xPos - 4][y] == board_elements) {
                    position[0] = (xPos - 4) + "," + y;
                }
                if (board[xPos + 1][y] == board_elements) {
                    position[0] = (xPos + 1) + "," + y;
                }
            }
            // 活三
            if (comb_tag == 3 && board[xPos - 3][y] == board_elements && board[xPos + 1][y] == board_elements) {
                position[1] = (xPos + 1) + "," + y;
                // break;
            }
            // 活二
            if (comb_tag == 2 && board[xPos - 2][y] == board_elements && board[xPos + 1][y] == board_elements) {
                position[2] = (xPos + 1) + "," + y;
                // break;
            }
            // 冲三
            if (comb_tag == 3 && (board[xPos - 3][y] == board_elements || board[xPos + 1][y] == board_elements)) {
                if (board[xPos - 3][y] == board_elements) {
                    position[3] = (xPos - 3) + "," + y;
                }
                if (board[xPos + 1][y] == board_elements) {
                    position[3] = (xPos + 1) + "," + y;
                }
                // break;
            }
        }

        // 遍历落子所在右斜线，看是否有连珠
        comb_tag = 0;
        int round_count = 0;
        int xPos = x, yPos = y;
        while (true) {
            /* 设置边界条件 */
            // 防止坐标溢出，溢出则退回并且从另一头开始
            if (xPos < 1 | yPos > BOARD_SIZE) {
                xPos++;
                yPos--;
                int c;
                c = xPos;
                xPos = yPos;
                yPos = c;
                round_count++;// 溢出一次，则记录一次
            }
            // 控制循环退出的条件，完整遍历后，退出循环
            if (round_count == 2) {// 当第二次溢出的时候，说明已经把棋子所在的右斜线遍历完了
                break;
            }

            // 判断连珠状态，用comb_tag记录
            if (board[xPos][yPos].equals(piece[piece_tag])) {
                comb_tag++;
            } else {
                comb_tag = 0;
            }

            // 两种跳四以及连三
            if (comb_tag == 2 && board[xPos + 2][yPos - 2] == board_elements
                    && board[xPos - 1][yPos + 1] == board_elements) {
                if (board[xPos - 2][yPos + 2].equals(piece[piece_tag])
                        && board[xPos - 3][yPos + 3].equals(piece[piece_tag])) {
                    position[0] = (xPos - 1) + "," + (yPos + 1);
                }
                if (board[xPos + 3][yPos - 3].equals(piece[piece_tag])
                        && board[xPos + 4][yPos - 4].equals(piece[piece_tag])) {
                    position[0] = (xPos + 2) + "," + (yPos - 2);
                }
            }
            if (comb_tag == 1 && board[xPos + 1][yPos - 1] == board_elements
                    && board[xPos - 1][yPos + 1] == board_elements) {
                if (board[xPos - 2][yPos + 2].equals(piece[piece_tag])
                        && board[xPos - 3][yPos + 3].equals(piece[piece_tag])
                        && (board[xPos - 4][yPos + 4].equals(piece[piece_tag])
                                || board[xPos - 4][yPos + 4] == board_elements)) {
                    position[0] = (xPos - 1) + "," + (yPos + 1);
                }
                if (board[xPos + 2][yPos - 2].equals(piece[piece_tag])
                        && board[xPos + 3][yPos - 3].equals(piece[piece_tag])
                        && (board[xPos + 4][yPos - 4].equals(piece[piece_tag])
                                || board[xPos + 4][yPos - 4] == board_elements)) {
                    position[0] = (xPos + 1) + "," + (yPos - 1);
                }

            }
            // 冲四
            if (comb_tag == 4
                    && (board[xPos + 4][yPos - 4] == board_elements || board[xPos - 1][yPos + 1] == board_elements)) {
                if (board[xPos + 4][yPos - 4] == board_elements) {
                    position[0] = (xPos + 4) + "," + (yPos - 4);
                }
                if (board[xPos - 1][yPos + 1] == board_elements) {
                    position[0] = (xPos - 1) + "," + (yPos + 1);
                }
            }
            // 活三
            if (comb_tag == 3 && board[xPos + 3][yPos - 3] == board_elements
                    && board[xPos - 1][yPos + 1] == board_elements) {
                position[1] = (xPos - 1) + "," + (yPos + 1);
                // break;
            }
            // 活二
            if (comb_tag == 2 && board[xPos + 2][yPos - 2] == board_elements
                    && board[xPos - 1][yPos + 1] == board_elements) {
                position[2] = (xPos - 1) + "," + (yPos + 1);
                // break;
            }
            // 冲三
            if (comb_tag == 3
                    && (board[xPos + 3][yPos - 3] == board_elements || board[xPos - 1][yPos + 1] == board_elements)) {
                if (board[xPos + 3][yPos - 3] == board_elements) {
                    position[3] = (xPos + 3) + "," + (yPos - 3);
                }
                if (board[xPos - 1][yPos + 1] == board_elements) {
                    position[3] = (xPos - 1) + "," + (yPos + 1);
                }
                // break;
            }

            // 坐标定位到下一个元素
            xPos--;
            yPos++;
        }

        // 遍历棋子所在左斜线，看是否有连珠
        comb_tag = 0;
        round_count = 0;
        xPos = x;
        yPos = y;
        while (true) {
            /* 设置边界条件 */
            // 防止坐标溢出，溢出则从另一边开始
            if (xPos == BOARD_SIZE + 1 && yPos == BOARD_SIZE + 1) {
                xPos--;
                yPos--;
                xPos = 1;
                yPos = 1;
                round_count++;// 溢出一次，则记录一次，下同
            } else if (yPos == BOARD_SIZE + 1) {
                xPos--;
                yPos--;
                yPos = yPos - (xPos - 1);
                xPos = 1;
                round_count++;
            } else if (xPos == BOARD_SIZE + 1) {
                xPos--;
                yPos--;
                xPos = xPos - (yPos - 1);
                yPos = 1;
                round_count++;
            } else {
                // 其它情况下则什么都不做
            }
            // 控制循环退出的条件，完整遍历后，退出循环
            if (round_count == 2) {// 当第二次溢出的时候，说明已经把棋子所在的右斜线遍历完了
                break;
            }
            // 判断连珠状态，用comb_tag记录
            if (board[xPos][yPos].equals(piece[piece_tag])) {
                comb_tag++;
            } else {
                comb_tag = 0;
            }

            // 两种跳四以及连三
            if (comb_tag == 2 && board[xPos - 2][yPos - 2] == board_elements
                    && board[xPos + 1][yPos + 1] == board_elements) {
                if (board[xPos + 2][yPos + 2].equals(piece[piece_tag])
                        && board[xPos + 3][yPos + 3].equals(piece[piece_tag])) {
                    position[0] = (xPos + 1) + "," + (yPos + 1);
                }
                if (board[xPos - 3][yPos - 3].equals(piece[piece_tag])
                        && board[xPos - 4][yPos - 4].equals(piece[piece_tag])) {
                    position[0] = (xPos - 2) + "," + (yPos - 2);
                }
            }
            if (comb_tag == 1 && board[xPos - 1][yPos - 1] == board_elements
                    && board[xPos + 1][yPos + 1] == board_elements) {
                if (board[xPos + 2][yPos + 2].equals(piece[piece_tag])
                        && board[xPos + 3][yPos + 3].equals(piece[piece_tag])
                        && (board[xPos + 4][yPos + 4].equals(piece[piece_tag])
                                || board[xPos + 4][yPos + 4] == board_elements)) {
                    position[0] = (xPos + 1) + "," + (yPos + 1);
                }
                if (board[xPos - 2][yPos - 2].equals(piece[piece_tag])
                        && board[xPos - 3][yPos - 3].equals(piece[piece_tag])
                        && (board[xPos - 4][yPos - 4].equals(piece[piece_tag])
                                || board[xPos - 4][yPos - 4] == board_elements)) {
                    position[0] = (xPos - 1) + "," + (yPos - 1);
                }
            }

            // 冲四
            if (comb_tag == 4
                    && (board[xPos - 4][yPos - 4] == board_elements || board[xPos + 1][yPos + 1] == board_elements)) {
                if (board[xPos - 4][yPos - 4] == board_elements) {
                    position[0] = (xPos - 4) + "," + (yPos - 4);
                }
                if (board[xPos + 1][yPos + 1] == board_elements) {
                    position[0] = (xPos + 1) + "," + (yPos + 1);
                }
            }
            // 活三
            if (comb_tag == 3 && board[xPos - 3][yPos - 3] == board_elements
                    && board[xPos + 1][yPos + 1] == board_elements) {
                position[1] = (xPos - 3) + "," + (yPos - 3);
                // break;
            }
            // 活二
            if (comb_tag == 2 && board[xPos - 2][yPos - 2] == board_elements
                    && board[xPos + 1][yPos + 1] == board_elements) {
                position[2] = (xPos + 1) + "," + (yPos + 1);
                // break;
            }
            // 冲三
            if (comb_tag == 3
                    && (board[xPos - 3][yPos - 3] == board_elements || board[xPos + 1][yPos + 1] == board_elements)) {
                if (board[xPos - 3][yPos - 3] == board_elements) {
                    position[3] = (xPos - 3) + "," + (yPos - 3);
                }
                if (board[xPos + 1][yPos + 1] == board_elements) {
                    position[3] = (xPos + 1) + "," + (yPos + 1);
                }
                // break;
            }
            // 坐标定位到下一个元素
            xPos++;
            yPos++;
        }

        if (position[0] == "" && position[1] == "" && position[2] == "" && position[3] == "") {
            while (true) {
                if (board[x + 1][y + 1] == board_elements) {
                    position[4] = (x + 1) + "," + (y + 1);
                    break;
                }
                x++;
            }
        }
        return position;
    }
}