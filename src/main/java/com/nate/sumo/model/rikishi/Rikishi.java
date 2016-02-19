package com.nate.sumo.model.rikishi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nate.sumo.DatabaseConstants;
import com.nate.sumo.DatabaseManager;
import com.nate.sumo.model.animation.AnimationMap;
import com.nate.sumo.model.appearence.AppearenceMap;
import com.nate.sumo.model.basho.Rank;
import com.nate.sumo.model.basho.Rank.RankClass;
import com.nate.sumo.model.basho.Rank.RankSide;
import com.nate.sumo.model.common.Height;
import com.nate.sumo.model.common.Location;
import com.nate.sumo.model.common.Name;
import com.nate.sumo.model.common.Record;
import com.nate.sumo.model.common.Weight;
import com.nate.sumo.model.fight.GRIP;
import com.nate.sumo.model.fight.scenario.SCENARIOS;

public class Rikishi {

	private RikishiInfo rikishiInfo;
	private RikishiStats rikishiStats;
	private RikishiTemperment rikishiTemperment;
	private RikishiTendencies rikishiTendencies;
	private AnimationMap animationMap;
	private AppearenceMap appearenceMap;
	
	public Rikishi(){}

	public RikishiInfo getRikishiInfo() {
		return rikishiInfo;
	}

	public void setRikishiInfo(RikishiInfo rikishiInfo) {
		this.rikishiInfo = rikishiInfo;
	}

	public RikishiStats getRikishiStats() {
		return rikishiStats;
	}

	public void setRikishiStats(RikishiStats rikishiStats) {
		this.rikishiStats = rikishiStats;
	}

	public RikishiTemperment getRikishiTemperment() {
		return rikishiTemperment;
	}

	public void setRikishiTemperment(RikishiTemperment rikishiTemperment) {
		this.rikishiTemperment = rikishiTemperment;
	}

	public RikishiTendencies getRikishiTendencies() {
		return rikishiTendencies;
	}

	public void setRikishiTendencies(RikishiTendencies rikishiTendencies) {
		this.rikishiTendencies = rikishiTendencies;
	}

	public AnimationMap getAnimationMap() {
		return animationMap;
	}

	public void setAnimationMap(AnimationMap animationMap) {
		this.animationMap = animationMap;
	}

	public AppearenceMap getAppearenceMap() {
		return appearenceMap;
	}

	public void setAppearenceMap(AppearenceMap appearenceMap) {
		this.appearenceMap = appearenceMap;
	}
	
	public List<String> getCreateSql( Integer year, Integer month ){
		
		List<String> creates = new ArrayList<String>();
		
		String suffix = "_" + year + "_" + month;
		
		String create = "INSERT INTO " + DatabaseConstants.BANZUKE_BASE + suffix + " ( " + DatabaseConstants.C_RIKISHI_ID + 
			" ) VALUES ( " + getRikishiInfo().getId() + " )";
		
		String createDna = "INSERT INTO " + DatabaseConstants.DNA_BASE + suffix + " ( " + DatabaseConstants.C_RIKISHI_ID + 
			" ) VALUES ( " + getRikishiInfo().getId() + " )";
		
		String createLook = "INSERT INTO " + DatabaseConstants.LOOK_BASE + suffix + " ( " + DatabaseConstants.C_RIKISHI_ID + 
			" ) VALUES ( " + getRikishiInfo().getId() + " )";
		
		String createAnimations = "INSERT INTO " + DatabaseConstants.ANIMATION_BASE + suffix + " ( " + DatabaseConstants.C_RIKISHI_ID +
			" ) VALUES ( " + getRikishiInfo().getId() + " )";
		
		creates.add( create );
		creates.add( createDna );
		creates.add( createLook );
		creates.add( createAnimations );
		
		return creates;
	}
	
