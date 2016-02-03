package com.nate.sumo.model.rikishi;

import java.util.ArrayList;
import java.util.List;

import com.nate.sumo.DatabaseConstants;
import com.nate.sumo.model.animation.AnimationMap;
import com.nate.sumo.model.appearence.AppearenceMap;
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
		
		String dnaUpdate = "INSERT INTO " + DatabaseConstants.DNA_BASE + suffix + " SET ";
		dnaUpdate += DatabaseConstants.C_WINNING + "='" + getRikishiTendencies().convertScenarioToString( SCENARIOS.WINNING.getScenario() ) + "',";
		dnaUpdate += DatabaseConstants.C_LOSING + "='" + getRikishiTendencies().convertScenarioToString( SCENARIOS.LOSING.getScenario() ) + "',";
		dnaUpdate += DatabaseConstants.C_EDGE_WINNING + "='" + getRikishiTendencies().convertScenarioToString( SCENARIOS.EDGE_VICTORY.getScenario() ) + "',";
		dnaUpdate += DatabaseConstants.C_EDGE_LOSING + "='" + getRikishiTendencies().convertScenarioToString( SCENARIOS.EDGE_DANGER.getScenario() ) + "',";
		dnaUpdate += DatabaseConstants.C_TACHI_AI + "='" + getRikishiTendencies().convertScenarioToString( SCENARIOS.TACHI_AI.getScenario() ) + "'";
		
		dnaUpdate += " WHERE " + DatabaseConstants.C_RIKISHI_ID + "=" + getRikishiInfo().getId();
		
		updates.add( dnaUpdate );
		
		//appearence
		String appUpdate = "INSERT INTO " + DatabaseConstants.LOOK_BASE + suffix + " SET ";
		appUpdate += DatabaseConstants.C_MAWASHI_COLOR + "='" + getAppearenceMap().colorToString( getAppearenceMap().getMawashiColor() ) + "',";
		appUpdate += DatabaseConstants.C_MAWASHI_TXT + "='" + getAppearenceMap().getMawashiTxt() + "',";
		appUpdate += DatabaseConstants.C_MAWASHI_MODEL + "='" + getAppearenceMap().getMawashiModel() + "',";
		appUpdate += DatabaseConstants.C_BODY_MODEL + "='" + getAppearenceMap().getBodyModel() + "',";
		appUpdate += DatabaseConstants.C_HEAD_MODEL + "='" + getAppearenceMap().getHeadModel() + "',";
		appUpdate += DatabaseConstants.C_BODY_TXT + "='" + getAppearenceMap().getBodyTxt() + "',";
		appUpdate += DatabaseConstants.C_HEAD_TXT + "='" + getAppearenceMap().getHeadTxt() + "',";
		appUpdate += DatabaseConstants.C_HAIR_COLOR + "='" + getAppearenceMap().colorToString( getAppearenceMap().getHairColor() ) + "',";
		appUpdate += DatabaseConstants.C_HAIR_TXT + "='" + getAppearenceMap().getHairTxt() + "',";
		appUpdate += DatabaseConstants.C_HAIR_MODEL + "='" + getAppearenceMap().getHairModel() + ",'";
		appUpdate += DatabaseConstants.C_KESHO_MODEL + "='" + getAppearenceMap().getKeshoModel() + "',";
		appUpdate += DatabaseConstants.C_KESHO_TXT + "='" + getAppearenceMap().getKeshoTxt() + "',";
		appUpdate += DatabaseConstants.C_PORTRAIT + "='" + getAppearenceMap().getPortrait() + "',";
		appUpdate += DatabaseConstants.C_SKIN_TONE + "='" + getAppearenceMap().colorToString( getAppearenceMap().getSkinTone() ) + "' WHERE " +
			DatabaseConstants.C_RIKISHI_ID + '=' + getRikishiInfo().getId();
		
		updates.add( appUpdate );
		
		
		if ( getAnimationMap() != null ){
			//animations
			String animUpdate = "INSERT INTO " + DatabaseConstants.ANIMATION_BASE + suffix + " SET ";
			animUpdate += DatabaseConstants.C_ANIMATIONS + "='" + getAnimationMap().toString() + "' WHERE " +
				DatabaseConstants.C_RIKISHI_ID + "=" + getRikishiInfo().getId();
			
			updates.add( animUpdate );
			
		}
		
		return updates;
	}
}
