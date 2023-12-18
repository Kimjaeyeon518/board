package com.example.board.domain.dto

import com.example.board.domain.entity.Board
import java.time.LocalDateTime

data class BoardDto(
    var boardId: Long? = null,
    var title: String,
    var writer: String? = null,
    var content: String,
    var comments: List<CommentDto>? = null
) {
    lateinit var createdAt: LocalDateTime
    lateinit var updatedAt: LocalDateTime

    companion object {
        fun fromEntity(board: Board): BoardDto {
            val dto = BoardDto(
                boardId = board.boardId,
                title = board.title,
                writer = board.writer,
                content = board.content,
            )

            dto.comments = CommentDto.fromEntities(board.comments)
            dto.createdAt = board.createdAt
            dto.updatedAt = board.updatedAt

            return dto
        }

        fun fromEntities(boards: List<Board>): List<BoardDto> {
            return boards.map {
                val dto = BoardDto(
                    boardId = it.boardId,
                    title = it.title,
                    writer = it.writer,
                    content = it.content
                )
//                dto.comments = CommentDto.fromEntities(it.comments)
                dto.createdAt = it.createdAt
                dto.updatedAt = it.updatedAt

                dto
            }
        }
    }
}