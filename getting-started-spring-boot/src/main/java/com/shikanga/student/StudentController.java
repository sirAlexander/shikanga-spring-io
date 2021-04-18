package com.shikanga.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired // Optional
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestBody Student student) {
        studentService.updateStudent(studentId, student);
    }

    @PutMapping(path = "/alternative/{studentId}")
    public void alternativeUpdateStudent(@PathVariable("studentId") Long studentId,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) String email) {
        studentService.alternativeUpdateStudent(studentId, name, email);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }
}