	public List<String> getUpdateSql( Integer year, Integer month ){
		
		List<String> updates = new ArrayList<String>();
		
		String suffix = "_" + year + "_" + month;
		
		Long hometown = -1L;
		
		if ( getRikishiInfo().getHometown() != null ){
			hometown = getRikishiInfo().getHometown().getId();
		}
		
		Long heya = -1L;
		
		if ( getRikishiInfo().getHeya() != null ){
			heya = getRikishiInfo().getHeya().getId();
		}
		
		String updateRinf = "UPDATE " + DatabaseConstants.BANZUKE_BASE + suffix + " SET " +
			DatabaseConstants.C_EN_FIRST_NAME + "='" + getRikishiInfo().getRealName().getFirstName_en() + "'," +
			DatabaseConstants.C_EN_LAST_NAME + "='" + getRikishiInfo().getRealName().getLastName_en() + "'," +
			DatabaseConstants.C_KJ_FIRST_NAME + "='" + getRikishiInfo().getRealName().getFirstName_kanji() + "'," +
			DatabaseConstants.C_KJ_LAST_NAME + "='" + getRikishiInfo().getRealName().getLastName_kanji() + "'," +
			DatabaseConstants.C_EN_SHIKONA_FIRST + "='" + getRikishiInfo().getShikona().getFirstName_en() + "'," +
			DatabaseConstants.C_EN_SHIKONA_LAST + "='" + getRikishiInfo().getShikona().getLastName_en() + "'," +
			DatabaseConstants.C_JP_SHIKONA_FIRST + "='" + getRikishiInfo().getShikona().getFirstName_jp() + "'," +
			DatabaseConstants.C_JP_SHIKONA_LAST + "='" + getRikishiInfo().getShikona().getLastName_jp() + "'," +
			DatabaseConstants.C_KJ_SHIKONA_FIRST + "='" + getRikishiInfo().getShikona().getFirstName_kanji() + "'," +
			DatabaseConstants.C_KJ_SHIKONA_LAST + "='" + getRikishiInfo().getShikona().getLastName_kanji() + "',";
		
		if ( getRikishiInfo().getUniversity() == null ){
			updateRinf += 
				DatabaseConstants.C_EN_UNIVERSITY + "=''," +
				DatabaseConstants.C_KJ_UNIVERSITY + "='',";
		}
		else {
			updateRinf += DatabaseConstants.C_EN_UNIVERSITY + "='" + getRikishiInfo().getUniversity().getFirstName_en() + "'," +
			DatabaseConstants.C_KJ_UNIVERSITY + "='" + getRikishiInfo().getUniversity().getFirstName_kanji() + "',";
		}
		
		updateRinf += DatabaseConstants.C_HEYA_ID + "=" + heya + "," +
			DatabaseConstants.C_HOMETOWN + "=" + hometown + "," +
			DatabaseConstants.C_HEIGHT + "=" + getRikishiInfo().getHeight().getValue() + "," +
			DatabaseConstants.C_WEIGHT + "=" + getRikishiInfo().getWeight().getValue() + "," +
			DatabaseConstants.C_BIRTHDAY + "=" + getRikishiInfo().getBirthday().getTime() + "," +
			DatabaseConstants.C_HATSU_BASHO + "=" + getRikishiInfo().getHatsuBasho().getTime() + "," +
			DatabaseConstants.C_CURRENT_RANK + "='" + getRikishiInfo().getCurrentRank().getRankClass().name() + "',";
		
		String side = "";
		Integer number = -1;
	
		if ( getRikishiInfo().getCurrentRank().getRankSide() != null ){
			side = getRikishiInfo().getCurrentRank().getRankSide().name();
		}
		
		updateRinf += DatabaseConstants.C_CURRENT_SIDE + "='" + side + "',";
		
		if ( getRikishiInfo().getCurrentRank().getRankNumber() != null ){
			number = getRikishiInfo().getCurrentRank().getRankNumber();
		}
			
		updateRinf += DatabaseConstants.C_CURRENT_NUMBER + "=" + number + ",";
		updateRinf += DatabaseConstants.C_HIGHEST_RANK + "='" + getRikishiInfo().getHighestRank().getRankClass().name() + "',";
		
		side = "";
		number = -1;
		
		if ( getRikishiInfo().getHighestRank().getRankSide() != null ) {
			side = getRikishiInfo().getHighestRank().getRankSide().name();
		}
		
		updateRinf += DatabaseConstants.C_HIGHEST_SIDE + "='" + side + "',";
		
		if ( getRikishiInfo().getHighestRank().getRankNumber() != null ){
			number = getRikishiInfo().getHighestRank().getRankNumber();
		}
		
		updateRinf += DatabaseConstants.C_HIGHEST_NUMBER + "=" + number + "," +
			DatabaseConstants.C_MAKUUCHI_YUSHO + "=" + getRikishiInfo().getMakuuchiYusho() + "," +
			DatabaseConstants.C_MAKUUCHI_JUN_YUSHO + "=" + getRikishiInfo().getMakuuchiJunYusho() + "," +
			DatabaseConstants.C_GINO_SHO + "=" + getRikishiInfo().getGinoSho() + "," +
			DatabaseConstants.C_SHUKUN_SHO + "=" + getRikishiInfo().getShukunSho() + "," +
			DatabaseConstants.C_KANTO_SHO + "=" + getRikishiInfo().getKantoSho() + "," +
			DatabaseConstants.C_JURYO_YUSHO + "=" + getRikishiInfo().getJuryoYusho() + "," +
			DatabaseConstants.C_JURYO_JUN_YUSHO + "=" + getRikishiInfo().getJuryoJunYusho() + "," +
			DatabaseConstants.C_MAKUSHITA_YUSHO + "=" + getRikishiInfo().getMakushitaYusho() + "," +
			DatabaseConstants.C_MAKUSHITA_JUN_YUSHO + "=" + getRikishiInfo().getMakushitaJunYusho() + "," +
			DatabaseConstants.C_SANDANME_YUSHO + "=" + getRikishiInfo().getSandanmeYusho() + "," +
			DatabaseConstants.C_SANDANME_JUN_YUSHO + "=" + getRikishiInfo().getSandanmeJunYusho() + "," +
			DatabaseConstants.C_JONIDAN_YUSHO + "=" + getRikishiInfo().getJonidanYusho() + "," +
			DatabaseConstants.C_JONIDAN_JUN_YUSHO + "=" + getRikishiInfo().getJonidanJunYusho() + "," +
			DatabaseConstants.C_JONOKUCHI_YUSHO + "=" + getRikishiInfo().getJonokuchiYusho() + "," +
			DatabaseConstants.C_JONOKUCHI_JUN_YUSHO + "=" + getRikishiInfo().getJonokuchiJunYusho() + "," +
			DatabaseConstants.C_MAE_ZUMO_YUSHO + "=" + getRikishiInfo().getMaeZumoYusho() + "," +
			DatabaseConstants.C_CAREER_WINS + "=" + getRikishiInfo().getCareerRecord().getWins() + "," +
			DatabaseConstants.C_CAREER_LOSES + "=" + getRikishiInfo().getCareerRecord().getLoses() + "," +
			DatabaseConstants.C_CAREER_FORFEITS + "=" + getRikishiInfo().getCareerRecord().getForfeits() + "," +
			DatabaseConstants.C_OSHI + "=" + getRikishiStats().getOshi().intValue() + "," +
			DatabaseConstants.C_GAKE + "=" + getRikishiStats().getGake().intValue() + "," +
			DatabaseConstants.C_YOTSU + "=" + getRikishiStats().getYotsu().intValue() + "," +
			DatabaseConstants.C_NAGE + "=" + getRikishiStats().getNage().intValue() + "," +
			DatabaseConstants.C_TSUKI + "=" + getRikishiStats().getTsuki().intValue() + "," +
			DatabaseConstants.C_HIKU + "=" + getRikishiStats().getHiku().intValue() + "," +
			DatabaseConstants.C_DEFENSE + "=" + getRikishiStats().getDefense().intValue() + "," +
			DatabaseConstants.C_OVERALL_SKILL + "=" + getRikishiStats().getOverallSkill().intValue() + "," +
			DatabaseConstants.C_TACHI_AI + "=" + getRikishiStats().getTachiAi().intValue() + "," +
			DatabaseConstants.C_LEFT_ARM + "=" + getRikishiStats().getLeftArm().intValue() + "," +
			DatabaseConstants.C_RIGHT_ARM + "=" + getRikishiStats().getRightArm().intValue() + "," +
			DatabaseConstants.C_UPPER_BODY + "=" + getRikishiStats().getUpperBody().intValue() + "," +
			DatabaseConstants.C_LOWER_BODY + "=" + getRikishiStats().getLowerBody().intValue() + "," +
			DatabaseConstants.C_LEFT_LEG + "=" + getRikishiStats().getLeftLeg().intValue() + "," +
			DatabaseConstants.C_QUICKNESS + "=" + getRikishiStats().getQuickness().intValue() + "," +
			DatabaseConstants.C_EDGE_TECHNIQUE + "=" + getRikishiStats().getEdgeTechnique().intValue() + "," +
			DatabaseConstants.C_GRIP_BREAK + "=" + getRikishiStats().getGripBreak().intValue() + "," +
			DatabaseConstants.C_GRIP_STRENGTH + "=" + getRikishiStats().getGripAbility().intValue() + "," +
			DatabaseConstants.C_SECONDARY_GRIP_STRENGTH + "=" + getRikishiStats().getSecondaryGripAbility().intValue() + "," +
			DatabaseConstants.C_PREFERRED_GRIP + "='" + getRikishiStats().getPreferredGrip().name() + "'," +
			DatabaseConstants.C_BALANCE_CONTROL + "=" + getRikishiStats().getBalanceControl().intValue() + "," +
			DatabaseConstants.C_POTENTIAL + "=" + getRikishiStats().getPotential().intValue() + "," +
			DatabaseConstants.C_FOCUS + "=" + getRikishiTemperment().getFocus().intValue() + "," +
			DatabaseConstants.C_ANGER + "=" + getRikishiTemperment().getAnger().intValue() + "," +
			DatabaseConstants.C_DRIVE + "=" + getRikishiTemperment().getDrive().intValue() + "," +
			DatabaseConstants.C_IQ + "=" + getRikishiTemperment().getIq().intValue() + ",";
		
		String injuryString = "";
		
		if ( getRikishiInfo().getInjuries() != null ){
			
			for ( Injury injury : getRikishiInfo().getInjuries() ){
				injuryString += injury.convertToStorageString() + ",";
			}
		}
		
		updateRinf += DatabaseConstants.C_INJURIES + "='" + injuryString + "',";
		
		updateRinf += DatabaseConstants.C_EMOTIONS + "=" + getRikishiTemperment().getEmotions().intValue() + " WHERE " +
			DatabaseConstants.C_RIKISHI_ID + "=" + getRikishiInfo().getId();
		
		updates.add( updateRinf );
		
		String dnaUpdate = "UPDATE " + DatabaseConstants.DNA_BASE + suffix + " SET ";
		dnaUpdate += DatabaseConstants.C_WINNING + "='" + getRikishiTendencies().convertScenarioToString( SCENARIOS.WINNING.getScenario() ) + "',";
		dnaUpdate += DatabaseConstants.C_LOSING + "='" + getRikishiTendencies().convertScenarioToString( SCENARIOS.LOSING.getScenario() ) + "',";
		dnaUpdate += DatabaseConstants.C_EDGE_WINNING + "='" + getRikishiTendencies().convertScenarioToString( SCENARIOS.EDGE_VICTORY.getScenario() ) + "',";
		dnaUpdate += DatabaseConstants.C_EDGE_LOSING + "='" + getRikishiTendencies().convertScenarioToString( SCENARIOS.EDGE_DANGER.getScenario() ) + "',";
		dnaUpdate += DatabaseConstants.C_TACHI_AI + "='" + getRikishiTendencies().convertScenarioToString( SCENARIOS.TACHI_AI.getScenario() ) + "'";
		
		dnaUpdate += " WHERE " + DatabaseConstants.C_RIKISHI_ID + "=" + getRikishiInfo().getId();
		
		updates.add( dnaUpdate );
		
		//appearence
		String appUpdate = "UPDATE " + DatabaseConstants.LOOK_BASE + suffix + " SET ";
		appUpdate += DatabaseConstants.C_MAWASHI_COLOR + "='" + getAppearenceMap().colorToString( getAppearenceMap().getMawashiColor() ) + "',";
		appUpdate += DatabaseConstants.C_MAWASHI_TXT + "='" + getAppearenceMap().getMawashiTxt() + "',";
		appUpdate += DatabaseConstants.C_MAWASHI_MODEL + "='" + getAppearenceMap().getMawashiModel() + "',";
		appUpdate += DatabaseConstants.C_BODY_MODEL + "='" + getAppearenceMap().getBodyModel() + "',";
		appUpdate += DatabaseConstants.C_HEAD_MODEL + "='" + getAppearenceMap().getHeadModel() + "',";
		appUpdate += DatabaseConstants.C_BODY_TXT + "='" + getAppearenceMap().getBodyTxt() + "',";
		appUpdate += DatabaseConstants.C_HEAD_TXT + "='" + getAppearenceMap().getHeadTxt() + "',";
		appUpdate += DatabaseConstants.C_HAIR_COLOR + "='" + getAppearenceMap().colorToString( getAppearenceMap().getHairColor() ) + "',";
		appUpdate += DatabaseConstants.C_HAIR_TXT + "='" + getAppearenceMap().getHairTxt() + "',";
		appUpdate += DatabaseConstants.C_HAIR_MODEL + "='" + getAppearenceMap().getHairModel() + "',";
		appUpdate += DatabaseConstants.C_KESHO_MODEL + "='" + getAppearenceMap().getKeshoModel() + "',";
		appUpdate += DatabaseConstants.C_KESHO_TXT + "='" + getAppearenceMap().getKeshoTxt() + "',";
		appUpdate += DatabaseConstants.C_PORTRAIT + "='" + getAppearenceMap().getPortrait() + "',";
		appUpdate += DatabaseConstants.C_SKIN_TONE + "='" + getAppearenceMap().colorToString( getAppearenceMap().getSkinTone() ) + "' WHERE " +
			DatabaseConstants.C_RIKISHI_ID + '=' + getRikishiInfo().getId();
		
		updates.add( appUpdate );
		
		
		if ( getAnimationMap() != null ){
			//animations
			String animUpdate = "UPDATE " + DatabaseConstants.ANIMATION_BASE + suffix + " SET ";
			animUpdate += DatabaseConstants.C_ANIMATIONS + "='" + getAnimationMap().toString() + "' WHERE " +
				DatabaseConstants.C_RIKISHI_ID + "=" + getRikishiInfo().getId();
			
			updates.add( animUpdate );
			
		}
		
		return updates;
	}
	
