
public class Vector3 {
	private float x;
	private float y;
	private float z;

	public Vector3(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void mult(Vector3 v) {
		this.x = this.x * v.getX();
		this.y = this.y * v.getY();
		this.z = this.z * v.getZ();
	}

	public float[] getFloats() {
		return new float[] { x, y, z };
	}

}
