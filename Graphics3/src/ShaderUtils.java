import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

public class ShaderUtils {


	public static int createShaderProgram() {
		GL4 gl = (GL4) GLContext.getCurrentGL();

		String vshaderSource[] = readShaderSource("src/vshader.glsl");
		String fshaderSource[] = readShaderSource("src/fshader.glsl");

		int vShader = gl.glCreateShader(GL4.GL_VERTEX_SHADER);
		gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);
		gl.glCompileShader(vShader);
		printShaderLog(vShader);

		int fShader = gl.glCreateShader(GL4.GL_FRAGMENT_SHADER);
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

	public static String[] readShaderSource(String filename) {
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
			program[i] = (String) lines.elementAt(i) + "\n";
			// System.out.print(program[i]);
		}

		return program;
	}

	public static void printShaderLog(int shader) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		int[] len = new int[1];
		int[] chWrittn = new int[1];
		byte[] log = null;
		// determine the length of the shader compilation log
		gl.glGetShaderiv(shader, GL4.GL_INFO_LOG_LENGTH, len, 0);
		if (len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetShaderInfoLog(shader, len[0], chWrittn, 0, log, 0);
			System.out.println("Shader Info Log: ");
			for (int i = 0; i < log.length; i++) {
				System.out.print((char) log[i]);
			}
		}
	}

	public static void printProgramLog(int prog) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		int[] len = new int[1];
		int[] chWrittn = new int[1];
		byte[] log = null;
		// determine the length of the program linking log
		gl.glGetProgramiv(prog, GL4.GL_INFO_LOG_LENGTH, len, 0);
		if (len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetProgramInfoLog(prog, len[0], chWrittn, 0, log, 0);
			System.out.println("Program Info Log: ");
			for (int i = 0; i < log.length; i++) {
				System.out.print((char) log[i]);
			}
		}
	}
}
