package com.nate.tools;

import com.nate.sumo.model.basho.Kimarite;
import com.nate.sumo.model.basho.Rank;
import com.nate.sumo.model.common.Record;

public class MatchResult
{
	public Long opponenId;
	public Boolean win;
	public Record opponentRecord;
	public Kimarite kimarite;
	public Rank opponentRank;

	public MatchResult( Long opponenId, Boolean win )
	{
		this.opponenId = opponenId;
		this.win = win;
	}
	
	public MatchResult(){}

	public Long getOpponenId()
	{
		return opponenId;
	}

	public void setOpponenId( Long opponenId )
	{
		this.opponenId = opponenId;
	}

	public Boolean getWin()
	{
		return win;
	}

	public void setWin( Boolean win )
	{
		this.win = win;
	}

	public Record getOpponentRecord()
	{
		return opponentRecord;
	}

	public void setOpponentRecord( Record opponentRecord )
	{
		this.opponentRecord = opponentRecord;
	}

	public Kimarite getKimarite()
	{
		return kimarite;
	}

	public void setKimarite( Kimarite kimarite )
	{
		this.kimarite = kimarite;
	}

	public Rank getOpponentRank()
	{
		return opponentRank;
	}

	public void setOpponentRank( Rank opponentRank )
	{
		this.opponentRank = opponentRank;
	}
}