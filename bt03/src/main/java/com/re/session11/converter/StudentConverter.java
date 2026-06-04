package com.re.session11.converter;

import com.re.session11.dto.request.StudentRequestDto;
import com.re.session11.dto.response.StudentResponseDto;
import com.re.session11.entity.Student;

import java.util.List;

public interface StudentConverter {
    StudentResponseDto toDto(Student entity);
    Student toEntity(StudentRequestDto dto);
    List<StudentResponseDto> toDtos(List<Student> entities);
}