	public static List<Rikishi> findAll(){
		List<Rikishi> list = new ArrayList<Rikishi>();
		String suffix = "_" + DatabaseManager.getInstance().getYear() + "_" + DatabaseManager.getInstance().getMonth();
		
		String sql = "SELECT " + 
			DatabaseConstants.C_RIKISHI_ID + ", " +
			DatabaseConstants.C_EN_FIRST_NAME + ", " + 
			DatabaseConstants.C_EN_LAST_NAME + ", " +
			DatabaseConstants.C_KJ_FIRST_NAME + ", " +
			DatabaseConstants.C_KJ_LAST_NAME + ", " +
			DatabaseConstants.C_EN_SHIKONA_FIRST + ", " +
			DatabaseConstants.C_EN_SHIKONA_LAST + ", " +
			DatabaseConstants.C_JP_SHIKONA_FIRST + ", " +
			DatabaseConstants.C_JP_SHIKONA_LAST  + ", " +
			DatabaseConstants.C_KJ_SHIKONA_FIRST + ", " +
			DatabaseConstants.C_KJ_SHIKONA_LAST + ", " +
			DatabaseConstants.C_EN_UNIVERSITY + ", " +
			DatabaseConstants.C_KJ_UNIVERSITY + ", " +
			DatabaseConstants.C_HEYA_ID + ", " +
			DatabaseConstants.C_HOMETOWN + ", " +
			DatabaseConstants.C_HEIGHT + ", " +
			DatabaseConstants.C_WEIGHT + ", " +
			DatabaseConstants.C_BIRTHDAY + ", " +
			DatabaseConstants.C_HATSU_BASHO + ", " +
			DatabaseConstants.C_CURRENT_RANK + ", " +
			DatabaseConstants.C_CURRENT_SIDE + ", " +
			DatabaseConstants.C_CURRENT_NUMBER + ", " +
			DatabaseConstants.C_HIGHEST_RANK + ", " +
			DatabaseConstants.C_HIGHEST_SIDE + ", " +
			DatabaseConstants.C_HIGHEST_NUMBER + ", " +
			DatabaseConstants.C_MAKUUCHI_YUSHO + ", " +
			DatabaseConstants.C_MAKUUCHI_JUN_YUSHO + ", " +
			DatabaseConstants.C_GINO_SHO + ", " +
			DatabaseConstants.C_SHUKUN_SHO + ", " +
			DatabaseConstants.C_KANTO_SHO + ", " +
			DatabaseConstants.C_JURYO_YUSHO + ", " +
			DatabaseConstants.C_JURYO_JUN_YUSHO + ", " +
			DatabaseConstants.C_MAKUSHITA_YUSHO + ", " +
			DatabaseConstants.C_MAKUSHITA_JUN_YUSHO + ", " +
			DatabaseConstants.C_SANDANME_YUSHO + ", " +
			DatabaseConstants.C_SANDANME_JUN_YUSHO + ", " +
			DatabaseConstants.C_JONIDAN_YUSHO + ", " +
			DatabaseConstants.C_JONIDAN_JUN_YUSHO + ", " +
			DatabaseConstants.C_JONOKUCHI_YUSHO + ", " +
			DatabaseConstants.C_JONOKUCHI_JUN_YUSHO + ", " +
			DatabaseConstants.C_MAE_ZUMO_YUSHO + ", " +
			DatabaseConstants.C_CAREER_WINS + ", " +
			DatabaseConstants.C_CAREER_LOSES + ", " +
			DatabaseConstants.C_CAREER_FORFEITS + ", " +
			DatabaseConstants.C_OSHI + ", " +
			DatabaseConstants.C_GAKE + ", " +
			DatabaseConstants.C_YOTSU + ", " +
			DatabaseConstants.C_NAGE + ", " +
			DatabaseConstants.C_TSUKI + ", " +
			DatabaseConstants.C_HIKU + ", " +
			DatabaseConstants.C_DEFENSE + ", " +
			DatabaseConstants.C_OVERALL_SKILL + ", " +
			DatabaseConstants.C_TACHI_AI + ", " +
			DatabaseConstants.C_LEFT_ARM + ", " +
			DatabaseConstants.C_RIGHT_ARM + ", " +
			DatabaseConstants.C_UPPER_BODY + ", " +
			DatabaseConstants.C_LOWER_BODY + ", " +
			DatabaseConstants.C_RIGHT_LEG + ", " +
			DatabaseConstants.C_LEFT_LEG + ", " +
			DatabaseConstants.C_QUICKNESS + ", " +
			DatabaseConstants.C_EDGE_TECHNIQUE + ", " +
			DatabaseConstants.C_GRIP_BREAK + ", " +
			DatabaseConstants.C_GRIP_STRENGTH + ", " +
			DatabaseConstants.C_SECONDARY_GRIP_STRENGTH + ", " +
			DatabaseConstants.C_PREFERRED_GRIP + ", " +
			DatabaseConstants.C_BALANCE_CONTROL + ", " +
			DatabaseConstants.C_RECOVERY + ", " +
			DatabaseConstants.C_POTENTIAL + ", " +
			DatabaseConstants.C_FOCUS + ", " +
			DatabaseConstants.C_ANGER + ", " +
			DatabaseConstants.C_DRIVE + ", " +
			DatabaseConstants.C_IQ + ", " +
			DatabaseConstants.C_EMOTIONS + ", " +
			DatabaseConstants.C_INJURIES +
				" FROM " + DatabaseConstants.BANZUKE_BASE + suffix; 
		
		List<List<Object>> results = DatabaseManager.getInstance().query( sql );
		
		for ( List<Object> rawRikishi : results ){
			list.add( Rikishi.convertFromVector( rawRikishi ) );
		}
		
		return list;
	}
	
