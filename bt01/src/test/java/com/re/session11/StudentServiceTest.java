package com.re.session11;

import com.re.session11.converter.StudentConverter;
import com.re.session11.dto.response.StudentResponseDto;
import com.re.session11.entity.Student;
import com.re.session11.repository.StudentRepository;
import com.re.session11.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

//@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @Mock
    StudentConverter studentConverter;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student studentEntity;
    private StudentResponseDto studentResponseDto;

    @BeforeEach // setup mock data
    void setUp(){
        studentEntity = new Student(1l, "dao", "studet@gmail.com", 9.5, LocalDate.now());
        studentResponseDto = new StudentResponseDto(1l, "dao", "studet@gmail.com", 9.5, LocalDate.now());
    }

    @Test
    @DisplayName("return dto when id is provided")
    void getStudent_validId_returnStudentResponseDTO(){
        when(studentRepository.findById(1l)).thenReturn(Optional.of(studentEntity));
        when(studentConverter.toDto(studentEntity)).thenReturn(studentResponseDto);

        StudentResponseDto actual = studentService.getStudent(1l);
        long expect = 1l;

        assertNotNull(actual);
        assertEquals(expect, actual.getId());

    }


}
