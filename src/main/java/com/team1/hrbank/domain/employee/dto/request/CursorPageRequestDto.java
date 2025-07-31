package com.team1.hrbank.domain.employee.dto.request;

import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursorPageRequestDto {

  private String nameOrEmail;
  private SortField sortField; // name, employeeNumber, hireDate
  private SortDirection sortDirection; // ASC, DESC
  private Integer size;
  private String departmentName;
  private String position;
  private EmployeeStatus status;
}