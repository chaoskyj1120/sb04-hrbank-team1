package com.team1.hrbank.domain.department.request;

import java.time.LocalDate;


public record DepartmentCreateRequestDto(
    String name,             // 생성할 부서 이름
    String description,      // 생성할 부서 설명
    LocalDate establishedDate   // 부서 설립일
) {

}
