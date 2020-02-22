package org.dmace.security.demo.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class UserEntity implements UserDetails {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String avatar;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    @CreatedDate
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
