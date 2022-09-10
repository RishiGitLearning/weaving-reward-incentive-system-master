package com.technoride.RewardIncentiveSystem.Id;

import java.io.Serializable;
import java.util.Objects;

public class CompanyUserId implements Serializable {

    private Long companyId;
    private Long userId;
    private String loginId;

    public CompanyUserId() {
    }

    public CompanyUserId(Long companyId, Long userId, String loginId) {
        this.companyId = companyId;
        this.userId = userId;
        this.loginId = loginId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyUserId that = (CompanyUserId) o;
        return Objects.equals(companyId, that.companyId) && Objects.equals(userId, that.userId) && Objects.equals(loginId, that.loginId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, userId, loginId);
    }
}
