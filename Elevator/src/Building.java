import java.util.ArrayList;

public class Building {

	private String name;
	private int numberOfFloors;
	private int numberOfElevators;
	private Controller controller;
	private ArrayList<Elevator> elevators = new ArrayList<>();
	private ArrayList<Floor> floors = new ArrayList<>();

	public Building(String name, int numberOfFloors, int numberOfElevators) {
		this.name = name;
		this.numberOfFloors = numberOfFloors;
		this.numberOfElevators = numberOfElevators;

		// Create floors
		for (int i = 0; i < numberOfFloors; i++)
			floors.add(new Floor(i, this));

		// Create Elevators
		for (int i = 0; i < numberOfElevators; i++) {
			elevators.add(new Elevator("Elevator " + (char) (i + 65), this));
			new Thread(this.elevators.get(i)).start();
		}
	}

	public Controller getController() {
		return controller;
	}

	public ArrayList<Elevator> getElevators() {
		return elevators;
	}

	public ArrayList<Floor> getFloors() {
		return floors;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfElevators() {
		return numberOfElevators;
	}

	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public String toString() {
		return name + " has " + numberOfFloors + " floors and " + numberOfElevators + " elevators";
	}

}
