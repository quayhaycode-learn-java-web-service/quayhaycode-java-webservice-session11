package com.re.session11.service;

import com.re.session11.dto.request.StudentRequestDto;
import com.re.session11.dto.response.StudentResponseDto;

import java.util.List;

public interface StudentService {
    List<StudentResponseDto> getStudents();
    StudentResponseDto getStudent(Long id);
    StudentResponseDto createStudent(StudentRequestDto studentRequestDto);
    StudentResponseDto updateStudent(Long id, StudentRequestDto studentRequestDto);
    StudentResponseDto updateScore(Long id, Double newScore);
    void deleteStudent(Long id);
}
