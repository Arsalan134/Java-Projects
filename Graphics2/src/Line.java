import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

public class Line extends JFrame implements GLEventListener {

	private GLCanvas myCanvas;

	private int rendering_program;

	private int vbo[] = new int[2];

	private int mTrans;

	public Line() {

		setTitle("Chapter2 - program1");

		setSize(600, 400);

		setLocation(200, 200);

		GLProfile profile = GLProfile.getDefault();

		GLCapabilities capabilities = new GLCapabilities(profile);

		myCanvas = new GLCanvas(capabilities);

		myCanvas.addGLEventListener(this);

		this.add(myCanvas);

		setVisible(true);

	}

	@Override
	public void display(GLAutoDrawable drawable) {

		GL2 gl = (GL2) GLContext.getCurrentGL();

		gl.glUseProgram(rendering_program);

		float a = 0;

		// Matrix4 mTransMatrix = new Matrix4();

		float mTransMatrix[] = {

				(float) Math.cos(a), (float) -Math.sin(a), 0, 0,

				(float) Math.sin(a), (float) Math.cos(a), 0, 0,

				0, 0, 1, 0,

				0, 0, 0, 1

		};

		gl.glUniformMatrix4fv(mTrans, 1, false, mTransMatrix, 0);

		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo[0]);

		gl.glVertexAttribPointer(0, 2, GL3.GL_FLOAT, false, 0, 0);

		gl.glEnableVertexAttribArray(0);

		gl.glDrawArrays(GL3.GL_TRIANGLES, 0, 6);

		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo[1]);

		gl.glVertexAttribPointer(0, 2, GL3.GL_FLOAT, false, 0, 0);

		gl.glEnableVertexAttribArray(0);

		gl.glDrawArrays(GL3.GL_QUADS, 0, 8);

	}

	@Override
	public void init(GLAutoDrawable drawable) {

		GL2 gl = (GL2) GLContext.getCurrentGL();

		rendering_program = createShaderProgram();

		// generating location for transformation matrix

		mTrans = gl.glGetUniformLocation(rendering_program, "trans");

		float[] vertex_positions = { -0.5f, -1.0f, 0.0f, -0.5f, 0.5f, -1.0f, };

		gl.glGenBuffers(vbo.length, vbo, 0);

		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo[0]);

		FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(vertex_positions);

		gl.glBufferData(GL3.GL_ARRAY_BUFFER, vertBuf.limit() * 4, vertBuf, GL3.GL_STATIC_DRAW);

		float[] vertex_positions2 = { -0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f };

		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo[1]);

		FloatBuffer vertBuf2 = Buffers.newDirectFloatBuffer(vertex_positions2);

		gl.glBufferData(GL3.GL_ARRAY_BUFFER, vertBuf2.limit() * 4, vertBuf2, GL3.GL_STATIC_DRAW);

	}

	private int createShaderProgram() {

		GL2 gl = (GL2) GLContext.getCurrentGL();

		// .glutInitDisplayMode(GLUT_3_2_CORE_PROFILE | ... );

		String vshaderSource[] = readShaderSource("src/vshader.glsl");

		String fshaderSource[] = readShaderSource("src/fshader.glsl");

		int vShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);

		gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);

		gl.glCompileShader(vShader);

		printShaderLog(vShader);

		int fShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);

		gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null, 0);

		gl.glCompileShader(fShader);

		printShaderLog(fShader);

		int vfprogram = gl.glCreateProgram();

		gl.glAttachShader(vfprogram, vShader);

		gl.glAttachShader(vfprogram, fShader);

		gl.glLinkProgram(vfprogram);

		gl.glDeleteShader(vShader);

		gl.glDeleteShader(fShader);

		printProgramLog(vfprogram);

		return vfprogram;

	}

	private String[] readShaderSource(String filename) {

		Vector<String> lines = new Vector<String>();

		Scanner sc;

		try {

			sc = new Scanner(new File(filename));

		} catch (IOException e) {

			System.err.println("IOException reading file: " + e);

			return null;

		}

		while (sc.hasNext()) {

			lines.addElement(sc.nextLine());

		}

		String[] program = new String[lines.size()];

		for (int i = 0; i < lines.size(); i++) {

			program[i] = lines.elementAt(i) + "\n";

			// System.out.print(program[i]);

		}

		return program;

	}

	private void printShaderLog(int shader) {

		GL2 gl = (GL2) GLContext.getCurrentGL();

		int[] len = new int[1];

		int[] chWrittn = new int[1];

		byte[] log = null;

		// determine the length of the shader compilation log

		gl.glGetShaderiv(shader, GL3.GL_INFO_LOG_LENGTH, len, 0);

		if (len[0] > 0) {

			log = new byte[len[0]];

			gl.glGetShaderInfoLog(shader, len[0], chWrittn, 0, log, 0);

			System.out.println("Shader Info Log: ");

			for (int i = 0; i < log.length; i++) {

				System.out.print((char) log[i]);

			}

		}

	}

	void printProgramLog(int prog) {

		GL2 gl = (GL2) GLContext.getCurrentGL();

		int[] len = new int[1];

		int[] chWrittn = new int[1];

		byte[] log = null;

		// determine the length of the program linking log

		gl.glGetProgramiv(prog, GL3.GL_INFO_LOG_LENGTH, len, 0);

		if (len[0] > 0) {

			log = new byte[len[0]];

			gl.glGetProgramInfoLog(prog, len[0], chWrittn, 0, log, 0);

			System.out.println("Program Info Log: ");

			for (int i = 0; i < log.length; i++) {

				System.out.print((char) log[i]);

			}

		}

	}

	public static void main(String[] args) {

		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		new Line();

	}

	@Override

	public void dispose(GLAutoDrawable arg0) {

		// TODO Auto-generated method stub

	}

	@Override

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {

		// TODO Auto-generated method stub

	}

}