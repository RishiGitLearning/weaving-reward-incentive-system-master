package com.technoride.RewardIncentiveSystem.Id;

import java.io.Serializable;
import java.util.Objects;

public class PtaxMonthId implements Serializable {
    private String ptEmpCode;
    private Integer ptMonth;
    private Integer ptYear;

    public PtaxMonthId() {
    }

    public PtaxMonthId(String ptEmpCode, Integer ptMonth, Integer ptYear) {
        this.ptEmpCode = ptEmpCode;
        this.ptMonth = ptMonth;
        this.ptYear = ptYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PtaxMonthId that = (PtaxMonthId) o;
        return Objects.equals(ptEmpCode, that.ptEmpCode) && Objects.equals(ptMonth, that.ptMonth) && Objects.equals(ptYear, that.ptYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ptEmpCode, ptMonth, ptYear);
    }
}