	public static Rikishi convertFromVector( List<Object> results ){
		
		Rikishi r = new Rikishi();
		
		RikishiInfo info = new RikishiInfo();
		
		info.setId( (Long)results.get( 0 ) );
		Name enName = new Name( results.get( 1 ).toString(),
				null, 
				results.get( 2 ).toString(),
				null,
				results.get( 3 ).toString(),
				results.get( 4 ).toString() );
		info.setRealName( enName );
		
		Name shikona = new Name( results.get( 5 ).toString(), 
			results.get( 7 ).toString(),
			results.get( 6 ).toString(),
			results.get( 8 ).toString(),
			results.get( 9 ).toString(),
			results.get( 10 ).toString() );
		
		info.setShikona( shikona );
		
		Name university = new Name( results.get( 11 ).toString(),
			null, null, null, results.get( 12 ).toString(), null );
		
		info.setUniversity( university );
		
		Heya heya = Heya.getById( (Long)results.get( 13 ) );
		info.setHeya( heya );
		
		Location loc = Location.getById( (Long)results.get( 14 ) );
		info.setHometown( loc );
		
		Height height = new Height( (Integer)results.get( 15 ) );
		info.setHeight( height );
		
		Weight weight = new Weight( (Integer)results.get( 16 ) );
		info.setWeight( weight );
		
		Date bday = new Date( (Long)results.get( 17 ) );
		info.setBirthday( bday );
		
		Date hatsu = new Date( (Long)results.get( 18 ) );
		info.setHatsuBasho( hatsu );
		
		RankClass rankClass = RankClass.valueOf( results.get( 19 ).toString() );
		RankSide rankSide = RankSide.valueOf( results.get( 20 ).toString() );
		Integer rankNumber = (Integer)results.get( 21 );
		Rank rank = new Rank( rankClass, rankSide, rankNumber );
		
		info.setCurrentRank( rank );
		
		rankClass = RankClass.valueOf( results.get( 22 ).toString() );
		rankSide = RankSide.valueOf( results.get( 23 ).toString() );
		rankNumber = (Integer)results.get( 24 );
		
		Rank highRank = new Rank( rankClass, rankSide, rankNumber );
		info.setHighestRank( highRank );
		
		Integer makuuchiYusho = (Integer)results.get( 25 );
		Integer makuuchiJunYusho = (Integer)results.get( 26 );
		Integer ginoSho = (Integer)results.get( 27 );
		Integer shukunSho = (Integer)results.get( 28 );
		Integer kantoSho = (Integer)results.get( 29 );
		Integer juryoYusho = (Integer)results.get( 30 );
		Integer juryoJunYusho = (Integer)results.get( 31 );
		Integer makushitaYusho = (Integer)results.get( 32 );
		Integer makushitaJunYusho = (Integer)results.get( 33 );
		Integer sandanmeYusho = (Integer)results.get( 34 );
		Integer sandanmeJunYusho = (Integer)results.get( 35 );
		Integer jonidanYusho = (Integer)results.get( 36 );
		Integer jonidanJunYusho = (Integer)results.get( 37 );
		Integer jonokuchiYusho = (Integer)results.get( 38 );
		Integer jonokuchiJunYusho = (Integer)results.get( 39 );
		Integer maezumoYusho = (Integer)results.get( 40 );
		Integer careerWins = (Integer)results.get( 41 );
		Integer careerLoses = (Integer)results.get( 42 );
		Integer careerForfeits = (Integer)results.get( 43 );
		
		info.setMakuuchiYusho(makuuchiYusho);
		info.setMakuuchiJunYusho(makuuchiJunYusho);
		info.setGinoSho(ginoSho);
		info.setShukunSho(shukunSho);
		info.setKantoSho(kantoSho);
		info.setJuryoYusho(juryoYusho);
		info.setJuryoJunYusho(juryoJunYusho);
		info.setMakushitaYusho(makushitaYusho);
		info.setMakushitaJunYusho(makushitaJunYusho);
		info.setSandanmeYusho(sandanmeYusho);
		info.setSandanmeJunYusho(sandanmeJunYusho);
		info.setJonidanYusho(jonidanYusho);
		info.setJonidanJunYusho(jonidanJunYusho);
		info.setJonokuchiYusho(jonokuchiYusho);
		info.setJonokuchiJunYusho(jonokuchiJunYusho);
		info.setMaeZumoYusho(maezumoYusho);
		
		Record careerRecord = new Record( careerWins, careerLoses, careerForfeits );
		info.setCareerRecord( careerRecord );
		
		String injuries = results.get( 73 ).toString();
		String[] listOfInjuries = injuries.split(",");
		
		for ( String injury : listOfInjuries ){
			
			Injury realInjury = Injury.convertFromString( injury );
			info.getInjuries().add( realInjury );
		}
		
		r.setRikishiInfo( info );
		
		// skills portion
		RikishiStats stats = new RikishiStats();
		
		Double oshi = ((Integer)results.get( 44 )).doubleValue();
		Double gake = ((Integer)results.get( 45 )).doubleValue();
		Double yotsu = ((Integer)results.get( 46 )).doubleValue();
		Double nage = ((Integer)results.get( 47 )).doubleValue();
		Double tsuki = ((Integer)results.get( 48 )).doubleValue();
		Double hiku = ((Integer)results.get( 49 )).doubleValue();
		Double defense = ((Integer)results.get( 50 )).doubleValue();
		Double overall = ((Integer)results.get( 51 )).doubleValue();
		Double tachiai = ((Integer)results.get( 52 )).doubleValue();
		Double leftArm = ((Integer)results.get( 53 )).doubleValue();
		Double rightArm = ((Integer)results.get( 54 )).doubleValue();
		Double upperBody = ((Integer)results.get( 55 )).doubleValue();
		Double lowerBody = ((Integer)results.get( 56 )).doubleValue();
		Double rightLeg = ((Integer)results.get( 57 )).doubleValue();
		Double leftLeg = ((Integer)results.get( 58 )).doubleValue();
		Double quickness = ((Integer)results.get( 59 )).doubleValue();
		Double edge = ((Integer)results.get( 60 )).doubleValue();
		Double gripBreak = ((Integer)results.get( 61 )).doubleValue();
		Double gripStrength = ((Integer)results.get( 62 )).doubleValue();
		Double secondaryStrength = ((Integer)results.get( 63 )).doubleValue();
		GRIP preferredGrip = GRIP.valueOf( results.get( 64 ).toString() );
		Double balance = ((Integer)results.get( 65 )).doubleValue();
		Double recovery = ((Integer)results.get( 66 )).doubleValue();
		Double potential = ((Integer)results.get( 67 )).doubleValue();
		
		stats.setOshi(oshi);
		stats.setGake(gake);
		stats.setYotsu(yotsu);
		stats.setNage(nage);
		stats.setTsuki(tsuki);
		stats.setHiku(hiku);
		stats.setDefense(defense);
		stats.setOverallSkill(overall);
		stats.setTachiAi(tachiai);
		stats.setLeftArm(leftArm);
		stats.setRightArm(rightArm);
		stats.setUpperBody(upperBody);
		stats.setLowerBody(lowerBody);
		stats.setRightLeg(rightLeg);
		stats.setLeftLeg(leftLeg);
		stats.setQuickness(quickness);
		stats.setEdgeTechnique(edge);
		stats.setGripBreak(gripBreak);
		stats.setGripAbility(gripStrength);
		stats.setSecondaryGripAbility(secondaryStrength);
		stats.setPreferredGrip(preferredGrip);
		stats.setBalanceControl(balance);
		stats.setRecovery(recovery);
		stats.setPotential(potential);
		
		r.setRikishiStats( stats );
		
		// mental portion
		RikishiTemperment temp = new RikishiTemperment();
		
		Double focus = ((Integer)results.get( 68 )).doubleValue();
		Double anger = ((Integer)results.get( 69 )).doubleValue();
		Double iq = ((Integer)results.get( 71 )).doubleValue();
		Double emotions = ((Integer)results.get( 72 )).doubleValue();
		Double drive = ((Integer)results.get( 70 )).doubleValue();
		
		temp.setAnger(anger);
		temp.setDrive(drive);
		temp.setEmotions(emotions);
		temp.setIq(iq);
		temp.setFocus(focus);
		
		r.setRikishiTemperment( temp );
		
		return r;
	}
}
