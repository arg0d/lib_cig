package com.cig.lib;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

public class GModel {

	private int _positionHandle, _colorHandle, _normalHandle, _indexHandle;

	private GMesh mesh;
	
	public GModel(GMesh mesh) {
		
		if (mesh == null) return;
		
		this.mesh = mesh;
		
		_positionHandle = glGenBuffers();
		_colorHandle = glGenBuffers();
		_normalHandle = glGenBuffers();
		_indexHandle = glGenBuffers();
		
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(mesh.vertices.length);
		positionBuffer.put(mesh.vertices);
		positionBuffer.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, _positionHandle);
		glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
		
		float[] colors = {
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f
		};
		
		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors.length);
		colorBuffer.put(colors);
		colorBuffer.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, _colorHandle);
		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
		
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(mesh.indices.length);
		indexBuffer.put(mesh.indices);
		indexBuffer.flip();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, _indexHandle);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		
	}

	public void update(float fDeltaTime) {

	}

	float rot = 0;
	
	public void render(GShaderProgram shader, Camera camera) {
		
		rot++;
		rot = rot % 360;
		
		if (this.mesh == null) return;
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		GLU.gluPerspective(70, 800f / 600f, 0.3f, 1000);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glRotatef(90, 1, 0, 0);
		glRotatef(rot, 0, 0, 1);
		
		
		shader.setUniformMatrix4("transformationMatrix", mesh.transformationMatrix);
		
		
		glEnableClientState(GL_VERTEX_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, _positionHandle);
		glVertexPointer(3, GL_FLOAT, 0, 0L);
		
		glBindBuffer(GL_ARRAY_BUFFER, _indexHandle);
		
		glDrawElements(GL_TRIANGLE_STRIP, mesh.indices.length, GL_UNSIGNED_INT, 0L);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableClientState(GL_VERTEX_ARRAY);
		
	}

}
