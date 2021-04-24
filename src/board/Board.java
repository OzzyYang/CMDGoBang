package board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import robot.*;

public class Board {
    private int BOARD_SIZE;// 棋盘大小
    private String[][] board;// 声明存放棋盘的数组
    private String board_elements = "╋";// 棋盘元素
    private String[] piece = { "○", "●" };// 棋子元素
    private String[] player = { "黑方", "白方" };
    private int round_tag = 0;// 用来标记回合，0表示黑方回合，1表示白方回合
    private boolean test_tag;// 用于标记是不是测试程序

    /* 初始化棋盘并打印出来 */
    public Board(boolean isTest) {
        this.BOARD_SIZE = 9;
        this.test_tag = isTest;// 测试标记
        this.initBoard();
    }

    /* 初始化棋盘 */
    public void initBoard() {
        board = new String[BOARD_SIZE + 1][BOARD_SIZE + 1];// 第0行和第0列放置坐标，这样数组下标就与用户输入实际坐标一致
        for (int i = 0; i < BOARD_SIZE + 1; i++) {
            for (int j = 0; j < BOARD_SIZE + 1; j++) {

                if (i == 0 && j == 0) {// 0,0存放空格
                    board[i][j] = " ";
                } else if (i == 0 && j != 0) {
                    board[i][j] = "" + j;// 0，y存放y坐标
                } else if (i != 0 && j == 0) {
                    board[i][j] = "" + i;// x,0存放x坐标
                } else {
                    board[i][j] = board_elements;// 其它地方存放棋盘
                }
            }
            if (test_tag == true) {
                this.test();
            }
        }
    }

    /* 用于测试 */
    public void test() {
        board[6][5] = board[7][5] = board[7][4] = board[7][6] = board[5][4] = piece[0];
        board[5][6] = board[5][7] = board[5][8] = piece[0];
        board[8][5] = piece[1];
    }

