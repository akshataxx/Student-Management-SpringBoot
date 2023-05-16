package com.example.demo.student;

//DATA ACCESS LAYER
//use this interface inside the service

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

    //this is basically just select * from student where email = ?
    //Student here is essentially Student class (where the @entity annotation is)
    //You dont ned the query annotation line and you can only have the optional line but better to have both
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
}
