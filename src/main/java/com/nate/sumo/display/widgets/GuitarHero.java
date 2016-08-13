package com.nate.sumo.display.widgets;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nate.model.Quaternarion;
import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.ScreenManager;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.widgets.SequenceIf.SEQUENCE_KEY;
import static org.lwjgl.opengl.GL11.*;

public class GuitarHero extends Widget {

	private SequenceIf sequencible;
	private float speed = 1.0f;
	private boolean towardIsLeft = false;
	private long lastTime = 0L;

	private float xFinish;
	private float yFinish;
	private float ySlope;
	private float xSlope;
	private float yInt;
	private float xInt;
	private float maxBack = 0.0f;
	private SequenceMapper currentHitPoint;
	
	private static final float SIZE = 0.2f;
	
	private List<SequenceMapper> mappers;
	
	private class SequenceMapper{
		
		public static final int MISS = 0;
		public static final int PARTIAL = 1;
		public static final int HIT = 2;
		public static final int NONE = -1;
		
		private SEQUENCE_KEY key;
		private float position = -100.0f;
		private int hitScore = NONE; 
		private int textureId = -1;
		
		public SequenceMapper( SEQUENCE_KEY key ){
			this.key = key;
		}
		
		public SequenceMapper( SEQUENCE_KEY key, float position ){
			this.key = key;
			this.position = position;
		}

		public SEQUENCE_KEY getKey() {
			return key;
		}
		
		public void setKey( SEQUENCE_KEY newKey ){
			if ( !getKey().equals( SEQUENCE_KEY.RANDOM ) ){
				return;
			}
			
			key = newKey;
		}

		public float getPosition() {
			return position;
		}

		public void setPosition(float position) {
			this.position = position;
		}

		public int getHitScore() {
			return hitScore;
		}

		public void setHitScore(int hitScore) {
			this.hitScore = hitScore;
		}
		
		public int getTextureId(){
			return textureId;
		}
		
		public void setTextureId( int textureId ){
			this.textureId = textureId;
		}
	};
	
	public GuitarHero( SequenceIf sequencible, boolean towardIsLeft, float speed, float xStart, float xFinish, float yStart, float yFinish ) {
		super();
		
		this.sequencible = sequencible;
		this.speed = speed;
		this.towardIsLeft = towardIsLeft;
		this.xFinish = xFinish;
		this.yFinish = yFinish;
		
		for ( int i = 0; i < getSequencible().getSequence().size(); i++ ){
			
			SequenceMapper m = new SequenceMapper( getSequencible().getSequence().get( i ), (i * -20.0f) - 30.0f );
			
			if ( m.getPosition() < maxBack ){
				maxBack = m.getPosition();
			}
			
			
			while ( m.getKey().equals( SEQUENCE_KEY.RANDOM ) ){
				m.setKey( SEQUENCE_KEY.values()[ (int)(Math.random()*SEQUENCE_KEY.values().length)] );
			}
			
			m.setTextureId( getTextureId( m.getKey() ) );
			
			getSequenceMapper().add( m );
		}
		
		calculateHelpers( xStart, xFinish, yStart, yFinish );
	}
	
	private int getTextureId( SEQUENCE_KEY key ){
		
		switch( key ){
		case AWAY:
			if ( isTowardLeft() ){
				return TextureManager.getInstance().getTextureId( TextureNames.CTL_RIGHT );
			}
			return TextureManager.getInstance().getTextureId( TextureNames.CTL_LEFT );
		case TOWARD:
			if ( isTowardLeft() ){
				return TextureManager.getInstance().getTextureId( TextureNames.CTL_LEFT );
			}
			
			return TextureManager.getInstance().getTextureId( TextureNames.CTL_RIGHT );
		case A:
			return TextureManager.getInstance().getTextureId( TextureNames.CTL_A );
		case B:
			return TextureManager.getInstance().getTextureId( TextureNames.CTL_B );
		case X:
			return TextureManager.getInstance().getTextureId( TextureNames.CTL_X );
		case Y:
			return TextureManager.getInstance().getTextureId( TextureNames.CTL_Y );
		case UP:
			return TextureManager.getInstance().getTextureId( TextureNames.CTL_UP );
		case DOWN:
			return TextureManager.getInstance().getTextureId( TextureNames.CTL_DOWN );
		}
		
		return -1;
	}
	
	private void calculateHelpers( float xStart, float xFinish, float yStart, float yFinish ){
		// ( pos, y) -> m(-5.0, yFinish), M(maxBack, yStart)
		ySlope = (yStart - yFinish) / (getMaxBack() - (-5.0f) );
		yInt = yStart - (ySlope * (getMaxBack()));
		
		// (pos, x) -> m(-5.0, xFinish), M(maxBack, xStart)
		xSlope = (xStart - xFinish) / (getMaxBack() - (-5.0f) );
		xInt = xStart - (xSlope * (getMaxBack()));
	}
	
