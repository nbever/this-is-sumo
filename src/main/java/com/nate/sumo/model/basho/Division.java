package com.nate.sumo.model.basho;

import java.util.Arrays;
import java.util.List;

import com.nate.sumo.model.basho.Rank.RankClass;
import com.nate.sumo.model.common.Name;

public enum Division {

	MAKUNOUCHI( new Name( "Makunouchi", "幕内"), Arrays.asList( RankClass.YOKOZUNA, RankClass.OZEKI, RankClass.SEKIWAKE, RankClass.KOMUSUBI, RankClass.MAEGASHIRA )),
	JURYO( new Name( "Juryo", "十両"), Arrays.asList( RankClass.JURYO )),
	MAKUSHITA( new Name( "Makushita", "幕下"), Arrays.asList( RankClass.MAKUSHITA )),
	SANDANME( new Name( "Sandanme", "三段目"), Arrays.asList( RankClass.SANDANME )),
	JONIDAN( new Name( "Jonidan", "序二段" ), Arrays.asList( RankClass.JONIDAN )),
	JONOKUCHI( new Name( "Jonokuchi", "序ノ口"), Arrays.asList( RankClass.JONOKUCHI )),
	MAE_ZUMO( new Name( "Mae-zumo", "前相撲"), Arrays.asList( RankClass.MAE_ZUMO ));
	
	private Name name;
	private List<RankClass> rankClasses;
	
	private Division( Name aName, List<RankClass> classesIncluded ){
		name = aName;
		rankClasses = classesIncluded;
	}
	
	public Name getName(){
		return name;
	}
	
	public List<RankClass> getClassesIncluded(){
		return rankClasses;
	}
}
