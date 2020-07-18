import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

enum Vote {
	ok, election, victory
};

public class Node extends Thread {

	private int ID;
	private Clock clock;
	private boolean isMaster = false;
	private boolean recievedOK = false;
	private boolean electionStarted = false;
	private int timeoutInSeconds = 3;
	private int masterID;
	private Date deadline;

	public Node(int id) {
		this.ID = id;
		this.clock = Clock.offset(Clock.systemDefaultZone(), Duration.ofMillis(new Random().nextInt(10000) + 1000));
	}

	void adjust(long millis) {
		clock = Clock.offset(clock, Duration.ofMillis(-millis));
	}

	void startElection() {
		deadline = new Date(new Date().getTime() + timeoutInSeconds * 1000);
		System.out.println(ID + " started election" + ". Deadline is " + deadline);
		for (int i = 1 + ID; i < Main.N; i++) {
			Main.nodes.get(i).sendVote(ID, Vote.election);
		}
	}

	void sendVote(int from, Vote message) {
		System.out.println("I am " + ID + ". Recieved " + message + " from " + from);

		switch (message) {
		case election:

			try {
				Thread.sleep(100);

				System.out.println("I am " + ID + ". Send " + Vote.ok + " to " + from);
				Main.nodes.get(from).sendVote(ID, Vote.ok);

				if (!electionStarted) {
					electionStarted = true;
					Thread.sleep(100);
					startElection();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			break;
		case ok:
			recievedOK = true;
			break;
		case victory:
			masterID = from;
			break;
		default:
			break;
		}
	}

	public int getID() {
		return ID;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	void adjustSlavesClock() throws InterruptedException {
		ArrayList<Clock> slaveClocks = new ArrayList<>();
		for (int i = 0; i < Main.N; i++) {

			Date timeWhenRequested = new Date();

			int m1 = new Random().nextInt(1000);
			Thread.sleep(m1);

			Clock slaveClock = Main.nodes.get(i).requestTime();
			int m2 = new Random().nextInt(1000);
			Date timeWhenRecieved = new Date();

			long rtt = timeWhenRecieved.getTime() - timeWhenRequested.getTime();
			slaveClocks.add(Clock.offset(slaveClock, Duration.ofMillis((rtt + m2 - m1) / 2)));
			System.out.println(rtt);
		}

		long average = 0;
		for (Clock slaveClock : slaveClocks) {
			average += 1.0 * slaveClock.millis() / Main.N;
		}

		System.out.println("Average time :\t" + new Date(average));

		for (int i = 0; i < Main.nodes.size(); i++) {
			long t = slaveClocks.get(i).millis() - average;
			System.out.println("Adjust with " + -t);
			Main.nodes.get(i).adjust(t);
		}

		printClocks();
	}

	public static long dateDifference(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	Clock requestTime() throws InterruptedException {
		return clock;
	}

	void printClocks() {
		for (Node node : Main.nodes) {
			System.out.println(node.ID + " " + LocalDateTime.now(node.clock).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		}
	}

	@Override
	public void run() {
		System.out.println("ID: " + ID + " \t " + LocalDateTime.now(clock).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		while (true) {

			try {
				Thread.sleep(100);
				if (!Main.electionStarted) {
					Main.electionMutex.acquire();
					Main.electionStarted = true;
					Main.electionMutex.release();
					startElection();
				}
				if (new Date().after(deadline)) {
					deadline = null;
					System.out.println(ID + " DEADLINE ");
					if (!recievedOK) {
						System.out.println(ID + " is a Master!");
						for (Node node : Main.nodes) {
							if (node == this)
								continue;
							node.sendVote(ID, Vote.victory);
						}
						adjustSlavesClock();
					}
				}
			} catch (Exception e) {
			}

		}

	}

}
