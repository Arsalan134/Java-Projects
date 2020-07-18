import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ppp implements Runnable {

	private ReentrantLock leftChopStick;
	private ReentrantLock rightChopStick;
	private int id;

	public AtomicBoolean mutex = new AtomicBoolean(true);

	private Random r = new Random();

	private int noOfTurnsToEat = 0;

	public ppp(int id) {
		this.id = id;
	}

	@Override
	public void run() {

		for (int i = 0; i < 5; i++) {
			philosophers[i] = new AtomicBoolean(true);
		}

		while (true) {
			try {
				think();
				hungry();
				takeForks();
				eat();
				putDownChopSticks();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void takeForks() {
		mutex.set(false);
	     state[i] = HUNGRY;
	     test(i);
	     up(&mutex);
	     down(&s[i]);
	}

	int pause = 5000;

	private void think() throws InterruptedException {
		System.out.printf("Philosopher %s is thinking \n", this.id);
		Thread.sleep(r.nextInt(pause));
	}

	private void hungry() {
		System.out.printf("Philosopher %s is hungry \n", this.id);
	}

	private void eat() throws InterruptedException {
		System.out.printf("Philosopher %s is eating", this.id);
		noOfTurnsToEat++;
		Thread.sleep(r.nextInt(pause));
	}

	private boolean pickupLeftChopStick() throws InterruptedException {
		if (leftChopStick.tryLock(10, TimeUnit.MILLISECONDS)) {
			System.out.printf("Philosopher %s pickedup Left ChopStick", this.id);
			return true;
		}
		return false;
	}

	private boolean pickupRightChopStick() throws InterruptedException {
		if (rightChopStick.tryLock(10, TimeUnit.MILLISECONDS)) {
			System.out.printf("Philosopher %s pickedup Right ChopStick", this.id);
			return true;
		}
		return false;
	}

	private void putDownChopSticks() {
		if (leftChopStick.isHeldByCurrentThread()) {
			leftChopStick.unlock();
			System.out.println(String.format("Philosopher %s putdown Left ChopStick", this.id));
		}
		if (rightChopStick.isHeldByCurrentThread()) {
			rightChopStick.unlock();
			System.out.println(String.format("Philosopher %s putdown Right ChopStick", this.id));
		}
	}

	public int getNoOfTurnsToEat() {
		return noOfTurnsToEat;
	}
}