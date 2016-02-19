package com.nate.sumo.model.basho;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.nate.sumo.model.rikishi.Rikishi;

public class Banzuke {

	Map<Division, Map<Rank, Rikishi>> fullBanzuke;
	
	public Map<Division, Map<Rank,Rikishi>> getFullBanzuke(){
		if ( fullBanzuke == null ){
			fullBanzuke = new HashMap<Division, Map<Rank, Rikishi>>();
		}
		
		return fullBanzuke;
	}
	
	public Rikishi getByRank( Division division, Rank rank ){
		
		Map<Rank, Rikishi> rankMap = getFullBanzuke().get( division );
		
		Optional<Rank> fRank = rankMap.keySet().stream().filter( rankKey -> {
			Boolean match = rankKey.equals( rank );
			Rikishi mR = null;
			
			if ( match == Boolean.TRUE ){
				mR = rankMap.get( rankKey );
			}
			return match;
		}).findFirst();
		
		if ( !fRank.isPresent() ){
			return null;
		}
		
		Rikishi rikishi = rankMap.get( fRank.get() );
		
		return rikishi;
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
