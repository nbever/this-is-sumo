package com.nate.sumo.model.basho;

import java.util.Arrays;

import com.nate.sumo.model.common.Name;

public class Rank {

	public enum RankClass{
		YOKOZUNA( new Name( "Yokozuna", Arrays.asList( 12, 3 )) ),
		OZEKI( new Name( "Ozeki", Arrays.asList( 1 )) ),
		SEKIWAKE( new Name( "Sekiwake", Arrays.asList( 3 )) ),
		KOMUSUBI( new Name( "Komusubi", Arrays.asList( 4 )) ),
		MAEGASHIRA( new Name( "Maegashira", Arrays.asList( 5 )) ),
		JURYO( new Name( "Juryo", Arrays.asList( 1 )) ),
		MAKUSHITA( new Name( "Makushita", Arrays.asList( 1 )) ),
		SANDANME( new Name( "Sandanme", Arrays.asList( 2 )) ),
		JONIDAN( new Name( "Jonidan", Arrays.asList( 2 )) ),
		JONOKUCHI( new Name( "Jonokuchi", Arrays.asList( 2 )) ),
		MAE_ZUMO( new Name( "Mae-zumo", Arrays.asList( 2 )) );
		
		private Name name;
		
		private RankClass( Name aName ){
			this.name = aName;
		}
		
		public Name getName(){
			return name;
		}
	};
	
	public enum RankSide {
		EAST, WEST
	};
	
	private RankClass rankClass;
	private Integer rankNumber;
	private RankSide rankSide;
	
	public Rank ( RankClass aClass, RankSide aSide, Integer aNumber ){
		this.rankClass = aClass;
		this.rankSide = aSide;
		
		if ( this.rankClass.equals( RankClass.MAEGASHIRA ) && aNumber > 17 ){
			aNumber = 17;
		}
		
		this.rankNumber = aNumber;
	}
	
	public RankClass getRankClass(){
		return this.rankClass;
	}
	
	public RankSide getRankSide(){
		return this.rankSide;
	}
	
	public Integer getRankNumber(){
		return this.rankNumber;
	}
	
	@Override
	public boolean equals(Object obj) {
	
		if ( !( obj instanceof Rank ) ){
			return false;
		}
		
		Rank oRank = (Rank)obj;
		
		return ( getRankClass().equals( oRank.getRankClass() ) &&
				 getRankSide().equals( oRank.getRankSide() ) &&
				 getRankNumber().equals( oRank.getRankNumber() ) );
	}
	
	@Override
	public String toString() {
	
		return getRankSide().name().substring( 0, 1 ) + " " + getRankClass().getName().getFirstName_en() + " " + getRankNumber();
	}
}
