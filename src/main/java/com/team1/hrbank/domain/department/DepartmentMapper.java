package com.team1.hrbank.domain.department;

import com.team1.hrbank.domain.department.dto.DepartmentDto;
import com.team1.hrbank.domain.department.request.DepartmentCreateRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

  Department toDepartment(DepartmentCreateRequestDto departmentCreateRequestDto);

  DepartmentDto toDepartmentDto(Department departmentEntity);

}
