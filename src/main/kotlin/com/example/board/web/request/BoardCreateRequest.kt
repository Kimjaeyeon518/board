package com.example.board.web.request

import com.example.board.domain.dto.BoardDto
import jakarta.validation.constraints.NotBlank

data class BoardCreateRequest(
    @field:NotBlank(message = "게시글 제목은 필수입니다.")
    private val title: String,
//    @field:NotBlank(message = "게시글 작성자 이름은 필수입니다.")
//    private val writer: String,
    @field:NotBlank(message = "게시글 내용은 필수입니다.")
    private val content: String,
) {
    fun toServiceDto(): BoardDto {
        return BoardDto(
            title = title,
//            writer = writer,
            content = content
        )
    }
}