package com.aulas.devdojo.awesome.endpoint;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aulas.devdojo.awesome.error.ResourcesNotFoundException;
import com.aulas.devdojo.awesome.model.Student;
import com.aulas.devdojo.awesome.repository.StudentRepository;

@RestController
@RequestMapping("students")
public class StudentEndPoint {
	private final StudentRepository studentDao;

	@Autowired
	public StudentEndPoint(StudentRepository studentDao) {
		this.studentDao = studentDao;
	}

	@GetMapping
	public ResponseEntity<?> listAll(Pageable pageable) {
		return new ResponseEntity<>(this.studentDao.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping(path = ("/{id}"))
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
		verifyStudentExists(id);
		Optional<Student> student = studentDao.findById(id);

		return new ResponseEntity<>(student, HttpStatus.OK);

	}

	@GetMapping(path = "/findByName/{name}")
	public ResponseEntity<?> findStudentsByName(@PathVariable String name) {
		return new ResponseEntity<>(this.studentDao.findByNameIgnoreCaseContaining(name), HttpStatus.OK);

	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> save(@Valid @RequestBody Student student) {
		// Pode mandar como resposta a URL mais a URI do novo objeto que foi salvo, para
		// o cliente
		// visualizar o objeto salvo. ex: http://localhost:8080/students/3 <- "indice do
		// novo objeto"
		// Ou pode retornar o próprio objeto para ser utilizado pelo cliente como no
		// exemplo abaixo.

		return new ResponseEntity<>(this.studentDao.save(student), HttpStatus.OK);

	}

	@DeleteMapping
	@Transactional
	public ResponseEntity<?> delete(@RequestBody Student student) {
		verifyStudentExists(student.getId());
		this.studentDao.delete(student);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> put(@Valid @RequestBody Student student) {
		verifyStudentExists(student.getId());
		this.studentDao.save(student);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	private void verifyStudentExists(Long id) {
		Optional<Student> student = studentDao.findById(id);
		if (!student.isPresent())
			throw new ResourcesNotFoundException("Student not found for ID: " + id);
	}
}
