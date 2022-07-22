package com.example.demo.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.DTO.Student;


@Repository
public interface DemoSQLRepository extends CrudRepository<Student, String>{

}