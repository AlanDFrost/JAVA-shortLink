package org.myproject.shortlink.admin.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

/*
* 用户返回参数响应
* */
@Data
public class UserRespDTO {
    private Long id;
    private String userName;
    private String realName;
    private String phone;
    private String mail;
    private LocalDateTime deletionTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean deleteFlag;

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", deletionTime=" + deletionTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDateTime getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(LocalDateTime deletionTime) {
        this.deletionTime = deletionTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
