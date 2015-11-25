package com.nate.sumo.model.fight;

public class Consequence {

	private Float energyChange;
	private Float mediaBalanceChange;
	private Float lateralBalanceChange;
	
	public Float getEnergyChange() {
		return energyChange;
	}
	
	public void setEnergyChange(Float energyChange) {
		this.energyChange = energyChange;
	}
	
	public Float getMediaBalanceChange() {
		return mediaBalanceChange;
	}
	
	public void setMediaBalanceChange(Float mediaBalanceChange) {
		this.mediaBalanceChange = mediaBalanceChange;
	}
	
	public Float getLateralBalanceChange() {
		return lateralBalanceChange;
	}
	
	public void setLateralBalanceChange(Float lateralBalanceChange) {
		this.lateralBalanceChange = lateralBalanceChange;
	}
}
