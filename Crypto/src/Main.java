import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Random r = new Random();

		System.out.print("Input p: ");

		int p = sc.nextInt();

		if (!isPrime(p)) {
			System.out.println(p + " is not prime");
			System.exit(0);
		}

		System.out.print("Input alpha: ");
		int alpha = sc.nextInt();

		sc.close();

		if (alpha > p - 2 || alpha < 2) {
			System.out.println(alpha + " is out of range");
			System.exit(0);
		}

		int a = r.nextInt(p - 3) + 2;
		int b = r.nextInt(p - 3) + 2;

		int A = (int) Math.pow(alpha, a) % p;
		int B = (int) Math.pow(alpha, b) % p;

		int Kab = (int) (Math.pow(A, b) % p);

		System.out.println("a: " + a + " b: " + b + " " + "A: " + A + " B: " + B + " Kab: " + Kab);

	}

	static boolean isPrime(int num) {
		boolean b = true;

		for (int i = 2; i < num; i++) {
			if (num % i == 0)
				return false;
		}

		return b;
	}

}
