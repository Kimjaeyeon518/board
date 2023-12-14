package com.example.board.web.request

import com.example.board.domain.dto.BoardDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

class BoardUpdateRequest(
    @field:NotNull(message = "게시글 ID 는 필수입니다.")
    val boardId: Long?,
    @field:NotBlank(message = "게시글 제목은 필수입니다.")
    val title: String,
    @field:NotBlank(message = "게시글 작성자 이름은 필수입니다.")
    val writer: String,
    @field:NotBlank(message = "게시글 본문은 필수입니다.")
    val content: String,
) {
    fun toServiceDto(): BoardDto {
        return BoardDto(
            boardId = boardId,
            title = title,
            writer = writer,
            content = content
        )
    }
}