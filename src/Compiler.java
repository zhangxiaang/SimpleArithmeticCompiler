/**
 * Created by zhangxiaang on 16/4/6.
 */
public class Compiler {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
//        lexer.testLexer();
        BasicParser parser = new BasicParser(lexer);
        CommonParser parser1 = new CommonParser(lexer);
        parser1.stms();
//        parser.stms();

    }
}
