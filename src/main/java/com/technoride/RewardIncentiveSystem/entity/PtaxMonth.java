package com.technoride.RewardIncentiveSystem.entity;

import com.technoride.RewardIncentiveSystem.Id.PtaxMonthId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_ATTPAY_MTH_PTAX")
@IdClass(PtaxMonthId.class)
public class PtaxMonth implements Serializable {
        @Id
        @Column(name = "PT_EMPCODE")
        private String ptEmpCode;
        @Id
        @Column(name = "PT_MONTH")
        private int ptMonth;
        @Id
        @Column(name = "PT_YEAR")
        private int ptYear;

        @Column(name = "PT_SALARY_GROSS", precision = 15, scale = 2)
        private Double ptSalaryGross;

        @Column(name = "PT_SALARY_PTAX")
        private Double ptSalaryPtax;

        @Column(name = "PT_COFF_GROSS")
        private Double ptCoffGross;

        @Column(name = "PT_COFF_PTAX")
        private Double ptCoffPtax;

        @Column(name = "PT_WVR_REWARD_GROSS")
        private Double ptWvrRewardGross;

        @Column(name = "PT_WVR_REWARD_PTAX")
        private Double ptWvrRewardPtax;

        @Column(name = "PT_WVG_INC_GROSS")
        private Double ptWvgIncGross;

        @Column(name = "PT_WVG_INC_PTAX")
        private Double ptWvgIncPtax;

        @Column(name = "PT_TOTAL_GROSS")
        private Double ptTotalGross;

        @Column(name = "PT_TOTAL_PTAX")
        private Double ptTotalPtax;

        @Column(name = "PTAX_PAID_DATE")
        private Date ptaxPaidDate;

        @Transient
        private String ptEmpName;

        @Transient
        private int id;

        @Transient
        private int netAmount;
}
