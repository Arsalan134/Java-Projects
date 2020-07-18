import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.util.FPSAnimator;

public class Main extends JFrame implements GLEventListener, MouseListener, MouseMotionListener {

	public static Random rand = new Random(System.currentTimeMillis());
	private GLCanvas myCanvas;

	private int shaderProgram;

	private ArrayList<Object3D> objects = new ArrayList();

	private boolean pressed = false;

	private Matrix4 mProjMatrix = new Matrix4();

	Vector3 camera = new Vector3(0, 0, 5);
	Vector3 light = new Vector3(0, -10, 0);

	Vector3 light_ambient = new Vector3(1, 1, 0);
	Vector3 light_diffuse = new Vector3(1, 1, 1);
	Vector3 light_specular = new Vector3(1, 1, 1);

	Vector3 mat_ambient = new Vector3(1.0f, 0.5f, 0.5f);
	Vector3 mat_diffuse = new Vector3(0.8f, 0.6f, 0.6f);
	Vector3 mat_specular = new Vector3(1.0f, 1.0f, 1.0f);

	float shininess = 50f;

	public Main() {
		setTitle("Chapter2 - program1");
		setSize(600, 600);
		setLocation(200, 200);

		GLProfile profile = GLProfile.getDefault();
		GLCapabilities capabilities = new GLCapabilities(profile);

		myCanvas = new GLCanvas(capabilities);
		myCanvas.addGLEventListener(this);
		myCanvas.addMouseListener(this);
		myCanvas.addMouseMotionListener(this);

		final FPSAnimator animator = new FPSAnimator(myCanvas, 50);
		animator.start();

		this.add(myCanvas);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				animator.stop();
				dispose();
				System.exit(0);
			}
		});
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glClear(GL4.GL_DEPTH_BUFFER_BIT | GL4.GL_COLOR_BUFFER_BIT);

		int locProj = gl.glGetUniformLocation(shaderProgram, "proj");
		gl.glUniformMatrix4fv(locProj, 1, false, mProjMatrix.getMatrix(), 0);

		Matrix4 mView = new Matrix4();
		mView.translate(-camera.getX(), -camera.getY(), -camera.getZ());

		for (Object3D object : objects) {
			object.display(gl, shaderProgram, mView);
		}
	}

	/**
	 * 
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		shaderProgram = ShaderUtils.createShaderProgram();

		gl.glUseProgram(shaderProgram);

		float aspect = (float) myCanvas.getWidth() / (float) myCanvas.getHeight();
		mProjMatrix.makePerspective(30.0f, aspect, 0.1f, 1000f);

		objects.add(new Cube3D(0, 0, 0));

		mat_ambient.mult(light_ambient);
		mat_diffuse.mult(light_diffuse);
		mat_specular.mult(light_specular);

		int locLight = gl.glGetUniformLocation(shaderProgram, "light");
		int locAmbient = gl.glGetUniformLocation(shaderProgram, "ambient");
		int locDiffuse = gl.glGetUniformLocation(shaderProgram, "diffuse");
		int locSpecular = gl.glGetUniformLocation(shaderProgram, "specular");
		int locShiny = gl.glGetUniformLocation(shaderProgram, "shininess");

		gl.glUniform3fv(locLight, 1, light.getFloats(), 0);
		gl.glUniform3fv(locAmbient, 1, mat_ambient.getFloats(), 0);
		gl.glUniform3fv(locDiffuse, 1, mat_diffuse.getFloats(), 0);
		gl.glUniform3fv(locSpecular, 1, mat_specular.getFloats(), 0);
		gl.glUniform1f(locShiny, shininess);
	}

	public static void main(String[] args) {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		new Main();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// inverse transformation from (x, y) screen projection to world
		// coordinates (x', y', 0)

		// float x = 8 * ((float) e.getX() / ((float) myCanvas.getWidth() / 2) -
		// 1);
		// float y = 8 * (1 - (float) e.getY() / ((float) myCanvas.getHeight() /
		// 2));
		// objects.add(new Cube())
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}