package com.nate.sumo.model.common;

import java.util.List;

public class Name {

	private String firstName_en;
	private String lastName_en;
	private String firstName_jp;
	private String lastName_jp;
	private List<Integer> firstName_kanji;
	private List<Integer> lastName_kanji;
	
	public Name(){}
	
	public Name( String firstName, List<Integer> kanji ){
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

	public List<Integer> getFirstName_kanji() {
		return firstName_kanji;
	}

	public void setFirstName_kanji(List<Integer> firstName_kanji) {
		this.firstName_kanji = firstName_kanji;
	}

	public List<Integer> getLastName_kanji() {
		return lastName_kanji;
	}

	public void setLastName_kanji(List<Integer> lastName_kanji) {
		this.lastName_kanji = lastName_kanji;
	}
	
	
	
}
