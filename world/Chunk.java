package world;

import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_NORMAL_ARRAY;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glNormalPointer;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

public class Chunk extends Drawable {
	public static final int SIZE = 8;// 16;//128;
	Cube[][][] cubes = new Cube[SIZE][SIZE][SIZE];
	public int numCubesEnabled = 0;

	public Chunk() {
		generate();
		generateVertexArray();
	}

	public void generate() {
		// temporary
//		int level = 3 + (int) (Math.random() * (SIZE - 3));
		int level = SIZE;
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < level; y++) {
				for (int z = 0; z < SIZE; z++) {
					cubes[x][y][z] = new Cube();
					if (y == level - 1 || true) {
						cubes[x][y][z].show = (Math.random() > 0.5);
						numCubesEnabled++;
					}
				}
			}
		}
	}

	FloatBuffer cBuffer; // colors
	FloatBuffer vBuffer; // vertices
	FloatBuffer nBuffer; // normals

	void generateVertexArray() {
		
		//om 512 kuber aktiverade: (alla)
		//den ritar 128+43=171 kuber = 1368 verts = 4104 vals
		//numValsPerVert * numVertsPerSide * numSidesPerCube * numCubesEnabled = 36864
		
		int numValsPerVert = 3;
		int numVertsPerSide = 4;
		int numSidesPerCube = 6;

		cBuffer = BufferUtils.createFloatBuffer(numValsPerVert
				* numVertsPerSide * numSidesPerCube * numCubesEnabled);
		vBuffer = BufferUtils.createFloatBuffer(numValsPerVert
				* numVertsPerSide * numSidesPerCube * numCubesEnabled);
		nBuffer = BufferUtils.createFloatBuffer(numValsPerVert
				* numVertsPerSide * numSidesPerCube * numCubesEnabled);
		
		int counter = 0;
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				for (int z = 0; z < SIZE; z++) {
					if (cubes[x][y][z] != null && cubes[x][y][z].show == true) {

						// bottom, top, right, left, close, far
						boolean[] sidesToRender = {
						((y == 0) || (cubes[x][y - 1][z] == null) || !(cubes[x][y - 1][z].show)),
						((y == SIZE - 1) || (cubes[x][y + 1][z] == null) || !(cubes[x][y + 1][z].show)),
						((x == SIZE - 1) || (cubes[x + 1][y][z] == null) || !(cubes[x + 1][y][z].show)),
						((x == 0) || (cubes[x - 1][y][z] == null) || !(cubes[x - 1][y][z].show)),
						((z == SIZE - 1) || (cubes[x][y][z + 1] == null) || !(cubes[x][y][z + 1].show)),
						((z == 0) || (cubes[x][y][z - 1] == null) || !(cubes[x][y][z - 1].show))};
						
						for (int i = 0; i < numVertsPerSide * numSidesPerCube; i++) {
							if (sidesToRender[i/4]) {
								cBuffer.put(cubes[x][y][z].color);
							}
						}
						// cBuffer.flip();

						for (int i = 0; i < numVertsPerSide * numSidesPerCube; i++) {
//							 vBuffer.put(Cube.vertices[i]);
							if (sidesToRender[i/4]) {
								vBuffer.put(Cube.vertices[i][0] + x);
								vBuffer.put(Cube.vertices[i][1] + y);
								vBuffer.put(Cube.vertices[i][2] + z);
								counter++;
							}
						}
						// vBuffer.flip();

						for (int i = 0; i < numVertsPerSide * numSidesPerCube; i++) {
							if (sidesToRender[i/4]) {
								nBuffer.put(Cube.normals[i][0]);
								nBuffer.put(Cube.normals[i][1]);
								nBuffer.put(Cube.normals[i][2]);
							}
						}
						// nBuffer.flip();

					}
				}
			}
		}
		cBuffer.limit(counter);
		vBuffer.limit(counter);
		nBuffer.limit(counter);
		cBuffer.flip();
		vBuffer.flip();
		nBuffer.flip();
		
		System.out.println("Counter: "+counter+", numEnabled: "+numCubesEnabled+", SIZE: "+SIZE);
		System.out.println("Arrays: "+numValsPerVert * numVertsPerSide * numSidesPerCube * numCubesEnabled);
		
	}

	void drawVertexArray() {
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
		
		glPolygonMode(GL_FRONT, GL_FILL);

		glNormalPointer(3 << 2, nBuffer);
		glColorPointer(3, /* stride */3 << 2, cBuffer);
		glVertexPointer(3, /* stride */3 << 2, vBuffer);
		glDrawArrays(GL_QUADS, 0, /* elements */numCubesEnabled * 24); // numVerts

		glDisableClientState(GL_NORMAL_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
	}

	/** Draw all the cubes in this chunk. */
	public void draw() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				for (int z = 0; z < SIZE; z++) {
					if (cubes[x][y][z] != null) {
						// if(!surrounded(x,y,z)){
						if (cubes[x][y][z].show == false) {
							glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
						}
						glPushMatrix();
						glTranslatef(x * Cube.SIZE, y * Cube.SIZE, z
								* Cube.SIZE);
						cubes[x][y][z].draw();
						glPopMatrix();

						glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
						// } //let if(cube show) end here
					}
				}
			}
		}
	}

	/**
	 * Help-method to determine if a cube needs to be drawn or not. (Only draw
	 * cubes that is NOT surrounded, not the most efficient, but better and easy
	 * to test.)
	 */
	private boolean surrounded(int x, int y, int z) {
		boolean[] b = new boolean[6]; // one boolean for each side of the cube
		Arrays.fill(b, false); // false like in "nothing there"
		if (0 < x && cubes[x - 1][y][z] != null)
			b[0] = true;
		if (x < cubes.length - 1 && cubes[x + 1][y][z] != null)
			b[1] = true;

		if (0 < y && cubes[x][y - 1][z] != null)
			b[2] = true;
		if (y < cubes[0].length - 1 && cubes[x][y + 1][z] != null)
			b[3] = true;

		if (0 < z && cubes[x][y][z - 1] != null)
			b[4] = true;
		if (z < cubes[0][0].length - 1 && cubes[x][y][z + 1] != null)
			b[5] = true;

		return b[0] && b[1] && b[2] && b[3] && b[4] && b[5];
	}
}
