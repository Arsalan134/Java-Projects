
// import java.util.Random;

import java.util.Random;

import javax.swing.JFrame;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Line extends JFrame implements GLEventListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private int rendering_program;
	private int vao[] = new int[1];

	public Line() {
	}

	// out = output of a shader
	// in = shader gets
	// gl position = output of shader
	// point by point

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = (GL2) GLContext.getCurrentGL();
		gl.glUseProgram(rendering_program);
		gl.glPointSize(5f);
		// gl.glDrawArrays(GL3.GL_POINTS, 0, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glRotated(1, 1, 1, 0);

		// draw3dSphere(gl);
		drawTorus(gl);
	}

	public void drawTorus(GL2 gl) {
		int numberOfMeridians = 18; // |
		int numberOfParallels = 18;

		float radiusIn = 0.4f;
		float radiusOut = 0.8f;

		gl.glBegin(GL2.GL_POINTS);

		gl.glColor3d(1, 1, 1);

		float height = 0;
		float alpha = 0;

		for (int j = 0; j < numberOfMeridians; j++) {
			for (int i = 0; i < numberOfParallels; i++) {
				float y = (float) Math.sin(Math.toRadians(height * 2)) * radiusIn / 2;

				float x = (float) (Math.sin(Math.toRadians(alpha)) * radiusIn * Math.cos(Math.toRadians(height))) * 1.8f;
				float z = (float) (Math.cos(Math.toRadians(alpha)) * radiusIn * Math.cos(Math.toRadians(height))) * 1.8f;

				gl.glVertex3f(x, y, z);
				alpha += 360.0f / numberOfMeridians;
			}
			height += 360.0f / numberOfParallels;
		}

		gl.glEnd();
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = (GL2) GLContext.getCurrentGL();
		rendering_program = createShaderProgram();
		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);
		gl.glEnable(GL3.GL_DEPTH_TEST);
	}

	private int createShaderProgram() {
		GL2 gl = (GL2) GLContext.getCurrentGL();

		String vshaderSource[] = { "#version 320 \n", "void main() \n", "{ gl_Position = vec4(0.0, 0.0, 0.0, 1.0); \n}" };
		String fshaderSource[] = { "#version 320 \n", "out vec4 color; \n", "void main() \n", " { color = vec4(1.0, 1.0, 1.0, 1.0); } \n" };

		int vShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);		// transform
		gl.glShaderSource(vShader, 3, vshaderSource, null, 0);

		int fShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);	// coloring
		gl.glShaderSource(fShader, 4, fshaderSource, null, 0);

		int vfprogram = gl.glCreateProgram();

		printShaderLog(vShader);
		printShaderLog(fShader);

		gl.glAttachShader(vfprogram, vShader);
		gl.glAttachShader(vfprogram, fShader);
		gl.glLinkProgram(vfprogram);
		gl.glDeleteShader(vShader);
		gl.glDeleteShader(fShader);

		printProgramLog(vfprogram);

		return vfprogram;
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {

	}

	float betta = 90;
	float angleY = 0;

	public void draw3dSphere(GL2 gl) {
		int numberOrVertices = 18;
		// angleX += .1f;
		// angleY += .1f;

		float radius = 0.5f;

		Random r = new Random();

		gl.glRotatef(1, 1, 0, 0);
		gl.glRotatef(1, 0, 1, 0);
		gl.glRotatef(1, 0, 0, 1);

		gl.glBegin(GL2.GL_POLYGON);

		for (int j = 0; j < numberOrVertices; j++) {
			for (int i = 0; i < numberOrVertices; i++) {
				float x = (float) (Math.cos(Math.toRadians(height)) * Math.cos(Math.toRadians(betta)) * radius);
				float y = (float) Math.sin(Math.toRadians(height)) * radius;
				float z = (float) (Math.cos(Math.toRadians(height)) * Math.sin(Math.toRadians(betta)) * radius);

				gl.glColor3d(r.nextFloat(), r.nextFloat(), r.nextFloat());

				gl.glVertex3f(x, y, z);
				betta += 360.0f / numberOrVertices;

				x = (float) (Math.cos(Math.toRadians(height)) * Math.cos(Math.toRadians(betta)) * radius);
				y = (float) Math.sin(Math.toRadians(height)) * radius;
				z = (float) (Math.cos(Math.toRadians(height)) * Math.sin(Math.toRadians(betta)) * radius);

				gl.glVertex3f(x, y, z);

				// 2
				betta += 360.0f / numberOrVertices;
				height -= 360.0f / numberOrVertices;

				x = (float) (Math.cos(Math.toRadians(height)) * Math.cos(Math.toRadians(betta)) * radius);
				y = (float) Math.sin(Math.toRadians(height)) * radius;
				z = (float) (Math.cos(Math.toRadians(height)) * Math.sin(Math.toRadians(betta)) * radius);

				gl.glColor3d(r.nextFloat(), r.nextFloat(), r.nextFloat());

				gl.glVertex3f(x, y, z);
				betta -= 360.0f / numberOrVertices;

				x = (float) (Math.cos(Math.toRadians(height)) * Math.cos(Math.toRadians(betta)) * radius);
				y = (float) Math.sin(Math.toRadians(height)) * radius;
				z = (float) (Math.cos(Math.toRadians(height)) * Math.sin(Math.toRadians(betta)) * radius);

				gl.glVertex3f(x, y, z);

				height += 360.0f / numberOrVertices;
			}
			height += 360.0f / numberOrVertices;
		}

		gl.glEnd();
	}

	float height = 0;

	public void draw3dCube(GL2 gl) {

		int numberOrVertices = 4;
		// angleX += .1f;
		// angleY += .1f;

		float radius = 0.5f;

		// Random r = new Random();

		betta += 1;
		// gl.glRotatef(1, 1, 0, 0);
		// gl.glRotatef(1, 0, 1, 0)

		gl.glBegin(GL2.GL_LINE_STRIP);

		gl.glColor3d(1, 0, 0);

		for (int i = 0; i < numberOrVertices + 1; i++) {
			float x = (float) Math.cos(Math.toRadians(height)) * radius;
			float y = (float) Math.sin(Math.toRadians(height)) * radius;

			gl.glVertex3f((float) (x * Math.cos(Math.toRadians(betta))), y, .5f);
			height += 360.0f / numberOrVertices;
		}

		gl.glColor3d(1, 1, 0);
		for (int i = 0; i < numberOrVertices + 1; i++) {
			float x = (float) Math.cos(Math.toRadians(height)) * radius;
			float y = (float) Math.sin(Math.toRadians(height)) * radius;

			gl.glVertex3f((float) (x * Math.sin(Math.toRadians(betta))), y, -.5f);
			height += 360.0f / numberOrVertices;
		}

		gl.glEnd();
	}

	public void drawBackground(GL2 gl) {
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		final GLCanvas glcanvas = new GLCanvas(capabilities);

		float hight = glcanvas.getHeight();
		float width = glcanvas.getWidth();

		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor3d(0, 0, 1);
		gl.glVertex2f(-1, 1);
		gl.glVertex2f(1, 1);
		gl.glVertex2f(1, 1 / 3f);
		gl.glVertex2f(-1, 1 / 3f);

		gl.glEnd();

		gl.glBegin(GL2.GL_TRIANGLE_FAN);

		gl.glColor3d(1, 0, 0);
		gl.glVertex2f(-1, 1 / 3f);
		gl.glVertex2f(1, 1 / 3f);
		gl.glVertex2f(1, -1 / 3f);
		gl.glVertex2f(-1, -1 / 3f);

		gl.glEnd();
		gl.glBegin(GL2.GL_TRIANGLE_FAN);

		gl.glColor3d(0, 1, 0);
		gl.glVertex2f(-1, -1 / 3f);
		gl.glVertex2f(1, -1 / 3f);
		gl.glVertex2f(1, -1);
		gl.glVertex2f(-1, -1);

		gl.glEnd();

	}

	float starRadius = 0.2f;
	float starRadiusIn = starRadius * .2f;

	float starX = 0;
	float starY = 0;
	float r = (float) Math.cos(Math.toRadians(45)) * starRadius;
	float h = r * .4f;

	public void star(GL2 gl) {

		Random ran = new Random();

		gl.glBegin(GL2.GL_TRIANGLE_FAN);

		gl.glColor3d(0, 0, 0);
		gl.glVertex2f(starX, starY);
		gl.glColor3d(1, 0, 0);

		gl.glVertex2f(starX + h, starY);
		gl.glVertex2f(starX + r, starY + r);
		gl.glVertex2f(starX, starY + h);
		gl.glVertex2f(starX - r, starY + r);
		gl.glVertex2f(starX - h, starY);
		gl.glVertex2f(starX - r, starY - r);
		gl.glVertex2f(starX, starY - h);
		gl.glVertex2f(starX + r, starY - r);
		gl.glVertex2f(starX + h, starY);

		// second

		gl.glVertex2f(starX, starY);
		gl.glColor3d(1, 0, 0);

		gl.glVertex2f(starX + starRadius, starY);
		gl.glVertex2f(starX + starRadiusIn, starY + starRadiusIn);
		gl.glVertex2f(starX, starY + starRadius);

		gl.glVertex2f(starX - starRadiusIn, starY + starRadiusIn);
		gl.glVertex2f(starX - starRadius, starY);
		gl.glVertex2f(starX - starRadiusIn, starY - starRadiusIn);

		gl.glVertex2f(starX, starY - starRadius);
		gl.glVertex2f(starX + starRadiusIn, starY - starRadiusIn);
		gl.glVertex2f(starX + starRadius, starY);

		gl.glEnd();
	}

	public void circle(GL2 gl) {

		int numberOrVertices = 36;
		float angle = 0;
		float radius = 0.5f;

		Random r = new Random();

		gl.glBegin(GL2.GL_TRIANGLE_FAN);

		gl.glColor3d(r.nextFloat(), r.nextFloat(), r.nextFloat());

		gl.glVertex2f(0, 0);

		// gl.glColor3d(0, 0, 0);

		for (int i = 0; i < numberOrVertices; i++) {
			float x = (float) Math.cos(Math.toRadians(angle)) * radius;
			float y = (float) Math.sin(Math.toRadians(angle)) * radius;

			gl.glVertex2f(x, y);
			angle += 360.0f / numberOrVertices;
		}

		gl.glVertex2f(radius, 0);
		gl.glEnd();
	}

	private void printShaderLog(int shader) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		int[] len = new int[1];
		int[] chWrittn = new int[1];
		byte[] log = null;

		gl.glGetShaderiv(shader, len[0], chWrittn, 0);

		if (len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetShaderInfoLog(shader, len[0], chWrittn, 0, log, 0);
			System.out.println("Shader info: ");
			for (int i = 0; i < log.length; i++) {
				System.out.print((char) log[i]);
			}
		}
	}

	private void printProgramLog(int program) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		int[] len = new int[1];
		int[] chWrittn = new int[1];
		byte[] log = null;

		gl.glGetProgramiv(program, GL4.GL_INFO_LOG_LENGTH, len, 0);

		if (len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetProgramInfoLog(program, len[0], chWrittn, 0, log, 0);
			System.out.println("Program info: ");
			for (int i = 0; i < log.length; i++) {
				System.out.print((char) log[i]);
			}
		}
	}

	public static void main(String[] args) {

		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		final GLCanvas glcanvas = new GLCanvas(capabilities);

		Line l = new Line();
		glcanvas.addGLEventListener(l);
		glcanvas.setSize(400, 400);

		final JFrame frame = new JFrame("Arsalan | OpenGL");

		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);

		FPSAnimator animator = new FPSAnimator(glcanvas, 24);
		animator.start();

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

}