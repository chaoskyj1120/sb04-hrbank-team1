package com.team1.hrbank.domain.employee.dto.response;

import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import java.util.List;

public record CursorPageResponseEmployeeDto(
    List<EmployeeDto> content,
    String nextCursor,
    Long nextIdAfter,
    Integer size,
    Long totalElements,
    Boolean hasNext
) {

}