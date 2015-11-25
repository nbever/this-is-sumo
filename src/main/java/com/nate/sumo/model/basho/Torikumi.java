package com.nate.sumo.model.basho;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Torikumi {

	private Integer day;
	private Map<Division, List<Match>> schedule;
	
	public Torikumi( Integer day ){
		this.day = day;
	}
	
	public Integer getDay(){
		return day;
	}
	
	public Map<Division, List<Match>> getSchedule(){
		if ( schedule == null ){
			schedule = new HashMap<Division, List<Match>>();
		}
		
		return schedule;
	}
}
