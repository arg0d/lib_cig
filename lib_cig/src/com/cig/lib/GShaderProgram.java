package com.cig.lib;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class GShaderProgram {

	private int _handle;

	public GShaderProgram(String vertexShader, String fragmentShader) {

		String vertexSource = readFile(vertexShader);
		String fragmentSource = readFile(fragmentShader);

		load(vertexSource, fragmentSource);

	}

	public void link() {

		glLinkProgram(_handle);

		int linkStatus = glGetProgrami(_handle, GL_LINK_STATUS);
		if (linkStatus == GL11.GL_FALSE) {
			String info = glGetProgramInfoLog(_handle, 1000);
			System.out.println(info);
		}

		bind();

	}

	private void load(String vertexSource, String fragmentSource) {

		int vertexShader = loadShader(GL_VERTEX_SHADER, vertexSource);
		int fragmentShader = loadShader(GL_FRAGMENT_SHADER, fragmentSource);

		_handle = glCreateProgram();

		glAttachShader(_handle, vertexShader);
		glAttachShader(_handle, fragmentShader);

	}

	private int loadShader(int shaderType, String source) {

		int handle = glCreateShader(shaderType);

		glShaderSource(handle, source);

		glCompileShader(handle);

		int compileStatus = glGetShaderi(handle, GL_COMPILE_STATUS);
		if (compileStatus == GL11.GL_FALSE) {

			String info = glGetShaderInfoLog(handle, 1000);
			System.out.println(info);

		}

		return handle;
	}

	private String readFile(String filePath) {

		InputStream inputStream = GShaderProgram.class.getResourceAsStream(filePath);

		if (inputStream == null) {
			throw new IllegalArgumentException("File not found: " + filePath);
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		String line = null;

		StringBuilder builder = new StringBuilder();

		try {

			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

	public void bind() {
		glUseProgram(_handle);
	}

	public int getHandle() {
		return _handle;
	}
	
	
	
	
	
	
	
	public void setUniformMatrix4(String uniformName, Matrix4f matrix) {
		int matrixLoc = glGetUniformLocation(getHandle(), uniformName);
		
		FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		glUniformMatrix4(matrixLoc, false, matrixBuffer);
	}

}
