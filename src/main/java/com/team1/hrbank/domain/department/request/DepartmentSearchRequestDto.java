package com.team1.hrbank.domain.department.request;

public record DepartmentSearchRequestDto(

    String nameOrDescription,            // 부서 이름 또는 설명
    Long idAfter,                        // 이전 페이지 마지막 요소 ID
    String cursor,                       // 커서 (다음 페이지 시작점)
    Integer size,                           // 페이지 크기 (기본값: 10)
    String sortField,                   // 정렬 필드 (name 또는 establishedDate)
    String sortDirection                 // "정렬 방향 (asc 또는 desc, 기본값: asc)"
) {

  // 기본값 적용 생성자
  public DepartmentSearchRequestDto() {
    this(null, null, null, 10, "establishedDate", "asc");
  }

  // 일부 값만 받고 나머지는 기본값
  public DepartmentSearchRequestDto(String nameOrDescription, Long idAfter, String cursor) {
    this(nameOrDescription, idAfter, cursor, 10, "establishedDate", "asc");
  }

}
