package com.technoride.RewardIncentiveSystem.entity;

import com.technoride.RewardIncentiveSystem.Id.FeltProdLoomWeaverId;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_FELT_PROD_LOOM_WVR_DETAIL")
@IdClass(FeltProdLoomWeaverId.class)
public class FeltProdLoomWeaver {
    @Id
    @Column(name = "SR_NO")
    private Integer srNo;

    @Id
    @Column(name = "DOC_NO")
    private String docNo;

    @Id
    @Column(name = "DOC_DATE")
    private Date docDate;

    @Id
    @Column(name = "SHIFT_ID")
    private String shiftId;

    @Column(name = "EMP_NAME")
    private String empName;

    @Column(name = "EMP_TYPE")
    private String empType;

    @Id
    @Column(name = "EMP_NO")
    private String empNo;

    @Column(name = "REG_ROKDI")
    private String regRokdi;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "LOOM_ENG")
    private String loomEng;

    @Column(name = "BEAM_WARP_SR_NO")
    private String beamWarpSrNo;

    @Column(name = "PRODUCT_GROUP")
    private String productGroup;

    @Column(name = "PIECE_NO")
    private String pieceNo;

    @Column(name = "WEFT_DETAILS")
    private String weftDetails;

    @Column(name = "PICKS_10CM")
    private String picks10CM;

    @Column(name = "START_READING")
    private String startReading;

    @Column(name = "END_READING")
    private String endReading;

    @Column(name = "PICK")
    private String pick;

    @Column(name = "TOTAL_WEAVE_TIME")
    private String totalWeaveTime;

    @Column(name = "NO_WEAVER")
    private String noWeaver;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "DESIGNATION_GRADE")
    private Double designationGrade;

    @Column(name = "CATEGORY_GRADE")
    private Double categoryGrade;

    @Column(name = "PRESENT_STATUS")
    private String presentStatus;

    @Column(name = "ROKDI")
    private Double rokdi;

    @Column(name = "PRESENT")
    private Double present;
}
