package com.nate.sumo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nate.sumo.model.basho.Banzuke;
import com.nate.sumo.model.rikishi.Rikishi;

public class RikishiCache
{

	private Banzuke currentBanzuke;
	private Map<Long, Rikishi> allRikishi;
	
	public RikishiCache(){}
	
	public Banzuke getBanzuke(){
		
		if ( currentBanzuke == null ){
			currentBanzuke = Banzuke.build( getAllRikishi().values() );
		}
		
		return currentBanzuke;
	}
	
	public Rikishi getRikishi( Long id )
	{
		return getAllRikishi().get( id );
	}
	
	public void clearCache(){
		currentBanzuke = null;
		allRikishi = null;
	}
	
	private Map<Long, Rikishi> getAllRikishi(){
		
		if ( allRikishi == null ){
			allRikishi = new HashMap<Long, Rikishi>();
			
			List<Rikishi> rikishi = Rikishi.findAll();
			
			for ( Rikishi riki : rikishi ){
				allRikishi.put( riki.getRikishiInfo().getId(), riki );
			}
		}
		
		return allRikishi;
	}
}
