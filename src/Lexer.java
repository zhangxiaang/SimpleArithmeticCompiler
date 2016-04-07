import java.util.Scanner;

/**
 * Created by zhangxiaang on 16/4/6.
 */
public class Lexer {
    public static final int EOF = 0;//end of file
    public static final int SEMI = 1;//;
    public static final int PLUS = 2;//+
    public static final int TIMES = 3;//*
    public static final int DEVIDE = 7;// /
    public static final int DECRESS = 8;// -
    public static final int LP = 4;
    public static final int RP = 5;
    public static final int NUM_OR_ID = 6;//digit or Alphabit


    private int lookAhead = -1;//当前指针
    private String current = "";//current 表示当前的terminal内的输入内容
    private String input_buffer = "";//缓存terminal输入的内容
    public String text = "";
    private int yylength = 0;

    //entry of this file
    public void runLexer() {
        while (!match(EOF)) {
            System.out.println("Token: " + token() + " Symbol:" + text);
            advance();
        }
    }

    private String token() {
        String token = "";
        switch (lookAhead) {
            case EOF:
                token = "EOF";
                break;
            case PLUS:
                token = "PLUS";
                break;
            case TIMES:
                token = "TIMES";
                break;
            case LP:
                token = "LP";
                break;
            case RP:
                token = "RP";
                break;
            case SEMI:
                token = "SEMI";
                break;
            case NUM_OR_ID:
                token = "NUM_OR_ID";
                break;
            case DEVIDE:
                token = "DEVIDE";
                break;
            case DECRESS:
                token = "DECRESS";
                break;
        }
        return token;
    }

    private void advance() {
        lookAhead = lex();//向前推进?
    }

    private boolean match(int token) {
        if (lookAhead == -1) {
            lookAhead = lex();
        }
        return token == lookAhead;
    }

    private int lex() {

        while (true) {
            while (current == "") {
                Scanner scanner = new Scanner(System.in);//等待输入
                while (true) {
                    String line = scanner.nextLine();
                    if (line.equals("end")) { //end 作为输入结束的标志
                        break;
                    }
                    input_buffer += line;
                }
                scanner.close();
                if (input_buffer.length() == 0) { // 表示什么都没输入
                    current = "";
                    return EOF;
                }
                current = input_buffer;
                current.trim();
            }
            //开始遍历处理 terminal 内输入的内容 每一个字符
            for (int i = 0; i < current.length(); i++) {
                char c = current.charAt(i);
                text = current.substring(0, 1);
                yylength = 0; //每次进来都应该初始化一下
                switch (c) {
                    case ';':
                        current = current.substring(1);
                        return SEMI; // 此处是直接跳出while(true)循环结束函数栈的
                    case '+':
                        current = current.substring(1);
                        return PLUS;
                    case '*':
                        current = current.substring(1);
                        return TIMES;
                    case '(':
                        current = current.substring(1);
                        return LP;
                    case ')':
                        current = current.substring(1);
                        return RP;
                    case '-':
                        current = current.substring(1);
                        return DECRESS;
                    case '/':
                        current = current.substring(1);
                        return DEVIDE;
                    case '\n':
                    case '\t':
                    case ' ':
                        current = current.substring(1);
                        break;
                    default:
                        if (!isNumOrAlphabit(c)) {
                            System.out.println("输入的字符有误" + c);
                        } else {
                            //需要判断连续输入的string是否是一个int
                            while (isNumOrAlphabit(current.charAt(i))) {
                                i++;
                                yylength++;
                            }
                            text = current.substring(0, yylength);
                            current = current.substring(yylength);
                            return NUM_OR_ID;
                        }
                        break;
                }

            }
        }
    }

    private boolean isNumOrAlphabit(char c) {
        if (Character.isAlphabetic(c) || Character.isDigit(c)) {
            return true;
        }
        return false;
    }
}
