import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {

	static ArrayList<Node> nodes = new ArrayList<>();

	static int N = 5;
	static Semaphore electionMutex = new Semaphore(1);
	static boolean electionStarted = false;

	public static void main(String[] args) {

		for (int i = 0; i < N; i++) {
			nodes.add(new Node(i));
		}

		System.out.println("Before");
		for (Node node : nodes) {
			node.start();
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
