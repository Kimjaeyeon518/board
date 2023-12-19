package com.example.board.web.request

import com.example.board.domain.dto.CommentDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CommentUpdateRequest(
    @field:NotNull(message = "댓글 ID 는 필수입니다.")
    val commentId: Long?,
    @field:NotBlank(message = "댓글 내용은 필수입니다.")
    private val content: String,
) {
    fun toServiceDto(): CommentDto {
        return CommentDto(
            commentId = commentId,
            content = content
        )
    }
}