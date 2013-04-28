
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import world.Sun;
import world.World;

public class Matscraft {
	World world;
	Camera cam;
	Sun sun;
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
		sun = new Sun(2f, 2f, 2f);
		
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
			float[] lp = {2f, 4f, 2f, 0f};
			glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer) BufferUtils.createFloatBuffer(4).put(lp).flip());
			/*
			glPushMatrix();
			{
				glBegin(GL_QUADS);
				{
					glColor3f(1f, 1f, 1f);
					glNormal3f(0f, 1f, 0f);
					glVertex3f(50, -1f, 50);
					glVertex3f(50, -1f, -50);
					glVertex3f(-50, -1f, -50);
					glVertex3f(-50, -1f, 50);
				}
				glEnd();
			}
			glPopMatrix();
			*/
			
			glPushMatrix();
			
			world.draw();
			glPopMatrix();
			
			Display.update();
		}
		Display.destroy();
	}
	
	private void tick(long passedTime) {
		// Amount to move or rotate
		float movement = 10f * passedTime / 1000000000;
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
//			cam.setRy(cam.getRy() + 50 * movement);
			cam.move(movement, 0);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
//			cam.setRy(cam.getRy() - 50 * movement);
			cam.move(-movement, 0);
		}
		
		if (Mouse.isButtonDown(1)) {
			cam.setRy(cam.getRy() + (int) (Mouse.getDX() * .6));
			cam.setRx(cam.getRx() - (int) (Mouse.getDY() * .6));
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			cam.setY(cam.getY() + movement);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			cam.setY(cam.getY() - movement);
		}
	}

	public static void main(String[] args) {
		new Matscraft();
	}
}
