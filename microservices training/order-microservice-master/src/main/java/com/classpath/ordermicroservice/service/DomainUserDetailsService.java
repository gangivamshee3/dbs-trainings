package com.classpath.ordermicroservice.service;

import com.classpath.ordermicroservice.model.Role;
import com.classpath.ordermicroservice.model.User;
import com.classpath.ordermicroservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class DomainUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Came inside the loadUserByUsername :: {}", username);
        UserDetails userDetails = this.userRepository
                .findByEmail(username)
                .map(DomainUserDetails:: new)
                .orElseThrow(() -> new UsernameNotFoundException("invalid username/password"));
        log.info("User Details:: "+userDetails.getUsername());
        log.info("User Details:: "+userDetails.getPassword());
        log.info("User Details:: "+userDetails.getAuthorities());
        return userDetails;
    }
}

class DomainUserDetails implements UserDetails {

    private final User user;
    private final String password;
    private final String email;

    public DomainUserDetails(User user){
        this.user = user;
        this.password = user.getPassword();
        this.email = user.getEmail();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user
                .getRoles()
                .stream()
                .map(Role::getRole)
                .map(SimpleGrantedAuthority::new)
                .collect(toUnmodifiableSet());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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