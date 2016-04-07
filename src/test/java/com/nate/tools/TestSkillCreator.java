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
import com.nate.sumo.model.rikishi.RikishiTemperment;

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
		
		assertTrue( "Expected peak to be 700 but was " + yPeak, yPeak == 700 );
		
		Rank ozekiRank = new Rank( RankClass.OZEKI, RankSide.WEST, 2 );
		Integer oPeak = sc.computePeakValue( ozekiRank );
		assertTrue( "Expected peak to be 493 but was " + oPeak, oPeak == 493 );
		
		Rank maegashiraRank = new Rank( RankClass.MAEGASHIRA, RankSide.WEST, 11 );
		Integer mPeak = sc.computePeakValue( maegashiraRank );
		assertTrue( "Expected peak to be 381 but was " + mPeak, mPeak == 381 );
		
		Rank jonidanRank = new Rank( RankClass.JONIDAN, RankSide.EAST, 31 );
		Integer jPeak = sc.computePeakValue( jonidanRank );
		assertTrue( "Expected peak to be 275 but was " + jPeak, jPeak == 275 );
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
	public void testTheDiffPointCalculator(){
		//should go from 0,0 to 30,20
		
		Double none = sc.getOpponentDiffValue( 0 );
		assertTrue( "(0,0) but got (0," + none + ")", none == 0.0 );
		Double max = sc.getOpponentDiffValue( 30 );
		assertTrue( "(30,20) but got (30," + max + ")", max < 20.01 && max > 19.6 );
	}
	
	@Test
	public void testMatchPointTally(){
		
		MatchResult match = new MatchResult( 9L, Boolean.TRUE );
		Double pointsToEarn = sc.getMatchPointTally( match, 0, 0, 1, 2 );
		
		assertTrue( "Expected 1.088 but got " + pointsToEarn, pointsToEarn == 1.088 );
		
		pointsToEarn = sc.getMatchPointTally( match, 0, 0, 1, 30 );
		assertTrue( "Expoected 20 but got " + pointsToEarn, pointsToEarn < 21.0 && pointsToEarn >20.6 );
		
		pointsToEarn = sc.getMatchPointTally( match, 14, 0, 15, 5 );
		assertTrue( "Expoected 20 but got " + pointsToEarn, pointsToEarn < 19.0 && pointsToEarn > 18.8 );
		
		pointsToEarn = sc.getMatchPointTally( match, 14, 0, 10, 5 );
		assertTrue( "Expoected 20 but got " + pointsToEarn, pointsToEarn == 8.55 );
		
		match.setWin( false );
		pointsToEarn = sc.getMatchPointTally( match, 0, 14, 15, 5 );
		assertTrue( "Expoected 20 but got " + pointsToEarn, pointsToEarn < 19.0 && pointsToEarn > 18.8 );
		
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
		
		sc.modifyStatsByKimarite( match, stats, new RikishiTemperment(), 5.0 );
		
		System.out.println( stats.toString() );
	}

	@Test
	public void testStartingStats(){
		
		RikishiInfo info = new RikishiInfo();
		
		Calendar c = Calendar.getInstance();
		c.add( Calendar.YEAR, -23 );
		c.set( Calendar.MONTH, 0 );
		c.set( Calendar.DAY_OF_MONTH, 1 );
		
		info.setBirthday( c.getTime() );
		info.setCurrentRank( new Rank( RankClass.MAKUSHITA, RankSide.EAST, 52 ) );
		
		RikishiStats stats = sc.getStartingStats( info, 2016, 3 );
		
		assertTrue( "Expected overall to be 238.5 but was " + stats.getOverallSkill(), stats.getOverallSkill() == 238.5 );
		assertTrue( stats.getPotential() >= 722 && stats.getPotential() < 723 );
		
		info.setCurrentRank( new Rank( RankClass.OZEKI, RankSide.WEST, 2 ) );
		
		stats = sc.getStartingStats( info, 2016, 3 );
		
		assertTrue( "Expected overall to be 455.5 but was " + stats.getOverallSkill(), stats.getOverallSkill() == 455.5 );
		assertTrue( stats.getPotential() >= 722 && stats.getPotential() < 723 );
		
	}
}
