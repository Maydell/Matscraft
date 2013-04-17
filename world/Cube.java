package com.matscraft.world;

import static org.lwjgl.opengl.GL11.*;

public class Cube extends Drawable {
	
	public static final float HALF_CUBE = 0.5f;
	public static final float SIZE = HALF_CUBE * 2;
	
	boolean show = false; //determines if we want to draw this cube
	
	float[] color = { 1.0f, 0.5f, 0.0f }; //change to material later
	
	public static float[][] vertices = {
		//bottom
		{-HALF_CUBE, -HALF_CUBE, -HALF_CUBE},
		{-HALF_CUBE, -HALF_CUBE,  HALF_CUBE},
		{ HALF_CUBE, -HALF_CUBE,  HALF_CUBE},
		{ HALF_CUBE, -HALF_CUBE, -HALF_CUBE},
		
		//top
		{-HALF_CUBE,  HALF_CUBE, -HALF_CUBE},
		{-HALF_CUBE,  HALF_CUBE,  HALF_CUBE},
		{ HALF_CUBE,  HALF_CUBE,  HALF_CUBE},
		{ HALF_CUBE,  HALF_CUBE, -HALF_CUBE},
		
		//right
		{ HALF_CUBE,  HALF_CUBE,  HALF_CUBE},
		{ HALF_CUBE, -HALF_CUBE,  HALF_CUBE},
		{ HALF_CUBE, -HALF_CUBE, -HALF_CUBE},
		{ HALF_CUBE,  HALF_CUBE, -HALF_CUBE},
		
		//left
		{-HALF_CUBE,  HALF_CUBE,  HALF_CUBE},
		{-HALF_CUBE, -HALF_CUBE,  HALF_CUBE},
		{-HALF_CUBE, -HALF_CUBE, -HALF_CUBE},
		{-HALF_CUBE,  HALF_CUBE, -HALF_CUBE},
		
		//close
		{ HALF_CUBE,  HALF_CUBE,  HALF_CUBE},
		{ HALF_CUBE, -HALF_CUBE,  HALF_CUBE},
		{-HALF_CUBE, -HALF_CUBE,  HALF_CUBE},
		{-HALF_CUBE,  HALF_CUBE,  HALF_CUBE},
		
		//far
		{ HALF_CUBE,  HALF_CUBE, -HALF_CUBE},
		{ HALF_CUBE, -HALF_CUBE, -HALF_CUBE},
		{-HALF_CUBE, -HALF_CUBE, -HALF_CUBE},
		{-HALF_CUBE,  HALF_CUBE, -HALF_CUBE}
	};
	
	public static float[][] normals = {
		{ 0,-1, 0},
		{ 0,-1, 0},
		{ 0,-1, 0},
		{ 0,-1, 0},
		
		{ 0, 1, 0},
		{ 0, 1, 0},
		{ 0, 1, 0},
		{ 0, 1, 0},
		
		{ 1, 0, 0},
		{ 1, 0, 0},
		{ 1, 0, 0},
		{ 1, 0, 0},
		
		{-1, 0, 0},
		{-1, 0, 0},
		{-1, 0, 0},
		{-1, 0, 0},

		{ 0, 0, 1},
		{ 0, 0, 1},
		{ 0, 0, 1},
		{ 0, 0, 1},
		
		{ 0, 0,-1},
		{ 0, 0,-1},
		{ 0, 0,-1},
		{ 0, 0,-1}
	};
	
	public void draw(){
		glColor3f(color[0], color[1], color[2]);
		
		glBegin(GL_QUADS);
		{
			//bottom 
			glNormal3f(0, -1, 0);
			glVertex3f(-HALF_CUBE, -HALF_CUBE, -HALF_CUBE);
			glVertex3f(-HALF_CUBE, -HALF_CUBE,  HALF_CUBE);
			glVertex3f( HALF_CUBE, -HALF_CUBE,  HALF_CUBE);
			glVertex3f( HALF_CUBE, -HALF_CUBE, -HALF_CUBE);
			
			//top
			glNormal3f(0, 1, 0);
			glVertex3f(-HALF_CUBE,  HALF_CUBE, -HALF_CUBE);
			glVertex3f(-HALF_CUBE,  HALF_CUBE,  HALF_CUBE);
			glVertex3f( HALF_CUBE,  HALF_CUBE,  HALF_CUBE);
			glVertex3f( HALF_CUBE,  HALF_CUBE, -HALF_CUBE);
			
			//right
			glNormal3f(1, 0, 0);
			glVertex3f( HALF_CUBE,  HALF_CUBE,  HALF_CUBE);
			glVertex3f( HALF_CUBE, -HALF_CUBE,  HALF_CUBE);
			glVertex3f( HALF_CUBE, -HALF_CUBE, -HALF_CUBE);
			glVertex3f( HALF_CUBE,  HALF_CUBE, -HALF_CUBE);
			
			//left
			glNormal3f(-1, 0, 0);
			glVertex3f(-HALF_CUBE,  HALF_CUBE,  HALF_CUBE);
			glVertex3f(-HALF_CUBE, -HALF_CUBE,  HALF_CUBE);
			glVertex3f(-HALF_CUBE, -HALF_CUBE, -HALF_CUBE);
			glVertex3f(-HALF_CUBE,  HALF_CUBE, -HALF_CUBE);
			
			//close
			glNormal3f(0, 0, 1);
			glVertex3f( HALF_CUBE,  HALF_CUBE,  HALF_CUBE);
			glVertex3f( HALF_CUBE, -HALF_CUBE,  HALF_CUBE);
			glVertex3f(-HALF_CUBE, -HALF_CUBE,  HALF_CUBE);
			glVertex3f(-HALF_CUBE,  HALF_CUBE,  HALF_CUBE);
			
			//far
			glNormal3f(0, 0, -1);
			glVertex3f( HALF_CUBE,  HALF_CUBE, -HALF_CUBE);
			glVertex3f( HALF_CUBE, -HALF_CUBE, -HALF_CUBE);
			glVertex3f(-HALF_CUBE, -HALF_CUBE, -HALF_CUBE);
			glVertex3f(-HALF_CUBE,  HALF_CUBE, -HALF_CUBE);
		}
		glEnd();
	}
}
