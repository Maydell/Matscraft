package world;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;


public class World extends Drawable {
	Chunk[][][] chunk; //will be used for displaying more cubes. index as [x][y][z]
	
	/** Create a new world, currently of static size, for debugging and testing purposes. */
	public World(){
		chunk = new Chunk[16][1][16]; //determines the biggest world size to 10*128 by 5*128
		for (int x = 0; x < chunk.length; x++) {
			for (int z = 0; z < chunk[0][0].length; z++) {
				chunk[x][0][z] = null; //initiate to null, and only draw real chunks.
			}
		}
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				chunk[x][0][z] = new Chunk();
			}
		}
	}
	
	public void generateChunk(int x, int z){
		
		Chunk c = new Chunk();
		
		chunk[x][0][z] = c;
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
