package com.nate.sumo.model.fight;

import java.util.LinkedList;

public class Route {

	private LinkedList<Pathway> pathways;
	
	public Route(){}
	
	public void addPathway( Pathway pathway ){
		getPathways().add( pathway );
	}

	public boolean routeCompleted(){
		return getPathways().isEmpty();
	}
	
	public Pathway getNextPath(){
		
		if ( routeCompleted() ){
			return null;
		}
		
		return getPathways().pop();
	}
	
	private LinkedList<Pathway> getPathways(){
		if ( pathways == null ){
			pathways = new LinkedList<Pathway>();
		}
		
		return pathways;
	}
}
