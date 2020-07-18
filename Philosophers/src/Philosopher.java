import java.util.Random;

class Philosopher implements Runnable {
	enum State {
		THINKING, HUNGRY, EATING
	};

	public static State[] philosopherState = new State[Main.NUM_PHILOSOPHERS];
	private Random r = new Random();
	private int id;
	int pause = 3000;

	public Philosopher(int id) {
		this.id = id;
	}

	public void hungry(int philosopherId) {
		philosopherState[philosopherId] = State.HUNGRY;
		System.out.println("Philosopher " + philosopherId + " is hungry\n");
		System.out.flush();
	}

	public synchronized void pickUpChopsticks(int philosopherId) throws InterruptedException {
		if (someNeighborIsEating(philosopherId))
			wait();
		philosopherState[philosopherId] = State.EATING;
	}

	public synchronized void putDownChopsticks(int philosopherId) {
		System.out.println("Philosopher " + philosopherId + " put down chopsticks\n");
		philosopherState[philosopherId] = State.THINKING;
		notifyAll();
	}

	@Override
	public void run() {
		try {
			while (true) {
				think();
				hungry(id);
				pickUpChopsticks(id);
				eat();
				putDownChopsticks(id);
				System.out.flush();
			}
		} catch (InterruptedException e) {
			System.out.println("Philosopher " + id + " was interrupted\n");
		}
	}

	private void eat() throws InterruptedException {
		System.out.println("Philosopher " + id + " is eating\n");
		Thread.sleep(r.nextInt(pause + 1000));
	}

	private boolean someNeighborIsEating(int philosopherId) {
		if (philosopherState[(philosopherId + 1) % philosopherState.length] == State.EATING)
			return true;
		if (philosopherState[(philosopherId + philosopherState.length - 1) % philosopherState.length] == State.EATING)
			return true;
		return false;
	}

	private void think() throws InterruptedException {
		System.out.println("Philosopher " + id + " is thinking\n");
		System.out.flush();
		Thread.sleep(r.nextInt(pause + 1000));
	}

}