    /* 打印输出棋盘 */
    public void printBoard() {
        for (int i = 0; i < BOARD_SIZE + 1; i++) {
            for (int j = 0; j < BOARD_SIZE + 1; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();// 每打印十个元素换行
        }
        System.out.println("------------------");
    }

    /* 开始游戏 */
    public void startGame() throws IOException, ArrayIndexOutOfBoundsException {
        System.out.println("------------------\n请输入对应数字选择游戏模式：\n1.单人模式\n2.双人模式\n------------------");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputStr = null;
        while ((inputStr = br.readLine()) != null) {
            if (inputStr.matches("[1-2]")) {// ；利用正则表达式判断输入是否合法
                if (inputStr.equals("1")) {
                    System.out.println("------------------\n*您所选择的模式为单人模式*\n------------------");
                    this.singleMode();
                } else {
                    System.out.println("------------------\n*您所选择的模式为双人模式*\n------------------");
                    this.doubleMode();
                }
            } else {
                System.out.print("输入无效，请重新输入：");
                continue;
            }
        }

    }

    /* 单人模式 */
    public void singleMode() throws IOException, ArrayIndexOutOfBoundsException {
        boolean person_first = false;
        int[] bot_pos;
        Robot bot = new Robot(board, BOARD_SIZE, board_elements, piece);

        System.out.println("请输入对应数字选择游戏难度：\n1.简单\n2.普通\n------------------");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputStr = null;
        while ((inputStr = br.readLine()) != null) {
            if (inputStr.matches("[1-2]")) {// ；利用正则表达式判断输入是否合法
                if (inputStr.equals("1")) {
                    bot.setLevel(1);
                    System.out.println("------------------\n*您选择的游戏难度为：简单*\n------------------");
                    System.out.println("请输入对应数字选择先后手：\n1.先手\n2.后手\n------------------");
                    break;
                } else {
                    bot.setLevel(2);
                    System.out.println("------------------\n*您选择的游戏难度为：普通*\n------------------");
                    System.out.println("请输入对应数字选择先后手：\n1.先手\n2.后手\n------------------");
                    break;
                }
            } else {
                System.out.print("输入无效，请重新输入：");
                continue;
            }
        }
        while ((inputStr = br.readLine()) != null) {
            if (inputStr.matches("[1-2]")) {// ；利用正则表达式判断输入是否合法
                if (inputStr.equals("1")) {
                    person_first = true;
                    bot.setPersonFirst(person_first);
                    System.out.println("------------------\n*您选择了先手行棋*\n------------------");
                    break;
                } else {
                    person_first = false;
                    bot.setPersonFirst(person_first);
                    System.out.println("------------------\n*您选择了后手行棋*\n------------------");
                    break;
                }
            } else {
                System.out.print("输入无效，请重新输入：");
                continue;
            }
        }

        this.printBoard();
        System.out.print("黑棋先行，请黑方输入坐标（格式为 x,y）：");

        while (true) {
            if (person_first) {
                while (true) {
                    if (round_tag == 0) {
                        inputStr = br.readLine();
                        if (inputStr.matches("[1-9],[1-9]")) {
                            String[] posStrArr = inputStr.split(",");
                            int xPos = Integer.parseInt(posStrArr[0]);
                            int yPos = Integer.parseInt(posStrArr[1]);
                            bot.inputPersonPos(xPos, yPos);
                            this.blackPlayChess(xPos, yPos);
                            bot.setRoundTag(round_tag);
                        } else {
                            System.out.print("输入坐标无效，请重新输入（格式为 x,y）：");
                            continue;
                        }
                    } else {
                        bot_pos = bot.playChess();
                        System.out.println("*电脑下棋坐标为(" + bot_pos[0] + "," + bot_pos[1] + ")*");
                        this.whitePlayChess(bot_pos[0], bot_pos[1]);
                        bot.setRoundTag(round_tag);
                    }
                    System.out.print("请" + player[round_tag] + "输入坐标（格式为 x,y）：");
                }
            } else {
                while (true) {
                    if (round_tag == 0) {
                        bot_pos = bot.playChess();
                        System.out.println("*电脑下棋坐标为(" + bot_pos[0] + "," + bot_pos[1] + ")*");
                        this.blackPlayChess(bot_pos[0], bot_pos[1]);
                        bot.setRoundTag(round_tag);
                    } else {
                        inputStr = br.readLine();
                        if (inputStr.matches("[1-9],[1-9]")) {
                            String[] posStrArr = inputStr.split(",");
                            int xPos = Integer.parseInt(posStrArr[0]);
                            int yPos = Integer.parseInt(posStrArr[1]);
                            bot.inputPersonPos(xPos, yPos);
                            this.whitePlayChess(xPos, yPos);
                            bot.setRoundTag(round_tag);
                        } else {
                            System.out.print("输入坐标无效，请重新输入（格式为 x,y）：");
                            continue;
                        }
                    }
                    System.out.print("请" + player[round_tag] + "输入坐标（格式为 x,y）：");
                }
            }
        }

    }

    /* 双人模式 */
    public void doubleMode() throws IOException {
        this.printBoard();
        System.out.print("黑棋先行，请黑方输入坐标（格式为 x,y）：");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputStr = null;
        while ((inputStr = br.readLine()) != null) {
            if (inputStr.matches("[1-9],[1-9]")) {// ；利用正则表达式判断输入坐标是否合法
                String[] posStrArr = inputStr.split(",");
                int xPos = Integer.parseInt(posStrArr[0]);// 接收x坐标
                int yPos = Integer.parseInt(posStrArr[1]);// 接收y坐标
                if (round_tag == 0) {// 黑方回合
                    this.blackPlayChess(xPos, yPos);
                } else {// 白方回合
                    this.whitePlayChess(xPos, yPos);
                }
                System.out.print("请" + player[round_tag] + "输入坐标（格式为 x,y）：");
            } else {
                System.out.print("输入坐标无效，请重新输入（格式为 x,y）：");
                continue;
            }
        }
    }

    /* 黑方下棋，(x,y)为下棋的坐标 */
    public void blackPlayChess(int x, int y) {
        if (board[x][y] != board_elements) {// 防止覆子
            System.out.print("落子无效，您所输入的坐标已有棋子！");

        } else {
            board[x][y] = piece[round_tag];
            this.printBoard();// 下完棋后立即打印一次棋盘
            this.isGameWin(x, y);
            round_tag++;// 黑方落子成功，进入白方回合
        }

    }

    /* 白方下棋，(x,y)为下棋的坐标 */
    public void whitePlayChess(int x, int y) {
        if (board[x][y] != board_elements) {// 防止覆子
            System.out.print("落子无效，您所输入的坐标已有棋子！");
        } else {
            board[x][y] = piece[round_tag];
            this.printBoard();// 下完棋后立即打印一次棋盘
            this.isGameWin(x, y);
            round_tag--;// 白方落子成功，进入黑方回合
        }
    }

    /* 判断落子后游戏是否结束 */
    public void isGameWin(int x, int y) {
        int comb_tag;

        // 遍历落子所在整行，看是否有连珠
        comb_tag = 0;
        for (int xPos = 1; xPos <= BOARD_SIZE; xPos++) {
            if (board[xPos][y].equals(piece[round_tag])) {
                comb_tag++;
            } else {
                comb_tag = 0;
            }
            /* 活四形成（即四子连珠且两边无阻挡时），结束游戏 */
            if (comb_tag == 4 && board[xPos - 4][y] == board_elements && board[xPos + 1][y] == board_elements) {
                System.out.println("游戏结束，" + player[round_tag] + "获得胜利！");
                System.exit(0);
            }
            if (comb_tag == 5) {// 五子连珠时，结束游戏
                System.out.println("五子连珠，" + player[round_tag] + "获得胜利！");
                System.exit(0);
            }
        }

        // 遍历落子所在整列，看是否有连珠
        comb_tag = 0;
        for (int yPos = 1; yPos < BOARD_SIZE; yPos++) {
            if (board[x][yPos].equals(piece[round_tag])) {
                comb_tag++;
            } else {
                comb_tag = 0;
            }
            /* 活四形成（即四子连珠且两边无阻挡时），结束游戏 */
            if (comb_tag == 4 && board[x][yPos - 4] == board_elements && board[x][yPos + 1] == board_elements) {
                System.out.println("游戏结束，" + player[round_tag] + "获得胜利！");
                System.exit(0);
            }
            if (comb_tag == 5) {// 五子连珠时，结束游戏
                System.out.println("五子连珠，" + player[round_tag] + "获得胜利！");
                System.exit(0);
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
            if (board[xPos][yPos].equals(piece[round_tag])) {
                comb_tag++;
            } else {
                comb_tag = 0;
            }

            /* 活四形成（即四子连珠且两边无阻挡时），结束游戏 */
            if (comb_tag == 4 && board[xPos + 4][yPos - 4] == board_elements
                    && board[xPos - 1][yPos + 1] == board_elements) {
                System.out.println("游戏结束，" + player[round_tag] + "获得胜利！");
                System.exit(0);
            }

            // 如果comb_tag为5，则五子连珠结束游戏
            if (comb_tag == 5) {
                System.out.println("五子连珠，" + player[round_tag] + "获得胜利！");
                System.exit(0);
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
            if (board[xPos][yPos].equals(piece[round_tag])) {
                comb_tag++;
            } else {
                comb_tag = 0;
            }
            /* 活四形成（即四子连珠且两边无阻挡时），结束游戏 */
            if (comb_tag == 4 && board[xPos - 4][yPos - 4] == board_elements
                    && board[xPos + 1][yPos + 1] == board_elements) {
                System.out.println("游戏结束，" + player[round_tag] + "获得胜利！");
                System.exit(0);
            }
            // 如果comb_tag为5，则五子连珠结束游戏
            if (comb_tag == 5) {
                System.out.println("五子连珠，" + player[round_tag] + "获得胜利！");
                System.exit(0);
            }
            // 坐标定位到下一个元素
            xPos++;
            yPos++;
        }
    }
}
