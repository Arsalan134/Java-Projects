
public class Controller {

	private String name;
	private Building building;

	public Controller(String name, Building building) {
		this.name = name;
		this.building = building;
	}

	private int calculateTotalRoute(Elevator elevator, int clientFloor) {
		int elevatorsRoute = 0;

		// Add distance between elevator's position and his first
		// destination
		if (elevator.getWaitingFloors().size() > 0)
			elevatorsRoute += findDistance(elevator.getPosition(), elevator.getWaitingFloors().get(0).getFloor());

		// Add distance between elevator's last destination and
		// clients position
		if (elevator.getWaitingFloors().size() > 0)
			elevatorsRoute += findDistance(elevator.getWaitingFloors().get(elevator.getWaitingFloors().size() - 1).getFloor(), clientFloor);

		// Add all routes of this working elevator
		for (int request = 1; request < elevator.getWaitingFloors().size(); request++)
			elevatorsRoute += findDistance(elevator.getWaitingFloors().get(request - 1).getFloor(), elevator.getWaitingFloors().get(request).getFloor());

		System.out.println(elevator + "'s route:\t" + elevatorsRoute);

		return elevatorsRoute;
	}

	private int findDistance(int currentFloor, int elevatorPosition) {
		return Math.abs(currentFloor - elevatorPosition);
	}

	private int necessaryDirection(int targetFloor, int currentFloor) {
		if (targetFloor > currentFloor)
			return Direction.UP;
		else if (targetFloor < currentFloor)
			return Direction.DOWN;
		return Direction.NONE;
	}

	private int chooseElevator(int clientFloor, int targetFloor) {
		int direction = necessaryDirection(targetFloor, clientFloor);
		int indexOfBestElevator = -1;
		int previousDistance = Integer.MAX_VALUE;

		// on the way
		for (Elevator elevator : building.getElevators()) {
			if ((elevator.getPosition() >= clientFloor && elevator.getCurrentDirection() == Direction.DOWN && direction == Direction.DOWN)
					|| (elevator.getPosition() <= clientFloor && elevator.getCurrentDirection() == Direction.UP && direction == Direction.UP))
				if (findDistance(clientFloor, elevator.getPosition()) < previousDistance) {
					previousDistance = findDistance(clientFloor, elevator.getPosition());
					indexOfBestElevator = building.getElevators().indexOf(elevator);
					System.out.println(building.getElevators().get(indexOfBestElevator) + " has same direction");
				}
		}

		if (indexOfBestElevator > 0) {
			System.out.println("Selected:\t\t" + building.getElevators().get(indexOfBestElevator));
			return indexOfBestElevator;
		}
		// shortest total
		previousDistance = Integer.MAX_VALUE;

		for (Elevator elevator : building.getElevators()) {
			if (calculateTotalRoute(elevator, clientFloor) < previousDistance) {
				previousDistance = calculateTotalRoute(elevator, clientFloor);
				indexOfBestElevator = building.getElevators().indexOf(elevator);
			}
		}

		System.out.println("Selected:\t\t" + building.getElevators().get(indexOfBestElevator));
		return indexOfBestElevator;
	}

	public synchronized int recievedFloor(String name, int currentFloor, int targetFloor) throws InterruptedException {
		int index = -1;

		index = chooseElevator(currentFloor, targetFloor);
		building.getElevators().get(index).plus(currentFloor, targetFloor, necessaryDirection(targetFloor, currentFloor));
		return index;
	}

	@Override
	public String toString() {
		return name;
	}
}
