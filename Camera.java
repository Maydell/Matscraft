import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Camera {

	// Coordinates and rotation of camera, field of view, aspect ratio, near and far cutting point
	private float x, y, z, rx, ry, rz, fov, aspect, near, far;
	
	public Camera(float fov, float aspect, float near, float far) {
		x = y = z = rx = ry = rz = 0;
		
		this.fov = fov;
		this.aspect = aspect;
		this.near = near;
		this.far = far;

		initProjection();
	}
	
	/**
	 * Initializes openGL.
	 */
	private void initProjection() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(fov, aspect, near, far);
		glMatrixMode(GL_MODELVIEW);
		
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		//glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer) BufferUtils.createFloatBuffer(4).put(-20f).put(40f).put(-20f).put(1).flip());
		
		/////////////////////
		//float[] lp = {10f,5f,0f,1f};
		//glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer) BufferUtils.createFloatBuffer(4).put(lp[0]).put(lp[1]).put(lp[2]).put(lp[3]).flip());
		glLightf(GL_LIGHT0, GL_QUADRATIC_ATTENUATION, 0.1f);
		glLightf(GL_LIGHT0, GL_CONSTANT_ATTENUATION, 1.5f);
		/////////////////////
		
		glEnable(GL_COLOR_MATERIAL);
	}
	
	/**
	 * Moves the camera to its coordinates and rotates it.
	 */
	public void useView() {
		glRotatef(rx, 1, 0, 0);
		glRotatef(ry, 0, 1, 0);
		glRotatef(rz, 0, 0, 1);
		glTranslatef(-x, -y, -z);
	}
	
	/**
	 * Moves the camera.
	 * @param amount The amount to move.
	 * @param dir This should be 1 if movement is forward/backwards and 0 if sideways.
	 */
	public void move(float amount, float dir) {
		z += amount * Math.sin(Math.toRadians(ry - 90 * dir));
		x += amount * Math.cos(Math.toRadians(ry - 90 * dir));
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry;
	}

	public float getRz() {
		return rz;
	}

	public void setRz(float rz) {
		this.rz = rz;
	}
}
