package com.nate.sumo.model.basho;

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
}
