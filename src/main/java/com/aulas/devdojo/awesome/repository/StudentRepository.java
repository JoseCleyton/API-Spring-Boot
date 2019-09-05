package com.aulas.devdojo.awesome.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aulas.devdojo.awesome.model.Student;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {

	List<Student> findByNameIgnoreCaseContaining(String name);
}
