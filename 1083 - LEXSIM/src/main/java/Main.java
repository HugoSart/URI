import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * User: hugo_<br/>
 * Date: 21/02/2018<br/>
 * Time: 18:28<br/>
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scan = new Scanner(new File("input"));

        WordBase base = new WordBase();
        Lexer analyser = new Lexer(base);
        Parser parser = new Parser(base);

        while (scan.hasNext()) {
            String line = scan.nextLine();
            if (!analyser.parse(line))
                System.out.println("Lexical Error!");

            try {
                System.out.println(parser.parse(line));
            } catch (Exception e) {
                System.out.println("Syntax Error!");
            }

        }

    }

}

class WordBase {

    private Set<Character> operators;
    private Set<Character> parentheses;
    private Set<Character> operands;

    private Map<Character, Integer> operatorsPriority;

    public WordBase() {
        operatorsPriority = new HashMap<>();
        initOperands();
        initParentheses();
        initOperators();
    }

    public boolean contains(char c) {
        return operands.contains(c) || parentheses.contains(c) || operators.contains(c);
    }

    public int getPriority(char c) {
        return operatorsPriority.get(c);
    }

    public boolean isOperator(char c) {
        return operators.contains(c);
    }

    public boolean isOperand(char c) {
        return operands.contains(c);
    }

    public boolean isParentheses(char c) {
        return parentheses.contains(c);
    }

    private void initOperators() {
        operators = new HashSet<>();
        operators.add('^');
        operatorsPriority.put('^', 6);
        operators.add('*');
        operatorsPriority.put('*', 5);
        operators.add('/');
        operatorsPriority.put('/', 5);
        operators.add('+');
        operatorsPriority.put('+', 4);
        operators.add('-');
        operatorsPriority.put('-', 4);
        operators.add('>');
        operatorsPriority.put('>', 3);
        operators.add('<');
        operatorsPriority.put('<', 3);
        operators.add('=');
        operatorsPriority.put('=', 3);
        operators.add('#');
        operatorsPriority.put('#', 3);
        operators.add('.');
        operatorsPriority.put('.', 2);
        operators.add('|');
        operatorsPriority.put('|', 1);
    }

    private void initOperands() {
        operands = new HashSet<>();
        for (int i = 0; i < 9; i++)
            operands.add(Character.forDigit(i, 10));
        for (int i = 65; i <= 90; i++)
            operands.add((char) i);
        for (int i = 97; i <= 122; i++)
            operands.add((char) i);
    }

    private void initParentheses() {
        parentheses = new HashSet<>();
        parentheses.add('(');
        parentheses.add(')');
        operatorsPriority.put('(', 999);
        operatorsPriority.put(')', 999);
    }

}

class Lexer {

    private WordBase base;

    public Lexer(WordBase base) {
        this.base = base;
    }

    public boolean parse(String str) {

        for (char c : str.toCharArray())
            if (!base.contains(c))
                return false;

        return true;

    }


}

class Parser {

    private WordBase base;
    private int pCounter = 0;
    public boolean debug = false;

    public Parser(WordBase base) {
        this.base = base;
    }

    public String parse(String line) throws Exception {
        if (!analyseSyntax(line)) throw new Exception("Syntax Error!");
        return organize(line);
    }

    private boolean analyseSyntax(String line) {
        pCounter = 0;

        boolean parsing = true;

        Character last = null;
        for (Character c : line.toCharArray()) {

            if (last == null) {
                parsing = afterOperator(c);
                if (debug) System.out.println("Checking '" + c + "': " + parsing);
            } else if (base.isOperand(last)) {
                parsing = afterOperand(c);
                if (debug) System.out.println("Checking '" + c + "': " + parsing);
            } else if (base.isOperator(last)) {
                parsing = afterOperator(c);
                if (debug) System.out.println("Checking '" + c + "': " + parsing);
            } else if (last == '(') {
                parsing = afterOpenParentheses(c);
                if (debug) System.out.println("Checking '" + c + "': " + parsing);
            } else if (last == ')') {
                parsing = afterCloseParentheses(c);
                if (debug) System.out.println("Checking '" + c + "': " + parsing);
            }

            if (!parsing)
                return false;

            last = c;
        }

        if (debug) System.out.println("Checking parentheses: " + pCounter);
        return pCounter == 0;
    }

    private String organize(String line) {
        char[] word = line.toCharArray();
        for (int j = 0; j < line.length() - 1; j++)
            for (int i = 0; i < line.length() - 1; i++) {
                if (base.isOperator(word[i])) {
                    if (base.isOperand(word[i + 1])
                            || base.isOperator(word[i + 1])
                            && base.getPriority(word[i]) < base.getPriority(word[i + 1]))
                        swap(word, i, i + 1);

                }
            }
        return new String(word);
    }

    private boolean afterOpenParentheses(char c) {
        return base.isOperand(c);
    }

    private boolean afterCloseParentheses(char c) {
        return base.isOperator(c);
    }

    private boolean afterOperand(char c) {
        if (c == ')') pCounter--;
        return (base.isOperator(c) || c == ')') && pCounter >= 0;
    }

    private boolean afterOperator(char c) {
        if (c == '(') pCounter++;
        return base.isOperand(c) || c == '(';
    }

    private void swap(char[] word, int i1, int i2) {
        char aux = word[i1];
        word[i1] = word[i2];
        word[i2] = aux;

    }

}
