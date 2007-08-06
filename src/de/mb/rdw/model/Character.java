package de.mb.rdw.model;

import java.io.Serializable;

/**
 * basic character
 * 
 * @author mbehnke
 *
 */
public class Character implements Serializable {

	public static final int FIGURE_NORMAL = 1;
	public static final int FIGURE_SLENDER = 2;
	public static final int FIGURE_STURDY = 3;


	protected String name;
	protected String titel;
	protected CharacterType type;

	protected int age;
	protected int size;
	protected int figure;
	protected CharacterProfession[] professions;
	protected CharacterLanguage[] languages;
	protected CharacterSkill[] skills;
	protected int money;
	protected int payload;
	protected int load;
	// schicht?

	protected int st; // Standhaftigkeit
	protected int ins; // Instinkt
	protected int pg; // psychische Gesundheit
	protected int imf; // immunitätsfaktor
	protected int zt;
	protected int kf;
	protected int rz1;
	protected int rz2;
	protected int rz3;
	protected int rz4;
	protected int zp;

	protected int auss;
	protected int kl;
	protected int wei;
	protected int tak;
	protected int ausd;
	protected int kk;
	protected int ge;

	protected int level_k;
	protected int level_w;

	protected int mkz;
	protected int zap;

	protected int wep;
	protected int kep;
	/**
	 * @return age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age Festzulegender age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return ausd
	 */
	public int getAusd() {
		return ausd;
	}
	/**
	 * @param ausd Festzulegender ausd
	 */
	public void setAusd(int ausd) {
		this.ausd = ausd;
	}
	/**
	 * @return auss
	 */
	public int getAuss() {
		return auss;
	}
	/**
	 * @param auss Festzulegender auss
	 */
	public void setAuss(int auss) {
		this.auss = auss;
	}
	/**
	 * @return figure
	 */
	public int getFigure() {
		return figure;
	}
	/**
	 * @param figure Festzulegender figure
	 */
	public void setFigure(int figure) {
		this.figure = figure;
	}
	/**
	 * @return ge
	 */
	public int getGe() {
		return ge;
	}
	/**
	 * @param ge Festzulegender ge
	 */
	public void setGe(int ge) {
		this.ge = ge;
	}
	/**
	 * @return imf
	 */
	public int getImf() {
		return imf;
	}
	/**
	 * @param imf Festzulegender imf
	 */
	public void setImf(int imf) {
		this.imf = imf;
	}
	/**
	 * @return ins
	 */
	public int getIns() {
		return ins;
	}
	/**
	 * @param ins Festzulegender ins
	 */
	public void setIns(int ins) {
		this.ins = ins;
	}
	/**
	 * @return kep
	 */
	public int getKep() {
		return kep;
	}
	/**
	 * @param kep Festzulegender kep
	 */
	public void setKep(int kep) {
		this.kep = kep;
	}
	/**
	 * @return kf
	 */
	public int getKf() {
		return kf;
	}
	/**
	 * @param kf Festzulegender kf
	 */
	public void setKf(int kf) {
		this.kf = kf;
	}
	/**
	 * @return kk
	 */
	public int getKk() {
		return kk;
	}
	/**
	 * @param kk Festzulegender kk
	 */
	public void setKk(int kk) {
		this.kk = kk;
	}
	/**
	 * @return kl
	 */
	public int getKl() {
		return kl;
	}
	/**
	 * @param kl Festzulegender kl
	 */
	public void setKl(int kl) {
		this.kl = kl;
	}
	/**
	 * @return languages
	 */
	public CharacterLanguage[] getLanguages() {
		return languages;
	}
	/**
	 * @param languages Festzulegender languages
	 */
	public void setLanguages(CharacterLanguage[] languages) {
		this.languages = languages;
	}
	/**
	 * @return level_k
	 */
	public int getLevel_k() {
		return level_k;
	}
	/**
	 * @param level_k Festzulegender level_k
	 */
	public void setLevel_k(int level_k) {
		this.level_k = level_k;
	}
	/**
	 * @return level_w
	 */
	public int getLevel_w() {
		return level_w;
	}
	/**
	 * @param level_w Festzulegender level_w
	 */
	public void setLevel_w(int level_w) {
		this.level_w = level_w;
	}
	/**
	 * @return load
	 */
	public int getLoad() {
		return load;
	}
	/**
	 * @param load Festzulegender load
	 */
	public void setLoad(int load) {
		this.load = load;
	}
	/**
	 * @return mkz
	 */
	public int getMkz() {
		return mkz;
	}
	/**
	 * @param mkz Festzulegender mkz
	 */
	public void setMkz(int mkz) {
		this.mkz = mkz;
	}
	/**
	 * @return money
	 */
	public int getMoney() {
		return money;
	}
	/**
	 * @param money Festzulegender money
	 */
	public void setMoney(int money) {
		this.money = money;
	}
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name Festzulegender name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return payload
	 */
	public int getPayload() {
		return payload;
	}
	/**
	 * @param payload Festzulegender payload
	 */
	public void setPayload(int payload) {
		this.payload = payload;
	}
	/**
	 * @return pg
	 */
	public int getPg() {
		return pg;
	}
	/**
	 * @param pg Festzulegender pg
	 */
	public void setPg(int pg) {
		this.pg = pg;
	}
	/**
	 * @return professions
	 */
	public CharacterProfession[] getProfessions() {
		return professions;
	}
	/**
	 * @param professions Festzulegender professions
	 */
	public void setProfessions(CharacterProfession[] professions) {
		this.professions = professions;
	}
	/**
	 * @return rz1
	 */
	public int getRz1() {
		return rz1;
	}
	/**
	 * @param rz1 Festzulegender rz1
	 */
	public void setRz1(int rz1) {
		this.rz1 = rz1;
	}
	/**
	 * @return rz2
	 */
	public int getRz2() {
		return rz2;
	}
	/**
	 * @param rz2 Festzulegender rz2
	 */
	public void setRz2(int rz2) {
		this.rz2 = rz2;
	}
	/**
	 * @return rz3
	 */
	public int getRz3() {
		return rz3;
	}
	/**
	 * @param rz3 Festzulegender rz3
	 */
	public void setRz3(int rz3) {
		this.rz3 = rz3;
	}
	/**
	 * @return rz4
	 */
	public int getRz4() {
		return rz4;
	}
	/**
	 * @param rz4 Festzulegender rz4
	 */
	public void setRz4(int rz4) {
		this.rz4 = rz4;
	}
	/**
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size Festzulegender size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return st
	 */
	public int getSt() {
		return st;
	}
	/**
	 * @param st Festzulegender st
	 */
	public void setSt(int st) {
		this.st = st;
	}
	/**
	 * @return tak
	 */
	public int getTak() {
		return tak;
	}
	/**
	 * @param tak Festzulegender tak
	 */
	public void setTak(int tak) {
		this.tak = tak;
	}
	/**
	 * @return titel
	 */
	public String getTitel() {
		return titel;
	}
	/**
	 * @param titel Festzulegender titel
	 */
	public void setTitel(String titel) {
		this.titel = titel;
	}
	/**
	 * @return type
	 */
	public CharacterType getType() {
		return type;
	}
	/**
	 * @param type Festzulegender type
	 */
	public void setType(CharacterType type) {
		this.type = type;
	}
	/**
	 * @return wei
	 */
	public int getWei() {
		return wei;
	}
	/**
	 * @param wei Festzulegender wei
	 */
	public void setWei(int wei) {
		this.wei = wei;
	}
	/**
	 * @return wep
	 */
	public int getWep() {
		return wep;
	}
	/**
	 * @param wep Festzulegender wep
	 */
	public void setWep(int wep) {
		this.wep = wep;
	}
	/**
	 * @return zap
	 */
	public int getZap() {
		return zap;
	}
	/**
	 * @param zap Festzulegender zap
	 */
	public void setZap(int zap) {
		this.zap = zap;
	}
	/**
	 * @return zp
	 */
	public int getZp() {
		return zp;
	}
	/**
	 * @param zp Festzulegender zp
	 */
	public void setZp(int zp) {
		this.zp = zp;
	}
	/**
	 * @return zt
	 */
	public int getZt() {
		return zt;
	}
	/**
	 * @param zt Festzulegender zt
	 */
	public void setZt(int zt) {
		this.zt = zt;
	}
	/**
	 * @return skills
	 */
	public CharacterSkill[] getSkills() {
		return skills;
	}
	/**
	 * @param skills Festzulegender skills
	 */
	public void setSkills(CharacterSkill[] skills) {
		this.skills = skills;
	}

}
