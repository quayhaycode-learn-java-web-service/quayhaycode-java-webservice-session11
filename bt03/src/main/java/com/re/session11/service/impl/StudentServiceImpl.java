package com.re.session11.service.impl;

import com.re.session11.converter.StudentConverter;
import com.re.session11.dto.request.StudentRequestDto;
import com.re.session11.dto.response.StudentResponseDto;
import com.re.session11.entity.Student;
import com.re.session11.repository.StudentRepository;
import com.re.session11.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentConverter studentConverter;

    @Override
    public List<StudentResponseDto> getStudents() {
        return studentConverter.toDtos(studentRepository.findAll());
    }

    @Override
    public StudentResponseDto getStudent(Long id) {
        return studentRepository.findById(id)
                .map(studentConverter::toDto)
                .orElse(null);
    }

    @Override
    public StudentResponseDto createStudent(StudentRequestDto dto) {
        boolean emailExists = studentRepository.existsByEmail(dto.getEmail());

        if (emailExists) {
            throw new RuntimeException("The email already exists!");
        }

        Student entity = studentConverter.toEntity(dto);
        Student savedEntity = studentRepository.save(entity);
        return studentConverter.toDto(savedEntity);
    }

    @Override
    public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No students found to update!"));

        if (!existingStudent.getEmail().equalsIgnoreCase(dto.getEmail())) {
            boolean emailExists = studentRepository.existsByEmail(dto.getEmail());
            if (emailExists) {
                throw new RuntimeException("The new email address is already taken!");
            }
        }

        existingStudent.setName(dto.getName());
        existingStudent.setEmail(dto.getEmail());
        existingStudent.setScore(dto.getScore());

        Student updatedEntity = studentRepository.save(existingStudent);
        return studentConverter.toDto(updatedEntity);
    }

    @Override
    public StudentResponseDto updateScore(Long id, Double newScore) {
        if (newScore < 0) {
            throw new RuntimeException("Error: The score cannot be negative!");
        }
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found id: " + id));

        student.setScore(newScore);
        Student updatedStudent = studentRepository.save(student);
        return studentConverter.toDto(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Not found student to delete!");
        }
        studentRepository.deleteById(id);
    }
}
