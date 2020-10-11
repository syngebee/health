package com.itheima.health.utils.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UserInfo {

    private Long id;

    private String username;

    private String password;

     private String roles;

    public UserInfo(Long id, String username, String roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
