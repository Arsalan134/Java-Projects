
public class Request {

	private int floor;
	private int direction;

	public Request(int floor, boolean isClient, int direction) {
		this.floor = floor;
		this.direction = direction;
	}

	public int getFloor() {
		return floor;
	}

	public int getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return floor + "(" + direction + ")";
	}

}
