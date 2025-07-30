package com.team1.hrbank.domain.department.dto;

import java.time.LocalDate;

public record DepartmentDto(
    long id,                      // 부서 ID
    String name,                  // 부서 이름
    String description,           // 부서 설명
    LocalDate establishedDate,    // 부서 설립일
    int employeeCount              // 부서 직원 수
) {

}
