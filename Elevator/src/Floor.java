public class Floor {

	private int currentFloor;
	private Building building;

	public Floor(int floor, Building building) {
		this.currentFloor = floor;
		this.building = building;
	}

	public int getFloor() {
		return currentFloor;
	}

	public void setFloor(int floor) {
		this.currentFloor = floor;
	}

	public int buttonPressed(String person, int targetFloor) throws InterruptedException {
		return this.building.getController().recievedFloor(person, currentFloor, targetFloor);
	}

	public Building getBuilding() {
		return building;
	}

	@Override
	public String toString() {
		return "Floor " + currentFloor;
	}

}
