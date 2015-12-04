package com.nate.sumo.model.rikishi;

import java.util.Date;

import com.nate.sumo.model.basho.Rank;
import com.nate.sumo.model.common.Height;
import com.nate.sumo.model.common.Location;
import com.nate.sumo.model.common.Name;
import com.nate.sumo.model.common.Weight;

public class RikishiInfo {

	private Long id;
	private Name realName;
	private Name shikona;
	private Location hometown;
	
	private Height height;
	private Weight weight;
	private Date birthday;
	private Integer age;
	private Rank highestRank;
	private Date hatsuBasho;
	
	public RikishiInfo(){}

	public Long getId(){
		return id;
	}
	
	public void setId( Long anId ){
		id = anId;
	}
	
	public Name getRealName() {
		return realName;
	}

	public void setRealName(Name realName) {
		this.realName = realName;
	}

	public Name getShikona() {
		return shikona;
	}

	public void setShikona(Name shikona) {
		this.shikona = shikona;
	}

	public Location getHometown() {
		return hometown;
	}

	public void setHometown(Location hometown) {
		this.hometown = hometown;
	}
	
	public Rank getHighestRank(){
		return highestRank;
	}
	
	public void setHighestRank( Rank rank ){
		highestRank = rank;
	}
	
	public Date getHatsuBasho(){
		return hatsuBasho;
	}
	
	public void setHatsuBasho( Date date ){
		hatsuBasho = date;
	}

	public Height getHeight() {
		return height;
	}

	public void setHeight(Height height) {
		this.height = height;
	}

	public Weight getWeight() {
		return weight;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
	
		return getShikona().getFirstName_en() + " " + getShikona().getLastName_en() + " (" + getRealName().getFirstName_en() + " " + getRealName().getLastName_en();
	}
	
}
