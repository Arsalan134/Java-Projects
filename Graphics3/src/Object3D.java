import com.jogamp.opengl.GL4;
import com.jogamp.opengl.math.Matrix4;

public abstract class Object3D extends Vector3 {

	public Object3D(float x, float y, float z) {
		super(x, y, z);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void init(GL4 gl);
	
	public abstract void display(GL4 gl, int program, Matrix4 mView);

}