	@Override
	public void draw() {
		
		if ( getLastTime() == 0L ){
			setLastTime();
		}
		
		long eTime = getElapsedTime();
		setLastTime();
		
		glPushMatrix();
		
			if ( getSequenceMapper().get( getSequenceMapper().size()-1 ).getPosition() < 0.0f ){
				drawControlStrip( eTime );
			}
			
			// draw the results
			glPushMatrix();
				glDisable( GL_TEXTURE_2D );
				glTranslatef( getXFinish() - SIZE, 0.13f, -5.0f );
				
				for ( int i = getSequenceMapper().size()-1; i >= 0; i-- ){

					SequenceMapper cM = getSequenceMapper().get( i );
					
					if ( cM.getHitScore() != SequenceMapper.NONE ){
						if ( cM.getHitScore() == SequenceMapper.HIT ){
							glColor4f( 0.1f, 0.9f, 0.1f, 0.8f );
						}
						else if ( cM.getHitScore() == SequenceMapper.PARTIAL ){
							glColor4f( 1.0f, 0.5f, 0.5f, 0.8f );
						}
						else {
							glColor4f( 0.9f, 0.1f, 0.0f, 0.8f );
						}
						
						ScreenHelper.getInstance().drawCircle( 0.01f, 30, 1.0f, true);
						glTranslatef( 0.0f, 0.03f, 0.0f );
					}
				}
			glPopMatrix();
			
			glDisable( GL_TEXTURE_2D );
			glDisable( GL_BLEND );
			
		glPopMatrix();
	}
	
	private void drawControlStrip( long eTime ){
		glEnable( GL_BLEND );
		glEnable( GL_TEXTURE_2D );
		glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
		// advance the list
		for ( SequenceMapper m : getSequenceMapper() ){
			
			float newPosition = ((float)eTime / 1000.0f ) * getSpeed() + m.getPosition();
			m.setPosition( newPosition );
			
			glPushMatrix();
				float y = getY( m.getPosition() );
				float x = getX( m.getPosition() );
				

				if ( newPosition > -5.0f ){
					glTranslatef( getXFinish() - 0.15f, getYFinish() - 0.05f, -5.0f );
				}
				else {
					glTranslatef( x, y, m.getPosition() );
				}
				
				if ( isInHitZone( m ) ){
					
					setCurrentHitPoint( m );
					
					glDisable( GL_TEXTURE_2D );
					glColor3f( 0.0f, 1.0f, 1.0f );
					
					glPushMatrix();
						glTranslatef( -0.1f, -0.15f, 0.0f );
						ScreenHelper.getInstance().drawCircleBurst( SIZE*1.5f, SIZE*2.0f, new Quaternarion( 0.0f, 1.0f, 1.0f, 1.0f ) );
					glPopMatrix();
				}
				else {
					clearHitPoint( m );
				}
				
				glEnable( GL_TEXTURE_2D );
				glBindTexture( GL_TEXTURE_2D, m.getTextureId() );
				glColor3f( 1.0f, 1.0f, 1.0f );
				ScreenHelper.getInstance().drawSquare( SIZE/2.0f, SIZE/2.0f, true );
				
			glPopMatrix();
			
		}		
	}
	
	private boolean isInHitZone( SequenceMapper mapper ){
		
		if ( mapper.getPosition() > -5.0f && mapper.getPosition() < 0.0f ){
			return true;
		}
		
		return false;
	}
	
	private SequenceMapper getCurrentHitPoint(){
		return currentHitPoint;
	}
	
	private void setCurrentHitPoint( SequenceMapper point ){
		if ( getCurrentHitPoint() != null && getCurrentHitPoint().equals( point ) ){
			return;
		}

		currentHitPoint = point;
	}
	
	private void clearHitPoint( SequenceMapper point ){
		
		if ( getCurrentHitPoint() == null || !getCurrentHitPoint().equals( point ) ){
			return;
		}
		
		if ( getCurrentHitPoint().getHitScore() == SequenceMapper.NONE ){
			getCurrentHitPoint().setHitScore( SequenceMapper.MISS );
		}
		
		currentHitPoint = null;
	}

	private float getYSlope(){
		return ySlope;
	}
	
	private float getXSlope(){
		return xSlope;
	}
	
	private float getYInt(){
		return yInt;
	}
	
	private float getXInt(){
		return xInt;
	}
	
	private float getXFinish(){
		return xFinish;
	}
	
	private float getYFinish(){
		return yFinish;
	}
	
	private float getY( float position ){
		float y = getYSlope() * position + getYInt(); 
		return y;
	}
	
	private float getX( float position ){
		float x = getXSlope() * position + getXInt();
		
		//(This centers it)
		x -= (SIZE/2.0f);
		return x;
	}
	
	protected long getElapsedTime(){
		return Instant.now().toEpochMilli() - getLastTime();
	}
	
	private long getLastTime(){
		return lastTime;
	}
	
	private void setLastTime(){
		lastTime = Instant.now().toEpochMilli();
	}

	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleDirections(float lateral, float vertical, int action) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getTextureNames() {
		// TODO Auto-generated method stub
		return Arrays.asList( TextureNames.CTL_LEFT, TextureNames.CTL_RIGHT, TextureNames.CTL_DOWN, TextureNames.CTL_UP );
	}
	
	private boolean isTowardLeft(){
		return towardIsLeft;
	}
	
	private SequenceIf getSequencible(){
		return sequencible;
	}
	
	private float getSpeed(){
		return speed;
	}
	
	private float getMaxBack(){
		return maxBack;
	}
	
	private List<SequenceMapper> getSequenceMapper(){
		if ( mappers == null ){
			mappers = new ArrayList<SequenceMapper>();
		}
		
		return mappers;
	}

}
