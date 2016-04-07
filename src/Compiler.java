/**
 * Created by zhangxiaang on 16/4/6.
 */
public class Compiler {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        BasicParser parser = new BasicParser(lexer);
        parser.stms();
//        lexer.testLexer();
    }
}
