package com.technoride.RewardIncentiveSystem.Id;

import java.io.Serializable;
import java.util.Objects;

public class WeaverProductionTargetsId implements Serializable {
    private Integer loom;
    private String weftDetails;

    public WeaverProductionTargetsId() {
    }

    public WeaverProductionTargetsId(Integer loom, String weftDetails) {
        this.loom = loom;
        this.weftDetails = weftDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeaverProductionTargetsId that = (WeaverProductionTargetsId) o;
        return Objects.equals(loom, that.loom) && Objects.equals(weftDetails, that.weftDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loom, weftDetails);
    }
}
