package com.technoride.RewardIncentiveSystem.Id;

import java.io.Serializable;
import java.util.Objects;

public class RewardIncentiveLockId implements Serializable {
    private int month;
    private int year;

    public RewardIncentiveLockId() {
    }

    public RewardIncentiveLockId(int month, int year) {
        this.month = month;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RewardIncentiveLockId that = (RewardIncentiveLockId) o;
        return month == that.month && year == that.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year);
    }
}
