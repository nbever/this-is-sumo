package com.nate.tools;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.nate.sumo.model.basho.Kimarite;
import com.nate.sumo.model.basho.Rank;
import com.nate.sumo.model.basho.Rank.RankClass;
import com.nate.sumo.model.basho.Rank.RankSide;
import com.nate.sumo.model.rikishi.RikishiInfo;
import com.nate.sumo.model.rikishi.RikishiStats;

public class TestSkillCreator
{

	private static SkillCreator sc;
	
	@BeforeClass
	public static void setup() throws IOException{
		sc = new SkillCreator();
	}
	
	@Test
	public void testComputePeakValue() throws IOException
	{
		RikishiInfo info = new RikishiInfo();
		
		Instant now = Instant.now();
		now.minus( 28*366, ChronoUnit.DAYS );
		
		info.setBirthday( new Date( now.toEpochMilli() ) );
		Rank rank = new Rank( RankClass.YOKOZUNA, RankSide.EAST, 1 );
		info.setCurrentRank( rank );
		
		Integer yPeak = sc.computePeakValue( rank );
		
		assertTrue( yPeak == 850 );
		
		Rank maegashiraRank = new Rank( RankClass.MAEGASHIRA, RankSide.WEST, 11 );
		Integer mPeak = sc.computePeakValue( maegashiraRank );
		assertTrue( mPeak == 450 );
		
		Rank jonidanRank = new Rank( RankClass.JONIDAN, RankSide.EAST, 31 );
		Integer jPeak = sc.computePeakValue( jonidanRank );
		assertTrue( jPeak == 225 );
	}
	
	@Test
	public void testRankDifference() throws IOException{
		
		Rank myRank = new Rank( RankClass.YOKOZUNA, RankSide.WEST, 2 );
		Rank theirRank = new Rank( RankClass.MAEGASHIRA, RankSide.EAST, 6 );
		
		int diff = sc.determineRelativeRank( myRank, theirRank );
		
		assertTrue( diff == -19 );
		
		myRank = new Rank( RankClass.JONOKUCHI, RankSide.EAST, 30 );
		theirRank = new Rank( RankClass.JONIDAN, RankSide.WEST, 110 );
		
		diff = sc.determineRelativeRank( myRank, theirRank );
		assertTrue( diff == 60 );
		
		myRank = new Rank( RankClass.MAEGASHIRA, RankSide.EAST, 4 );
		theirRank = new Rank( RankClass.MAEGASHIRA, RankSide.WEST, 6 );
		
		diff = sc.determineRelativeRank( myRank, theirRank );
		assertTrue( diff == -4 );
	}
	
	@Test
	public void testConsecutiveBonus() {
		
		Double cVal = sc.getConsecutiveValue( 14 );
		assertTrue( "c = 14, cVal = " + cVal, cVal == 7 );
		
		cVal = sc.getConsecutiveValue( 1 );
		assertTrue( "c = 1, cVal = " + cVal, cVal == 0.5 );
	}
	
	@Test
	public void testEndOfBashoBonus() {
		
		Double perc = sc.endOfBashoBonusPercentage( 11 );
		assertTrue( "day = 11, perc = " + perc, perc == 0.05 );
		
		perc = sc.endOfBashoBonusPercentage( 15 );
		assertTrue( "day = 15, perc = " + perc, perc == 0.2 );
	}
	
	@Test
	public void testGetAge(){
		
		Calendar c = Calendar.getInstance();
		c.set( 1982, Calendar.JANUARY, 28 );
		
		// how old was I in october this year
		Integer age = sc.getAge( c.getTime(), 2015, Calendar.OCTOBER );
		
		assertTrue( age == 33 );
	}
	
	@Test
	public void testStatsEditForMatch(){
		
		RikishiStats stats = new RikishiStats();
		
		MatchResult match = new MatchResult();
		match.setKimarite( Kimarite.OSHIDASHI );
		match.setWin( Boolean.TRUE );
		match.setOpponenId( 1L );
		match.setOpponentRank( new Rank( RankClass.YOKOZUNA ) );
		
		sc.modifyStatsByKimarite( match, stats, 5.0 );
		
		System.out.println( stats.toString() );
	}

}
