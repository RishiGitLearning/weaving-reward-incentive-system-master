package com.technoride.RewardIncentiveSystem.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class MonthlyAttendanceId implements Serializable{

    private String docNo;
    private Date docDate;
    private String productCategory;
    private String masEmpid;
    private Integer masMM;
    private Integer masYYYY;

    public MonthlyAttendanceId() {
    }

    public MonthlyAttendanceId(String docNo, Date docDate, String productCategory, String masEmpid, Integer masMM, Integer masYYYY) {
        this.docNo = docNo;
        this.docDate = docDate;
        this.productCategory = productCategory;
        this.masEmpid = masEmpid;
        this.masMM = masMM;
        this.masYYYY = masYYYY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthlyAttendanceId that = (MonthlyAttendanceId) o;
        return Objects.equals(docNo, that.docNo) && Objects.equals(docDate, that.docDate) && Objects.equals(productCategory, that.productCategory) && Objects.equals(masEmpid, that.masEmpid) && Objects.equals(masMM, that.masMM) && Objects.equals(masYYYY, that.masYYYY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docNo, docDate, productCategory, masEmpid, masMM, masYYYY);
    }
}
