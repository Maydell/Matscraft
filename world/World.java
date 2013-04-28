package world;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.util.Random;


public class World extends Drawable {
	Chunk[][][] chunk; //will be used for displaying more cubes. index as [x][y][z]
	
	/** Create a new world, currently of static size, for debugging and testing purposes. */
	public World(){
		chunk = new Chunk[32][1][32]; //determines the biggest world size to 10*128 by 5*128
		Random r = new Random();
		int[][] heightmap = new int[chunk.length * Chunk.SIZE][chunk[0][0].length * Chunk.SIZE];
		for (int i = 0; i < heightmap.length; i++) {
			for (int e = 0; e < heightmap[0].length; e++) {
				int dy = 0;
				float random = r.nextFloat();
				if (i != 0 || e != 0) { // If there is a previous on i-axis
					if (random <= .1) {
						dy = -1;
					} else if (random >= .9f) {
						dy = 1;
					}
					if (i == 0) {
						heightmap[i][e] = heightmap[i][e - 1] + dy;
					} else if(e == 0) {
						heightmap[i][e] = heightmap[i - 1][e] + dy;
					} else { // Both are non-zero
						int heighti = heightmap[i][e] = heightmap[i][e - 1] + dy;
						int heighte = heightmap[i][e] = heightmap[i - 1][e] + dy;
						if (heighti == heighte) {
							heightmap[i][e] = heighti;
						} else {
							heightmap[i][e] = (r.nextFloat() >= .5f)? heighti : heighte;
						}
					}
					if (heightmap[i][e] > 8) heightmap[i][e] = 8;
					if (heightmap[i][e] < 1) heightmap[i][e] = 1;
				} else {
					heightmap[i][e] = 3 + r.nextInt(Chunk.SIZE - 5);
				}
			}
		}
		
		for (int x = 0; x < chunk.length; x++) {
			for (int z = 0; z < chunk[0][0].length; z++) {
				chunk[x][0][z] = null; //initiate to null, and only draw real chunks.
			}
		}
		for (int x = 0; x < 32; x++) {
			for (int z = 0; z < 32; z++) {
				chunk[x][0][z] = new Chunk(heightmap, x * Chunk.SIZE, z * Chunk.SIZE);
			}
		}
	}
	
	public void generateChunk(int x, int z){
		
//		Chunk c = new Chunk();
		
//		chunk[x][0][z] = c;
	}

	/** Draw all the cubes in this world. Can later be changed to also draw players, lights, etc. */
	public void draw() {
		
		// enable wireframe
//		glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
		
		for (int x = 0; x < chunk.length; x++) {
			for (int z = 0; z < chunk[0][0].length; z++) {
				if(chunk[x][0][z] != null){
					glPushMatrix();
					glTranslatef(x*Chunk.SIZE*Cube.SIZE, 0, z*Chunk.SIZE*Cube.SIZE);
//					chunk[x][0][z].draw();
					chunk[x][0][z].drawVertexArray();
					glPopMatrix();
				}
			}
		}
		
		// restore regular rendering
//		glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
	}
}
