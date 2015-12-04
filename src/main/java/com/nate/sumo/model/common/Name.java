package com.nate.sumo.model.common;


public class Name {

	private String firstName_en;
	private String lastName_en;
	private String firstName_jp;
	private String lastName_jp;
	private String firstName_kanji;
	private String lastName_kanji;
	
	public Name(){}
	
	public Name( String firstName_en, String firstName_jp, String lastName_en, String lastName_jp, String firstName_kanji, String lastName_kanji ){
		this.firstName_en = firstName_en;
		this.firstName_jp = firstName_jp;
		this.lastName_en = lastName_en;
		this.lastName_jp = lastName_jp;
		this.firstName_kanji = firstName_kanji;
		this.lastName_kanji = lastName_kanji;
	}
	
	public Name( String firstName, String kanji ){
		this.firstName_en = firstName;
		this.firstName_kanji = kanji;
	}

	public String getFirstName_en() {
		return firstName_en;
	}

	public void setFirstName_en(String firstName_en) {
		this.firstName_en = firstName_en;
	}

	public String getLastName_en() {
		return lastName_en;
	}

	public void setLastName_en(String lastName_en) {
		this.lastName_en = lastName_en;
	}

	public String getFirstName_jp() {
		return firstName_jp;
	}

	public void setFirstName_jp(String firstName_jp) {
		this.firstName_jp = firstName_jp;
	}

	public String getLastName_jp() {
		return lastName_jp;
	}

	public void setLastName_jp(String lastName_jp) {
		this.lastName_jp = lastName_jp;
	}

	public String getFirstName_kanji() {
		return firstName_kanji;
	}

	public void setFirstName_kanji( String firstName_kanji) {
		this.firstName_kanji = firstName_kanji;
	}

	public String getLastName_kanji() {
		return lastName_kanji;
	}

	public void setLastName_kanji( String lastName_kanji) {
		this.lastName_kanji = lastName_kanji;
	}
	
}
