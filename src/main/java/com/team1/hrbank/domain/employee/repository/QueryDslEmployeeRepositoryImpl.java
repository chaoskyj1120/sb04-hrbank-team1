package com.team1.hrbank.domain.employee.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.team1.hrbank.domain.employee.dto.request.CursorPageRequestDto;
import com.team1.hrbank.domain.employee.dto.request.SortDirection;
import com.team1.hrbank.domain.employee.dto.request.SortField;
import com.team1.hrbank.domain.employee.entity.Employee;
import jakarta.persistence.EntityManager;
import java.util.List;
import com.querydsl.jpa.impl.JPAQueryFactory;


public class QueryDslEmployeeRepositoryImpl implements QueryDslEmployeeRepository {

  private final JPAQueryFactory queryFactory;

  public QueryDslEmployeeRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<Employee> findEmployeesByRequest(CursorPageRequestDto cursorPageRequestDto) {

    QEmployee employee = QEmployee.employee;
    PathBuilder<Employee> path = new PathBuilder<>(Employee.class, "employee");

    SortField sortField = cursorPageRequestDto.getSortField();
    SortDirection sortDirection = cursorPageRequestDto.getSortDirection();
    Integer size = cursorPageRequestDto.getSize();

    String nameOrEmail = cursorPageRequestDto.getNameOrEmail();       // nullable
    String departmentName = cursorPageRequestDto.getDepartmentName(); // nullable
    String position = cursorPageRequestDto.getPosition();             // nullable
    String status = String.valueOf(cursorPageRequestDto.getStatus()); // nullable

    if (nameOrEmail == null) {
      nameOrEmail = "";
    }
    if (departmentName == null) {
      departmentName = "";
    }
    if (position == null) {
      position = "";
    }
    if (status.equals("null") || status.isEmpty()) {
      status = "";
    }

    // 정렬 조건
    OrderSpecifier<?> order = new OrderSpecifier<>(
        sortDirection == SortDirection.ASC ? Order.ASC : Order.DESC,
        path.get(sortField.name().toLowerCase())  // enum 이름이 필드명과 일치한다고 가정
    );

    // 조건들 조합
    BooleanBuilder where = new BooleanBuilder();
    if (!nameOrEmail.isBlank()) {
      where.and(
          employee.name.containsIgnoreCase(nameOrEmail)
              .or(employee.email.containsIgnoreCase(nameOrEmail))
      );
    }
    if (!position.isBlank()) {
      where.and(employee.position.containsIgnoreCase(position));
    }
    if (!status.isBlank()) {
      where.and(employee.status.stringValue().equalsIgnoreCase(status));
    }
    if (!departmentName.isBlank()) {
      where.and(employee.department.name.containsIgnoreCase(departmentName));
    }

    // 1. "이름 또는 이메일" 필드 값을 이름과 이메일 각각 like 비교해서 더한다.
    // 2. "포지션" 과 칼럼을 비교한다
    // 3. "상태" 를 칼럼과 비교한다.
    // 4. "부서 이름" 을 department와 조인하여 비교한다.

    // 쿼리 실행
    List<Employee> employees = queryFactory
        .selectFrom(employee)
        .where(where)
        .orderBy(order)
        .limit(size)
        .fetch();

    return employees;  // 실제 구현 필요
  }
}