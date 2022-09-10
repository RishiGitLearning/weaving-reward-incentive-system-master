package com.technoride.RewardIncentiveSystem.entity;

import com.technoride.RewardIncentiveSystem.Id.MonthTotalWeavingProdId;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_MTH_TOTAL_WEAVING_PRODUCTION")
@IdClass(MonthTotalWeavingProdId.class)
public class MonthTotalWeavingProduction {
    @Id
    @Column(name = "PRODUCT_GROUP")
    private String productGroup;
    @Id
    @Column(name = "DEPARTMENT")
    private String department;
    @Id
    @Column(name = "PICK_MTH")
    private Integer pickMonth;
    @Id
    @Column(name = "PICK_YEAR")
    private Integer pickYear;

    @Column(name = "PICKMTR")
    private Double pickMtr;

    @Column(name = "PICKKG")
    private Double pickKg;

    @Column(name = "PIECE_COUNT")
    private Double pieceCount;

    @Column(name = "MTH_WEIGHT")
    private Double mthWeight;
}
