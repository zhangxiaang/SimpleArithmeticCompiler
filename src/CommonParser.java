/**
 * Created by zhangxiaang on 16/4/7.
 */
public class CommonParser {
    private Lexer lexer;
    String[] backupNames = {"t0", "t1", "t2", "t3", "t4", "t5", "t6",
            "t7", "t8", "t9", "t10", "t11", "t12", "t13", "t14", "t15"};
    private int nameP = 0;

    public CommonParser(Lexer lexer) {
        this.lexer = lexer;
    }

    private String allocName() {
        if (nameP > backupNames.length) {
            System.out.println("Expression too complex...");
            System.exit(1);
        }
        String reg = backupNames[nameP];
        nameP++;
        return reg;
    }

    private void freeNames(String name) {
        if (nameP > 0) {
            backupNames[nameP] = name;
            nameP--;
        } else
            System.out.println("Internal error");
    }

    public void stms() {
        String tempName = allocName();
        expt(tempName);

        while (lexer.match(Lexer.EOF)) {
            expt(tempName);
//            freeNames(tempName);

            if (lexer.match(Lexer.SEMI)) {
                lexer.next();
            } else
                System.out.println("Inserting missing semicolon");
        }
    }

    private void expt(String tempName) {
        String tempName2;
        term(tempName);
        while (lexer.match(Lexer.PLUS)) {
            lexer.next();
            tempName2 = allocName();
            term(tempName2);
            System.out.println(tempName + " += " + tempName2);
//            freeNames(tempName2);
        }
        while (lexer.match(Lexer.DECRESS)) {
            lexer.next();
            tempName2 = allocName();
            term(tempName2);
            System.out.println(tempName + " -= " + tempName2);
//            freeNames(tempName2);
        }
    }

    private void term(String tempName) {
        String tempName2;
        factor(tempName);
        while (lexer.match(Lexer.TIMES)) {
            lexer.next();
            tempName2 = allocName();
            factor(tempName2);
            System.out.println(tempName + " *= " + tempName2);
//            freeNames(tempName2);
        }
        while (lexer.match(Lexer.DEVIDE)) {
            lexer.next();
            tempName2 = allocName();
            factor(tempName2);
            System.out.println(tempName + " /= " + tempName2);
//            freeNames(tempName2);
        }
    }

    private void factor(String tempName) {
        if (lexer.match(Lexer.NUM_OR_ID)) {
            System.out.println(tempName + " = " + lexer.tempText);
            lexer.next();
        } else if (lexer.match(Lexer.LP)) {
            lexer.next();
            expt(tempName);
            if (lexer.match(Lexer.RP)) {
                lexer.next();
            } else System.out.println("Missing )");
        } else System.out.println("illegal symbol");
    }
}