package com.ohigraffers.practice.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "포스트 등록 DTO")
/* Swagger 문서화 시 설명 어노테이션 작성 */
public class PostCreateRequest {

    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* 필수 값이므로 유효성 검사 어노테이션 작성 */
    @NotNull(message = "책 제목은 반드시 입력되어야 합니다.")
    @Schema(description = "책 제목")
    private String title;

    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* 필수 값이므로 유효성 검사 어노테이션 작성 */
    @NotNull(message = "책 내용 반드시 입력되어야 합니다.")
    @Schema(description = "책 내용")
    private String content;

    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* 필수 값이므로 유효성 검사 어노테이션 작성 */
    @NotNull(message = "저자는 반드시 입력되어야 합니다.")
    @Schema(description = "책 저자")
    private String writer;

}

