package com.technoride.RewardIncentiveSystem.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WI_WEAVING_PICK_BASED_INCENTIVE_TARGET")
public class WeavingPickBasedIncentiveTarget implements Serializable {
    @Id
    @Column(name = "PRODUCT_GROUP")
    private String productGroup;
    @Id
    @Column(name = "SLAB_NO")
    private String slabNo;

    @Column(name = "MIN_PICK_KG")
    private Double minPickKg;

    @Column(name = "MAX_PICK_KG")
    private Double maxPickKg;

    @Column(name = "RATE")
    private Double rate;
}
