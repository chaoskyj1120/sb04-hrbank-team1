package com.team1.hrbank.domain.employee.service;

import com.team1.hrbank.domain.department.entity.Department;
import com.team1.hrbank.domain.department.repository.DepartmentRepository;
import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.dto.request.EmployeeUpdateRequestDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import com.team1.hrbank.domain.employee.mapper.EmployeeMapper;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import com.team1.hrbank.domain.employee.dto.request.EmployeeCreateRequestDto;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;
  private final FileMetaDataService fileMetaDataService;
  private final EmployeeMapper employeeMapper;

  @Transactional
  public EmployeeDto createEmployee(EmployeeCreateRequestDto employeeCreateRequestDto,
      MultipartFile profile) {

    validateDuplicateEmail(employeeCreateRequestDto.email());
    Department department = getValidateDepartment(employeeCreateRequestDto.departmentId());

    FileMetaData fileMetaData = null;
    if (profile != null && !profile.isEmpty()) {
      fileMetaData = fileMetaDataService.createFileMetaData(profile);
    }

    String employeeNumber = createEmployeeNumber();
    Employee employee = employeeMapper.toEmployee(employeeCreateRequestDto, employeeNumber,
        EmployeeStatus.ACTIVE, department,
        fileMetaData);

    // TODO employeeCreateRequest.memo() 를 사용해서 수정 로그 남기는것 추가 필요함!!

    return employeeMapper.toEmployeeDto(employeeRepository.save(employee));
  }

  @Transactional
  public EmployeeDto updateEmployee(long employeeId,
      EmployeeUpdateRequestDto employeeUpdateRequestDto,
      MultipartFile profile) {
    validateDuplicateEmail(employeeUpdateRequestDto.email());
    Department department = getValidateDepartment(employeeUpdateRequestDto.departmentId());
    Employee employee = getValidateEmployee(employeeId);

    employee.setName(employeeUpdateRequestDto.name());
    employee.setEmail(employeeUpdateRequestDto.email());
    employee.setPosition(employeeUpdateRequestDto.position());
    employee.setHireDate(employeeUpdateRequestDto.hireDate());

    EmployeeStatus employeeStatus = EmployeeStatus.valueOf(employeeUpdateRequestDto.status());
    employee.setStatus(employeeStatus);

    employee.setDepartment(department);

    FileMetaData fileMetaData = null;
    if (profile != null && !profile.isEmpty()) {
      fileMetaData = fileMetaDataService.createFileMetaData(profile);
    }
    employee.setFileMetaData(fileMetaData);

    return employeeMapper.toEmployeeDto(employeeRepository.save(employee));
    // TODO employeeUpdateRequestDto.memo() 를 사용해서 수정 로그 남기는것 추가 필요함!!
  }


  private String createEmployeeNumber() {
    String prefix = "EMP"; // 직원
    String year = String.valueOf(LocalDate.now().getYear()); // 연도

    String timestamp = String.valueOf(System.currentTimeMillis());  // 13자리
    int randomTwoDigits = (int) (Math.random() * 90) + 10;         // 10~99 사이 두자리 난수
    String unique = randomTwoDigits + timestamp; // 랜덤한 15자리 숫자

    String employeeNumber = prefix + "-" + year + "-" + unique;

    // 중복 확인 후 재귀 호출
    if (employeeRepository.findByEmployeeNumber(employeeNumber).isPresent()) {
      return createEmployeeNumber(); // 중복 시 다시 생성
    }

    return employeeNumber;
  }

  private Department getValidateDepartment(Long departmentId) {
    return departmentRepository.findById(departmentId)
        .orElseThrow(() -> new IllegalArgumentException("Department not found"));
  }

  private void validateDuplicateEmail(String email) {
    if (employeeRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("Email already in use");
    }
  }

  private Employee getValidateEmployee(long employeeId) {
    Optional<Employee> employee = employeeRepository.findById(employeeId);
    if (employee.isEmpty()) {
      throw new IllegalArgumentException("Employee not found");
    }
    return employee.get();
  }

}