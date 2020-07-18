import java.io.Serializable;
import java.util.Random;

public class Person implements Runnable, Serializable {

	private static final long serialVersionUID = 4324334654134806693L;

	private String name;
	private Building building;
	private int currentFloor;
	private int targetFloor;
	private int targetElevatorIndex;
	private boolean pressed;
	private boolean inElevator;
	private boolean reachedDestination;

	Random r = new Random();

	public Person(String name, Building building) {
		this.building = building;
		this.name = name;
		this.currentFloor = r.nextInt(building.getNumberOfFloors());
		// generateNewTargetFloor();
	}

	public Person(String name, Building building, int currentFloor) {
		this.name = name;
		this.building = building;
		this.currentFloor = currentFloor;
		generateNewTargetFloor();
	}

	public Person(String name, Building building, int currentFloor, int targetFloor) {
		this.name = name;
		this.building = building;
		this.currentFloor = currentFloor;
		this.targetFloor = targetFloor;
	}

	private void checkElevator() throws InterruptedException {
		if (!inElevator && currentFloor == building.getElevators().get(targetElevatorIndex).getPosition()
				&& building.getElevators().get(targetElevatorIndex).areDoorsOpen() && !reachedDestination)
			enter();
		else if (inElevator)
			currentFloor = building.getElevators().get(targetElevatorIndex).getPosition();
		if (inElevator && building.getElevators().get(targetElevatorIndex).getPosition() == targetFloor
				&& building.getElevators().get(targetElevatorIndex).areDoorsOpen())
			exit();
	}

	private void enter() {
		building.getElevators().get(targetElevatorIndex).getPeopleInElevator().add(this);
		System.out.println("\n");
		System.out.println(this.name + " entered " + building.getElevators().get(targetElevatorIndex) + " at " + " floor:\t"
				+ building.getElevators().get(targetElevatorIndex).getPosition());
		System.out.println("\n");
		System.out.flush();
		inElevator = true;
	}

	private void exit() {
		building.getElevators().get(targetElevatorIndex).getPeopleInElevator().remove(this);
		inElevator = false;
		System.out.println("\n");
		System.out.println(this.name + " exit from " + building.getElevators().get(targetElevatorIndex) + " at " + " floor:\t"
				+ building.getElevators().get(targetElevatorIndex).getPosition());
		System.out.println("\n");
		System.out.flush();
		reachedDestination = true;

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		generateNewTargetFloor();
	}

	private void generateNewTargetFloor() {
		targetFloor = (new Random()).nextInt(building.getNumberOfFloors());
		while (targetFloor == currentFloor)
			targetFloor = (new Random()).nextInt(building.getNumberOfFloors());
		pressed = false;
		reachedDestination = false;
	}

	public Building getBuilding() {
		return building;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public String getName() {
		return name;
	}

	public int getTargetElevatorIndex() {
		return targetElevatorIndex;
	}

	public synchronized int getTargetFloor() {
		return targetFloor;
	}

	public boolean isPressed() {
		return pressed;
	}

	public int pressButton(int targetFloor) throws InterruptedException {
		System.out.println(this.name + " pressed: \t" + targetFloor);
		System.out.flush();
		System.out.println();
		return building.getFloors().get(currentFloor).buttonPressed(this.name, targetFloor);
	}

	@Override
	public void run() {
		System.out.println();
		System.out.println(this.name + " is at Floor:\t" + currentFloor);
		while (true) {
			try {
				if (targetFloor == -1)
					targetFloor = (new Random()).nextInt(building.getNumberOfFloors());
				if (!pressed) {
					this.targetElevatorIndex = pressButton(targetFloor);
					pressed = true;
				}
				checkElevator();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.flush();
			System.err.flush();
		}
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public void setTargetElevatorIndex(int targetElevator) {
		this.targetElevatorIndex = targetElevator;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
