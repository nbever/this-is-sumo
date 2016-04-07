package com.nate.sumo.model.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VectorAnimationChain {

	private List<List<VectorAnimation>> chain;
	private boolean started = false;
	private boolean stopped = false;
	private int index = 0;
	
	public VectorAnimationChain(){}
	
	public void addChainLink( List<VectorAnimation> anims ){
		
		if ( !started ){
			getChain().add( anims );
		}
	}
	
	private List<List<VectorAnimation>> getChain(){
		if ( chain == null ){
			chain = new ArrayList<List<VectorAnimation>>();
		}
		
		return chain;
	}
	
	public void advance(){
		
		if ( stopped ){
			return;
		}
		
		started = true;
		
		List<VectorAnimation> step = getChain().get( index );
		
		step.stream().forEach( va -> {
			va.advance();
		});
		
		Optional<VectorAnimation> runningAnim = 
			step.stream().filter( VectorAnimation::hasStopped ).findAny();
		
		if ( !runningAnim.isPresent() ){
			index++;
			
			if ( getChain().size() >= index ){
				stopped = true;
			}
		}
	}
	
	public void restart(){
		stopped = false;
		index = 0;
	}
}
