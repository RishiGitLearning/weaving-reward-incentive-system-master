package com.technoride.RewardIncentiveSystem.model;

import com.technoride.RewardIncentiveSystem.entity.CompanyUser;

public class LoggedUser {
    public CompanyUser user;
    private static LoggedUser loggedUser = null;

    private LoggedUser() {
    }

    public CompanyUser getUser() {
        return user;
    }

    public void setUser(CompanyUser user) {
        this.user = user;
    }

    public static LoggedUser getInstance()
    {
        if (loggedUser == null)
            loggedUser = new LoggedUser();

        return loggedUser;
    }
}
