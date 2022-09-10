package com.technoride.RewardIncentiveSystem.entity;

import com.technoride.RewardIncentiveSystem.Id.WeaverProductionTargetsId;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_WEAVER_PRODUCTION_TARGETS")
@IdClass(WeaverProductionTargetsId.class)
public class WeaverProductionTargets {
    @Id
    @Column(name = "LOOM_PRIMARY")
    private String loomPrimary;
    @Id
    @Column(name = "LOOM")
    private Integer loom;

    @Column(name = "RPM")
    private String rpm;

    @Column(name = "EFF_PER")
    private String effPer;

    @Column(name = "PRODN_PER_HR")
    private String prodnPerHr;

    @Id
    @Column(name = "WEFT_DETAILS")
    private String weftDetails;

    @Column(name = "SHIFT_AB")
    private String shiftAB;

    @Column(name = "SHIFT_AB_PICKS_FOR_REWARDS")
    private String shiftABPicksForRewards;

    @Column(name = "SHIFT_C")
    private String shiftC;

    @Column(name = "SHIFT_C_PICKS_FOR_REWARDS")
    private String shiftCPicksForRewards;

    @Column(name = "SHIFT_AB_PICKS_FOR_REWARDS2")
    private String shiftABPicksForRewards2;

    @Column(name = "SHIFT_C_PICKS_FOR_REWARDS2")
    private String shiftCPicksForRewards2;

    @Column(name = "SHIFT_AB_PICKS_FOR_REWARDS3")
    private String shiftABPicksForRewards3;

    @Column(name = "SHIFT_C_PICKS_FOR_REWARDS3")
    private String shiftCPicksForRewards3;

    @Column(name = "SHIFT_AB_PICKS_FOR_REWARDS4")
    private String shiftABPicksForRewards4;

    @Column(name = "SHIFT_C_PICKS_FOR_REWARDS4")
    private String shiftCPicksForRewards4;

    @Column(name = "SHIFT_AB_PICKS_FOR_REWARDS5")
    private String shiftABPicksForRewards5;

    @Column(name = "SHIFT_C_PICKS_FOR_REWARDS5")
    private String shiftCPicksForRewards5;

    @Column(name = "SHIFT_AB_PICKS_FOR_REWARDS6")
    private String shiftABPicksForRewards6;

    @Column(name = "SHIFT_C_PICKS_FOR_REWARDS6")
    private String shiftCPicksForRewards6;

    @Column(name = "SHIFT_AB_PICKS_FOR_REWARDS7")
    private String shiftABPicksForRewards7;

    @Column(name = "SHIFT_C_PICKS_FOR_REWARDS7")
    private String shiftCPicksForRewards7;

    @Column(name = "SHIFT_AB_PICKS_FOR_REWARDS8")
    private String shiftABPicksForRewards8;

    @Column(name = "SHIFT_C_PICKS_FOR_REWARDS8")
    private String shiftCPicksForRewards8;

    @Column(name = "SHIFT_AB_PICKS_FOR_REWARDS9")
    private String shiftABPicksForRewards9;

    @Column(name = "SHIFT_C_PICKS_FOR_REWARDS9")
    private String shiftCPicksForRewards9;

    @Column(name = "SHIFT_AB_PICKS_FOR_REWARDS10")
    private String shiftABPicksForRewards10;

    @Column(name = "SHIFT_C_PICKS_FOR_REWARDS10")
    private String shiftCPicksForRewards10;

    @Column(name = "REWARDS1_RS")
    private String reward1;

    @Column(name = "REWARDS2_RS")
    private String reward2;

    @Column(name = "REWARDS3_RS")
    private String reward3;

    @Column(name = "REWARDS4_RS")
    private String reward4;

    @Column(name = "REWARDS5_RS")
    private String reward5;

    @Column(name = "REWARDS6_RS")
    private String reward6;

    @Column(name = "REWARDS7_RS")
    private String reward7;

    @Column(name = "REWARDS8_RS")
    private String reward8;

