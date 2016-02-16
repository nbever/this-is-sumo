package com.nate.sumo.model.basho;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.nate.sumo.model.rikishi.Rikishi;

public class Banzuke {

	Map<Division, Map<Rank, Rikishi>> fullBanzuke;
	
	public Map<Division, Map<Rank,Rikishi>> getFullBanzuke(){
		if ( fullBanzuke == null ){
			fullBanzuke = new HashMap<Division, Map<Rank, Rikishi>>();
		}
		
		return fullBanzuke;
	}
	
	public static Banzuke build( Collection<Rikishi> rikishiList ){
		
		Banzuke banzuke = new Banzuke();
		
		for ( Rikishi rikishi : rikishiList ){
			
			Rank rank = rikishi.getRikishiInfo().getCurrentRank();
			
			Division division = Division.getDivisionForRank( rank.getRankClass() );
			
			Map<Rank, Rikishi> divisionMap = banzuke.getFullBanzuke().get( division );
			
			if ( divisionMap == null ){
				divisionMap = new HashMap<Rank, Rikishi>();
			}
			
			divisionMap.put( rank, rikishi );
			banzuke.getFullBanzuke().put( division, divisionMap );
		}
		
		return banzuke;
	}
}
