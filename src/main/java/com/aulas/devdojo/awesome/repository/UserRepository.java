package com.aulas.devdojo.awesome.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.aulas.devdojo.awesome.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUsername(String username);
}