    @Column(name = "REWARDS9_RS")
    private String reward9;

    @Column(name = "REWARDS10_RS")
    private String reward10;

    public String getLoomPrimary() {
        return loomPrimary;
    }

    public void setLoomPrimary(String loomPrimary) {
        this.loomPrimary = loomPrimary;
    }

    public Integer getLoom() {
        return loom;
    }

    public void setLoom(Integer loom) {
        this.loom = loom;
    }

    public String getRpm() {
        return rpm;
    }

    public void setRpm(String rpm) {
        this.rpm = rpm;
    }

    public String getEffPer() {
        return effPer;
    }

    public void setEffPer(String effPer) {
        this.effPer = effPer;
    }

    public String getProdnPerHr() {
        return prodnPerHr;
    }

    public void setProdnPerHr(String prodnPerHr) {
        this.prodnPerHr = prodnPerHr;
    }

    public String getWeftDetails() {
        return weftDetails;
    }

    public void setWeftDetails(String weftDetails) {
        this.weftDetails = weftDetails;
    }

    public String getShiftAB() {
        return shiftAB;
    }

    public void setShiftAB(String shiftAB) {
        this.shiftAB = shiftAB;
    }

    public String getShiftABPicksForRewards() {
        return shiftABPicksForRewards;
    }

    public void setShiftABPicksForRewards(String shiftABPicksForRewards) {
        this.shiftABPicksForRewards = shiftABPicksForRewards;
    }

    public String getShiftC() {
        return shiftC;
    }

    public void setShiftC(String shiftC) {
        this.shiftC = shiftC;
    }

    public String getShiftCPicksForRewards() {
        return shiftCPicksForRewards;
    }

    public void setShiftCPicksForRewards(String shiftCPicksForRewards) {
        this.shiftCPicksForRewards = shiftCPicksForRewards;
    }

    public String getShiftABPicksForRewards2() {
        return shiftABPicksForRewards2;
    }

    public void setShiftABPicksForRewards2(String shiftABPicksForRewards2) {
        this.shiftABPicksForRewards2 = shiftABPicksForRewards2;
    }

    public String getShiftCPicksForRewards2() {
        return shiftCPicksForRewards2;
    }

    public void setShiftCPicksForRewards2(String shiftCPicksForRewards2) {
        this.shiftCPicksForRewards2 = shiftCPicksForRewards2;
    }

    public String getShiftABPicksForRewards3() {
        return shiftABPicksForRewards3;
    }

    public void setShiftABPicksForRewards3(String shiftABPicksForRewards3) {
        this.shiftABPicksForRewards3 = shiftABPicksForRewards3;
    }

    public String getShiftCPicksForRewards3() {
        return shiftCPicksForRewards3;
    }

    public void setShiftCPicksForRewards3(String shiftCPicksForRewards3) {
        this.shiftCPicksForRewards3 = shiftCPicksForRewards3;
    }

    public String getShiftABPicksForRewards4() {
        return shiftABPicksForRewards4;
    }

    public void setShiftABPicksForRewards4(String shiftABPicksForRewards4) {
        this.shiftABPicksForRewards4 = shiftABPicksForRewards4;
    }

    public String getShiftCPicksForRewards4() {
        return shiftCPicksForRewards4;
    }

    public void setShiftCPicksForRewards4(String shiftCPicksForRewards4) {
        this.shiftCPicksForRewards4 = shiftCPicksForRewards4;
    }

    public String getShiftABPicksForRewards5() {
        return shiftABPicksForRewards5;
    }

    public void setShiftABPicksForRewards5(String shiftABPicksForRewards5) {
        this.shiftABPicksForRewards5 = shiftABPicksForRewards5;
    }

    public String getShiftCPicksForRewards5() {
        return shiftCPicksForRewards5;
    }

    public void setShiftCPicksForRewards5(String shiftCPicksForRewards5) {
        this.shiftCPicksForRewards5 = shiftCPicksForRewards5;
    }

