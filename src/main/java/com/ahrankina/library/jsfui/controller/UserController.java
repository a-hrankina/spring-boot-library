package com.ahrankina.library.jsfui.controller;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.util.ResourceBundle;

@ManagedBean
@ViewScoped
@Component
@Getter
@Setter
public class UserController {
    private String username;
    private String password;

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean hasRole(String role) {
        return getCurrentUser().getAuthorities().stream().filter(x -> x.getAuthority().equals("ROLE_" + role)).count() > 0;
    }

    public String getLoginFailedMessage() {
        Object obj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginFailed");

        if (obj == null) return "";

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");

        String message = bundle.getString("login_failed");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("loginFailed");
        if (Strings.isNullOrEmpty(message)) {
            return "";
        } else {
            return message;
        }
    }

}
