package com.example.board.api

import com.example.board.domain.dto.BoardDto
import com.example.board.exception.BadRequestException
import com.example.board.web.request.BoardCreateRequest
import com.example.board.web.request.BoardUpdateRequest
import com.example.board.service.BoardService
import com.example.board.web.response.SingleResponse
import com.example.board.web.response.ListResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/boards")
class BoardApi(private val boardService: BoardService) {

    @GetMapping
    fun findAll(): ResponseEntity<ListResponse<BoardDto>> {
        val boards = boardService.findAll()
        return ResponseEntity(ListResponse.successOf(boards), HttpStatus.OK)
    }

    @GetMapping("/{boardId}")
    fun find(
        @PathVariable boardId: Long
    ): ResponseEntity<SingleResponse<BoardDto>> {
        val board = boardService.findById(boardId)
        return ResponseEntity(SingleResponse.successOf(board), HttpStatus.OK)
    }

    @PostMapping
    fun create(
        @Valid @RequestBody request: BoardCreateRequest
    ): ResponseEntity<SingleResponse<BoardDto>> {
        val board = boardService.create(request.toServiceDto())
        return ResponseEntity(SingleResponse.successOf(board), HttpStatus.OK)
    }

    @PutMapping("/{boardId}")
    fun update(
        @PathVariable boardId: Long,
        @Valid @RequestBody request: BoardUpdateRequest
    ): ResponseEntity<SingleResponse<BoardDto>> {
        if (boardId != request.boardId) {
            throw BadRequestException("수정하려는 게시글이 아닙니다.")
        }
        val board = boardService.update(request.toServiceDto())
        return ResponseEntity(SingleResponse.successOf(board), HttpStatus.OK)
    }

    @DeleteMapping("/{boardId}")
    fun delete(
        @PathVariable boardId: Long
    ): ResponseEntity<SingleResponse<String>> {
        boardService.delete(boardId)
        return ResponseEntity(SingleResponse.success(), HttpStatus.OK)
    }
}