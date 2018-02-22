/** Resolução do problema "Balanço de Parenteses I" de código 1068
 *  @link https://www.urionlinejudge.com.br/judge/pt/problems/view/1068
 *  Código desenvolvido por Hugo Vinícius Sartori **/

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        while (scan.hasNext()) {

            String str = scan.nextLine();

            boolean resp = parenthesesCheck(str);

            if (resp)   System.out.println("correct");
            else        System.out.println("incorrect");

        }

    }

    private static boolean parenthesesCheck(String str) {

        int count = 0;

        for (char c : str.toCharArray()) {
            if      (count < 0) break;
            else if (c == '(' ) count++;
            else if (c == ')' ) count--;
        }

        return count == 0;

    }

}
