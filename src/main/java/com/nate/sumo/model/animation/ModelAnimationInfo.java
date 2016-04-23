package com.nate.sumo.model.animation;

import java.net.URL;

import com.nate.model.MD5Animation;
import com.nate.model.MD5AnimationInfo;
import com.nate.model.MD5Joint;
import com.nate.model.MD5Mesh;
import com.nate.model.MD5Model;
import com.nate.sumo.display.Drawable;
import com.nate.sumo.display.ModelManager;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.model.appearence.AppearenceMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

public class ModelAnimationInfo implements Drawable{
	
	private MD5Animation currentAnimation;
	
	private MD5Joint[] skeleton;
	
	private MD5AnimationInfo animationInfo;
	
	private AppearenceMap appearence;

	private MD5Model bodyModel;
	private MD5Model hairModel;
	private MD5Model headModel;
	private MD5Model mawashiModel;

	public ModelAnimationInfo( AppearenceMap appMap ){
		appearence = appMap;
		
		getBodyModel();
		skeleton = getBodyModel().getBaseSkeleton();
		getHairModel();
		getHeadModel();
		getMawashiModel();
	}
	
	// weird thing is... x = z, z = x, y = y
	public void draw(){
		
		glPushMatrix();
		
		glTranslatef( 2.4f, 0.4f, -2.0f );
		glScalef( 1.2f, 1.2f, 1.2f );
		
		glRotatef( -90.0f, 1.0f, 0.0f, 0.0f );
		
		glEnableClientState( GL_VERTEX_ARRAY );
		glEnableClientState( GL_TEXTURE_COORD_ARRAY );
		
		goThroughMeshes( getBodyModel(), getSkeleton() );
		goThroughMeshes( getHeadModel(), getSkeleton() );
		goThroughMeshes( getHairModel(), getSkeleton() );
		goThroughMeshes( getMawashiModel(), getSkeleton() );
		
		glDisableClientState( GL_VERTEX_ARRAY );
		glDisableClientState( GL_TEXTURE_COORD_ARRAY );
		
		glPopMatrix();
	}
	
	public void goThroughMeshes( MD5Model model, MD5Joint[] skeleton ){
		
		for ( MD5Mesh mesh : model.getMeshes() ){
			
			model.prepareModel( mesh, skeleton );
			mesh.getIndexArray().flip();
			mesh.getVertexArray().flip();
			mesh.getTexelArray().flip();
			
			glVertexPointer( 3, 0, mesh.getVertexArray() );
			glTexCoordPointer( 2, 0, mesh.getTexelArray() );
			
			glDrawElements( GL_TRIANGLES, mesh.getIndexArray() );
		}
	}
	
	private AppearenceMap getAppearence(){
		return appearence;
	}
	
	public MD5Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public MD5Model getBodyModel() {
		
		if ( bodyModel == null ){
			String model = getAppearence().getBodyModel();
			
			if ( model == null ){
				model = "/base_sumo.md5mesh";
			}
			
			bodyModel = ModelManager.getInstance().loadModel( model );
		}
		
		return bodyModel;
	}

	public MD5Model getHairModel() {
		
		if ( hairModel == null ){
			String model = getAppearence().getHairModel();
			
			if ( model == null ){
				model = "/base_hair.md5mesh";
			}
			
			hairModel = ModelManager.getInstance().loadModel( model );
		}
		
		return hairModel;
	}

	public MD5Model getHeadModel() {
		
		if ( headModel == null ){
			String model = getAppearence().getHeadModel();
			
			if ( model == null ){
				model = "/base_head.md5mesh";
			}
			
			headModel = ModelManager.getInstance().loadModel( model );
		}
		
		return headModel;
	}

	public MD5Model getMawashiModel() {
		
		if ( mawashiModel == null ){
			String model = getAppearence().getMawashiModel();
			
			if ( model == null ){
				model = "/base_head.md5mesh";
			}
			
			mawashiModel = ModelManager.getInstance().loadModel( model );
		}
		
		return mawashiModel;
	}

	public MD5Joint[] getSkeleton() {
		return skeleton;
	}

	public void setSkeleton(MD5Joint[] skeleton) {
		this.skeleton = skeleton;
	}

	public MD5AnimationInfo getAnimationInfo() {
		return animationInfo;
	}

	public void setAnimationInfo(MD5AnimationInfo animationInfo) {
		this.animationInfo = animationInfo;
	}
	
	
}
