
package com.dinstone.uams.model;

import java.io.Serializable;
import java.util.Date;

public class UserProfile implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    private int id;

    private String nickname;

    private String email;

    private Date createTime;

    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "UserProfile [id=" + id + ", nickname=" + nickname + ", email=" + email + "]";
    }

}
