package com.technoride.RewardIncentiveSystem.entity;

import com.technoride.RewardIncentiveSystem.Id.CompanyUserId;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "D_COM_USER_MASTER")
@IdClass(CompanyUserId.class)
public class CompanyUser {
    @Id
    @Column(name = "COMPANY_ID")
    private Long companyId;

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Id
    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "DEPT_ID")
    private Long deptId;

    @Column(name = "SUPERIOR_ID")
    private int superiorId;

    @Column(name = "USER_TYPE")
    private int userType;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "MODIFIED_BY")
    private Long modifiedBy;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "INTERNAL_EMAIL")
    private String internalEmail;

    @Column(name = "EXTERNAL_EMAIL")
    private String externalEmail;

    @Column(name = "CHANGED")
    private Short changed;

    @Column(name = "CHANGED_DATE")
    private Date changedDate;

    @Column(name = "LOCKED")
    private Short locked;

    @Column(name = "PASSWORD_EXPIRY_DATE")
    private Date passwordExpiryDate;

    @Column(name = "INVOICE_PROCESS_FLAG")
    private Short invoiceProcessFlag;

    @Column(name = "ATTPAY_EMPCODE")
    private String attpayEmpcode;

    @Column(name = "REWARD_ACCESS_RIGHTS_LEVEL")
    private int rewardAccessRightsLevel;

}
