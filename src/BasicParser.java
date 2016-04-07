/**
 * Created by zhangxiaang on 16/4/7.
 * the parsing step is statements->
 * expression->term->factor.
 * and be careful the prime of exp,term
 */
public class BasicParser {
    private Lexer lexer;
    private boolean isLegalStm = true;

    public BasicParser(Lexer lexer) {
        this.lexer = lexer;
    }

    //entry of parsing
    public void stms() {
        exp();
        if (lexer.match(Lexer.SEMI)) {
            lexer.next();
        } else {
            isLegalStm = false;
            System.out.println("Missing semicolon");
            return;
        }

        if (!lexer.match(Lexer.EOF)) {
            //分号后还有statements
            stms();
        }

        if (isLegalStm) {
            System.out.println("the statements is legal");
        }
    }

    private void exp() {
        term();
        exp_prime();
    }

    private void exp_prime() {
        if (lexer.match(Lexer.PLUS) || lexer.match(Lexer.DECRESS)) {
            lexer.next();
            term();
            exp_prime();
        } else if (lexer.match(Lexer.UNKNOWN)) {
            isLegalStm = false;
            System.out.println("Unknown symbol:" + lexer.tempText);
        }
    }

    private void term() {
        factor();
        term_prime();
    }

    private void term_prime() {
        if (lexer.match(Lexer.TIMES) || lexer.match(Lexer.DEVIDE)) {
            lexer.next();
            factor();
            term_prime();
        }
    }

    private void factor() {
        if (lexer.match(Lexer.NUM_OR_ID)) {
            lexer.next();
        } else if (lexer.match(Lexer.LP)) {
            lexer.next();
            exp();//why  here have to call exp()?
            if (lexer.match(Lexer.RP)) {
                lexer.next();
            } else {
                isLegalStm = false;
                System.out.println("Missing )");
            }
        } else {
            isLegalStm = false;
            System.out.println("illegal statements");
        }
    }
}
