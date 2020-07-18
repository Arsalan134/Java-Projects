public class Main {
	static final int NUM_PHILOSOPHERS = 5;

	public static void main(String[] args) {
		Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
		Philosopher.philosopherState = new Philosopher.State[NUM_PHILOSOPHERS];

		for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
			Philosopher.philosopherState[i] = Philosopher.State.THINKING;
			philosophers[i] = new Philosopher(i);
			new Thread(philosophers[i]).start();
		}
	}
}
