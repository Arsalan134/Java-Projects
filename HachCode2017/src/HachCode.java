import java.util.ArrayList;
import java.util.Scanner;

public class HachCode {
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		int V = in.nextInt();
		int E = in.nextInt();
		int R = in.nextInt();
		int C = in.nextInt();
		int SizeOfCache = in.nextInt();

		int[] videoSizes = new int[V];

		ArrayList<EndPoint> endPoints = new ArrayList<>();

		for (int i = 0; i < V; i++) {
			videoSizes[i] = in.nextInt();
		}

		for (int i = 0; i < E; i++) {
			int latency = in.nextInt();
			int numberOfCaches = in.nextInt();
			endPoints.add(new EndPoint(latency, numberOfCaches));
			for (int j = 0; j < numberOfCaches; j++) {
				
			}
		}

	}
}
