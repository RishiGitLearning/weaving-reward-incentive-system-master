package com.technoride.RewardIncentiveSystem.Id;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;


public class FeltProdLoomWeaverId implements Serializable {
    private Integer srNo;
    private String docNo;
    private Date docDate;
    private String shiftId;

    public FeltProdLoomWeaverId() {
    }

    public FeltProdLoomWeaverId(Integer srNo, String docNo, Date docDate, String shiftId) {
        this.srNo = srNo;
        this.docNo = docNo;
        this.docDate = docDate;
        this.shiftId = shiftId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeltProdLoomWeaverId that = (FeltProdLoomWeaverId) o;
        return Objects.equals(srNo, that.srNo) && Objects.equals(docNo, that.docNo) && Objects.equals(docDate, that.docDate) && Objects.equals(shiftId, that.shiftId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(srNo, docNo, docDate, shiftId);
    }
}
