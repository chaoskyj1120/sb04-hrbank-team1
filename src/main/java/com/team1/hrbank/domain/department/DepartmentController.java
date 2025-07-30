package com.team1.hrbank.domain.department;

import com.team1.hrbank.domain.department.dto.DepartmentDto;
import com.team1.hrbank.domain.department.request.DepartmentCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

  private final DepartmentService departmentService;

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<DepartmentDto> create(
      @RequestBody DepartmentCreateRequestDto departmentCreateRequestDto) {
    DepartmentDto department = departmentService.create(departmentCreateRequestDto);
    return ResponseEntity.ok(department);
  }

}
