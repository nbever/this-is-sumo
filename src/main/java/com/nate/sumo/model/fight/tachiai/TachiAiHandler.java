package com.nate.sumo.model.fight.tachiai;

import static org.lwjgl.opengl.GL11.*;

import java.time.Instant;
import java.util.List;

import com.nate.sumo.KeyMapper;
import com.nate.sumo.display.KeyHandler;
import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.display.widgets.GuitarHero;
import com.nate.sumo.display.widgets.SequenceIf.SEQUENCE_KEY;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.RikishiStatus;
import com.nate.sumo.model.fight.RikishiStatus.PLAYER_CONTROL;
import com.nate.sumo.model.fight.actions.tachiai.Oshi;
import com.nate.sumo.model.rikishi.RikishiTemperment;
import com.nate.util.MathHelper;

public class TachiAiHandler implements KeyHandler{

	private RikishiStatus east;
	private RikishiStatus west;
	
	private TachiAiAi ai;
	private TachiAiAction eastAction;
	private TachiAiAction westAction;
	private double eastEffectiveness = -1.0;
	private double westEffectiveness = -1.0;
	private GuitarHero eastGuitarHero;
	private GuitarHero westGuitarHero;
	
	private double eastBreathRate = -1.0;
	private double westBreatRate = -1.0;
	private double runningTime = 0.0;
	
	private FightKnowledgeIf fightKnowledge;
	
	public TachiAiHandler( RikishiStatus east, RikishiStatus west, FightKnowledgeIf fightKnowledge ){
		this.east = east;
		this.west = west;
		this.fightKnowledge = fightKnowledge;
	}
	
	public void draw(){
		
		initializeCpu();
		
		glPushMatrix();
			glEnable( GL_BLEND );
			glEnable( GL_TEXTURE_2D );
			
			if ( !getEast().getControl().equals( PLAYER_CONTROL.CPU ) ){
				if ( getEastAction() == null ){
					drawDisplay( -1.2f, -0.1f );
				}
				else if ( getEastEffectiveness() == -1.0 ){
					getEastGuitarHero().draw();
					
					if ( getEastGuitarHero().isDone() ){
						setEastEffectiveness( (double)getEastGuitarHero().getScore() / 100.0 );
					}
				}
			}
			
			if ( !getWest().getControl().equals( PLAYER_CONTROL.CPU ) ){
				if ( getWestAction() == null ){
					drawDisplay( 0.4f, -0.1f );
				}
				else if ( getWestEffectiveness() == -1.0 ){
					getWestGuitarHero().draw();
					
					if ( getWestGuitarHero().isDone() ){
						setWestEffectiveness( (double)getWestGuitarHero().getScore() / 100.0 );
					}
				}
			}
			
			// draw the clouds
			if ( getEastEffectiveness() != -1.0 && getWestEffectiveness() != -1.0 ){
				drawClouds();
			}
			
			glDisable( GL_TEXTURE_2D );
			glDisable( GL_BLEND );
		glPopMatrix();
	}
	
	private void drawClouds(){
		
		if ( runningTime == 0.0 ){
			runningTime = Instant.now().toEpochMilli();
		}
		
		double elapsed = Instant.now().toEpochMilli() - runningTime;
		
		// initialize the breath rates
		if ( getEastBreathRate() == -1.0 ){
			setEastBreathRate( (Math.random()*8.0 + 2.0) / 1000.0 );
		}
		
		if ( getWestBreathRate() == -1.0 ){
			setWestBreathRate( (Math.random()*8.0 + 2.0) / 1000.0 );
		}
		
		float scaleFactor = (float)( 0.4 * Math.sin( elapsed * getEastBreathRate() ) + 1.0);
		
		glPushMatrix();
			glColor3f( 1.0f, 1.0f, 1.0f );
			glTranslatef( 0.0f, 0.0f, ScreenHelper.SCREEN_DEPTH );
			glScalef( scaleFactor, scaleFactor, 1.0f );
			ScreenHelper.getInstance().drawSquare( 0.2f, 0.2f, false, true, 1.0f );
		glPopMatrix();
	}
	
	private void drawDisplay( float x, float y ){
		glPushMatrix();
			glColor3f( 1.0f, 1.0f, 1.0f );
			glTranslatef( x, y, ScreenHelper.SCREEN_DEPTH );
			glScalef( 1.4f, 1.4f, 0.0f );
			Font.JAPANESE_CALI.drawJapaneseString( "立会いを選びます！" );
		glPopMatrix();
	}
	
	private void initializeCpu(){
		
		if ( getEast().getControl().equals( PLAYER_CONTROL.CPU ) ){
			setEastAction( determineCpuAction( getEast() ) );
			setEastEffectiveness( determineCpuEffectiveness( getEast().getRikishi().getRikishiTemperment(), getEastAction().getSequence() ) );
		}
		
		if ( getWest().getControl().equals( PLAYER_CONTROL.CPU ) ){
			setWestAction( determineCpuAction( getWest() ) );
			setWestEffectiveness( determineCpuEffectiveness( getWest().getRikishi().getRikishiTemperment(), getWestAction().getSequence() ) );
		}
	}
	
	private TachiAiAction determineCpuAction( RikishiStatus rikishi ){
		TachiAiAction action = getAi().chooseTachiAi( getFightKnowledge(), rikishi );
		return action;
	}
	
