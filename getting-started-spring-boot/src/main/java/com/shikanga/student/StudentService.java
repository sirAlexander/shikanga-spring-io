package com.shikanga.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

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

    @Transactional
    public void updateStudent(Long studentId, Student student) {
        studentRepository.findById(studentId)
                .ifPresentOrElse(
                        existingStudent -> {
                            updateNameIfValid(student.getName(), existingStudent);
                            updateEmailIfValid(student.getEmail(), existingStudent);
                        },
                        () -> {
                            throw new IllegalStateException(
                                    "student with id " + studentId + " does not exist"
                            );
                        }
                );

    }

    private void updateNameIfValid(String name, Student existingStudent) {
        if (name != null
                && name.length() > 0
                && !Objects.equals(existingStudent.getName(), name)) {
            existingStudent.setName(name);
        }

    }

    private void updateEmailIfValid(String email, Student existingStudent) {

        if (email != null
                && email.length() > 0
                && !Objects.equals(existingStudent.getEmail(), email)) {

            studentRepository.findStudentByEmail(email)
                    .ifPresent(student -> {
                        throw new IllegalStateException("email taken");
                    });

            existingStudent.setEmail(email);
        }

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
