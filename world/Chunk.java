package com.matscraft.world;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

public class Chunk extends Drawable {
	public static final int SIZE = 8;//16;//128;
	Cube[][][] cubes = new Cube[SIZE][SIZE][SIZE];
	

	public Chunk(){
		generate();
		generateVertexArray();
	}
	
	public void generate(){
		//temporary, until simplex noise is in place
		int level = 3 + (int)(Math.random()*(SIZE-3));
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < level; y++) {
				for (int z = 0; z < SIZE; z++) {
					cubes[x][y][z] = new Cube();
					if(y == level-1){
						cubes[x][y][z].show = true;
					}
				}
			}
		}
	}
	
	FloatBuffer cBuffer; //colors
	FloatBuffer vBuffer; //vertices
	FloatBuffer nBuffer; //normals
	
	void generateVertexArray(){
		Cube c = new Cube();
		
		cBuffer = BufferUtils.createFloatBuffer(3*4*6);
		for (int i = 0; i < 4*6; i++) {
			cBuffer.put(c.color);
		}
		cBuffer.flip();

		vBuffer = BufferUtils.createFloatBuffer(3*4*6);
		for (int i = 0; i < 4*6; i++) {
			vBuffer.put(Cube.vertices[i][0]).put(Cube.vertices[i][1]).put(Cube.vertices[i][2]);
		}
		vBuffer.flip();
		
		nBuffer = BufferUtils.createFloatBuffer(3*4*6);
		for (int i = 0; i < 4*6; i++) {
			nBuffer.put(Cube.normals[i][0]).put(Cube.normals[i][1]).put(Cube.normals[i][2]);
		}
		nBuffer.flip();
	}
	
	void drawVertexArray()
	{
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);

		glNormalPointer(3 << 2, nBuffer);
		glColorPointer(3, /* stride */3 << 2, cBuffer);
		glVertexPointer(3, /* stride */3 << 2, vBuffer);
		glDrawArrays(GL_QUADS, 0, /* elements */4*6);

		glDisableClientState(GL_NORMAL_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
	}
	
	/** Draw all the cubes in this chunk. */
	public void draw() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				for (int z = 0; z < SIZE; z++) {
					if(cubes[x][y][z] != null){
						//if(!surrounded(x,y,z)){
						if(cubes[x][y][z].show == false){
							glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
						}
							glPushMatrix();
							glTranslatef(x*Cube.SIZE, y*Cube.SIZE, z*Cube.SIZE);
							cubes[x][y][z].draw();
							glPopMatrix();
							
							glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
						//} //let if(cube show) end here
					}
				}
			}
		}
	}
	/** Help-method to determine if a cube needs to be drawn or not. 
	 * (Only draw cubes that is NOT surrounded, not the most efficient, but better and easy to test.) */
	private boolean surrounded(int x, int y, int z) {
		boolean[] b = new boolean[6]; //one boolean for each side of the cube
		Arrays.fill(b, false); //false like in "nothing there"
		if( 0 < x && cubes[x-1][y][z] != null ) b[0] = true;
		if( x < cubes.length-1 && cubes[x+1][y][z] != null ) b[1] = true;
		
		if( 0 < y && cubes[x][y-1][z] != null ) b[2] = true;
		if( y < cubes[0].length-1 && cubes[x][y+1][z] != null ) b[3] = true;
		
		if( 0 < z && cubes[x][y][z-1] != null ) b[4] = true;
		if( z < cubes[0][0].length-1 && cubes[x][y][z+1] != null ) b[5] = true;
		
		return b[0] && b[1] && b[2] && b[3] && b[4] && b[5];
	}
}
