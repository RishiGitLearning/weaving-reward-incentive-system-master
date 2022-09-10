package com.technoride.RewardIncentiveSystem.Id;

import java.util.Objects;

public class WeavingPickBasedIncentiveTargetId {
    private String productGroup;
    private String slabNo;

    public WeavingPickBasedIncentiveTargetId() {
    }

    public WeavingPickBasedIncentiveTargetId(String productGroup, String slabNo) {
        this.productGroup = productGroup;
        this.slabNo = slabNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeavingPickBasedIncentiveTargetId that = (WeavingPickBasedIncentiveTargetId) o;
        return Objects.equals(productGroup, that.productGroup) && Objects.equals(slabNo, that.slabNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productGroup, slabNo);
    }
}
