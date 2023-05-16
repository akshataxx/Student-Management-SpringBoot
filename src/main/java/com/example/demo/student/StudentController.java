package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//API LAYER talking to SERVICE LAYER
//this class is responsible for handling the HTTP requests and returning the responses
/*in order to display something on the endpoint browser and to not have 404
	error, we need to add a controller(annotate) and a method that will return a string.
	Because we want to get something from the server, we add a get mapping annotation.
*/
@RestController

//this annotation is used to map web requests onto specific handler classes and/or handler methods
//url path = "localhost:8080/api/v1/student"
@RequestMapping(path = "api/v1/student")
public class StudentController {
    //creating a reference for the StudentService class so we can use the business logic in there
    private final StudentService studentService;

    //StudentService should be autowired for rest. So studentService should be instantiated for rest
    //and then instructed into StudentController constructor
    //Also make sure StudentService is instantiated with spring bean - Component/Service

    @Autowired //dependency injection
    public StudentController(StudentService studentService) {
        this.studentService = studentService; //can be done via dependency injection - autowired
    }
    //using the student service class to get students as opposed to hardcoding it in controller

    @GetMapping
    public List<Student> getStudents(){
        //returns a json object with the student data
        return studentService.getStudents();
    }
    //reason why we are putting request body is because we are trying to map the
    // json blob from client to Student by taking it from the request
    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    //delete mapping is used to delete a student based on the student id
    @DeleteMapping(path="{studentId}")
    public void deleteStudent(
            @PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

    //put mapping is used to update a student based on the student id
    @PutMapping(path="{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email){
        studentService.updateStudent(studentId, name, email);
    }
}
