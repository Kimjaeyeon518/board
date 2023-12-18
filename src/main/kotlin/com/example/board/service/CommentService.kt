package com.example.board.service

import com.example.board.domain.dto.BoardDto
import com.example.board.domain.dto.CommentDto
import com.example.board.domain.entity.Board
import com.example.board.domain.entity.Comment
import com.example.board.exception.BadRequestException
import com.example.board.exception.NotFoundException
import com.example.board.repository.BoardRepository
import com.example.board.repository.CommentRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class CommentService(
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository
) {
    @Transactional
    fun create(username: String, boardId: Long, dto: CommentDto): CommentDto {
        val board = boardRepository.findById(boardId)
            .orElseThrow { NotFoundException("해당 게시글을 찾을 수 없습니다.") }
        val comment = Comment(board, username, dto.content)
        val savedComment = commentRepository.save(comment)
        return CommentDto.fromEntity(savedComment)
    }

    @Transactional
    fun update(username: String, dto: CommentDto): CommentDto {
        val comment = commentRepository.findById(dto.commentId!!)
            .orElseThrow { NotFoundException("수정할 댓글을 찾을 수 없습니다.") }
        if (comment.writer != username) {
            throw BadRequestException("해당 댓글의 작성자만 수정할 수 있습니다.")
        }
        comment.update(username, dto.content)
        return CommentDto.fromEntity(comment)
    }

    fun delete(username: String, commentId: Long) {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { NotFoundException("삭제할 댓글을 찾을 수 없습니다.") }
        if (comment.writer != username) {
            throw BadRequestException("해당 댓글의 작성자만 삭제할 수 있습니다.")
        }
        commentRepository.delete(comment)
    }
}