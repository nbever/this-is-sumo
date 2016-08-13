package com.nate.sumo.display.widgets;

import java.util.List;

public interface SequenceIf {

	public enum SEQUENCE_KEY{ TOWARD, AWAY, UP, DOWN, X, Y, A, B, RANDOM };
	
	public List<SEQUENCE_KEY> getSequence();
}
