class PhilosopherMonitor {
	private enum State {
		THINKING, HUNGRY, EATING
	};

	private State[] philosopherState;

	public PhilosopherMonitor(int numPhilosophers) {
		philosopherState = new State[numPhilosophers];
		for (int i = 0; i < philosopherState.length; i++)
			philosopherState[i] = State.THINKING;
	}

	public synchronized void pickUpChopsticks(int philosopherId) throws InterruptedException {
		if (someNeighborIsEating(philosopherId))
			wait();
		philosopherState[philosopherId] = State.EATING;
	}

	private boolean someNeighborIsEating(int philosopherId) {
		if (philosopherState[(philosopherId + 1) % philosopherState.length] == State.EATING)
			return true;
		if (philosopherState[(philosopherId + philosopherState.length - 1) % philosopherState.length] == State.EATING)
			return true;
		return false;
	}

	public synchronized void putDownChopsticks(int philosopherId) {
		System.out.println("Philosopher " + philosopherId + " put down chopsticks\n");
		philosopherState[philosopherId] = State.THINKING;
		notifyAll();
	}

	public void hungry(int philosopherId) {
		philosopherState[philosopherId] = State.HUNGRY;
		System.out.println("Philosopher " + philosopherId + " is hungry\n");
		System.out.flush();
	}
}