package ru.mmtr.translationdictionary;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.mmtr.translationdictionary.infrastructure.repositories.user.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getRoleName()));
            return new User("argentoff1", "$2a$10$VmPCbUb3Erx9XJEhhanjIepOrGYHs7Qhzw2eH/flO/zG9lZQIeQY.", authorities);
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