    public String getShiftABPicksForRewards6() {
        return shiftABPicksForRewards6;
    }

    public void setShiftABPicksForRewards6(String shiftABPicksForRewards6) {
        this.shiftABPicksForRewards6 = shiftABPicksForRewards6;
    }

    public String getShiftCPicksForRewards6() {
        return shiftCPicksForRewards6;
    }

    public void setShiftCPicksForRewards6(String shiftCPicksForRewards6) {
        this.shiftCPicksForRewards6 = shiftCPicksForRewards6;
    }

    public String getShiftABPicksForRewards7() {
        return shiftABPicksForRewards7;
    }

    public void setShiftABPicksForRewards7(String shiftABPicksForRewards7) {
        this.shiftABPicksForRewards7 = shiftABPicksForRewards7;
    }

    public String getShiftCPicksForRewards7() {
        return shiftCPicksForRewards7;
    }

    public void setShiftCPicksForRewards7(String shiftCPicksForRewards7) {
        this.shiftCPicksForRewards7 = shiftCPicksForRewards7;
    }

    public String getShiftABPicksForRewards8() {
        return shiftABPicksForRewards8;
    }

    public void setShiftABPicksForRewards8(String shiftABPicksForRewards8) {
        this.shiftABPicksForRewards8 = shiftABPicksForRewards8;
    }

    public String getShiftCPicksForRewards8() {
        return shiftCPicksForRewards8;
    }

    public void setShiftCPicksForRewards8(String shiftCPicksForRewards8) {
        this.shiftCPicksForRewards8 = shiftCPicksForRewards8;
    }

    public String getShiftABPicksForRewards9() {
        return shiftABPicksForRewards9;
    }

    public void setShiftABPicksForRewards9(String shiftABPicksForRewards9) {
        this.shiftABPicksForRewards9 = shiftABPicksForRewards9;
    }

    public String getShiftCPicksForRewards9() {
        return shiftCPicksForRewards9;
    }

    public void setShiftCPicksForRewards9(String shiftCPicksForRewards9) {
        this.shiftCPicksForRewards9 = shiftCPicksForRewards9;
    }

    public String getShiftABPicksForRewards10() {
        return shiftABPicksForRewards10;
    }

    public void setShiftABPicksForRewards10(String shiftABPicksForRewards10) {
        this.shiftABPicksForRewards10 = shiftABPicksForRewards10;
    }

    public String getShiftCPicksForRewards10() {
        return shiftCPicksForRewards10;
    }

    public void setShiftCPicksForRewards10(String shiftCPicksForRewards10) {
        this.shiftCPicksForRewards10 = shiftCPicksForRewards10;
    }

    public String getReward1() {
        return reward1;
    }

    public void setReward1(String reward1) {
        this.reward1 = reward1;
    }

    public String getReward2() {
        return reward2;
    }

    public void setReward2(String reward2) {
        this.reward2 = reward2;
    }

    public String getReward3() {
        return reward3;
    }

    public void setReward3(String reward3) {
        this.reward3 = reward3;
    }

    public String getReward4() {
        return reward4;
    }

    public void setReward4(String reward4) {
        this.reward4 = reward4;
    }

    public String getReward5() {
        return reward5;
    }

    public void setReward5(String reward5) {
        this.reward5 = reward5;
    }

    public String getReward6() {
        return reward6;
    }

    public void setReward6(String reward6) {
        this.reward6 = reward6;
    }

    public String getReward7() {
        return reward7;
    }

    public void setReward7(String reward7) {
        this.reward7 = reward7;
    }

    public String getReward8() {
        return reward8;
    }

    public void setReward8(String reward8) {
        this.reward8 = reward8;
    }

    public String getReward9() {
        return reward9;
    }

    public void setReward9(String reward9) {
        this.reward9 = reward9;
    }

    public String getReward10() {
        return reward10;
    }

    public void setReward10(String reward10) {
        this.reward10 = reward10;
    }
}
