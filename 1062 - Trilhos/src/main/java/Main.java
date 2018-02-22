/**
 * Resolução do problema "Trilhos" de código 1062
 *
 * @link https://www.urionlinejudge.com.br/judge/pt/problems/view/1062
 * @author Hugo Vinícius Sartori
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        WHILE:
        while (scan.hasNext()) {

            int size = scan.nextInt();
            if (size == 0) continue;

            Integer target[] = new Integer[size];
            Integer next;

            DO:
            do {
                for (int i = 0; i < size; i++)
                    if ((next = scan.nextInt()) != 0) target[i] = next;
                    else break DO;
                if (check(target)) System.out.println("Yes");
                else System.out.println("No");
            } while (true);

            System.out.println();

        }

    }

    public static boolean check(Integer[] target) {

        Integer[] wagons = init(target.length);

        Stack<Integer> wA = new Stack<>();
        Stack<Integer> wM = new Stack<>();
        Stack<Integer> wB = new Stack<>();

        for (int i = 0; i < wagons.length; i++)
            wA.push(wagons[i]);

        /*System.out.println("Initial");
        System.out.println("  A: " + wA);
        System.out.println("  M: " + wM);
        System.out.println("  B: " + wB);
        System.out.println();*/

        int count = 0;
        while (wB.size() < wagons.length) {

            Integer last = target[count];

            if (wM.contains(last))
                if (wM.peek().equals(last)) move(wM, wB);
                else return false;
            else {
                for (int i = target.length - wA.size(); i < last; i++)
                    move(wA, wM);
                move(wM, wB);
            }

            /*System.out.println("Iteration " + count + " -> Last: " + last);
            System.out.println("  A: " + wA);
            System.out.println("  M: " + wM);
            System.out.println("  B: " + wB);
            System.out.println();*/

            count++;

        }

        /*System.out.println("Final:  " + wB);
        System.out.print("Target: ");
        print(target);
        System.out.println();*/

        return Arrays.equals(wB.toArray(), target);

    }

    private static Integer[] init(int size) {
        Integer[] vet = new Integer[size];
        for (int i = 0; i < size; i++)
            vet[i] = size - i;
        return vet;
    }

    private static void move(Stack<Integer> s1, Stack<Integer> s2) {
        s2.push(s1.pop());
    }

    private static void print(Integer[] array) {
        System.out.print("[");
        for (Integer integer : array) {
            System.out.print(integer);
            if (array[array.length - 1] != integer)
                System.out.print(", ");
        }
        System.out.print("]");
    }

}
