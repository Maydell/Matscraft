
package com.matscraft;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.matscraft.world.World;

import static org.lwjgl.opengl.GL11.*;

public class Matscraft {
	World world;
	Camera cam;
	public Matscraft(){
		
		world = new World();
		
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setTitle("Matscraft");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		cam = new Camera(70f, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000f);
		
		long lastTime = System.nanoTime();
		
		while (!Display.isCloseRequested()) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - lastTime;
			int fps = (int) (1000000000 / passedTime);
			lastTime = currentTime;
			Display.setTitle(cam.getX() + ", " + cam.getY() + ", " + cam.getZ() + " FPS: " + fps);
			tick(passedTime);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			cam.useView();
			glPushMatrix();
			{
				glBegin(GL_QUADS);
				{
					glColor3f(1f, 1f, 1f);
					glVertex3f(50, -1f, 50);
					glVertex3f(50, -1f, -50);
					glVertex3f(-50, -1f, -50);
					glVertex3f(-50, -1f, 50);
				}
				glEnd();
			}
			glPopMatrix();
			
			////////////////////
			float[] lp = {50f,5f,0f,1f};
			//glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer) BufferUtils.createFloatBuffer(4).put(lp[0]).put(lp[1]).put(lp[2]).put(lp[3]).flip());
			
			//nu kommer ljuset ifrån där vi står:
			glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer) BufferUtils.createFloatBuffer(4).put(cam.getX()).put(cam.getY()).put(cam.getZ()).put(lp[3]).flip());
			
			////////////////////
			glPushMatrix();
			//glTranslatef(1, 1, 1);
			//glRotatef(currentTime*0.00000001f, 0, 1, 0); //rotating test for normals
			////////////////////
			
			world.draw();
			glPopMatrix();
			
			Display.update();
		}
		Display.destroy();
	}
	
	private void tick(long passedTime) {
		// Amount to move or rotate
		float movement = 5f * passedTime / 1000000000;
		if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			cam.move(movement, 1);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			cam.move(-movement, 1);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			cam.move(-movement, 0);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			cam.move(movement, 0);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			cam.setRy(cam.getRy() + 50 * movement);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			cam.setRy(cam.getRy() - 50 * movement);
		}
		
		cam.setY(cam.getY() + Mouse.getDWheel()/100);
	}

	public static void main(String[] args) {
		new Matscraft();
	}
}
