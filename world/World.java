package com.matscraft.world;

import static org.lwjgl.opengl.GL11.*;

import java.util.Arrays;


public class World extends Drawable {
	Chunk[][][] chunk; //will be used for displaying more cubes. index as [x][y][z]
	
	/** Create a new world, currently of static size, for debugging and testing purposes. */
	public World(){
		chunk = new Chunk[10][1][5]; //determines the biggest world size to 10*128 by 5*128
		for (int x = 0; x < chunk.length; x++) {
			for (int z = 0; z < chunk[0][0].length; z++) {
				chunk[x][0][z] = null; //initiate to null, and only draw real chunks.
			}
		}
		chunk[0][0][0] = new Chunk();
		chunk[1][0][0] = new Chunk();
		chunk[2][0][0] = new Chunk();
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
			for (int y = 0; y < chunk[0].length; y++) {
				if(chunk[x][y][0] != null){
					glPushMatrix();
					glTranslatef(x*Chunk.SIZE*Cube.SIZE, 0, y*Chunk.SIZE*Cube.SIZE);
//					chunk[x][y][0].draw();
					chunk[x][y][0].drawVertexArray();
					glPopMatrix();
				}
			}
		}
		
		// restore regular rendering
//		glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
	}
}
