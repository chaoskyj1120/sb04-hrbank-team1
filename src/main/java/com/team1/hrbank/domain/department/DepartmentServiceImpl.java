package com.team1.hrbank.domain.department;

import com.team1.hrbank.domain.department.dto.DepartmentDto;
import com.team1.hrbank.domain.department.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.request.DepartmentUpdateRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

  private final DepartmentRepository departmentRepository;
  private final DepartmentMapper departmentMapper;

  @Override
  public DepartmentDto create(DepartmentCreateRequestDto departmentCreateRequestDto) {
    Department departmentEntity = departmentMapper.toDepartment(departmentCreateRequestDto);
    if (departmentRepository.existsByName(departmentEntity.getName())) {
      throw new IllegalArgumentException("중복된 이름이 존재 합니다.");
    }

    Department savedDepartment = departmentRepository.save(departmentEntity);
    // 부서에 소속된 직원 수 조회가 완성 됐을 시 employeeRepository 참조하여 아래 메서드를 return 으로 변경
    // DepartmentDto departmentDto = this.toDepartmentDto(savedDepartment);

    return departmentMapper.toDepartmentDto(savedDepartment);
  }

  @Override
  public DepartmentDto update(DepartmentUpdateRequestDto departmentUpdateRequestDto) {
    return null;
  }

  @Override
  public List<DepartmentDto> findAll() {
    return List.of();
  }

  @Override
  public DepartmentDto findById(long id) {
    return null;
  }

  @Override
  public void delete(long id) {

  }

  // 부서별 직원 수 조회용 변환 메서드 분리
   /* private DepartmentDto toDepartmentDto(Department department) {
    int employeeCount = (int) employeeRepository.countByDepartmentId(department.getId());
    return new DepartmentDto(
        department.getId(),
        department.getName(),
        department.getDescription(),
        department.getEstablishedDate(),
        employeeCount
    );
  } */
}