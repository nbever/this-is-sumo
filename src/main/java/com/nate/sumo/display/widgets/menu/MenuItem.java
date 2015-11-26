package com.nate.sumo.display.widgets.menu;

public class MenuItem
{
	private String text;
	private Object object;
	private boolean enabled;
	
	public MenuItem( String someText ){
		text = someText;
	}
	
	public MenuItem( String someText, Object something ){
		this( someText );
		object = something;
	}

	public String getText()
	{
		return text;
	}

	public void setText( String text )
	{
		this.text = text;
	}
	
	public Object getObject(){
		return object;
	}
	
	public void setObject( Object something ){
		object = something;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled( boolean enabled )
	{
		this.enabled = enabled;
	}
	
	
}
