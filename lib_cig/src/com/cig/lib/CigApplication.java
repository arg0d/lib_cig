package com.cig.lib;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.cig.lib.collada.ColladaParser;

public class CigApplication {

	GModel model;
	GShaderProgram shader;

	public CigApplication() {

	}

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();

			loop();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public void loop() {

		model = new ColladaParser().load();
		
		shader = new GShaderProgram("/vs.txt", "/fs.txt");
		shader.link();

		while (!Display.isCloseRequested()) {

			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			model.render(shader, null);

			Display.update();
			Display.sync(60);
		}

		Display.destroy();

	}

	public static void main(String[] args) {
		new CigApplication().start();
	}

}