	private double determineCpuEffectiveness( RikishiTemperment temperment, List<SEQUENCE_KEY> sequence ){
		
		//figure out their chance for success
		Double focus = temperment.getFocus();
		Double anger = temperment.getAnger();
		Double emotion = temperment.getEmotions();
		
		// curve for focus - (1000, 100), (0,15)
		Double a = MathHelper.findAPoly2( 0.0, 15.0, 1000.0, 100.0 );
		Double f = a * Math.pow( focus - 1000.0, 2.0 ) + 100.0;
		
		// now factor in the anger.  High emotion means anger is wild, low means it's focused
		// from a0, ai0 - a1000, ai0.8
		Double angerInfluence = (0.8/1000.0)*anger;
		
		//emotion offset
		// e0, 0.8 - e1000, -0.8
		Double emotionInfluence = ((-0.8 - 0.8)/1000.0)*emotion + 0.8;
		
		Double inf = angerInfluence + emotionInfluence;
		
		inf = (1000.0-f) * inf;
		f += inf;
		Double fQuad = f/4.0;
		
		// now go through each in the sequence
		int rez = 0;
		for ( SEQUENCE_KEY key : sequence ){
			int val = (int)(Math.random()*100.0);
			
			if ( val <= fQuad ){
				val = 1;
			}
			else if ( val <= f ){
				val = 2;
			}
			else {
				val = 0;
			}
			
			rez += val;
		}
		
		int total = sequence.size()*2;
		Double fInal = rez / (double)total;
		
		return fInal;
	}
	
	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {
		
		if ( getEastAction() == null || getWestAction() == null ){
			handleTachiAiChoice( key );
			return;
		}
		
		if ( getEastAction() != null && getWestAction() != null && 
			(getEastEffectiveness() == -1.0 || getWestEffectiveness() == -1.0 ) ){
			
			if ( eastGuitarHero != null ){
				getEastGuitarHero().handleKey( key, scanCode, action, mods );
			}
			
			if ( westGuitarHero != null ){
				getWestGuitarHero().handleKey( key, scanCode, action, mods );
			}
			
			return;
		}
	}

	@Override
	public void handleDirections(float lateral, float vertical, int action) {
		
		if ( getEastAction() != null && getWestAction() != null && 
				(getEastEffectiveness() == -1.0 || getWestEffectiveness() == -1.0 ) ){

			if ( eastGuitarHero != null ){
				getEastGuitarHero().handleDirections( lateral, vertical, action );
			}
			
			if ( westGuitarHero != null ){
				getWestGuitarHero().handleDirections( lateral, vertical, action );
			}
			
			return;
		}
	}
	
	private void handleTachiAiChoice( int key ){
		
		RikishiStatus p1Rikishi = null;
		RikishiStatus p2Rikishi = null;
		
		if ( getEast().getControl().equals( PLAYER_CONTROL.PLAYER_1 ) ){
			p1Rikishi = getEast();
		}
		else if ( getWest().getControl().equals( PLAYER_CONTROL.PLAYER_1 ) ){
			p1Rikishi = getWest();
		}
		
		if ( getEast().getControl().equals( PLAYER_CONTROL.PLAYER_2 ) ){
			p2Rikishi = getEast();
		}
		else if ( getWest().getControl().equals( PLAYER_CONTROL.PLAYER_2 ) ){
			p2Rikishi = getWest();
		}
		
		if ( key == KeyMapper.A_BUTTON && p1Rikishi != null ){
			setAction( new Oshi( p1Rikishi,  getFightKnowledge() ), p1Rikishi );
		}
	}
	
	private TachiAiAi getAi(){
		if ( ai == null ){
			ai = new TachiAiAi();
		}
	
		return ai;
	}
	
	private FightKnowledgeIf getFightKnowledge(){
		return fightKnowledge;
	}
	
	private RikishiStatus getEast(){
		return east;
	}
	
	private RikishiStatus getWest(){
		return west;
	}
	
	private void setAction( TachiAiAction action, RikishiStatus rikishi ){
		if ( rikishi.equals( getWest() ) ){
			setWestAction( action );
		}
		else if ( rikishi.equals( getEast() ) ){
			setEastAction( action );
		}
	}
	
	private void setEastAction( TachiAiAction action ){
		eastAction = action;
	}
	
	private TachiAiAction getEastAction(){
		return eastAction;
	}
	
	private void setWestAction( TachiAiAction action ){
		westAction = action;
	}
	
	private TachiAiAction getWestAction(){
		return westAction;
	}
	
	private void setEastEffectiveness( double eff ){
		eastEffectiveness = eff;
	}
	
	private double getEastEffectiveness(){
		return eastEffectiveness;
	}
	
	private void setWestEffectiveness( double eff ){
		westEffectiveness = eff;
	}
	
	private double getWestEffectiveness(){
		return westEffectiveness;
	}
	
	private double getEastBreathRate(){
		return eastBreathRate;
	}
	
	private void setEastBreathRate( double rate ){
		eastBreathRate = rate;
	}
	
	private double getWestBreathRate(){
		return westBreatRate;
	}
	
	private void setWestBreathRate( double rate ){
		westBreatRate = rate;
	}
	
	private GuitarHero getEastGuitarHero(){
		if ( eastGuitarHero == null ){
			eastGuitarHero = new GuitarHero( getEastAction(), false, 20.0f, -0.2f, -0.25f, 5.0f, 0.0f );
		}
		
		return eastGuitarHero;
	}
	
	private GuitarHero getWestGuitarHero(){
		if ( westGuitarHero == null ){
			westGuitarHero = new GuitarHero( getEastAction(), false, 20.0f, 1.8f, 0.45f, 5.0f, 0.0f );
		}
		
		return westGuitarHero;
	}
}
