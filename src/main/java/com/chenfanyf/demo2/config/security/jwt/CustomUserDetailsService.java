package com.chenfanyf.demo2.config.security.jwt;

import com.chenfanyf.demo2.entity.User;
import com.chenfanyf.demo2.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Jinhe in 2020. [e-mail: 943348115@qq.com]
 * @version  v1
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Resource
    UserRepo userRepo;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Let people login with ether username or email
        User user = userRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username or email : " + username));
        return UserPrincipal.create(user);
    }

    // This method is used by JwtAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
}
