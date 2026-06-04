package com.re.session11;


import com.re.session11.entity.Student;
import com.re.session11.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("")
    public void testFinalAll_Success(){
        studentRepository.save(new Student(null, "student 1", "email", 9.10, LocalDate.now()));

        int expect = 2;
        List<Student> actual = studentRepository.findAll();

        assertEquals(expect, actual.size());
    }
}
