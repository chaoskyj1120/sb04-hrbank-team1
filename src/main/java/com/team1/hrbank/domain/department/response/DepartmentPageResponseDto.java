package com.team1.hrbank.domain.department.response;

import com.team1.hrbank.domain.department.dto.DepartmentDto;
import java.util.List;

public record DepartmentPageResponseDto(
    List<DepartmentDto> content,               // 페이지 내용
    String nextCursor,                       // 다음 페이지 커서
    Long nextIdAfter,                        // 마지막 요소의 ID
    Integer size,                                // 페이지 크기
    long totalElements,                      // 총 요소 수
    boolean hasNext                         // 다음 페이지 조회 시 사용할 cursor
) {

}
