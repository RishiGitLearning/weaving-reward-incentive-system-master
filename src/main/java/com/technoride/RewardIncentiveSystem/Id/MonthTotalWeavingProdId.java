package com.technoride.RewardIncentiveSystem.Id;

import java.io.Serializable;
import java.util.Objects;

public class MonthTotalWeavingProdId implements Serializable {
    private String productGroup;
    private String department;
    private Integer pickMonth;
    private Integer pickYear;

    public MonthTotalWeavingProdId() {
    }

    public MonthTotalWeavingProdId(String productGroup, String department, Integer pickMonth, Integer pickYear) {
        this.productGroup = productGroup;
        this.department = department;
        this.pickMonth = pickMonth;
        this.pickYear = pickYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthTotalWeavingProdId that = (MonthTotalWeavingProdId) o;
        return Objects.equals(productGroup, that.productGroup) && Objects.equals(department, that.department) && Objects.equals(pickMonth, that.pickMonth) && Objects.equals(pickYear, that.pickYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productGroup, department, pickMonth, pickYear);
    }
}
