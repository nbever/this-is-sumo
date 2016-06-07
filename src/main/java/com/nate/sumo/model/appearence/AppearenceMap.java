package com.nate.sumo.model.appearence;

import com.nate.model.Quaternarion;

public class AppearenceMap {

	private String mawashiModel = "/base_mawashi.md5mesh";
	private String mawashiTxt = "default_mawashi_tex.png";
	private Quaternarion mawashiColor = new Quaternarion( 0.9f, 0.1f, 0.1f, 1.0f );
	
	private String bodyModel = "/base_sumo.md5mesh";
	private String bodyTxt = "default_sumo_tex.png";
	private Quaternarion skinTone;
	
	private String hairModel =  "/base_hair.md5mesh";
	private String hairTxt;
	private Quaternarion hairColor = new Quaternarion( 0.0f, 0.0f, 0.0f, 1.0f );
	
	private String headModel = "/base_head.md5mesh";
	private String headTxt = "default_head_tex.png";
	
	private String keshoModel;
	private String keshoTxt;
	
	private String portrait;
	
	public AppearenceMap(){
		
	}

	public String colorToString( Quaternarion color ){
		return color.getX() + "," + color.getY() + "," + color.getZ() + "," + color.getW();
	}
	
	public Quaternarion stringToQuaternarion( String colorString ){
		String[] sections = colorString.split( "," );
		
		int r = Integer.parseInt( sections[0] );
		int g = Integer.parseInt( sections[1] );
		int b = Integer.parseInt( sections[2] );
		int a = 255;
		
		if ( sections.length > 3 ){
			a = Integer.parseInt( sections[3] );
		}
		
		Quaternarion color = new Quaternarion( r, g, b, a );
		
		return color;
	}
	
	public String getMawashiModel()
	{
		return mawashiModel;
	}

	public void setMawashiModel( String mawashiModel )
	{
		this.mawashiModel = mawashiModel;
	}

	public String getMawashiTxt()
	{
		return mawashiTxt;
	}

	public void setMawashiTxt( String mawashiTxt )
	{
		this.mawashiTxt = mawashiTxt;
	}

	public Quaternarion getMawashiColor()
	{
		return mawashiColor;
	}

	public void setMawashiColor( Quaternarion mawashiQuaternarion )
	{
		this.mawashiColor = mawashiQuaternarion;
	}

	public String getBodyModel()
	{
		return bodyModel;
	}

	public void setBodyModel( String bodyModel )
	{
		this.bodyModel = bodyModel;
	}

	public String getBodyTxt()
	{
		return bodyTxt;
	}

	public void setBodyTxt( String bodyTxt )
	{
		this.bodyTxt = bodyTxt;
	}

	public String getHairModel()
	{
		return hairModel;
	}

	public void setHairModel( String hairModel )
	{
		this.hairModel = hairModel;
	}

	public String getHairTxt()
	{
		return hairTxt;
	}

	public void setHairTxt( String hairTxt )
	{
		this.hairTxt = hairTxt;
	}

	public Quaternarion getHairColor()
	{
		return hairColor;
	}

	public void setHairColor( Quaternarion hairQuaternarion )
	{
		this.hairColor = hairQuaternarion;
	}

	public String getKeshoModel()
	{
		return keshoModel;
	}

	public void setKeshoModel( String keshoModel )
	{
		this.keshoModel = keshoModel;
	}

	public String getKeshoTxt()
	{
		return keshoTxt;
	}

	public void setKeshoTxt( String keshoTxt )
	{
		this.keshoTxt = keshoTxt;
	}

	public String getPortrait()
	{
		return portrait;
	}

	public void setPortrait( String portrait )
	{
		this.portrait = portrait;
	}

	public String getHeadModel()
	{
		return headModel;
	}

	public void setHeadModel( String headModel )
	{
		this.headModel = headModel;
	}

	public String getHeadTxt()
	{
		return headTxt;
	}

	public void setHeadTxt( String headTxt )
	{
		this.headTxt = headTxt;
	}

	public Quaternarion getSkinTone()
	{
		return skinTone;
	}

	public void setSkinTone( Quaternarion skinTone )
	{
		this.skinTone = skinTone;
	}
}
