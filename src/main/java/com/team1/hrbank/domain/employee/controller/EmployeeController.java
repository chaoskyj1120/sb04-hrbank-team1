package com.team1.hrbank.domain.employee.controller;

import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.dto.request.CursorPageRequestDto;
import com.team1.hrbank.domain.employee.dto.request.EmployeeCreateRequestDto;
import com.team1.hrbank.domain.employee.dto.request.EmployeeUpdateRequestDto;
import com.team1.hrbank.domain.employee.dto.response.CursorPageResponseEmployeeDto;
import com.team1.hrbank.domain.employee.dto.response.EmployeeDistributionDto;
import com.team1.hrbank.domain.employee.dto.response.EmployeeTrendDto;
import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import com.team1.hrbank.domain.employee.entity.TimeUnitType;
import com.team1.hrbank.domain.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
      @RequestPart(value = "profile", required = false) MultipartFile profile,
      HttpServletRequest request) {

    String clientIp = request.getRemoteAddr();
    EmployeeDto employeeDto = employeeService.createEmployee(employeeCreateRequestDto, profile,
        clientIp);
    return ResponseEntity.status(HttpStatus.CREATED).body(employeeDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,
      @RequestPart("employee") EmployeeUpdateRequestDto employeeUpdateRequestDto,
      @RequestPart(value = "profile", required = false) MultipartFile profile,
      HttpServletRequest request) {

    String clientIp = request.getRemoteAddr();
    EmployeeDto employeeDto = employeeService.updateEmployee(id, employeeUpdateRequestDto, profile,
        clientIp);
    return ResponseEntity.ok(employeeDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long id, HttpServletRequest request) {
    String clientIp = request.getRemoteAddr();
    employeeService.deleteEmployee(id, clientIp);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<CursorPageResponseEmployeeDto> findEmployees(
      @RequestParam(required = false) CursorPageRequestDto cursorPageRequestDto) {
    CursorPageResponseEmployeeDto cursorPageResponseEmployeeDto = employeeService.findEmployees(
        cursorPageRequestDto);
    return ResponseEntity.ok(cursorPageResponseEmployeeDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDto> findEmployee(@PathVariable("id") Long id) {
    EmployeeDto employeeDto = employeeService.findEmployee(id);
    return ResponseEntity.ok(employeeDto);
  }

  // 대시보드
  @GetMapping("/count")
  public ResponseEntity<Long> countEmployees(
      @RequestParam(required = false) EmployeeStatus status,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate fromDate,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate toDate) {

    long employeesCount = employeeService.countEmployees(status, fromDate, toDate);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(employeesCount);
  }

  @GetMapping("/stats/trend")
  public ResponseEntity<List<EmployeeTrendDto>> statusTrendEmployees(
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate from,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate to,
      @RequestParam(required = false, defaultValue = "month") String unit) {

    TimeUnitType timeUnitType = TimeUnitType.fromString(unit);
    List<EmployeeTrendDto> trend = employeeService.getEmployeeTrend(from, to, timeUnitType);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(trend);
  }

  @GetMapping("/stats/distribution")
  public ResponseEntity<List<EmployeeDistributionDto>> statusDistributionEmployees(
      @RequestParam(required = false, defaultValue = "department") String groupBy,
      @RequestParam(required = false, defaultValue = "ACTIVE") EmployeeStatus status
  ) {

    List<EmployeeDistributionDto> distribution = employeeService.getEmployeeDistribution(groupBy,
        status);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(distribution);
  }
}