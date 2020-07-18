import java.nio.FloatBuffer;
import java.util.Random;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.math.Matrix4;

public class Cube3D extends Object3D {

	private int vbo[] = new int[2];
	private int vao[] = new int[2];

	private boolean initialized = false;

	private float alpha = Main.rand.nextFloat();
	private float speed = 0.1f * Main.rand.nextFloat();
	private Vector3 vRot = new Vector3(Main.rand.nextFloat(), Main.rand.nextFloat(), Main.rand.nextFloat());

	public final static float[] vertices = {
			// Front face
			-1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f,
			1.0f,

			// Right face
			1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f,
			-1.0f,

			// Back face
			1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f,
			1.0f, -1.0f,

			// Left face
			-1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f,
			1.0f, 1.0f,

			// Top face
			-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
			-1.0f,

			// Bottom face
			1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f,
			-1.0f, -1.0f, };

	public final static float[] normals = {
			// Front face
			0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,

			// Right face
			1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,

			// Back face
			0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
			-1.0f,

			// Left face
			-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f,
			0.0f,

			// Top face
			0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,

			// Bottom face
			0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f,
			0.0f };
	
	public static final float[] v2 = {
			// 0           1             2             3
			-1, -1, -1,    1, -1, -1,    1, 1, -1,    -1, 1, -1,
			// 4           5             6             7 
			-1, -1, 1,     1, -1, 1,     1, 1, 1,     -1, 1, 1
	};
	
	public static final float[] n2 = {
			
	};

	public Cube3D(float x, float y, float z) {
		super(x, y, z);

	}

	/**
	 * 
	 * @param gl
	 */
	@Override
	public void init(GL4 gl) {
		if (initialized)
			return;

		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);

		gl.glGenBuffers(vbo.length, vbo, 0);

		gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[0]);
		FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(vertices);
		gl.glBufferData(GL4.GL_ARRAY_BUFFER, vertBuf.limit() * 4, vertBuf, GL4.GL_STATIC_DRAW);

		gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[1]);
		FloatBuffer normBuf = Buffers.newDirectFloatBuffer(normals);
		gl.glBufferData(GL4.GL_ARRAY_BUFFER, normBuf.limit() * 4, normBuf, GL4.GL_STATIC_DRAW);

		initialized = true;
	}

	/**
	 * 
	 * @param gl
	 * @param program
	 * @param camera
	 */
	@Override
	public void display(GL4 gl, int program, Matrix4 mView) {
		// initialize first before displaying, if has not been initialized yet
		init(gl);

		Matrix4 mModel = new Matrix4();
		mModel.translate(getX(), getY(), getZ());
		mModel.rotate(alpha, vRot.getX(), vRot.getY(), vRot.getZ());

		Matrix4 mModelView = new Matrix4();
		mModelView.multMatrix(mView);
		mModelView.multMatrix(mModel);

		int locModelView = gl.glGetUniformLocation(program, "modelview");
		gl.glUniformMatrix4fv(locModelView, 1, false, mModelView.getMatrix(), 0);

		int locVert = gl.glGetAttribLocation(program, "position");
		int locNorm = gl.glGetAttribLocation(program, "normal");

		gl.glEnableVertexAttribArray(locVert);
		gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[0]);
		gl.glVertexAttribPointer(locVert, 3, GL4.GL_FLOAT, false, 0, 0);

		gl.glEnableVertexAttribArray(locNorm);
		gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo[1]);
		gl.glVertexAttribPointer(locNorm, 3, GL4.GL_FLOAT, false, 0, 0);

		gl.glEnable(GL4.GL_DEPTH_TEST);
		gl.glDepthFunc(GL4.GL_LEQUAL);
		gl.glDrawArrays(GL4.GL_TRIANGLES, 0, vertices.length / 3);

		alpha += speed;
	}
}
