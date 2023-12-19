package com.example.board.web.request

import com.example.board.domain.dto.CommentDto
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import lombok.Getter

@Getter
data class CommentCreateRequest @JsonCreator constructor(
    @field:NotBlank(message = "댓글 내용은 필수입니다.")
    @param:JsonProperty("content")
    private val content: String
) {
    fun toServiceDto(): CommentDto {
        return CommentDto(
            content = content
        )
    }
}