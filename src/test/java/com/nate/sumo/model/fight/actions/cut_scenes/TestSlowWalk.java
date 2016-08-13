package com.nate.sumo.model.fight.actions.cut_scenes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.nate.sumo.model.fight.Pathway;
import com.nate.sumo.model.fight.RikishiStatus;
import com.nate.sumo.model.fight.Route;
import com.nate.util.MathHelper;

public class TestSlowWalk {

	@Test
	public void testTurn(){
		
		Double wantToFace = 250.0;
		Double facing = 0.0;
		
		SlowWalk slowWalk = new SlowWalk( null, null, null );
		float turn = slowWalk.turn( wantToFace, facing, 2.0 );
		
		assertTrue( turn == -2.0f );
		
		wantToFace = 90.0;
		facing = 0.0;
		turn = slowWalk.turn( wantToFace, facing, 2.4 );
		
		assertTrue( "Expected 2.4 but got: " + turn, turn == 2.4f );
		
		turn = slowWalk.turn( 2.0, 280.0, 2.0 );
		
		assertTrue( "Expected 2 but got " + turn, turn == 2.0 );
		
		turn = slowWalk.turn( 200.0, 270.0, 4.1 );

		assertTrue( "Expected -4.1 but got " + turn, MathHelper.equals( turn, -4.1 ) );
		
		turn = slowWalk.turn( 254.18, 270.0, 1.0 );
		
		assertTrue( "Expected -1 but got: " + turn, MathHelper.equals( turn, -1.0 ) );
	}
	
	@Test
	public void testAngleCalc(){
		
		RikishiStatus rStat = new RikishiStatus( null, true, null );
		rStat.getFightCoordinates().setX( 0.0f );
		rStat.getFightCoordinates().setY( 0.0f );
		Pathway pathway = new Pathway( 100.0f, 100.0f, Pathway.DESTINATION, 0.0f );
		Route route = new Route();
		route.addPathway( pathway );
		
		SlowWalk slowWalk = new SlowWalk( rStat, null, route );
		
		double angle = slowWalk.getAngleToDest();
		double rads = Math.PI / 4.0;
		
		assertTrue( "Expecting " + rads + " radians but got: " + angle, angle == rads );
		
		pathway = new Pathway( 100.0f, 100.0f, Pathway.DESTINATION, 0.0f );
		route = new Route();
		route.addPathway( pathway );
		
		slowWalk = new SlowWalk( rStat, null, route );
		
		assertTrue( "Expecting " + rads + " radians but got: " + angle, angle == rads );
	}
	
	@Test
	public void testWannaFace(){
		
		RikishiStatus rStat = new RikishiStatus( null, true, null );
		rStat.getFightCoordinates().setX( 0.0f );
		rStat.getFightCoordinates().setY( 0.0f );
		Pathway pathway = new Pathway( 100.0f, -100.0f, Pathway.DESTINATION, 0.0f );
		Route route = new Route();
		route.addPathway( pathway );
		
		SlowWalk slowWalk = new SlowWalk( rStat, null, route );
		Double facing = slowWalk.getFacingDesire();
		
		assertTrue( "Expected 315 or -45 but got: " + facing, facing == 315 || facing == -45);
		
		rStat = new RikishiStatus( null, true, null );
		rStat.getFightCoordinates().setX( 0.0f );
		rStat.getFightCoordinates().setY( 0.0f );
		pathway = new Pathway( -100.0f, -100.0f, Pathway.DESTINATION, 0.0f );
		route = new Route();
		route.addPathway( pathway );
		
		slowWalk = new SlowWalk( rStat, null, route );
		
		facing = slowWalk.getFacingDesire();
		
		assertTrue( "Expected 225 but got: " + facing, facing == 225 );
	}
	
}
