package com.team1.hrbank.domain.employee.controller;


import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.dto.request.CursorPageRequestDto;
import com.team1.hrbank.domain.employee.dto.request.EmployeeCreateRequestDto;
import com.team1.hrbank.domain.employee.dto.request.EmployeeUpdateRequestDto;
import com.team1.hrbank.domain.employee.dto.response.CursorPageResponseEmployeeDto;
import com.team1.hrbank.domain.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "Employee", description = "직원 관리 API")
@RequestMapping("/api/employees")
public class EmployeeController {

  private final EmployeeService employeeService;

  @PostMapping
  public ResponseEntity<EmployeeDto> createEmployee(
      @RequestPart("employee") EmployeeCreateRequestDto employeeCreateRequestDto,
      @RequestPart(value = "profile", required = false) MultipartFile profile) {

    EmployeeDto employeeDto = employeeService.createEmployee(employeeCreateRequestDto, profile);
    return ResponseEntity.status(HttpStatus.CREATED).body(employeeDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,
      @RequestPart("employee") EmployeeUpdateRequestDto employeeUpdateRequestDto,
      @RequestPart(value = "profile", required = false) MultipartFile profile) {

    EmployeeDto employeeDto = employeeService.updateEmployee(id, employeeUpdateRequestDto, profile);
    return ResponseEntity.ok(employeeDto);
  }

  @GetMapping
  public ResponseEntity<CursorPageResponseEmployeeDto> findEmployeesByInfo(
      CursorPageRequestDto cursorPageRequestDto
  ) {
    CursorPageResponseEmployeeDto pagedEmployees = employeeService.findEmployeesByInfo(
        cursorPageRequestDto);
    return ResponseEntity.ok(pagedEmployees);
  }
}