package com.nate.sumo.display.widgets;

public class Action {

	private String actionKey;
	private String modifier;
	private Widget source;
	private Object item;
	
	public Action(){}

	public String getActionKey() {
		return actionKey;
	}

	public void setActionKey(String actionKey) {
		this.actionKey = actionKey;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Widget getSource() {
		return source;
	}

	public void setSource(Widget source) {
		this.source = source;
	}
	
	public Object getItem(){
		return item;
	}
	
	public void setItem( Object anItem ){
		item = anItem;
	}
	
	
}
