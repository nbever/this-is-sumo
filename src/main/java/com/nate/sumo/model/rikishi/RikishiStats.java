package com.nate.sumo.model.rikishi;

import com.nate.sumo.model.fight.GRIP;

public class RikishiStats {

	// skill section
	private Integer oshi;
	private Integer gake;
	private Integer nage;
	private Integer yorikiri;
	private Integer hatakikomi;
	private Integer tsuki;
	
	//strength
	private Integer upperBody;
	private Integer lowerBody;
	private Integer rightArm;
	private Integer leftArm;
	private Integer rightLeg;
	private Integer leftLeg;
	
	//other
	private Integer quickness;
	private Integer edgeTechnique;
	private Integer gripBreak;
	private Integer gripAbility;
	private Integer secondaryGripAbility;
	
	private GRIP preferredGrip;

	public Integer getOshi() {
		return oshi;
	}

	public void setOshi(Integer oshi) {
		this.oshi = oshi;
	}

	public Integer getNage() {
		return nage;
	}
	
	public void setNage(Integer nage) {
		this.nage = nage;
	}
	
	public Integer getGake(){
		return gake;
	}
	
	public void setGake( Integer gake ){
		this.gake = gake;
	}

	public Integer getYorikiri() {
		return yorikiri;
	}

	public void setYorikiri(Integer yorikiri) {
		this.yorikiri = yorikiri;
	}

	public Integer getHatakikomi() {
		return hatakikomi;
	}

	public void setHatakikomi(Integer hatakikomi) {
		this.hatakikomi = hatakikomi;
	}

	public Integer getTsuki() {
		return tsuki;
	}

	public void setTsuki(Integer tsuki) {
		this.tsuki = tsuki;
	}

	public Integer getUpperBody() {
		return upperBody;
	}

	public void setUpperBody(Integer upperBody) {
		this.upperBody = upperBody;
	}

	public Integer getLowerBody() {
		return lowerBody;
	}

	public void setLowerBody(Integer lowerBody) {
		this.lowerBody = lowerBody;
	}

	public Integer getRightArm() {
		return rightArm;
	}

	public void setRightArm(Integer rightArm) {
		this.rightArm = rightArm;
	}

	public Integer getLeftArm() {
		return leftArm;
	}

	public void setLeftArm(Integer leftArm) {
		this.leftArm = leftArm;
	}

	public Integer getRightLeg() {
		return rightLeg;
	}

	public void setRightLeg(Integer rightLeg) {
		this.rightLeg = rightLeg;
	}

	public Integer getLeftLeg() {
		return leftLeg;
	}

	public void setLeftLeg(Integer leftLeg) {
		this.leftLeg = leftLeg;
	}

	public Integer getQuickness() {
		return quickness;
	}

	public void setQuickness(Integer quickness) {
		this.quickness = quickness;
	}

	public Integer getEdgeTechnique() {
		return edgeTechnique;
	}

	public void setEdgeTechnique(Integer edgeTechnique) {
		this.edgeTechnique = edgeTechnique;
	}

	public Integer getGripBreak() {
		return gripBreak;
	}

	public void setGripBreak(Integer gripBreak) {
		this.gripBreak = gripBreak;
	}

	public Integer getGripAbility() {
		return gripAbility;
	}

	public void setGripAbility(Integer gripAbility) {
		this.gripAbility = gripAbility;
	}

	public Integer getSecondaryGripAbility() {
		return secondaryGripAbility;
	}

	public void setSecondaryGripAbility(Integer secondaryGripAbility) {
		this.secondaryGripAbility = secondaryGripAbility;
	}

	public GRIP getPreferredGrip() {
		return preferredGrip;
	}

	public void setPreferredGrip(GRIP preferredGrip) {
		this.preferredGrip = preferredGrip;
	}
	
	
}
