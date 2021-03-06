package com.nate.sumo.display.widgets;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nate.model.Quaternarion;
import com.nate.sumo.KeyMapper;
import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.fonts.Font;
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
	private boolean done = false;
	
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
		default:
			return -1;
		}
	}
	
	private void calculateHelpers( float xStart, float xFinish, float yStart, float yFinish ){
		// ( pos, y) -> m(0.0, yFinish), M(maxBack, yStart)
		ySlope = (yStart - yFinish) / getMaxBack();
		yInt = yStart - (ySlope * (getMaxBack()));
		
		// (pos, x) -> m(0.0, xFinish), M(maxBack, xStart)
		xSlope = (xStart - xFinish) / getMaxBack();
		xInt = xStart - (xSlope * (getMaxBack()));
	}
	
	public int getScore(){
		
		double total = 0;
		double points = 0;
		
		for ( SequenceMapper sm : getSequenceMapper() ){
			points += sm.getHitScore();
			total += 2;
		}
		
		int score = (int)Math.round( (points / total) * 100.0 );
		return score;
	}
	
	public boolean isDone(){
		return done;
	}
	
	@Override
	public void draw() {
		
		if ( getLastTime() == 0L ){
			setLastTime();
		}
		
		long eTime = getElapsedTime();
		setLastTime();
		
		glPushMatrix();
		
			if ( getSequenceMapper().get( getSequenceMapper().size()-1 ).getPosition() < 5.0f ){
				drawControlStrip( eTime );
			}
			else {
				done = true;
			}
			
			// draw the results
			glPushMatrix();
				glDisable( GL_TEXTURE_2D );
				glTranslatef( getXFinish() - SIZE, getYFinish(), 0.0f );
				
				for ( int i = getSequenceMapper().size()-1; i >= 0; i-- ){

					SequenceMapper cM = getSequenceMapper().get( i );
					
					if ( cM.getHitScore() != SequenceMapper.NONE ){
						if ( cM.getHitScore() == SequenceMapper.HIT ){
							glColor4f( 0.1f, 0.9f, 0.1f, 0.8f );
						}
						else if ( cM.getHitScore() == SequenceMapper.PARTIAL ){
							glColor4f( 1.0f, 1.0f, 0.0f, 0.8f );
						}
						else {
							glColor4f( 0.9f, 0.1f, 0.0f, 0.8f );
						}
						
						ScreenHelper.getInstance().drawCircle( 0.01f, 30, 1.0f, true);
						glTranslatef( 0.0f, 0.03f, 0.0f );
					}
				}
			glPopMatrix();
			
			if ( isDone() ){
				glPushMatrix();
					glEnable( GL_BLEND );
					glEnable( GL_TEXTURE_2D );
					glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
					glColor3f( 1.0f, 1.0f, 1.0f );
					glTranslatef( getXFinish() - 0.15f, getYFinish(), 0.0f );
					glScalef( 1.8f, 1.8f, 1.0f );
					Font.TIMES_NEW_ROMAN.drawString( getScore() + "%" );
				glPopMatrix();
			}
			
			glDisable( GL_TEXTURE_2D );
			glDisable( GL_BLEND );
			glDisable( GL_DEPTH_TEST );
			
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
			boolean inHitZone = isInHitZone( m );
			
			glPushMatrix();
				float y = getY( m.getPosition() );
				float x = getX( m.getPosition() );
				

				if ( newPosition > 0.0f ){
					glTranslatef( getXFinish() - 0.15f, getYFinish(), 0.0f );
				}
				else {
					glTranslatef( x, y, m.getPosition() );
				}
				
				if ( inHitZone ){
					
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
				
				if ( inHitZone || m.getPosition() < 5.0f ){

					glEnable( GL_TEXTURE_2D );
					glBindTexture( GL_TEXTURE_2D, m.getTextureId() );
					glColor3f( 1.0f, 1.0f, 1.0f );
					ScreenHelper.getInstance().drawSquare( SIZE/2.0f, SIZE/2.0f, true );
				}
				
			glPopMatrix();
			
		}	
		
		glDisable( GL_BLEND );
		glDisable( GL_TEXTURE_2D );
		glDisable( GL_DEPTH_TEST );
	}
	
	private boolean isInHitZone( SequenceMapper mapper ){
		
		if ( mapper.getPosition() > 0.0f && mapper.getPosition() < 5.0f ){
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
		buttonEntered( key );
	}

	@Override
	public void handleDirections(float lateral, float vertical, int action) {
		if ( Math.abs( lateral ) > Math.abs( vertical ) ){
			if ( lateral < 0 ){
				buttonEntered( KeyMapper.LEFT_DPAD );
			}
			else {
				buttonEntered( KeyMapper.RIGHT_DPAD );
			}
		}
		else {
			if ( vertical < 0 ){
				buttonEntered( KeyMapper.DOWN_DPAD );
			}
			else {
				buttonEntered( KeyMapper.UP_DPAD );
			}
		}
	}
	
	private void buttonEntered( int key ){
		
		SequenceMapper sm = getCurrentHitPoint();
		
		if ( sm == null ){
			return;
		}
		
		if ( sm.getHitScore() == SequenceMapper.NONE ){
			
			boolean isKeyRight = pressMatch( sm.getKey(), key );
			
			if ( !isKeyRight ){
				sm.setHitScore( SequenceMapper.MISS );
				return;
			}
			
			if ( sm.getPosition() < 2.0 && sm.getPosition() > -1.0 ){
//				System.out.println( "Hit: " + sm.getPosition() );
				sm.setHitScore( SequenceMapper.HIT );
			}
			else if ( sm.getPosition() <= 5.0 ){
//				System.out.println( "Partial: " + sm.getPosition() );
				sm.setHitScore( SequenceMapper.PARTIAL );
			}
			else {
//				System.out.println(  "Missed: " + sm.getPosition() );
				sm.setHitScore( SequenceMapper.MISS );
			}
		}
	}
	
	private boolean pressMatch( SEQUENCE_KEY sKey, int bKey ){
		
		if ( sKey.equals( SEQUENCE_KEY.A ) && bKey == KeyMapper.A_BUTTON ){
			return true;
		}
		
		if ( sKey.equals( SEQUENCE_KEY.B ) && bKey == KeyMapper.B_BUTTON ){
			return true;
		}
		
		if ( sKey.equals( SEQUENCE_KEY.X ) && bKey == KeyMapper.X_BUTTON ){
			return true;
		}
		
		if ( sKey.equals( SEQUENCE_KEY.Y ) && bKey == KeyMapper.Y_BUTTON ){
			return true;
		}
		
		if ( sKey.equals( SEQUENCE_KEY.UP ) && bKey == KeyMapper.UP_DPAD ){
			return true;
		}
		
		if ( sKey.equals( SEQUENCE_KEY.DOWN ) && bKey == KeyMapper.DOWN_DPAD ){
			return true;
		}
		
		if ( sKey.equals( SEQUENCE_KEY.AWAY ) && isTowardLeft() && bKey == KeyMapper.RIGHT_DPAD ){
			return true;
		}
		
		if ( sKey.equals( SEQUENCE_KEY.TOWARD ) && !isTowardLeft() && bKey == KeyMapper.RIGHT_DPAD ){
			return true;
		}
		
		if ( sKey.equals( SEQUENCE_KEY.AWAY ) && !isTowardLeft() && bKey == KeyMapper.LEFT_DPAD ){
			return true;
		}
		
		if ( sKey.equals( SEQUENCE_KEY.TOWARD ) && isTowardLeft() && bKey == KeyMapper.LEFT_DPAD ){
			return true;
		}
		
		return false;
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
