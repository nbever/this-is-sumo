package com.nate.sumo.model.basho;

import com.nate.sumo.model.common.Name;

public class Rank {

	public static final Integer UNLIMITED = -1;
	
	public enum RankClass{
		YOKOZUNA( new Name( "Yokozuna", "横綱"), UNLIMITED, UNLIMITED ),
		OZEKI( new Name( "Ozeki", "大関"), UNLIMITED, 4 ),
		SEKIWAKE( new Name( "Sekiwake", "関脇"), 4, 2 ),
		KOMUSUBI( new Name( "Komusubi", "小結"), 4, 2 ),
		MAEGASHIRA( new Name( "Maegashira", "前頭"), 17, 16 ),
		JURYO( new Name( "Juryo", "十両"), 16, 14 ),
		MAKUSHITA( new Name( "Makushita", "幕下"), 60, 60 ),
		SANDANME( new Name( "Sandanme", "三段目"), 100, 100 ),
		JONIDAN( new Name( "Jonidan", "序二段"), 130, 100 ),
		JONOKUCHI( new Name( "Jonokuchi", "序ノ口"), 50, 20 ),
		MAE_ZUMO( new Name( "Mae-zumo", "前相撲"), UNLIMITED, UNLIMITED );
		
		private Name name;
		private Integer max;
		private Integer preferred;
		
		private RankClass( Name aName, Integer max, Integer preferred ){
			this.name = aName;
			this.max = max;
			this.preferred = preferred;
		}
		
		public Name getName(){
			return name;
		}
		
		public Integer getMax(){
			return max;
		}
		
		public Integer getPreferred(){
			return preferred;
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
		
		this.rankNumber = aNumber;
	}
	
	public Rank ( RankClass aClass ){
		this.rankClass = aClass;
	}
	
	public Rank( RankClass aClass, Integer aNumber ){
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
