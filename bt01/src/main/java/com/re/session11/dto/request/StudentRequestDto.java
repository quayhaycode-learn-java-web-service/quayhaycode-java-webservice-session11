package com.re.session11.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class StudentRequestDto {
    private Long id;
    private String name;
    private String email;
    private Double score;
}
