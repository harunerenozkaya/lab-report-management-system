package com.harun.labManagement.security;

import com.harun.labManagement.model.User;
import com.harun.labManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LabUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    private List<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority(user.getRole().trim()));

        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findById(Long.valueOf(username));
        User user = null;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        else{
            throw new UsernameNotFoundException("There is no user with this ID");
        }

        return new LabUserDetails(new org.springframework.security.core.userdetails.User(user.getUserId().toString(),
                user.getUserPassword().trim(),
                getAuthorities(user)));
    }
}
