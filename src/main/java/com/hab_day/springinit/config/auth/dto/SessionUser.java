package com.hab_day.springinit.config.auth.dto;

import com.hab_day.springinit.domain.user.User;
import lombok.Getter;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        System.out.println("세션 저장");
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
