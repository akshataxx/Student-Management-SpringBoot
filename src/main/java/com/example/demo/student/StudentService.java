package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//SERVICE LAYER that gives data back to the API LAYER
//to make sure that this is spring bean we use component/service annotation
//preferred is service for semantics/readability
@Service
public class StudentService {
    private final StudentRepository studentRepository ;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        //this is where we will connect to the database
    }
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    //business logic to check whether email is already present in db then throw error
    //is email is not present then it should add student
    public void addNewStudent(Student student) {
        /*studentRepository.findStudentByEmail(student.getEmail())
                .ifPresentOrElse(s -> {
                    throw new IllegalStateException("email taken");
                }, () -> {
                    studentRepository.save(student);
                });*/
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }

        //so that the data from post request is saved to the database
        studentRepository.save(student);
        System.out.println(student);
    }

    //business logic to check whether student exists in db then delete
    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }


    //business logic to update the student details in db based on id
    //Generally the @Transactional annotation is written at the service level. It is
    // used to combine more than one writes on a database as a single atomic operation.
    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        //if student is not present then throw exception
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exist"
                ));

        if (name != null && name.length() > 0 && !name.equals(student.getName())){
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !email.equals(student.getEmail())){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            //also checking if the email hasn't been taken (back to the main business logic
            //of checking if email is already present in db then throw error
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
        //when you have @transactional you don't need a query for above ^ like the query
        //present in the StudentRepository for post mapping

    }
}