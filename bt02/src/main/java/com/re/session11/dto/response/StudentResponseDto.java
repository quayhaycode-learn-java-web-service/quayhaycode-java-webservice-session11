package com.re.session11.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class StudentResponseDto {
    private Long id;
    private String name;
    private String email;
    private Double score;
    private LocalDate createdAt;
}
