package com.example.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String userId;

    private String password;

    private Integer roleId;

    private String profilePic;

    private Integer isLock;

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic == null ? null : profilePic.trim();
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    //获取用户的角色名称
    public String getRole(int roleId){
        if(roleId==1)
            return "ADMIN";
        if(roleId==2)
            return "COOK";
        if(roleId==3)
            return "WAITER";
        return null;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", password=").append(password);
        sb.append(", roleId=").append(roleId);
        sb.append(", profilePic=").append(profilePic);
        sb.append(", isLock=").append(isLock);
        sb.append("]");
        return sb.toString();
    }
}