package com.nate.sumo.model.fight;

public class ActionResult {

	private float powerEffect = 0.0f;
	private float maxPowerEffect = 0.0f;
	private float medialBalanceEffect = 0.0f;
	private float lateralBalanceEffect = 0.0f;
	private float confidenceEffect = 0.0f;
	private float balanceFactorEffect = 0.0f;
	private float xPositionEffect = 0.0f;
	private float yPositionEffect = 0.0f;
	private float positionDirection = 0.0f;
	
	public float getPowerEffect() {
		return powerEffect;
	}
	public void setPowerEffect(float powerEffect) {
		this.powerEffect = powerEffect;
	}
	public float getMaxPowerEffect() {
		return maxPowerEffect;
	}
	public void setMaxPowerEffect(float maxPowerEffect) {
		this.maxPowerEffect = maxPowerEffect;
	}
	public float getMedialBalanceEffect() {
		return medialBalanceEffect;
	}
	public void setMedialBalanceEffect(float medialBalanceEffect) {
		this.medialBalanceEffect = medialBalanceEffect;
	}
	public float getLateralBalanceEffect() {
		return lateralBalanceEffect;
	}
	public void setLateralBalanceEffect(float lateralBalanceEffect) {
		this.lateralBalanceEffect = lateralBalanceEffect;
	}
	public float getConfidenceEffect() {
		return confidenceEffect;
	}
	public void setConfidenceEffect(float confidenceEffect) {
		this.confidenceEffect = confidenceEffect;
	}
	public float getBalanceFactorEffect() {
		return balanceFactorEffect;
	}
	public void setBalanceFactorEffect(float balanceFactorEffect) {
		this.balanceFactorEffect = balanceFactorEffect;
	}
	public float getPositionDirection(){
		return this.positionDirection;
	}
	public void setPositionDirection( float positionDirection ){
		this.positionDirection = positionDirection;
	}
	public float getxPositionEffect() {
		return xPositionEffect;
	}
	public void setxPositionEffect(float xPositionEffect) {
		this.xPositionEffect = xPositionEffect;
	}
	public float getyPositionEffect() {
		return yPositionEffect;
	}
	public void setyPositionEffect(float yPositionEffect) {
		this.yPositionEffect = yPositionEffect;
	}
	
}
