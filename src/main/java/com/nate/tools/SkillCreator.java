package com.nate.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.nate.sumo.model.basho.Kimarite;
import com.nate.sumo.model.basho.Kimarite.Type;
import com.nate.sumo.model.basho.Rank;
import com.nate.sumo.model.basho.Rank.RankClass;
import com.nate.sumo.model.basho.Rank.RankSide;
import com.nate.sumo.model.common.Height;
import com.nate.sumo.model.common.Location;
import com.nate.sumo.model.common.Name;
import com.nate.sumo.model.common.Record;
import com.nate.sumo.model.common.Weight;
import com.nate.sumo.model.rikishi.Heya;
import com.nate.sumo.model.rikishi.RikishiInfo;
import com.nate.sumo.model.rikishi.RikishiStats;

public class SkillCreator
{

	Map<RankClass, Integer[]> spread = new HashMap<RankClass, Integer[]>();
	
	public static void main( String[] args )
	{
		System.out.println( "Year: ");
		int year = Integer.parseInt( System.console().readLine() );
		System.out.println( "Month: " );
		int month = Integer.parseInt( System.console().readLine() );
		
		File crunchFile = new File( year + "-" + month + ".crunch" );
		
		if ( !crunchFile.exists() ){
			System.out.println( "Crunch file for this basho does not exist.");
			System.exit(1);
		}
		
		try
		{
			SkillCreator sc = new SkillCreator();
			sc.createSkills( crunchFile, year, month );
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SkillCreator() throws IOException{
		
		spread.put( RankClass.YOKOZUNA, new Integer[]{700, 300} );
		spread.put( RankClass.OZEKI, new Integer[]{600, 300} );
		spread.put( RankClass.SEKIWAKE, new Integer[]{550, 200} );
		spread.put( RankClass.KOMUSUBI, new Integer[]{500, 200} );
		spread.put( RankClass.MAEGASHIRA, new Integer[]{400, 250} );
		spread.put( RankClass.JURYO, new Integer[]{350, 150} );
		spread.put( RankClass.MAKUSHITA, new Integer[]{250, 150} );
		spread.put( RankClass.SANDANME, new Integer[]{200, 150} );
		spread.put( RankClass.JONIDAN, new Integer[]{150, 150} );
		spread.put( RankClass.JONOKUCHI, new Integer[]{50, 200} );
		spread.put( RankClass.MAE_ZUMO, new Integer[]{0, 400} );
	}
	
	public void createSkills(  File crunchFile, Integer year, Integer month ) throws FileNotFoundException, IOException{
		
		try ( BufferedReader buffRead = new BufferedReader( new FileReader( crunchFile ) ) ){
			
			String line = null;
			
			while ( (line = buffRead.readLine()) != null ){
				
				// break it into pieces
				String[] firstBreak = line.split( "-_-" );
				String info = firstBreak[0];
				
				String[] bashos = firstBreak[1].split("\\*\\*\\*");
				
				RikishiInfo rinf = buildRikishiInfo( info );
				
				List<List<MatchResult>> bashoResults = convertToList( bashos );
				
				RikishiStats stats = getStartingStats( rinf, year, month );
				
				matchAnalysis( rinf, stats, bashoResults );
			}
		}
	}
	
	protected void matchAnalysis( RikishiInfo rinf, RikishiStats stats, List<List<MatchResult>> bashoResults ){
		
		// going oldest basho to latest
		Collections.reverse( bashoResults );
		Iterator<List<MatchResult>> bashoIt = bashoResults.iterator();
		
		while( bashoIt.hasNext() ){
			List<MatchResult> matches = bashoIt.next();
			
			int consecutiveWins = 0;
			int consecutiveLoses = 0;
			int day = 1;
			
			for ( MatchResult match : matches ){
				
				int opponentRankPosition = determineRelativeRank( rinf.getCurrentRank(), match.getOpponentRank() );
				Double pointTally = 1.0;
				
				// take into account the opponent difference as given by the points (1,0) and (15,10) as a quadratic
				// where x is the difference and y is the bonus
				Double q = getOpponentDiffValue( opponentRankPosition );
				pointTally += q;
				
				// take into account the consecutive nature.
				int c = consecutiveLoses;
				if ( match.getWin() ){
					c = consecutiveWins;
				}
				
				// for consecutives we add 0.5 per consecutive win
				Double cQ = getConsecutiveValue( c );
				pointTally += cQ;
				
				// bonus for the last five days going from 5% to 20%
				if ( day >= 11 ){
					Double perc = endOfBashoBonusPercentage( day );
					Double bVal = pointTally * (1.0 + perc );
					pointTally += bVal;
				}
				
				if ( !match.getWin() ){
					pointTally *= -1.0;
					consecutiveLoses++;
					consecutiveWins = 0;
					
					// how much we lose on defense is based on rikishi difference
					Double percToLose = ((-0.9/22.0) * opponentRankPosition  + (2.0/11.0));
					
					if ( percToLose < 0 ){
						percToLose = 0.0;
					}
					
					if ( percToLose > 1 ){
						percToLose = 1.0;
					}
					
					stats.setDefense( stats.getDefense() + (percToLose * pointTally) );
				}
				else {
					consecutiveWins++;
					consecutiveLoses = 0;
				}
				
				modifyStatsByKimarite( match, stats, pointTally );
				
				day++;
			}
		}// while
	}
	
	/**
	 * For a given match, change the stats accordingly
	 * 
	 * @param match
	 * @param stats
	 */
	protected void modifyStatsByKimarite( MatchResult match, RikishiStats stats, Double pointTally ){
		
		// now we need to figure out the skills that are affected
		if ( match.getKimarite().getType().equals( Type.OSHI ) ){
			
			stats.setOshi( stats.getOshi() + pointTally );
			stats.setTsuki( stats.getTsuki() + ( 0.5 *pointTally ) );
			stats.setUpperBody( stats.getUpperBody() + (0.8 * pointTally ) );
			stats.setLowerBody( stats.getLowerBody() + ( 0.2 * pointTally ) );
			
			stats.setRightArm( stats.getRightArm() + ( 0.8 * pointTally ) );
			stats.setLeftArm( stats.getLeftArm() + ( 0.8 * pointTally ) );
			
			stats.setOverallSkill( stats.getOverallSkill() + ( 0.2 * pointTally ) );
		}
		else if ( match.kimarite.getType().equals( Type.YOTSU ) ){
			
			stats.setYotsu( stats.getYotsu() + pointTally );
			stats.setUpperBody( stats.getUpperBody() + (0.3 * pointTally) );
			stats.setLowerBody( stats.getLowerBody() + (0.8 * pointTally ) );
			stats.setGripAbility( stats.getGripAbility() + (0.8 * pointTally ) );
			
			stats.setLeftLeg( stats.getLeftLeg() + ( 0.8 * pointTally ) );
			stats.setRightLeg( stats.getRightLeg() + ( 0.8 * pointTally ) );
			
			stats.setOverallSkill( stats.getOverallSkill() + ( 0.3 * pointTally ) );
		}
		else if ( match.getKimarite().getType().equals( Type.GAKE ) ){
			
			stats.setGake( stats.getGake() + pointTally );
			
			stats.setBalanceControl( stats.getBalanceControl() + ((0.8 * pointTally)/100.0));
			
			stats.setLowerBody( stats.getLowerBody() + ( 0.5 * pointTally ) );
			stats.setOverallSkill( stats.getOverallSkill() + ( 0.4 * pointTally ) );
			
			if ( isRight() ){
				stats.setRightLeg( stats.getRightLeg() + (0.7*pointTally) );
				stats.setLeftLeg( stats.getLeftLeg() + (0.3 * pointTally) );
			}
			else {
				stats.setRightLeg( stats.getRightLeg() + (0.3*pointTally) );
				stats.setLeftLeg( stats.getLeftLeg() + (0.7 * pointTally) );						
			}
		}
		else if ( match.getKimarite().getType().equals( Type.DEFENSE ) ){
			
			if ( match.getWin() ){
				stats.setDefense( stats.getDefense() + pointTally );
			}
			
			stats.setBalanceControl( stats.getBalanceControl() + (( 0.3 * pointTally)/ 10.0));
			stats.setUpperBody( stats.getUpperBody() + (0.2 * pointTally) );
			stats.setLowerBody( stats.getLowerBody() + (0.2 * pointTally) );
			
			stats.setOverallSkill( stats.getOverallSkill() + (0.3 * pointTally) );
			
			// probably a mental boost here
			
			if ( match.getKimarite().equals( Kimarite.UTCHARI ) ){
				stats.setEdgeTechnique( stats.getEdgeTechnique() + pointTally );
			}
		}
		else if ( match.getKimarite().getType().equals( Type.TSUKI ) ){
			
			stats.setOshi( stats.getOshi() + ( 0.3 * pointTally ) );
			stats.setTsuki( stats.getTsuki() + pointTally );
			stats.setQuickness( stats.getQuickness() + ( 0.3 * pointTally) );
			
			stats.setBalanceControl( stats.getBalanceControl() + (( 0.2 * pointTally) / 10.0 ));
			
			stats.setUpperBody( stats.getUpperBody() + ( 0.8 * pointTally ) );
			
			if ( isRight() ){
				stats.setRightArm( stats.getRightArm() + (0.8 * pointTally) );
			}
			else {
				stats.setLeftArm( stats.getLeftArm() + (0.8 * pointTally) );
			}
			
			stats.setOverallSkill( stats.getOverallSkill() + ( 0.2 * pointTally ) );
		}
		else if ( match.getKimarite().getType().equals( Type.NAGE ) ){
			
			stats.setNage( stats.getNage() + pointTally );
			stats.setOverallSkill( stats.getOverallSkill() + pointTally );
			
			stats.setLowerBody( stats.getLowerBody() + ( 0.4 * pointTally ) );
			stats.setUpperBody( stats.getUpperBody() + ( 0.3 * pointTally ) );
			stats.setGripAbility( stats.getGripAbility() + ( 0.4 * pointTally ) );
			stats.setBalanceControl( stats.getBalanceControl() + (( 0.5 * pointTally )/ 100.0));
			
			if ( isRight() ){
				stats.setRightArm( stats.getRightArm() + (0.8 * pointTally) );
			}
			else {
				stats.setLeftArm( stats.getLeftArm() + (0.8 * pointTally) );
			}
		}
		else if ( match.getKimarite().getType().equals( Type.HIKU ) ){
			
			stats.setHiku( stats.getHiku() + pointTally ); 
			stats.setDefense( stats.getDefense() + (0.2 * pointTally ) );
			stats.setUpperBody( stats.getUpperBody() + ( 0.2 * pointTally ) );
		}
		else if ( match.getKimarite().getType().equals( Type.SPECIAL ) ){
			
			stats.setOverallSkill( stats.getOverallSkill() + (0.8 * pointTally) );
			stats.setQuickness( stats.getQuickness() + (0.3 * pointTally) );
		}
		else if ( match.getKimarite().getType() == null ){
			
			stats.setEdgeTechnique( stats.getEdgeTechnique() + (1.3 * pointTally) );
			
			// mental implications too
		}
	}
	
	/**
	 * Just pick whether it was right of left randomly
	 * @return
	 */
	protected Boolean isRight(){
		
		int v = (int)((Math.random()* 100 ) + 1);
		
		return (v <= 75);
	}
	
	/**
	 * Holds the percentage bonus for end of basho performance
	 * 
	 * @param day
	 * @return
	 */
	protected Double endOfBashoBonusPercentage( int day ){
		
		Double perc = (15.0/4.0) * (double)day - ( 145.0 / 4.0 );
		perc /= 100.0;
		
		return perc;
	}
	
	/**
	 * Holds the opponent bonus calculation
	 * 
	 * @param opponentRankPosition
	 * @return
	 */
	protected Double getOpponentDiffValue( int opponentRankPosition ){
		return (10.196)*Math.pow( (double)Math.abs( opponentRankPosition ), 2.0);
	}
	
	/**
	 * Holds the consecutive calculation
	 * @param consecutives
	 * @return
	 */
	protected Double getConsecutiveValue( int consecutives ){
		return 0.5 * (double)consecutives;
	}
	
	/**
	 * The plus/minus of the relative rank you are fighting.  + if the opponent is better than you,
	 * minus if they are worse
	 * @param myRank
	 * @return
	 */
	protected Integer determineRelativeRank( Rank myRank, Rank theirRank ){
		
		int myIndex = myRank.getRankClass().ordinal();
		int theirIndex = theirRank.getRankClass().ordinal();
		
		Rank highRank = myRank;
		Rank lowRank = theirRank;
		
		// this way we only traverse one direction and fix the sign at the end.
		if ( (myIndex == theirIndex && myRank.getRankNumber() > theirRank.getRankNumber() ) ||
			myIndex > theirIndex ||
			(myIndex == theirIndex && myRank.getRankNumber() == theirRank.getRankNumber() && theirRank.getRankSide().equals( RankSide.EAST )) ) {
			
			highRank = theirRank;
			lowRank = myRank;
		}
		
		Rank rankIndex = new Rank( lowRank.getRankClass(), lowRank.getRankSide(), lowRank.getRankNumber() );
		int difference = 0;
		
		while( rankIndex.getRankClass().ordinal() > highRank.getRankClass().ordinal() ){
			
			int d = (rankIndex.getRankNumber() - 1) * 2;
			d += 1;
			
			if ( rankIndex.getRankSide().equals( RankSide.WEST ) ){
				d += 1;
			}
			
			difference += d;
			
			rankIndex = new Rank( RankClass.values()[rankIndex.getRankClass().ordinal()-1], 
					RankSide.WEST, RankClass.values()[rankIndex.getRankClass().ordinal()-1].getPreferred() );
		}
		
		// now we're in the same rank
		if ( rankIndex.getRankNumber() < highRank.getRankNumber() ){
			// this is in case we're at like... yokozuna where numbers aren't really set.
			difference++;
		}
		else {
			int realHigh = (highRank.getRankNumber() * 2 ) - 1;
			if ( highRank.getRankSide().equals( RankSide.WEST ) ){
				realHigh++;
			}
			
			int realLow = (rankIndex.getRankNumber() * 2) - 1;
			if ( rankIndex.getRankSide().equals( RankSide.WEST ) ){
				realLow++;
			}
			
			difference += realLow - realHigh;
		}
		
		if ( myRank.equals( highRank ) ){
			difference--;
			difference *= -1;
		}
		
		return difference;
	}
	
	/**
	 * compute the rikishi age
	 * 
	 * @param birthday
	 * @param year
	 * @param month
	 * @return
	 */
	protected Integer getAge( Date birthday, Integer year, Integer month ){
		
		Calendar bc = Calendar.getInstance();
		bc.setTime( birthday );
		
		LocalDate bDate = LocalDate.of( bc.get( Calendar.YEAR ), bc.get( Calendar.MONTH )+1, bc.get( Calendar.DAY_OF_MONTH ) );
		LocalDate nowDate = LocalDate.of( year, month, 1 );
		
		Period between = Period.between( bDate, nowDate );
		Integer age = between.getYears();
		return age;
	}
	
	// set up the base line.
	private RikishiStats getStartingStats( RikishiInfo info, Integer year, Integer month ){
		
		Integer age = getAge( info.getBirthday(), year, month ); 
		
		// figure what our range is given our status.
		Integer peak = computePeakValue( info.getCurrentRank() );  
		
		Double overall = ( -1.5 * Math.pow( (age-18.0) - 10.0, 2.0) + peak );
		
		RikishiStats stats = new RikishiStats();
		
		stats.setDefense( (overall * 0.8) );
		stats.setEdgeTechnique( overall * 0.8 );
		stats.setGake( (overall * 0.6) );
		stats.setGripAbility( overall );
		stats.setGripBreak( (overall * 0.7) );
		stats.setHiku( overall );
		stats.setLeftArm( overall );
		stats.setRightArm( overall );
		stats.setRightLeg( overall );
		stats.setLeftLeg( overall );
		stats.setLowerBody( overall );
		stats.setUpperBody( overall );
		stats.setNage( overall );
		stats.setOshi( overall );
		stats.setSecondaryGripAbility( (overall*0.6) );
		stats.setOverallSkill( overall );
		stats.setTsuki( overall );
		
		stats.setPotential( ( (1000.0/-18.0) * age + 2000 ) );
		stats.setBalanceControl( overall / 10 );
		stats.setRecovery( overall / 10 );
		stats.setQuickness( overall * 0.6 );
		
		return stats;
	}
	
	protected Integer computePeakValue( Rank currentRank ){
		
		// assumes peak age of 28
		Integer[] skillVals = spread.get( currentRank.getRankClass() );
		
		// figure what our range is given our status.
		Double perSpot = ((skillVals[1]/2.0) / (currentRank.getRankClass().getPreferred()*2));
		Integer peak = skillVals[0] + skillVals[1]/2;  
		
		if ( currentRank.getRankClass().getPreferred() != Rank.UNLIMITED && currentRank.getRankNumber() != 0 ){
			
			Integer myNumber = (currentRank.getRankClass().getMax() - currentRank.getRankNumber()) * 2;
			
			if ( currentRank.getRankSide().equals( RankSide.EAST ) ){
				myNumber += 2;
			}
			else {
				myNumber += 1;
			}
			
			peak = skillVals[0] + (int)(perSpot * myNumber);
		}
		
		return peak;
	}
	
	private RikishiInfo buildRikishiInfo( String line ){
		
		RikishiInfo rinf = new RikishiInfo();
		
		String[] fields = line.split( ":" )[1].split( "," );
		Iterator<String> strIt = Arrays.asList( fields ).iterator();
		
		rinf.setId( Long.parseLong( strIt.next() ) ); 
		
		Name realName = new Name();
		realName.setFirstName_en( strIt.next() );
		realName.setFirstName_jp( strIt.next() );
		realName.setFirstName_kanji( strIt.next() );
		realName.setLastName_en( strIt.next() );
		realName.setLastName_jp( strIt.next() );
		realName.setLastName_kanji( strIt.next() );
		
		rinf.setRealName( realName );
		
		RankClass currentClass = RankClass.valueOf( strIt.next() );
		RankSide currentSide = RankSide.valueOf( strIt.next() );
		Integer currentNumber = Integer.parseInt( strIt.next() );
		rinf.setCurrentRank( new Rank( currentClass, currentSide, currentNumber ) );
		
		Name shikona = new Name();
		shikona.setFirstName_en( strIt.next() );
		shikona.setFirstName_jp( strIt.next() );
		shikona.setFirstName_kanji( strIt.next() );
		shikona.setLastName_en( strIt.next() );
		shikona.setLastName_jp( strIt.next() );
		shikona.setLastName_kanji( strIt.next() );
		
		rinf.setShikona( shikona );
		
		Name university = new Name();
		
		university.setFirstName_en( strIt.next() );
		university.setFirstName_jp( strIt.next() );
		
		if ( university.getFirstName_en().trim().equalsIgnoreCase( "" ) ){
			university = null;
		}
		
		rinf.setuniversity( university );
		
		rinf.setBirthday( new Date( Long.parseLong( strIt.next() ) ) );
		rinf.setHatsuBasho( new Date( Long.parseLong( strIt.next() ) ) );
		
		rinf.setHeight( new Height( Integer.parseInt( strIt.next() ) ) );
		rinf.setWeight( new Weight( Integer.parseInt( strIt.next() ) ) );
		
		Long heyaId = Long.parseLong( strIt.next() );
		
		if ( heyaId == -1 ){
			rinf.setHeya( null );
		}
		else {
			rinf.setHeya( Heya.getKnownHeya().get( heyaId ) );
		}
		
		Long locationId = Long.parseLong( strIt.next() );
		
		if ( locationId == -1 ){
			rinf.setHometown( null );
		}
		else {
			rinf.setHometown( Location.getKnownLocations().get( locationId ) );
		}
		
		RankClass clazz = RankClass.valueOf( strIt.next() );
		Integer number = Integer.parseInt( strIt.next() );
		RankSide side = RankSide.valueOf( strIt.next() );
		Rank rank = new Rank( clazz, side, number );
		
		rinf.setHighestRank( rank );
		
		Integer wins = Integer.parseInt( strIt.next() );
		Integer loses = Integer.parseInt( strIt.next() );
		Integer forfeits = Integer.parseInt( strIt.next() );
		
		Record record = new Record( wins, loses, forfeits );
		
		rinf.setCareerRecord( record );
		
		rinf.setMakuuchiYusho( Integer.parseInt( strIt.next() ) );
		rinf.setMakuuchiJunYusho( Integer.parseInt( strIt.next() ) );
		rinf.setGinoSho( Integer.parseInt( strIt.next() ) );
		rinf.setKantoSho( Integer.parseInt( strIt.next() ) );
		rinf.setShukunSho( Integer.parseInt( strIt.next() ) );
		rinf.setJuryoYusho( Integer.parseInt( strIt.next() ) );
		rinf.setJuryoJunYusho( Integer.parseInt( strIt.next() ) );
		rinf.setMakushitaYusho( Integer.parseInt( strIt.next() ) );
		rinf.setMakushitaJunYusho( Integer.parseInt( strIt.next() ) );
		rinf.setSandanmeYusho( Integer.parseInt( strIt.next() ) );
		rinf.setSandanmeJunYusho( Integer.parseInt( strIt.next() ) );
		rinf.setJonidanYusho( Integer.parseInt( strIt.next() ) );
		rinf.setJonidanJunYusho( Integer.parseInt( strIt.next() ) );
		rinf.setJonokuchiYusho( Integer.parseInt( strIt.next() ) );
		rinf.setJonokuchiJunYusho( Integer.parseInt( strIt.next() ) );
		rinf.setMaeZumoYusho( Integer.parseInt( strIt.next() ) );
		
		return rinf;
	}
	
	private List<List<MatchResult>> convertToList( String[] bashos ){
		
		List<List<MatchResult>> bashoResults = new ArrayList<List<MatchResult>>();
		
		for ( String basho : bashos ){
			
			String[] matches = basho.split( "=" );
			List<MatchResult> matchList = new ArrayList<MatchResult>();
			
			for ( String match : matches ){
				
				Iterator<String> mIt = Arrays.asList( match.split( "," ) ).iterator();
				
				MatchResult result = new MatchResult();
				
				result.setWin( Boolean.parseBoolean( mIt.next() ) );
				
				String kStr = mIt.next();
				
				if ( !kStr.trim().equalsIgnoreCase( "" ) ){
					result.setKimarite( Kimarite.valueOf( kStr ) );
				}
				
				result.setOpponenId( Long.parseLong( mIt.next() ) );
				
				RankClass clazz = RankClass.valueOf( mIt.next() );
				Integer number = Integer.parseInt( mIt.next() );
				RankSide side = RankSide.valueOf( mIt.next() );
				
				result.setOpponentRank( new Rank( clazz, side, number ) );
				
				Integer wins = Integer.parseInt( mIt.next() );
				Integer loses = Integer.parseInt( mIt.next() );
				Integer forfeits = Integer.parseInt( mIt.next() );
				
				Record oRecord = new Record( wins, loses, forfeits );

				result.setOpponentRecord( oRecord );
				
				matchList.add( result );
			
			}// matches
			
			bashoResults.add( matchList );
			
		}// end of bashos
		
		return bashoResults;
	}

}
