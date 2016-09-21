
package com.dinstone.uams.model;

import java.util.Date;

public class LocalAccount {

    private int userId;

    private String username;

    private String password;

    private Date createTime;

    private Date updateTime;

    public LocalAccount() {
        super();
    }

    public LocalAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserAccount [userId=" + userId + ", username=" + username + ", createTime=" + createTime
                + ", updateTime=" + updateTime + "]";
    }

}
