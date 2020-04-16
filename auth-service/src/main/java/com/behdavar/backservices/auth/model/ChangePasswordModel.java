package com.behdavar.backservices.auth.model;

import com.behdavar.backservices.auth.model.supper.AuthBaseModel;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.validation.constraints.NotNull;

/**
 * @author Abbas Alishirvani
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = ChangePasswordModel.class, property = "id")
public class ChangePasswordModel extends AuthBaseModel<Long> {

    @NotNull
    private String username;
    @NotNull
    private String newPassword;
    private String oldPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
