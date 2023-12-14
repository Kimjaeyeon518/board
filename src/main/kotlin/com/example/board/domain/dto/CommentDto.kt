package com.example.board.domain.dto

import com.example.board.domain.entity.Comment
import java.time.LocalDateTime

data class CommentDto(
    var commentId: Long? = null,
    var boardId: Long? = null,
    var writer: String,
    var content: String
) {
    lateinit var createdAt: LocalDateTime
    lateinit var updatedAt: LocalDateTime

    companion object {
        fun fromEntity(comment: Comment): CommentDto {
            val dto = CommentDto(
                commentId = comment.commentId,
                boardId = comment.board.boardId,
                writer = comment.writer,
                content = comment.content
            )

            dto.createdAt = comment.createdAt
            dto.updatedAt = comment.updatedAt

            return dto
        }

        fun fromEntities(comments: List<Comment>): List<CommentDto> {
            return comments.map {
                val dto = CommentDto(
                    commentId = it.commentId,
                    boardId = it.board.boardId,
                    writer = it.writer,
                    content = it.content
                )
                dto.createdAt = it.createdAt
                dto.updatedAt = it.updatedAt

                dto
            }
        }
    }
}