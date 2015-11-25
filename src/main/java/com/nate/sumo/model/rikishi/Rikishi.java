package com.nate.sumo.model.rikishi;

import com.nate.sumo.model.animation.AnimationMap;
import com.nate.sumo.model.appearence.AppearenceMap;

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
	
	
}
