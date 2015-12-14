package com.nate.sumo.model.basho;

import com.nate.sumo.model.rikishi.Rikishi;

public class Match {

	private Rank eastRank;
	private Rank westRank;
	private Rikishi eastRikishi;
	private Rikishi westRikishi;
	private Rikishi winner;
	private Kimarite kimarite;
	
	public Match( Rank eastRank, Rikishi eastRikishi, Rank westRank, Rikishi westRikishi ){
		this.eastRank = eastRank;
		this.westRank = westRank;
		this.eastRikishi = eastRikishi;
		this.westRikishi = westRikishi;
	}
	
	public Match(){}
	
	public Rank getEastRank(){
		return this.eastRank;
	}
	
	public Rank getWestRank(){
		return this.westRank;
	}
	
	public Rikishi getEastRikishi(){
		return eastRikishi;
	}
	
	public Rikishi getWestRikishi(){
		return westRikishi;
	}
	
	public void setWinner( Rikishi winner, Kimarite kimarite ){
		this.winner = winner;
		this.kimarite = kimarite;
	}
	
	public Rikishi getWinner(){
		return winner;
	}
	
	public Kimarite getKimarite(){
		return kimarite;
	}
}
