package com.nate.sumo.model.rikishi;

import java.util.Date;
import java.util.List;

import com.nate.sumo.model.basho.Rank;
import com.nate.sumo.model.common.Height;
import com.nate.sumo.model.common.Location;
import com.nate.sumo.model.common.Name;
import com.nate.sumo.model.common.Record;
import com.nate.sumo.model.common.Weight;

public class RikishiInfo {

	private Long id;
	private Name realName;
	private Name shikona;
	private Name university;
	private Location hometown;
	private Heya heya;
	
	private Height height;
	private Weight weight;
	private Date birthday;
	private Integer age;
	private Rank highestRank;
	private Rank currentRank;
	private Date hatsuBasho;
	
	private Integer makuuchiYusho;
	private Integer makuuchiJunYusho;
	private Integer ginoSho;
	private Integer shukunSho;
	private Integer kantoSho;
	private Integer juryoYusho;
	private Integer juryoJunYusho;
	private Integer makushitaYusho;
	private Integer makushitaJunYusho;
	private Integer sandanmeYusho;
	private Integer sandanmeJunYusho;
	private Integer jonidanYusho;
	private Integer jonidanJunYusho;
	private Integer jonokuchiYusho;
	private Integer jonokuchiJunYusho;
	private Integer maeZumoYusho;
	
	private List<Injury> injuries;
	
	private Record careerRecord;
	
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
	
	public Name getUniversity(){
		return this.university;
	}
	
	public void setuniversity( Name aName ){
		this.university = aName;
	}

	public Location getHometown() {
		return hometown;
	}

	public void setHometown(Location hometown) {
		this.hometown = hometown;
	}
	
	public Heya getHeya(){
		return heya;
	}
	
	public void setHeya( Heya aheya ){
		this.heya = aheya;
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
	
	public Record getCareerRecord(){
		return careerRecord;
	}
	
	public void setCareerRecord( Record record ){
		careerRecord = record;
	}

	public Rank getCurrentRank()
	{
		return currentRank;
	}

	public void setCurrentRank( Rank currentRank )
	{
		this.currentRank = currentRank;
	}

	public Integer getMakuuchiYusho()
	{
		return makuuchiYusho;
	}

	public void setMakuuchiYusho( Integer makuuchiYusho )
	{
		this.makuuchiYusho = makuuchiYusho;
	}

	public Integer getMakuuchiJunYusho()
	{
		return makuuchiJunYusho;
	}

	public void setMakuuchiJunYusho( Integer makuuchiJunYusho )
	{
		this.makuuchiJunYusho = makuuchiJunYusho;
	}

	public Integer getGinoSho()
	{
		return ginoSho;
	}

	public void setGinoSho( Integer ginoSho )
	{
		this.ginoSho = ginoSho;
	}

	public Integer getShukunSho()
	{
		return shukunSho;
	}

	public void setShukunSho( Integer shukunSho )
	{
		this.shukunSho = shukunSho;
	}

	public Integer getKantoSho()
	{
		return kantoSho;
	}

	public void setKantoSho( Integer kantoSho )
	{
		this.kantoSho = kantoSho;
	}

	public Integer getJuryoYusho()
	{
		return juryoYusho;
	}

	public void setJuryoYusho( Integer juryoYusho )
	{
		this.juryoYusho = juryoYusho;
	}

	public Integer getMakushitaYusho()
	{
		return makushitaYusho;
	}

	public void setMakushitaYusho( Integer makushitaYusho )
	{
		this.makushitaYusho = makushitaYusho;
	}

	public Integer getSandanmeYusho()
	{
		return sandanmeYusho;
	}

	public void setSandanmeYusho( Integer sandanmeYusho )
	{
		this.sandanmeYusho = sandanmeYusho;
	}

	public Integer getJonidanYusho()
	{
		return jonidanYusho;
	}

	public void setJonidanYusho( Integer jonidanYusho )
	{
		this.jonidanYusho = jonidanYusho;
	}

	public Integer getJonokuchiYusho()
	{
		return jonokuchiYusho;
	}

	public void setJonokuchiYusho( Integer jonokuchiYusho )
	{
		this.jonokuchiYusho = jonokuchiYusho;
	}

	public Integer getMaeZumoYusho()
	{
		return maeZumoYusho;
	}

	public void setMaeZumoYusho( Integer maeZumoYusho )
	{
		this.maeZumoYusho = maeZumoYusho;
	}

	public void setUniversity( Name university )
	{
		this.university = university;
	}

	public Integer getJuryoJunYusho()
	{
		return juryoJunYusho;
	}

	public void setJuryoJunYusho( Integer juryoJunYusho )
	{
		this.juryoJunYusho = juryoJunYusho;
	}

	public Integer getMakushitaJunYusho()
	{
		return makushitaJunYusho;
	}

	public void setMakushitaJunYusho( Integer makushitaJunYusho )
	{
		this.makushitaJunYusho = makushitaJunYusho;
	}

	public Integer getSandanmeJunYusho()
	{
		return sandanmeJunYusho;
	}

	public void setSandanmeJunYusho( Integer sandanmeJunYusho )
	{
		this.sandanmeJunYusho = sandanmeJunYusho;
	}

	public Integer getJonidanJunYusho()
	{
		return jonidanJunYusho;
	}

	public void setJonidanJunYusho( Integer jonidanJunYusho )
	{
		this.jonidanJunYusho = jonidanJunYusho;
	}

	public Integer getJonokuchiJunYusho()
	{
		return jonokuchiJunYusho;
	}

	public void setJonokuchiJunYusho( Integer jonokuchiJunYusho )
	{
		this.jonokuchiJunYusho = jonokuchiJunYusho;
	}
	
	public List<Injury> getInjuries(){
		return injuries;
	}
	
	public void setInjuries( List<Injury> injuries ){
		this.injuries = injuries;
	}
	
	@Override
	public String toString() {
	
		return getShikona().getFirstName_en() + " " + getShikona().getLastName_en() + " (" + getRealName().getFirstName_en() + " " + getRealName().getLastName_en();
	}
}
