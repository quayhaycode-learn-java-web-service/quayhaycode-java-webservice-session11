package com.re.session11.converter.impl;

import com.re.session11.converter.StudentConverter;
import com.re.session11.dto.request.StudentRequestDto;
import com.re.session11.dto.response.StudentResponseDto;
import com.re.session11.entity.Student;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentConverterImpl implements StudentConverter {

    public StudentResponseDto toDto(Student entity) {
        if (entity == null) return null;
        StudentResponseDto dto = new StudentResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setScore(entity.getScore());
        dto.setCreatedAt(LocalDate.now());
        return dto;
    }

    public Student toEntity(StudentRequestDto dto) {
        if (dto == null) return null;
        Student entity = new Student();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setScore(dto.getScore());
        entity.setCreatedAt(LocalDate.now());
        return entity;
    }

    public List<StudentResponseDto> toDtos(List<Student> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
