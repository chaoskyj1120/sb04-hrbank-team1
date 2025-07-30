package com.team1.hrbank.domain.department.request;

import java.time.LocalDate;

public record DepartmentUpdateRequestDto(
    String name,            // 수정할 부서 이름
    String description,     // 수정할 부서 설명
    LocalDate establishedDate // 수정할 부서 설립일
) {

}
