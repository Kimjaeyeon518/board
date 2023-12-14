package com.example.board.service

import com.example.board.domain.dto.BoardDto
import com.example.board.domain.entity.Board
import com.example.board.domain.entity.Comment
import com.example.board.exception.NotFoundException
import com.example.board.repository.BoardRepository
import com.example.board.repository.CommentRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class BoardService(private val boardRepository: BoardRepository, private val commentRepository: CommentRepository) {

    fun findAll(): List<BoardDto> {
        val boards = boardRepository.findAll()
        return BoardDto.fromEntities(boards)
    }

    fun findById(boardId: Long): BoardDto {
        val board = boardRepository.findById(boardId)
            .orElseThrow { NotFoundException("해당 게시글을 찾을 수 없습니다.") }
        return BoardDto.fromEntity(board)
    }

    @Transactional
    fun create(dto: BoardDto): BoardDto {
        val board = Board(dto.title, dto.writer, dto.content)
        val savedBoard = boardRepository.save(board)
        val bbb = boardRepository.findById(1L).orElse(savedBoard)
        val comment = Comment(bbb, "zz", "zz")
        val savedComment = commentRepository.save(comment)
        return BoardDto.fromEntity(savedBoard)
    }

    @Transactional
    fun update(dto: BoardDto): BoardDto {
        val board = boardRepository.findById(dto.boardId!!)
            .orElseThrow { NotFoundException("수정할 게시글을 찾을 수 없습니다.") }
        board.update(dto.title, dto.writer, dto.content)
        return BoardDto.fromEntity(board)
    }

    fun delete(boardId: Long) {
        val result = boardRepository.existsById(boardId)
        if (!result) {
            throw NotFoundException("삭제할 게시글을 찾을 수 없습니다.")
        }
        boardRepository.deleteById(boardId)
    }
}