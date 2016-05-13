package com.nate.sumo.model.animation;

import java.util.List;

import com.nate.model.MD5Animation;
import com.nate.model.MD5Joint;
import com.nate.model.MD5Mesh;
import com.nate.model.MD5Model;
import com.nate.sumo.display.Drawable;
import com.nate.sumo.display.ModelManager;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.model.appearence.AppearenceMap;

import static org.lwjgl.opengl.GL11.*;

public class ModelAnimationInfo implements Drawable{
	
	private AppearenceMap appearence;

	private MD5Model model;
	private MD5Animation animation;

	public ModelAnimationInfo( AppearenceMap appMap ){
		appearence = appMap;
		
		getModel();
//		skeleton = getBodyModel().getBaseSkeleton();
	}
	
	public void update( long deltaTime ){
		getModel().update( deltaTime );
	}
	
	// weird thing is... x = z, z = y, y = x
	public void draw(){
		
		glPushMatrix();
		
		glRotatef( -90.0f, 1.0f, 0.0f, 0.0f );
		
		glTranslatef( 2.4f, 2.0f, 0.4f );
		glScalef( 1.2f, 1.2f, 1.2f );
		
		
		glEnableClientState( GL_VERTEX_ARRAY );
		glEnableClientState( GL_NORMAL_ARRAY );
		glEnableClientState( GL_TEXTURE_COORD_ARRAY );
		
		getModel().render();
//		goThroughMeshes( getModel() );
//		goThroughMeshes( getHeadModel(), getSkeleton() );
//		goThroughMeshes( getHairModel(), getSkeleton() );
//		goThroughMeshes( getMawashiModel(), getSkeleton() );
		
		glDisable( GL_TEXTURE_2D );
		glDisableClientState( GL_VERTEX_ARRAY );
		glDisableClientState( GL_NORMAL_ARRAY );
		glDisableClientState( GL_TEXTURE_COORD_ARRAY );
		
		glPopMatrix();
	}
	
//	public void goThroughMeshes( MD5Model model ){
//		
//		for ( MD5Mesh mesh : model.getMeshes() ){
//			
//			List<? extends MD5Joint> aJoints = model.getJoints();
//			
//			if ( model.hasAnimation() ){
//				aJoints = model.getAnimation().getAnimatedSkeleton().getSkeletonJoints();
//			}
//			
//			model.prepareMesh( mesh, aJoints );
//			mesh.getIndexArray().flip();
//			mesh.getVertexArray().flip();
//			mesh.getTexelArray().flip();
//			mesh.getNormalArray().flip();
//			
//			glNormalPointer( 3, mesh.getNormalArray() );
//			glVertexPointer( 3, 0, mesh.getVertexArray() );
//			glTexCoordPointer( 2, 0, mesh.getTexelArray() );
//			
//			glDrawElements( GL_TRIANGLES, mesh.getIndexArray() );
//		}
//	}
	
	private AppearenceMap getAppearence(){
		return appearence;
	}

	public MD5Model getModel() {
		
		if ( model == null ){
			String modelStr = getAppearence().getBodyModel();
			
			int texId = TextureManager.getInstance().getTextureId( "default_sumo_tex.png" );
			
			if ( modelStr == null ){
				modelStr = "/base_sumo.md5mesh";
			}
			
			model = ModelManager.getInstance().loadModel( modelStr, texId );
			
			model.getMeshes().add( loadHairModel() );
			model.getMeshes().add( loadHeadModel() );
			model.getMeshes().add( loadMawashiModel() );
			
			model.setNumberOfMeshes( model.getNumberOfMeshes() + 3 );
			
			for ( MD5Mesh mesh : model.getMeshes() ){
				model.prepareMesh( mesh, model.getJoints() );
				model.prepareNormals( mesh );
			}
		}
		
		return model;
	}

	private MD5Mesh loadHairModel() {
		
		String modelStr = getAppearence().getHairModel();
		
		if ( modelStr == null ){
			modelStr = "/base_hair.md5mesh";
		}
		
		MD5Model hairModel = ModelManager.getInstance().loadModel( modelStr );
		ModelManager.getInstance().unloadModel( modelStr );
		
		return hairModel.getMeshes().get( 0 );
	}

	public MD5Mesh loadHeadModel() {
		
		String modelStr = getAppearence().getHeadModel();
		
		if ( modelStr == null ){
			modelStr = "/base_head.md5mesh";
		}
		
		MD5Model headModel = ModelManager.getInstance().loadModel( modelStr );
		ModelManager.getInstance().unloadModel( modelStr );
		
		return headModel.getMeshes().get( 0 );
	}

	public MD5Mesh loadMawashiModel() {
		
		String modelStr = getAppearence().getMawashiModel();
		
		if ( modelStr == null ){
			modelStr = "/base_head.md5mesh";
		}
		
		MD5Model mawashiModel = ModelManager.getInstance().loadModel( modelStr );
		ModelManager.getInstance().unloadModel( modelStr );
	
		return mawashiModel.getMeshes().get( 0 );
	}
	
}
