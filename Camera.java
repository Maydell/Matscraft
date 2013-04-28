import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

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
		
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
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
