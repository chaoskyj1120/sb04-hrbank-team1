package com.team1.hrbank.domain.department;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

  Department save(Department department);

  Optional<Department> findById(long id);

  List<Department> findByName(String name);

  void deleteById(long id);

  boolean existsByName(String name);

  String name(String name);

  Optional<Department> findDepartmentByDepartmentId(long departmentId);
}
