package com.nate.sumo.model.rikishi;

import com.nate.sumo.model.fight.GRIP;

public class RikishiStats {
	
	// skill section
	private Double oshi = 500.0;
	private Double gake = 500.0;
	private Double nage = 500.0;
	private Double yotsu = 500.0;
	private Double hiku = 500.0;
	private Double tsuki = 500.0;
	private Double defense = 500.0;
	private Double overallSkill = 500.0;
	
	//strength
	private Double upperBody = 500.0;
	private Double lowerBody = 500.0;
	private Double rightArm = 500.0;
	private Double leftArm = 500.0;
	private Double rightLeg = 500.0;
	private Double leftLeg = 500.0;
	
	//other
	private Double edgeTechnique = 500.0;
	private Double quickness = 500.0;
	private Double gripBreak = 500.0;
	private Double gripAbility = 500.0;
	private Double secondaryGripAbility = 500.0;
	
	// 1-100
	private Double balanceControl = 50.0;
	private Double recovery = 50.0;
	
	// 1-2000
	private Double potential = 1000.0;
	
	private GRIP preferredGrip;

	public Double getOshi() {
		return oshi;
	}

	public void setOshi(Double oshi) {
		this.oshi = oshi;
	}

	public Double getNage() {
		return nage;
	}
	
	public void setNage(Double nage) {
		this.nage = nage;
	}
	
	public Double getGake(){
		return gake;
	}
	
	public void setGake( Double gake ){
		this.gake = gake;
	}

	public Double getYotsu() {
		return yotsu;
	}

	public void setYotsu(Double yotsu) {
		this.yotsu = yotsu;
	}

	public Double getHiku() {
		return hiku;
	}

	public void setHiku(Double hiku) {
		this.hiku = hiku;
	}

	public Double getTsuki() {
		return tsuki;
	}

	public void setTsuki(Double tsuki) {
		this.tsuki = tsuki;
	}

	public Double getUpperBody() {
		return upperBody;
	}

	public void setUpperBody(Double upperBody) {
		this.upperBody = upperBody;
	}

	public Double getLowerBody() {
		return lowerBody;
	}

	public void setLowerBody(Double lowerBody) {
		this.lowerBody = lowerBody;
	}

	public Double getRightArm() {
		return rightArm;
	}

	public void setRightArm(Double rightArm) {
		this.rightArm = rightArm;
	}

	public Double getLeftArm() {
		return leftArm;
	}

	public void setLeftArm(Double leftArm) {
		this.leftArm = leftArm;
	}

	public Double getRightLeg() {
		return rightLeg;
	}

	public void setRightLeg(Double rightLeg) {
		this.rightLeg = rightLeg;
	}

	public Double getLeftLeg() {
		return leftLeg;
	}

	public void setLeftLeg(Double leftLeg) {
		this.leftLeg = leftLeg;
	}

	public Double getQuickness() {
		return quickness;
	}

	public void setQuickness(Double quickness) {
		this.quickness = quickness;
	}

	public Double getEdgeTechnique() {
		return edgeTechnique;
	}

	public void setEdgeTechnique(Double edgeTechnique) {
		this.edgeTechnique = edgeTechnique;
	}

	public Double getGripBreak() {
		return gripBreak;
	}

	public void setGripBreak(Double gripBreak) {
		this.gripBreak = gripBreak;
	}

	public Double getGripAbility() {
		return gripAbility;
	}

	public void setGripAbility(Double gripAbility) {
		this.gripAbility = gripAbility;
	}

	public Double getSecondaryGripAbility() {
		return secondaryGripAbility;
	}

	public void setSecondaryGripAbility(Double secondaryGripAbility) {
		this.secondaryGripAbility = secondaryGripAbility;
	}

	public GRIP getPreferredGrip() {
		return preferredGrip;
	}

	public void setPreferredGrip(GRIP preferredGrip) {
		this.preferredGrip = preferredGrip;
	}

	public Double getDefense()
	{
		return defense;
	}

	public void setDefense( Double defense )
	{
		this.defense = defense;
	}

	public Double getRecovery()
	{
		return recovery;
	}

	public void setRecovery( Double recovery )
	{
		this.recovery = recovery;
	}

	public Double getBalanceControl()
	{
		return balanceControl;
	}

	public void setBalanceControl( Double balanceControl )
	{
		this.balanceControl = balanceControl;
	}
	
	public Double getPotential(){
		return this.potential;
	}
	
	public void setPotential( Double somePotential ){
		this.potential = somePotential;
	}

	public Double getOverallSkill()
	{
		return overallSkill;
	}

	public void setOverallSkill( Double overalSkill )
	{
		this.overallSkill = overalSkill;
	}
	
	@Override
	public String toString()
	{
		String str = "";
		
		str += "Overall: " + getOverallSkill() + "\n";
		str += "Upper Body: " + getUpperBody() + "\n";
		str += "Lower Body: " + getLowerBody() + "\n";
		str += "Right Arm: " + getRightArm() + "\n";
		str += "Left Arm: " + getLeftArm() + "\n";
		str += "Right Leg: " + getRightLeg() + "\n";
		str += "Left Leg: " + getLeftLeg() + "\n";
		str += "Quickness: " + getQuickness() + "\n";
		str += "Balance: " + getBalanceControl() + "\n";
		str += "Potential: " + getPotential() + "\n";
		str += "Recovery: " + getRecovery() + "\n";
		str += "Edge Ability: " + getEdgeTechnique() + "\n\n";
		str += "Oshi: " + getOshi() + "\n";
		str += "Tsuki: " + getTsuki() + "\n";
		str += "Yotsu: " + getYotsu() + "\n";
		str += "Nage: " + getNage() + "\n";
		str += "Defense: " + getDefense() + "\n";
		str += "Hiku: " + getHiku() + "\n";
		str += "Gake: " + getGake() + "\n\n";
		str += "Grip Ability: " + getGripAbility() + "\n";
		str += "Secondary Grip: " + getSecondaryGripAbility() + "\n";
		str += "Grip Break: " + getGripBreak() + "\n";
		
		return str;
	}
}
