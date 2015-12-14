package com.nate.sumo.model.common;

public class Record
{

	private Integer wins = 0;
	private Integer loses = 0;
	private Integer forfeits = 0;
	
	public Record(){}
	
	public Record( Integer wins, Integer loses, Integer forfeits ){
		this.wins = wins;
		this.loses = loses;
		this.forfeits = forfeits;
	}
	
	public Integer getWins(){
		return wins;
	}
	
	public void setWins( Integer wins ){
		this.wins = wins;
	}
	
	public Integer getLoses(){
		return loses;
	}
	
	public void setLoses( Integer loses ){
		this.loses = loses;
	}
	
	public Integer getForfeits(){
		return forfeits;
	}
	
	public void setForfeits( Integer forfeits ){
		this.forfeits = forfeits;
	}
}
