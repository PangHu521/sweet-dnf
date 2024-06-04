package com.sweet.simple.login.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 
 * @TableName charac_info
 */
@TableName("taiwan_cain.charac_info")
public class CharacInfo {
    /**
     * 
     */
    private Integer characNo;

    /**
     * 
     */
    private Integer mId;

    /**
     * 
     */
    private String characName;

    /**
     * 
     */
    private Integer village;

    /**
     * 
     */
    private Integer job;

    /**
     * 
     */
    private Integer lev;

    /**
     * 
     */
    private Integer exp;

    /**
     * 
     */
    private Integer growType;

    /**
     * 
     */
    private Integer hp;

    /**
     * 
     */
    private Integer maxhp;

    /**
     * 
     */
    private Integer maxmp;

    /**
     * 
     */
    private Integer phyAttack;

    /**
     * 
     */
    private Integer phyDefense;

    /**
     * 
     */
    private Integer magAttack;

    /**
     * 
     */
    private Integer magDefense;

    /**
     * 
     */
    private Integer invenWeight;

    /**
     * 
     */
    private Integer hpRegen;

    /**
     * 
     */
    private Integer mpRegen;

    /**
     * 
     */
    private Integer moveSpeed;

    /**
     * 
     */
    private Integer attackSpeed;

    /**
     * 
     */
    private Integer castSpeed;

    /**
     * 
     */
    private Integer hitRecovery;

    /**
     * 
     */
    private Integer jump;

    /**
     * 
     */
    private Integer characWeight;

    /**
     * 
     */
    private Integer fatigue;

    /**
     * 
     */
    private Integer maxFatigue;

    /**
     * 
     */
    private Integer premiumFatigue;

    /**
     * 
     */
    private Integer maxPremiumFatigue;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date lastPlayTime;

    /**
     * 
     */
    private Integer dungeonClearPoint;

    /**
     * 
     */
    private Date deleteTime;

    /**
     * 
     */
    private Integer deleteFlag;

    /**
     * 
     */
    private Integer guildId;

    /**
     * 
     */
    private Integer guildRight;

    /**
     * 
     */
    private Integer memberFlag;

    /**
     * 
     */
    private Integer sex;

    /**
     * 
     */
    private Integer expertJob;

    /**
     * 
     */
    private Integer skillTreeIndex;

    /**
     * 
     */
    private Integer linkCharacNo;

    /**
     * 
     */
    private Integer eventCharacLevel;

    /**
     * 
     */
    private Integer guildSecede;

    /**
     * 
     */
    private Integer startTime;

    /**
     * 
     */
    private Integer finishTime;

    /**
     * 
     */
    private Integer competitionArea;

    /**
     * 
     */
    private Integer competitionPeriod;

    /**
     * 
     */
    private Integer mercenaryStartTime;

    /**
     * 
     */
    private Integer mercenaryFinishTime;

    /**
     * 
     */
    private Integer mercenaryArea;

    /**
     * 
     */
    private Integer mercenaryPeriod;

    /**
     * 
     */
    private byte[] elementResist;

    /**
     * 
     */
    private byte[] specProperty;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getCharacNo() {
        return characNo;
    }

    /**
     * 
     */
    public void setCharacNo(Integer characNo) {
        this.characNo = characNo;
    }

    /**
     * 
     */
    public Integer getmId() {
        return mId;
    }

    /**
     * 
     */
    public void setmId(Integer mId) {
        this.mId = mId;
    }

    /**
     * 
     */
    public String getCharacName() {
        return characName;
    }

    /**
     * 
     */
    public void setCharacName(String characName) {
        this.characName = characName;
    }

    /**
     * 
     */
    public Integer getVillage() {
        return village;
    }

    /**
     * 
     */
    public void setVillage(Integer village) {
        this.village = village;
    }

    /**
     * 
     */
    public Integer getJob() {
        return job;
    }

    /**
     * 
     */
    public void setJob(Integer job) {
        this.job = job;
    }

    /**
     * 
     */
    public Integer getLev() {
        return lev;
    }

    /**
     * 
     */
    public void setLev(Integer lev) {
        this.lev = lev;
    }

    /**
     * 
     */
    public Integer getExp() {
        return exp;
    }

    /**
     * 
     */
    public void setExp(Integer exp) {
        this.exp = exp;
    }

    /**
     * 
     */
    public Integer getGrowType() {
        return growType;
    }

