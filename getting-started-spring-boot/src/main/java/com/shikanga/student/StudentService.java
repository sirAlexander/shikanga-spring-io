package com.shikanga.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired // remember this is optional
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        studentRepository.findStudentByEmail(student.getEmail())
                .ifPresent(existingStudent -> {
                    throw new IllegalStateException("email taken");
                });
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.findById(studentId)
                .ifPresentOrElse(
                        student -> studentRepository.deleteById(studentId),
                        () -> {
                            throw new IllegalStateException(
                                    "student with id " + studentId + " does not exist"
                            );
                        });
    }

}
