package com.nate.sumo.model.fight;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.nate.sumo.model.MockFight;
import com.nate.sumo.model.MockRikishiStatus;
import com.nate.sumo.model.fight.Fight.PHASE;
import com.nate.sumo.model.fight.FightAction.STATUS;
import com.nate.sumo.model.rikishi.Rikishi;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;

public class TestFightAction{

	
	@Test
	public void testAdvance(){
		
		MockFightAction mockAction = new MockFightAction( new MockRikishiStatus( null, false, null ), new MockFight() );
		mockAction.setCurrentStatus( STATUS.ATTEMPT );
		FightAction spyAction = spy( mockAction );
		doReturn( true ).when( spyAction ).doSuccessTest();
		doReturn( 3L ).when( spyAction ).getElapsedTime();
		doReturn( 2L ).when( spyAction ).getTryTime();
		doReturn( 4L ).when( spyAction ).getActionTime();
		doReturn( 0L ).when( spyAction ).getRecoveryTime();
		
		spyAction.advance();
		
		assertTrue( spyAction.getCurrentStatus().equals(  STATUS.EXECUTE ) );
		
		doReturn( 5L ).when( spyAction ).getElapsedTime();
		spyAction.advance();
		
		assertTrue( spyAction.getCurrentStatus().equals(  STATUS.EXECUTE ) );
		
		doReturn( 7L ).when( spyAction ).getElapsedTime();
		spyAction.advance();
		
		assertTrue( spyAction.getCurrentStatus().equals(  STATUS.RECOVER ) );
		
		spyAction.advance();
		
		assertTrue( spyAction.getCurrentStatus().equals(  STATUS.DONE ) );
	}

}