    /**
     * 
     */
    public void setGrowType(Integer growType) {
        this.growType = growType;
    }

    /**
     * 
     */
    public Integer getHp() {
        return hp;
    }

    /**
     * 
     */
    public void setHp(Integer hp) {
        this.hp = hp;
    }

    /**
     * 
     */
    public Integer getMaxhp() {
        return maxhp;
    }

    /**
     * 
     */
    public void setMaxhp(Integer maxhp) {
        this.maxhp = maxhp;
    }

    /**
     * 
     */
    public Integer getMaxmp() {
        return maxmp;
    }

    /**
     * 
     */
    public void setMaxmp(Integer maxmp) {
        this.maxmp = maxmp;
    }

    /**
     * 
     */
    public Integer getPhyAttack() {
        return phyAttack;
    }

    /**
     * 
     */
    public void setPhyAttack(Integer phyAttack) {
        this.phyAttack = phyAttack;
    }

    /**
     * 
     */
    public Integer getPhyDefense() {
        return phyDefense;
    }

    /**
     * 
     */
    public void setPhyDefense(Integer phyDefense) {
        this.phyDefense = phyDefense;
    }

    /**
     * 
     */
    public Integer getMagAttack() {
        return magAttack;
    }

    /**
     * 
     */
    public void setMagAttack(Integer magAttack) {
        this.magAttack = magAttack;
    }

    /**
     * 
     */
    public Integer getMagDefense() {
        return magDefense;
    }

    /**
     * 
     */
    public void setMagDefense(Integer magDefense) {
        this.magDefense = magDefense;
    }

    /**
     * 
     */
    public Integer getInvenWeight() {
        return invenWeight;
    }

    /**
     * 
     */
    public void setInvenWeight(Integer invenWeight) {
        this.invenWeight = invenWeight;
    }

    /**
     * 
     */
    public Integer getHpRegen() {
        return hpRegen;
    }

    /**
     * 
     */
    public void setHpRegen(Integer hpRegen) {
        this.hpRegen = hpRegen;
    }

    /**
     * 
     */
    public Integer getMpRegen() {
        return mpRegen;
    }

    /**
     * 
     */
    public void setMpRegen(Integer mpRegen) {
        this.mpRegen = mpRegen;
    }

