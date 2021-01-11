import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        ArrayList<Integer> divisors = new ArrayList<Integer>();
        for (int i = 1; i <= num; i++) {
          int n = i;
            for (int j = 2; j <= Math.sqrt(n); j++) {
                if (n % j == 0) {
                    divisors.add(j);
                    if (j * j != n) {
                        divisors.add(n / j);
                    }
                }
            }
            Collections.sort(divisors);
            if (n != 1) {
                System.out.print(n + ": 1, ");
            } else {
                System.out.print(n + ": ");
            }
            for (Integer number : divisors) {
                System.out.print(number + ", ");
            }
            System.out.println(n);
            divisors.clear();
        }
    }
}
