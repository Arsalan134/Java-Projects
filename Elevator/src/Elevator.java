import java.io.Serializable;
import java.util.ArrayList;

public class Elevator implements Runnable, Serializable {

	private static final long serialVersionUID = -6590075545000914500L;

	private static final int pause = 100;

	private String name;
	private int position;
	private int currentDirection;
	private boolean doorsAreOpen;
	private Building building;
	private ArrayList<Person> peopleInElevator = new ArrayList<>();
	private ArrayList<Request> waitingFloors = new ArrayList<>();
	private ArrayList<Request> extraFloors = new ArrayList<>();

	private boolean printed;

	public Elevator(String name, Building building) {
		this.building = building;
		this.name = name;
	}

	public Elevator(String name, int initialFloor, Building building) {
		this.name = name;
		this.building = building;
		this.position = initialFloor;
	}

	private void bubbleSort(ArrayList<Request> waitingFloors) {
		// If elevator is going up
		if (this.currentDirection == Direction.UP) {
			for (int i = 0; i < waitingFloors.size() - 1; i++)
				for (int j = 1; j < waitingFloors.size() - i; j++)
					// first sort by directions
					if (waitingFloors.get(j - 1).getDirection() < waitingFloors.get(j).getDirection())
						waitingFloors.add(j - 1, waitingFloors.remove(j));
					// If directions are equal and is UP sort by floor number
					else if (waitingFloors.get(j - 1).getDirection() == waitingFloors.get(j).getDirection()
							&& waitingFloors.get(j - 1).getDirection() == Direction.DOWN) {
						if (waitingFloors.get(j - 1).getFloor() < waitingFloors.get(j).getFloor())
							waitingFloors.add(j - 1, waitingFloors.remove(j));
						// If directions are equal and is DOWN sort by floor
						// number
					} else if (waitingFloors.get(j - 1).getDirection() == waitingFloors.get(j).getDirection() && waitingFloors.get(j - 1).getDirection() == Direction.UP)
						if (waitingFloors.get(j - 1).getFloor() > waitingFloors.get(j).getFloor())
							waitingFloors.add(j - 1, waitingFloors.remove(j));

			// If elevator is going down
		} else if (this.currentDirection == Direction.DOWN) {
			for (int i = 0; i < waitingFloors.size() - 1; i++)
				for (int j = 1; j < waitingFloors.size() - i; j++)
					// first sort by directions
					if (waitingFloors.get(j - 1).getDirection() > waitingFloors.get(j).getDirection())
						waitingFloors.add(j - 1, waitingFloors.remove(j));
					// If directions are equal and is UP sort by floor number
					else if (waitingFloors.get(j - 1).getDirection() == waitingFloors.get(j).getDirection()
							&& waitingFloors.get(j - 1).getDirection() == Direction.DOWN) {
						if (waitingFloors.get(j - 1).getFloor() < waitingFloors.get(j).getFloor())
							waitingFloors.add(j - 1, waitingFloors.remove(j));
						// If directions are equal and is DOWN sort by floor
						// number
					} else if (waitingFloors.get(j - 1).getDirection() == waitingFloors.get(j).getDirection() && waitingFloors.get(j - 1).getDirection() == Direction.UP)
						if (waitingFloors.get(j - 1).getFloor() > waitingFloors.get(j).getFloor())
							waitingFloors.add(j - 1, waitingFloors.remove(j));
		}
	}

	private void closeDoors() throws InterruptedException {
		System.out.println(this + " closes doors");
		this.doorsAreOpen = false;
	}

	private void down() throws InterruptedException {
		System.out.println(this + " is going down. Current Floor: \t" + --position);
		currentDirection = Direction.DOWN;
		Thread.sleep(pause);
	}

	private int necessaryDirection(int targetFloor) {
		if (targetFloor > position)
			return Direction.UP;
		else if (targetFloor < position)
			return Direction.DOWN;
		return Direction.NONE;
	}

	private void openDoors() throws InterruptedException {
		System.out.println(this + " opens doors");
		this.doorsAreOpen = true;
	}

	private void up() throws InterruptedException {
		System.out.println(this + " is going up. Current Floor: \t\t" + ++position);
		currentDirection = Direction.UP;
		Thread.sleep(pause);
	}

	public boolean areDoorsOpen() {
		return doorsAreOpen;
	}

	public Building getBuilding() {
		return building;
	}

	public int getCurrentDirection() {
		return currentDirection;
	}

	public ArrayList<Request> getExtraFloors() {
		return extraFloors;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Person> getPeopleInElevator() {
		return peopleInElevator;
	}

	public int getPosition() {
		return position;
	}

	public ArrayList<Request> getWaitingFloors() {
		return waitingFloors;
	}

	public synchronized void goTo(int targetFloor) throws InterruptedException {
		int direction = necessaryDirection(targetFloor);
		if (position != targetFloor && direction == Direction.UP)
			up();
		else if (position != targetFloor && direction == Direction.DOWN)
			down();
		else if (position == targetFloor) {
			waitingFloors.remove(0);
			openDoors();
			Thread.sleep(pause);
			closeDoors();
			Thread.sleep(pause);
		}

		System.out.println();
		System.out.println(this.name + "'s waiting array is:\t" + waitingFloors);
		System.out.println(this.name + "'s people are :\t" + peopleInElevator);
		System.out.println();
		System.out.flush();
	}

	public void plus(int clientFloor, int targetFloor, int direction) {
		this.waitingFloors.add(new Request(clientFloor, true, direction));
		this.waitingFloors.add(new Request(targetFloor, false, direction));
		bubbleSort(waitingFloors);
	}

	public void plusExtra(int clientFloor, int targetFloor, int direction) {
		this.extraFloors.add(new Request(clientFloor, true, direction));
		this.extraFloors.add(new Request(targetFloor, false, direction));
		System.out.println(this.name + " added to extra queue");
	}

	@Override
	public void run() {
		System.out.println("Elevator " + this.name + " STARTED");
		while (true)
			try {
				System.out.flush();
				while (!waitingFloors.isEmpty()) {
					System.out.println(this + " Printing array: " + waitingFloors);
					goTo(waitingFloors.get(0).getFloor());
					System.out.flush();
					printed = false;
				}
				if (!printed) {
					System.err.println(this.name + " has no route");
					printed = true;
				}
				if (extraFloors.size() > 0) {
					System.err.println(this.name + " added to main queue:\t" + extraFloors);
					waitingFloors.addAll(extraFloors);
					bubbleSort(waitingFloors);
					extraFloors.clear();
					System.err.println(this.name + " main queue is:\t" + waitingFloors);
				} else
					currentDirection = Direction.NONE;
			} catch (InterruptedException e) {
				System.err.println("\n\n\n Message is : \t\t" + e.getMessage());
				System.err.flush();
			}
	}

	@Override
	public String toString() {
		return name;
	}

}
