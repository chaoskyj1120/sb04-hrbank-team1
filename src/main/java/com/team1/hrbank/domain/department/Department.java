package com.team1.hrbank.domain.department;

import com.team1.hrbank.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "departments")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department extends BaseEntity {

  @Column(name = "name", nullable = false, length = 50)
  private String name;                       // 부서 이름

  @Column(name = "description", nullable = false, length = 255)
  private String description;                // 부서 설명

  @Column(name = "established_date", nullable = false)
  private LocalDate establishedDate;     // 부서 설립일

  /* 직원 리스트
  @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
  private List<Employee> employees = new ArrayList<>();
   */
}
