package com.nate.sumo.model.appearence;

import java.awt.Color;

public class AppearenceMap {

	private String mawashiModel;
	private String mawashiTxt;
	private Color mawashiColor;
	
	private String bodyModel;
	private String bodyTxt;
	private Color skinTone;
	
	private String hairModel;
	private String hairTxt;
	private Color hairColor;
	
	private String headModel;
	private String headTxt;
	
	private String keshoModel;
	private String keshoTxt;
	
	private String portrait;
	
	public AppearenceMap(){}

	public String colorToString( Color color ){
		return color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "," + color.getAlpha();
	}
	
	public Color stringToColor( String colorString ){
		String[] sections = colorString.split( "," );
		
		int r = Integer.parseInt( sections[0] );
		int g = Integer.parseInt( sections[1] );
		int b = Integer.parseInt( sections[2] );
		int a = 255;
		
		if ( sections.length > 3 ){
			a = Integer.parseInt( sections[3] );
		}
		
		Color color = new Color( r, g, b, a );
		
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

	public Color getMawashiColor()
	{
		return mawashiColor;
	}

	public void setMawashiColor( Color mawashiColor )
	{
		this.mawashiColor = mawashiColor;
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

	public Color getHairColor()
	{
		return hairColor;
	}

	public void setHairColor( Color hairColor )
	{
		this.hairColor = hairColor;
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

	public Color getSkinTone()
	{
		return skinTone;
	}

	public void setSkinTone( Color skinTone )
	{
		this.skinTone = skinTone;
	}
}
