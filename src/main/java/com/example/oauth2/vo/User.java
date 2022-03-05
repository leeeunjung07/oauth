package com.example.oauth2.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@Data
@Entity
public class User {
    @Column(nullable = true, length = 20)
    private String client;
    @Id
    @Column(nullable = true, length = 50)
    private String id;
    @Column(length = 10)
    private String name;
    @Column(nullable = true, length = 200)
    private String image;
    @Column(nullable = true, unique = true, length = 50)
    private String email;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")); }
    public String getUsername() {
        return this.name; }
    public String getId() {
        return this.id; }
    }

