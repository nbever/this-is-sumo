package com.nate.sumo.model.animation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.nate.model.MD5Animation;
import com.nate.model.MD5Joint;
import com.nate.model.MD5Mesh;
import com.nate.model.MD5Model;
import com.nate.sumo.display.Drawable;
import com.nate.sumo.display.ModelManager;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.model.appearence.AppearenceMap;
import com.nate.sumo.model.fight.FightCoordinates;

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
	
	// weird thing is... ( +back -forward, -right +left,+up and -down)
	/**
	 * GL coords
	 *    (1,1,0)    (1,0,0)   (1,-1,0)
	 *        ---------------------
	 *        |         |         |
	 *        |  +,+    |   +,-   |
	 *        |         |         |
	 *(0,1,0) |---------|-------- | (0,-1,0)
	 *        |         |         |
	 *        |  -,+    |  -,-    |
	 *        |         |         |
	 *        ---------------------
	 *    (-1,1,0)   (-1,0,0)   (-1,-1,0)
	 * 
	 * We'll draw it funky here but the coordinates
	 * will be in "normal" cartesian system
	 * 
	 * FightCoords
	 *
	 *     (-1,1)     (0,1)     (1,1)
	 *        ---------------------
	 *        |         |         |
	 *        |  +,+    |   +,-   |
	 *        |         |         |
	 * (-1,0) |---------|-------- | (1,0)
	 *        |         |         |
	 *        |  -,+    |  -,-    |
	 *        |         |         |
	 *        ---------------------
	 *     (-1,-1)    (0,-1)     (1,-1)
	 * 
	 * @param coords
	 */
	public void draw( FightCoordinates coords ){
		glPushMatrix();
				
		//		glRotatef( -90.0f, 1.0f, 0.0f, 0.0f );
		//		
		//		glTranslatef( 2.4f, 2.0f, 0.4f );
		//		glScalef( 0.5f, 0.5f, 0.5f ); 
	
			if ( coords != null ){
				glTranslatef( 1.0f, 0.0f, 0.0f );
			}
				
			glEnableClientState( GL_VERTEX_ARRAY );
			glEnableClientState( GL_NORMAL_ARRAY );
			glEnableClientState( GL_TEXTURE_COORD_ARRAY );
			
			getModel().render();
	
			glDisable( GL_TEXTURE_2D );
			glDisableClientState( GL_VERTEX_ARRAY );
			glDisableClientState( GL_NORMAL_ARRAY );
			glDisableClientState( GL_TEXTURE_COORD_ARRAY );
			
		glPopMatrix();
	}
	
	public void draw(){
		
		this.draw( null );
	}
	
	private AppearenceMap getAppearence(){
		return appearence;
	}

	public MD5Model getModel() {
		
		if ( model == null ){
			String modelStr = getAppearence().getBodyModel();
			
			int texId = -1;
			try {
				texId = TextureManager.getInstance().loadTexture( getAppearence().getBodyTxt() );
			} catch ( Exception e) {
				System.out.println( "Body texture: " + getAppearence().getBodyTxt() + " could not be loaded." );
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
		
		MD5Model hairModel = ModelManager.getInstance().loadModel( modelStr );
		
		MD5Mesh mesh = hairModel.getMeshes().get( 0 );
		
		try {
			mesh.setColor( getAppearence().getHairColor() );
			mesh.setTextureId( TextureManager.getInstance().loadTexture( getAppearence().getHairTxt() ) );
		} catch (Exception e) {
			System.out.println( "Hair texture " + getAppearence().getHairTxt() + " could not be loaded." );
		}
		
		return hairModel.getMeshes().get( 0 );
	}

	public MD5Mesh loadHeadModel() {
		
		String modelStr = getAppearence().getHeadModel();
		
		MD5Model headModel = ModelManager.getInstance().loadModel( modelStr );
		MD5Mesh mesh = headModel.getMeshes().get( 0 );
		
		try {
			mesh.setTextureId( TextureManager.getInstance().loadTexture( getAppearence().getHeadTxt() ) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return headModel.getMeshes().get( 0 );
	}

	public MD5Mesh loadMawashiModel() {
		
		String modelStr = getAppearence().getMawashiModel();
		
		MD5Model mawashiModel = ModelManager.getInstance().loadModel( modelStr );
		MD5Mesh mesh = mawashiModel.getMeshes().get( 0 );
		
		try {
			mesh.setColor( getAppearence().getMawashiColor() );
			mesh.setTextureId( TextureManager.getInstance().loadTexture( getAppearence().getMawashiTxt() ) );
		} catch (Exception e) {
			System.out.println( "Mawashi texture " + getAppearence().getMawashiTxt() + " could not be loaded." );
		}
		
		return mesh;
	}
	
}
