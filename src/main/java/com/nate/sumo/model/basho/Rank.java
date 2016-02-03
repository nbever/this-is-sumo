package com.nate.sumo.model.basho;

import com.nate.sumo.model.common.Name;

public class Rank {

	public static final Integer UNLIMITED = -1;
	
	public enum RankClass{
		YOKOZUNA( new Name( "Yokozuna", "横綱"), UNLIMITED, UNLIMITED, "Y" ),
		OZEKI( new Name( "Ozeki", "大関"), UNLIMITED, 2, "O" ),
		SEKIWAKE( new Name( "Sekiwake", "関脇"), 2, 1, "S" ),
		KOMUSUBI( new Name( "Komusubi", "小結"), 2, 1, "K" ),
		MAEGASHIRA( new Name( "Maegashira", "前頭"), 17, 16, "M" ),
		JURYO( new Name( "Juryo", "十両"), 16, 14, "J" ),
		MAKUSHITA( new Name( "Makushita", "幕下"), 60, 60, "Ms" ),
		SANDANME( new Name( "Sandanme", "三段目"), 100, 100, "Sd" ),
		JONIDAN( new Name( "Jonidan", "序二段"), 130, 100, "Jd" ),
		JONOKUCHI( new Name( "Jonokuchi", "序ノ口"), 50, 20, "Jk" ),
		MAE_ZUMO( new Name( "Mae-zumo", "前相撲"), UNLIMITED, UNLIMITED, "Mz" ),
		BANZUKE_GAI( new Name( "Banzuke-gai", "番付外" ), UNLIMITED, UNLIMITED, "Bg" );
		
		private Name name;
		private Integer max;
		private Integer preferred;
		private String abbreviation;
		
		private RankClass( Name aName, Integer max, Integer preferred, String abbreviation ){
			this.name = aName;
			this.max = max;
			this.preferred = preferred;
			this.abbreviation = abbreviation;
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
		
		public String getAbbreviation(){
			return abbreviation;
		}
		
		public static RankClass parseAbbr( String abbr ){
			
			if ( abbr.indexOf( MAE_ZUMO.getAbbreviation() ) != -1 ){
				return RankClass.MAE_ZUMO;
			}
			else if ( abbr.indexOf( JONOKUCHI.getAbbreviation() ) != -1 ){
				return RankClass.JONOKUCHI;
			}
			else if ( abbr.indexOf( JONIDAN.getAbbreviation() ) != -1 ){
				return RankClass.JONIDAN;
			}
			else if ( abbr.indexOf( SANDANME.getAbbreviation() ) != -1 ){
				return RankClass.SANDANME;
			}
			else if ( abbr.indexOf( MAKUSHITA.getAbbreviation() ) != -1 ){
				return RankClass.MAKUSHITA;
			}
			else if ( abbr.indexOf( JURYO.getAbbreviation() ) != -1 ){
				return RankClass.JURYO;
			}
			else if ( abbr.indexOf( MAEGASHIRA.getAbbreviation() ) != -1 ){
				return RankClass.MAEGASHIRA;
			}
			else if ( abbr.indexOf( KOMUSUBI.getAbbreviation() ) != -1 ){
				return RankClass.KOMUSUBI;
			}
			else if ( abbr.indexOf( SEKIWAKE.getAbbreviation() ) != -1 ){
				return RankClass.SEKIWAKE;
			}			
			else if ( abbr.indexOf( OZEKI.getAbbreviation() ) != -1 ){
				return RankClass.OZEKI;
			}
			else if ( abbr.indexOf( YOKOZUNA.getAbbreviation() ) != -1 ){
				return RankClass.YOKOZUNA;
			}
			else if ( abbr.indexOf( BANZUKE_GAI.getAbbreviation() ) != -1 ){
				return RankClass.BANZUKE_GAI;
			}
			else {
				return null;
			}
		}
	};
	
	public enum RankSide {
		EAST, WEST
	};
	
	private RankClass rankClass;
	private Integer rankNumber;
	private RankSide rankSide;
	
	public static Rank parseRank( String abbr ){
		
		RankSide rankSide = RankSide.EAST;
		RankClass clazz = RankClass.JONIDAN;
		Integer number = 0;
		
		String side = abbr.substring( abbr.length() - 1 );
		
		if ( side.equalsIgnoreCase( "e" ) ){
			rankSide = RankSide.EAST;
		}
		else if ( side.equalsIgnoreCase( "w" )){
			rankSide = RankSide.WEST;
		}
		else {
			rankSide = null;
		}
		
		clazz = RankClass.parseAbbr( abbr );
		
		Rank rank = new Rank( clazz, rankSide, number );
		
		if ( clazz == null ){
			return rank;
		}
		
		if ( clazz == RankClass.MAE_ZUMO || clazz == RankClass.BANZUKE_GAI ){
			rank = new Rank( clazz );
			return rank;
		}
		
		String numStr = abbr.substring( clazz.getAbbreviation().length() );
		
		if ( rankSide != null ){
			numStr = numStr.substring( 0, numStr.length() - 1 );
		}
		
		number = Integer.parseInt( numStr );
		rank = new Rank( clazz, rankSide, number );
		
		return rank;
	}
	
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
		this.rankClass = aClass;
	}
	
	public RankClass getRankClass(){
		if ( this.rankClass == null ){
			this.rankClass = RankClass.BANZUKE_GAI;
		}
		
		return this.rankClass;
	}
	
	public RankSide getRankSide(){
		
		if ( this.rankSide == null ){
			this.rankSide = RankSide.EAST;
		}
		
		return this.rankSide;
	}
	
	public Integer getRankNumber(){
		
		if ( this.rankNumber == null ){
			this.rankNumber = 1;
		}
		
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
