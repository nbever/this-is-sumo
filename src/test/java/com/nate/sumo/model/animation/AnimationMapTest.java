package com.nate.sumo.model.animation;

import static org.junit.Assert.*;

import org.junit.Test;

public class AnimationMapTest {

	@Test
	public void test_get_animations() {
		AnimationMap aMap = new AnimationMap();
		
		String result = aMap.getAttackAnimation( "OSHI_ATTACK" );
		
		assertTrue( result == "ME" );
	}

}
