package com.aulas.devdojo.awesome.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.aulas.devdojo.awesome.model.User;
import com.aulas.devdojo.awesome.repository.UserRepository;

@Component
public class CustomUserDatailService implements UserDetailsService {
	private final UserRepository userRepository;

	@Autowired
	public CustomUserDatailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		User user = Optional.ofNullable(this.userRepository.findByUsername(username))
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		System.out.println(user.getNome());
		System.out.println(user.getPassword());
		System.out.println(user.isAdmin());
		System.out.println(user.getUsername());
		List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
				user.isAdmin() ? authorityListAdmin : authorityListUser);
	}

}
