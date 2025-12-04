import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String a,b,c;
        System.out.println("Input the plaintext. Extraneous characters will be removed or converted.");
        a = scanner.nextLine();
        System.out.println("Input the first key");
        b = scanner.nextLine();
        System.out.println("Input the last key");
        c = scanner.nextLine();
        String x = Alg.doTheWholeThingamabob(a, b, c);
        System.out.println(x);
        x = Alg.unDoTheWholeThingamabob(x, b, c);
        System.out.println(x);
    }
}
