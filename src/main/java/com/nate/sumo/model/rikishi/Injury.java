package com.nate.sumo.model.rikishi;

public class Injury
{

	public enum PART {
		NECK,
		EYE,
		RIGHT_ARM,
		LEFT_ARM,
		RIGHT_WRIST,
		LEFT_WRIST,
		RIGHT_CHEST,
		LEFT_CHEST,
		RIGHT_SHOULDER,
		LEFT_SHOULDER,
		RIGHT_HAND,
		LEFT_HAND,
		RIGHT_LEG,
		LEFT_LEG,
		RIGHT_KNEE,
		LEFT_KNEE,
		RIGHT_ANKLE,
		LEFT_ANKLE,
		RIGHT_FOOT,
		LEFT_FOOT,
		LOWER_BACK
	};
	
	private PART location;
	
	// how many times this has happened
	private Integer occurrence;
	
	// medical name
	private String description;
	
	// 100 is really bad condition
	private Integer effect;
	
	// days to heal
	private Integer prognosis;

	public String convertToStorageString(){
		String st = "";
		
		st += getLocation().name() + "|" + getOccurrence() + "|" + getDescription() + "|" + getEffect() + "|" + getPrognosis();
		
		return st;
	}
	
	public Injury convertFromString( String str ){
		
		String[] tokens = str.split( "|" );
		
		Injury injury = new Injury();
		injury.setLocation( PART.valueOf( tokens[0] ) );
		injury.setOccurrence( Integer.parseInt( tokens[1] ) );
		injury.setDescription( tokens[2] );
		injury.setEffect( Integer.parseInt( tokens[3] ) );
		injury.setPrognosis( Integer.parseInt( tokens[4] ) );
		
		return injury;
	}
	
	public PART getLocation()
	{
		return location;
	}

	public void setLocation( PART location )
	{
		this.location = location;
	}

	public Integer getOccurrence()
	{
		return occurrence;
	}

	public void setOccurrence( Integer occurrence )
	{
		this.occurrence = occurrence;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public Integer getEffect()
	{
		return effect;
	}

	public void setEffect( Integer effect )
	{
		this.effect = effect;
	}

	public Integer getPrognosis()
	{
		return prognosis;
	}

	public void setPrognosis( Integer prognosis )
	{
		this.prognosis = prognosis;
	}
	
}
