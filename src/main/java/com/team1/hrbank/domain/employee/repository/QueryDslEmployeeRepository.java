package com.team1.hrbank.domain.employee.repository;

import com.team1.hrbank.domain.employee.dto.request.CursorPageRequestDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import java.util.List;

public interface QueryDslEmployeeRepository {

  List<Employee> findEmployeesByRequest(CursorPageRequestDto cursorPageRequestDto);
}
