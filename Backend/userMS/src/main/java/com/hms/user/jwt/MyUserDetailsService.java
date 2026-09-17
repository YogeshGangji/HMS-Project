package com.hms.user.jwt;

import com.hms.user.dto.UserDTO;
import com.hms.user.exception.HMSException;
import com.hms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            UserDTO dto = userService.getUserByEmail(email);
            return new CustomUserDetails(dto.getId(), dto.getEmail(), dto.getEmail(), dto.getPassword(), dto.getRole(), dto.getName(),dto.getProfileId(),null);
        }
        catch (HMSException e) {
            e.printStackTrace();
        }
    return null;
    }
}
