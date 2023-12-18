package com.example.board.service

import com.example.board.domain.dto.BoardDto
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
class BoardService(private val boardRepository: BoardRepository) {

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
    fun create(username: String, dto: BoardDto): BoardDto {
        val board = Board(dto.title, username, dto.content)
        val savedBoard = boardRepository.save(board)
        return BoardDto.fromEntity(savedBoard)
    }

    @Transactional
    fun update(username: String, dto: BoardDto): BoardDto {
        val board = boardRepository.findById(dto.boardId!!)
            .orElseThrow { NotFoundException("수정할 게시글을 찾을 수 없습니다.") }
        if (board.writer != username) {
            throw BadRequestException("해당 게시글의 작성자만 수정할 수 있습니다.")
        }
        board.update(dto.title, username, dto.content)
        return BoardDto.fromEntity(board)
    }

    fun delete(username: String, boardId: Long) {
        val board = boardRepository.findById(boardId)
            .orElseThrow { NotFoundException("삭제할 게시글을 찾을 수 없습니다.") }
        if (board.writer != username) {
            throw BadRequestException("해당 게시글의 작성자만 삭제할 수 있습니다.")
        }
        boardRepository.delete(board)
    }
}