    /**
     * 
     */
    public Integer getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * 
     */
    public void setMoveSpeed(Integer moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    /**
     * 
     */
    public Integer getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * 
     */
    public void setAttackSpeed(Integer attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    /**
     * 
     */
    public Integer getCastSpeed() {
        return castSpeed;
    }

    /**
     * 
     */
    public void setCastSpeed(Integer castSpeed) {
        this.castSpeed = castSpeed;
    }

    /**
     * 
     */
    public Integer getHitRecovery() {
        return hitRecovery;
    }

    /**
     * 
     */
    public void setHitRecovery(Integer hitRecovery) {
        this.hitRecovery = hitRecovery;
    }

    /**
     * 
     */
    public Integer getJump() {
        return jump;
    }

    /**
     * 
     */
    public void setJump(Integer jump) {
        this.jump = jump;
    }

    /**
     * 
     */
    public Integer getCharacWeight() {
        return characWeight;
    }

    /**
     * 
     */
    public void setCharacWeight(Integer characWeight) {
        this.characWeight = characWeight;
    }

    /**
     * 
     */
    public Integer getFatigue() {
        return fatigue;
    }

    /**
     * 
     */
    public void setFatigue(Integer fatigue) {
        this.fatigue = fatigue;
    }

    /**
     * 
     */
    public Integer getMaxFatigue() {
        return maxFatigue;
    }

    /**
     * 
     */
    public void setMaxFatigue(Integer maxFatigue) {
        this.maxFatigue = maxFatigue;
    }

    /**
     * 
     */
    public Integer getPremiumFatigue() {
        return premiumFatigue;
    }

    /**
     * 
     */
    public void setPremiumFatigue(Integer premiumFatigue) {
        this.premiumFatigue = premiumFatigue;
    }

    /**
     * 
     */
    public Integer getMaxPremiumFatigue() {
        return maxPremiumFatigue;
    }

    /**
     * 
     */
    public void setMaxPremiumFatigue(Integer maxPremiumFatigue) {
        this.maxPremiumFatigue = maxPremiumFatigue;
    }

    /**
     * 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     */
    public Date getLastPlayTime() {
        return lastPlayTime;
    }

    /**
     * 
     */
    public void setLastPlayTime(Date lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    /**
     * 
     */
    public Integer getDungeonClearPoint() {
        return dungeonClearPoint;
    }

    /**
     * 
     */
    public void setDungeonClearPoint(Integer dungeonClearPoint) {
        this.dungeonClearPoint = dungeonClearPoint;
    }

    /**
     * 
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * 
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * 
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * 
     */
    public Integer getGuildId() {
        return guildId;
    }

    /**
     * 
     */
    public void setGuildId(Integer guildId) {
        this.guildId = guildId;
    }

    /**
     * 
     */
    public Integer getGuildRight() {
        return guildRight;
    }

    /**
     * 
     */
    public void setGuildRight(Integer guildRight) {
        this.guildRight = guildRight;
    }

    /**
     * 
     */
    public Integer getMemberFlag() {
        return memberFlag;
    }

    /**
     * 
     */
    public void setMemberFlag(Integer memberFlag) {
        this.memberFlag = memberFlag;
    }

    /**
     * 
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 
     */
    public Integer getExpertJob() {
        return expertJob;
    }

    /**
     * 
     */
    public void setExpertJob(Integer expertJob) {
        this.expertJob = expertJob;
    }

    /**
     * 
     */
    public Integer getSkillTreeIndex() {
        return skillTreeIndex;
    }

    /**
     * 
     */
    public void setSkillTreeIndex(Integer skillTreeIndex) {
        this.skillTreeIndex = skillTreeIndex;
    }

    /**
     * 
     */
    public Integer getLinkCharacNo() {
        return linkCharacNo;
    }

    /**
     * 
     */
    public void setLinkCharacNo(Integer linkCharacNo) {
        this.linkCharacNo = linkCharacNo;
    }

    /**
     * 
     */
    public Integer getEventCharacLevel() {
        return eventCharacLevel;
    }

    /**
     * 
     */
    public void setEventCharacLevel(Integer eventCharacLevel) {
        this.eventCharacLevel = eventCharacLevel;
    }

    /**
     * 
     */
    public Integer getGuildSecede() {
        return guildSecede;
    }

    /**
     * 
     */
    public void setGuildSecede(Integer guildSecede) {
        this.guildSecede = guildSecede;
    }

    /**
     * 
     */
    public Integer getStartTime() {
        return startTime;
    }

    /**
     * 
     */
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     */
    public Integer getFinishTime() {
        return finishTime;
    }

    /**
     * 
     */
    public void setFinishTime(Integer finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 
     */
    public Integer getCompetitionArea() {
        return competitionArea;
    }

    /**
     * 
     */
    public void setCompetitionArea(Integer competitionArea) {
        this.competitionArea = competitionArea;
    }

    /**
     * 
     */
    public Integer getCompetitionPeriod() {
        return competitionPeriod;
    }

    /**
     * 
     */
    public void setCompetitionPeriod(Integer competitionPeriod) {
        this.competitionPeriod = competitionPeriod;
    }

    /**
     * 
     */
    public Integer getMercenaryStartTime() {
        return mercenaryStartTime;
    }

    /**
     * 
     */
    public void setMercenaryStartTime(Integer mercenaryStartTime) {
        this.mercenaryStartTime = mercenaryStartTime;
    }

    /**
     * 
     */
    public Integer getMercenaryFinishTime() {
        return mercenaryFinishTime;
    }

    /**
     * 
     */
    public void setMercenaryFinishTime(Integer mercenaryFinishTime) {
        this.mercenaryFinishTime = mercenaryFinishTime;
    }

    /**
     * 
     */
    public Integer getMercenaryArea() {
        return mercenaryArea;
    }

    /**
     * 
     */
    public void setMercenaryArea(Integer mercenaryArea) {
        this.mercenaryArea = mercenaryArea;
    }

    /**
     * 
     */
    public Integer getMercenaryPeriod() {
        return mercenaryPeriod;
    }

    /**
     * 
     */
    public void setMercenaryPeriod(Integer mercenaryPeriod) {
        this.mercenaryPeriod = mercenaryPeriod;
    }

    /**
     * 
     */
    public byte[] getElementResist() {
        return elementResist;
    }

    /**
     * 
     */
    public void setElementResist(byte[] elementResist) {
        this.elementResist = elementResist;
    }

    /**
     * 
     */
    public byte[] getSpecProperty() {
        return specProperty;
    }

    /**
     * 
     */
    public void setSpecProperty(byte[] specProperty) {
        this.specProperty = specProperty;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CharacInfo other = (CharacInfo) that;
        return (this.getCharacNo() == null ? other.getCharacNo() == null : this.getCharacNo().equals(other.getCharacNo()))
            && (this.getmId() == null ? other.getmId() == null : this.getmId().equals(other.getmId()))
            && (this.getCharacName() == null ? other.getCharacName() == null : this.getCharacName().equals(other.getCharacName()))
            && (this.getVillage() == null ? other.getVillage() == null : this.getVillage().equals(other.getVillage()))
            && (this.getJob() == null ? other.getJob() == null : this.getJob().equals(other.getJob()))
            && (this.getLev() == null ? other.getLev() == null : this.getLev().equals(other.getLev()))
            && (this.getExp() == null ? other.getExp() == null : this.getExp().equals(other.getExp()))
            && (this.getGrowType() == null ? other.getGrowType() == null : this.getGrowType().equals(other.getGrowType()))
            && (this.getHp() == null ? other.getHp() == null : this.getHp().equals(other.getHp()))
            && (this.getMaxhp() == null ? other.getMaxhp() == null : this.getMaxhp().equals(other.getMaxhp()))
            && (this.getMaxmp() == null ? other.getMaxmp() == null : this.getMaxmp().equals(other.getMaxmp()))
            && (this.getPhyAttack() == null ? other.getPhyAttack() == null : this.getPhyAttack().equals(other.getPhyAttack()))
            && (this.getPhyDefense() == null ? other.getPhyDefense() == null : this.getPhyDefense().equals(other.getPhyDefense()))
            && (this.getMagAttack() == null ? other.getMagAttack() == null : this.getMagAttack().equals(other.getMagAttack()))
            && (this.getMagDefense() == null ? other.getMagDefense() == null : this.getMagDefense().equals(other.getMagDefense()))
            && (this.getInvenWeight() == null ? other.getInvenWeight() == null : this.getInvenWeight().equals(other.getInvenWeight()))
            && (this.getHpRegen() == null ? other.getHpRegen() == null : this.getHpRegen().equals(other.getHpRegen()))
            && (this.getMpRegen() == null ? other.getMpRegen() == null : this.getMpRegen().equals(other.getMpRegen()))
            && (this.getMoveSpeed() == null ? other.getMoveSpeed() == null : this.getMoveSpeed().equals(other.getMoveSpeed()))
            && (this.getAttackSpeed() == null ? other.getAttackSpeed() == null : this.getAttackSpeed().equals(other.getAttackSpeed()))
            && (this.getCastSpeed() == null ? other.getCastSpeed() == null : this.getCastSpeed().equals(other.getCastSpeed()))
            && (this.getHitRecovery() == null ? other.getHitRecovery() == null : this.getHitRecovery().equals(other.getHitRecovery()))
            && (this.getJump() == null ? other.getJump() == null : this.getJump().equals(other.getJump()))
            && (this.getCharacWeight() == null ? other.getCharacWeight() == null : this.getCharacWeight().equals(other.getCharacWeight()))
            && (this.getFatigue() == null ? other.getFatigue() == null : this.getFatigue().equals(other.getFatigue()))
            && (this.getMaxFatigue() == null ? other.getMaxFatigue() == null : this.getMaxFatigue().equals(other.getMaxFatigue()))
            && (this.getPremiumFatigue() == null ? other.getPremiumFatigue() == null : this.getPremiumFatigue().equals(other.getPremiumFatigue()))
            && (this.getMaxPremiumFatigue() == null ? other.getMaxPremiumFatigue() == null : this.getMaxPremiumFatigue().equals(other.getMaxPremiumFatigue()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getLastPlayTime() == null ? other.getLastPlayTime() == null : this.getLastPlayTime().equals(other.getLastPlayTime()))
            && (this.getDungeonClearPoint() == null ? other.getDungeonClearPoint() == null : this.getDungeonClearPoint().equals(other.getDungeonClearPoint()))
            && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()))
            && (this.getGuildId() == null ? other.getGuildId() == null : this.getGuildId().equals(other.getGuildId()))
            && (this.getGuildRight() == null ? other.getGuildRight() == null : this.getGuildRight().equals(other.getGuildRight()))
            && (this.getMemberFlag() == null ? other.getMemberFlag() == null : this.getMemberFlag().equals(other.getMemberFlag()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getExpertJob() == null ? other.getExpertJob() == null : this.getExpertJob().equals(other.getExpertJob()))
            && (this.getSkillTreeIndex() == null ? other.getSkillTreeIndex() == null : this.getSkillTreeIndex().equals(other.getSkillTreeIndex()))
            && (this.getLinkCharacNo() == null ? other.getLinkCharacNo() == null : this.getLinkCharacNo().equals(other.getLinkCharacNo()))
            && (this.getEventCharacLevel() == null ? other.getEventCharacLevel() == null : this.getEventCharacLevel().equals(other.getEventCharacLevel()))
            && (this.getGuildSecede() == null ? other.getGuildSecede() == null : this.getGuildSecede().equals(other.getGuildSecede()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getFinishTime() == null ? other.getFinishTime() == null : this.getFinishTime().equals(other.getFinishTime()))
            && (this.getCompetitionArea() == null ? other.getCompetitionArea() == null : this.getCompetitionArea().equals(other.getCompetitionArea()))
            && (this.getCompetitionPeriod() == null ? other.getCompetitionPeriod() == null : this.getCompetitionPeriod().equals(other.getCompetitionPeriod()))
            && (this.getMercenaryStartTime() == null ? other.getMercenaryStartTime() == null : this.getMercenaryStartTime().equals(other.getMercenaryStartTime()))
            && (this.getMercenaryFinishTime() == null ? other.getMercenaryFinishTime() == null : this.getMercenaryFinishTime().equals(other.getMercenaryFinishTime()))
            && (this.getMercenaryArea() == null ? other.getMercenaryArea() == null : this.getMercenaryArea().equals(other.getMercenaryArea()))
            && (this.getMercenaryPeriod() == null ? other.getMercenaryPeriod() == null : this.getMercenaryPeriod().equals(other.getMercenaryPeriod()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCharacNo() == null) ? 0 : getCharacNo().hashCode());
        result = prime * result + ((getmId() == null) ? 0 : getmId().hashCode());
        result = prime * result + ((getCharacName() == null) ? 0 : getCharacName().hashCode());
        result = prime * result + ((getVillage() == null) ? 0 : getVillage().hashCode());
        result = prime * result + ((getJob() == null) ? 0 : getJob().hashCode());
        result = prime * result + ((getLev() == null) ? 0 : getLev().hashCode());
        result = prime * result + ((getExp() == null) ? 0 : getExp().hashCode());
        result = prime * result + ((getGrowType() == null) ? 0 : getGrowType().hashCode());
        result = prime * result + ((getHp() == null) ? 0 : getHp().hashCode());
        result = prime * result + ((getMaxhp() == null) ? 0 : getMaxhp().hashCode());
        result = prime * result + ((getMaxmp() == null) ? 0 : getMaxmp().hashCode());
        result = prime * result + ((getPhyAttack() == null) ? 0 : getPhyAttack().hashCode());
        result = prime * result + ((getPhyDefense() == null) ? 0 : getPhyDefense().hashCode());
        result = prime * result + ((getMagAttack() == null) ? 0 : getMagAttack().hashCode());
        result = prime * result + ((getMagDefense() == null) ? 0 : getMagDefense().hashCode());
        result = prime * result + ((getInvenWeight() == null) ? 0 : getInvenWeight().hashCode());
        result = prime * result + ((getHpRegen() == null) ? 0 : getHpRegen().hashCode());
        result = prime * result + ((getMpRegen() == null) ? 0 : getMpRegen().hashCode());
        result = prime * result + ((getMoveSpeed() == null) ? 0 : getMoveSpeed().hashCode());
        result = prime * result + ((getAttackSpeed() == null) ? 0 : getAttackSpeed().hashCode());
        result = prime * result + ((getCastSpeed() == null) ? 0 : getCastSpeed().hashCode());
        result = prime * result + ((getHitRecovery() == null) ? 0 : getHitRecovery().hashCode());
        result = prime * result + ((getJump() == null) ? 0 : getJump().hashCode());
        result = prime * result + ((getCharacWeight() == null) ? 0 : getCharacWeight().hashCode());
        result = prime * result + ((getFatigue() == null) ? 0 : getFatigue().hashCode());
        result = prime * result + ((getMaxFatigue() == null) ? 0 : getMaxFatigue().hashCode());
        result = prime * result + ((getPremiumFatigue() == null) ? 0 : getPremiumFatigue().hashCode());
        result = prime * result + ((getMaxPremiumFatigue() == null) ? 0 : getMaxPremiumFatigue().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getLastPlayTime() == null) ? 0 : getLastPlayTime().hashCode());
        result = prime * result + ((getDungeonClearPoint() == null) ? 0 : getDungeonClearPoint().hashCode());
        result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        result = prime * result + ((getGuildId() == null) ? 0 : getGuildId().hashCode());
        result = prime * result + ((getGuildRight() == null) ? 0 : getGuildRight().hashCode());
        result = prime * result + ((getMemberFlag() == null) ? 0 : getMemberFlag().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getExpertJob() == null) ? 0 : getExpertJob().hashCode());
        result = prime * result + ((getSkillTreeIndex() == null) ? 0 : getSkillTreeIndex().hashCode());
        result = prime * result + ((getLinkCharacNo() == null) ? 0 : getLinkCharacNo().hashCode());
        result = prime * result + ((getEventCharacLevel() == null) ? 0 : getEventCharacLevel().hashCode());
        result = prime * result + ((getGuildSecede() == null) ? 0 : getGuildSecede().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getFinishTime() == null) ? 0 : getFinishTime().hashCode());
        result = prime * result + ((getCompetitionArea() == null) ? 0 : getCompetitionArea().hashCode());
        result = prime * result + ((getCompetitionPeriod() == null) ? 0 : getCompetitionPeriod().hashCode());
        result = prime * result + ((getMercenaryStartTime() == null) ? 0 : getMercenaryStartTime().hashCode());
        result = prime * result + ((getMercenaryFinishTime() == null) ? 0 : getMercenaryFinishTime().hashCode());
        result = prime * result + ((getMercenaryArea() == null) ? 0 : getMercenaryArea().hashCode());
        result = prime * result + ((getMercenaryPeriod() == null) ? 0 : getMercenaryPeriod().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", characNo=").append(characNo);
        sb.append(", mId=").append(mId);
        sb.append(", characName=").append(characName);
        sb.append(", village=").append(village);
        sb.append(", job=").append(job);
        sb.append(", lev=").append(lev);
        sb.append(", exp=").append(exp);
        sb.append(", growType=").append(growType);
        sb.append(", hp=").append(hp);
        sb.append(", maxhp=").append(maxhp);
        sb.append(", maxmp=").append(maxmp);
        sb.append(", phyAttack=").append(phyAttack);
        sb.append(", phyDefense=").append(phyDefense);
        sb.append(", magAttack=").append(magAttack);
        sb.append(", magDefense=").append(magDefense);
        sb.append(", invenWeight=").append(invenWeight);
        sb.append(", hpRegen=").append(hpRegen);
        sb.append(", mpRegen=").append(mpRegen);
        sb.append(", moveSpeed=").append(moveSpeed);
        sb.append(", attackSpeed=").append(attackSpeed);
        sb.append(", castSpeed=").append(castSpeed);
        sb.append(", hitRecovery=").append(hitRecovery);
        sb.append(", jump=").append(jump);
        sb.append(", characWeight=").append(characWeight);
        sb.append(", fatigue=").append(fatigue);
        sb.append(", maxFatigue=").append(maxFatigue);
        sb.append(", premiumFatigue=").append(premiumFatigue);
        sb.append(", maxPremiumFatigue=").append(maxPremiumFatigue);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastPlayTime=").append(lastPlayTime);
        sb.append(", dungeonClearPoint=").append(dungeonClearPoint);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", guildId=").append(guildId);
        sb.append(", guildRight=").append(guildRight);
        sb.append(", memberFlag=").append(memberFlag);
        sb.append(", sex=").append(sex);
        sb.append(", expertJob=").append(expertJob);
        sb.append(", skillTreeIndex=").append(skillTreeIndex);
        sb.append(", linkCharacNo=").append(linkCharacNo);
        sb.append(", eventCharacLevel=").append(eventCharacLevel);
        sb.append(", guildSecede=").append(guildSecede);
        sb.append(", startTime=").append(startTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", competitionArea=").append(competitionArea);
        sb.append(", competitionPeriod=").append(competitionPeriod);
        sb.append(", mercenaryStartTime=").append(mercenaryStartTime);
        sb.append(", mercenaryFinishTime=").append(mercenaryFinishTime);
        sb.append(", mercenaryArea=").append(mercenaryArea);
        sb.append(", mercenaryPeriod=").append(mercenaryPeriod);
        sb.append(", elementResist=").append(elementResist);
        sb.append(", specProperty=").append(specProperty);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}