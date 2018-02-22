/**
 * Resolução do problema "Trilhos" de código 1062
 *
 * @link https://www.urionlinejudge.com.br/judge/pt/problems/view/1062
 * @author Hugo Vinícius Sartori
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        while (scan.hasNext()) {

            int size = scan.nextInt();
            if (size == 0) continue;

            char[] wA = new char[size];
            char[] wB = new char[size];

            for (int i = 0; i < size; i++)
                wA[i] = scan.next(".").charAt(0);
            for (int i = 0; i < size; i++)
                wB[i] = scan.next(".").charAt(0);

            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < size; i++)
                map.put(wA[i], i + 1);

            Integer[] iB = initDict(map, wB);
            check(iB);


        }

    }

    public static boolean check(Integer[] target) {

        Integer[] wagons = init(target.length);

        Stack<Integer> wA = new Stack<>();
        Stack<Integer> wM = new Stack<>();
        Stack<Integer> wB = new Stack<>();

        for (int i = 0; i < wagons.length; i++)
            wA.push(wagons[i]);

        int count = 0;
        while (wB.size() < wagons.length) {

            Integer last = target[count];

            if (wM.contains(last))
                if (wM.peek().equals(last)) {
                    move(wM, wB);
                    System.out.print("R");
                } else {
                    for (Integer integer : wA) {
                        System.out.print("I");
                    }
                    System.out.println(" Impossible");
                    return false;
                }
            else {
                for (int i = target.length - wA.size(); i < last; i++) {
                    move(wA, wM);
                    System.out.print("I");
                }
                move(wM, wB);
                System.out.print("R");
            }

            count++;

        }

        System.out.println();

        return Arrays.equals(wB.toArray(), target);

    }

    private static Integer[] init(int size) {
        Integer[] vet = new Integer[size];
        for (int i = size - 1; i >= 0; i--)
            vet[i] = size - i;
        return vet;
    }

    private static Integer[] initDict(Map<Character, Integer> dict, char[] cv) {
        Integer[] iv = new Integer[cv.length];
        for (int i = 0; i < iv.length; i++)
            iv[i] = dict.get(cv[i]);
        return iv;
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
