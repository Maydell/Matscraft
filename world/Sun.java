package world;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Sun {

	FloatBuffer light, position;
	
	public Sun(float x, float y, float z) {
		light = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(.8f).put(.8f).put(.8f).put(1).flip();
		position = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(x).put(y).put(z).put(0).flip();
		
		glLight(GL_LIGHT0, GL_POSITION, position);

		glLight(GL_LIGHT0, GL_DIFFUSE, light);
		glLight(GL_LIGHT0, GL_SPECULAR, light);
		
		glEnable(GL_LIGHT0);
	}
}
