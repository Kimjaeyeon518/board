package com.example.board.api

import com.example.board.domain.dto.BoardDto
import com.example.board.exception.BadRequestException
import com.example.board.service.BoardService
import com.example.board.web.request.BoardCreateRequest
import com.example.board.web.request.BoardUpdateRequest
import com.example.board.web.response.ListResponse
import com.example.board.web.response.SingleResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class BoardApi(private val boardService: BoardService) {

    @GetMapping("/v1/boards")
    fun findAll(): ResponseEntity<ListResponse<BoardDto>> {
        val boards = boardService.findAll()
        return ResponseEntity(ListResponse.successOf(boards), HttpStatus.OK)
    }

    @GetMapping("/v1/boards/{boardId}")
    fun find(
        @PathVariable boardId: Long
    ): ResponseEntity<SingleResponse<BoardDto>> {
        val board = boardService.findById(boardId)
        return ResponseEntity(SingleResponse.successOf(board), HttpStatus.OK)
    }

    @PostMapping("/v2/boards")
    fun create(
        httpRequest: HttpServletRequest,
        @Valid @RequestBody request: BoardCreateRequest
    ): ResponseEntity<SingleResponse<BoardDto>> {
        val username = httpRequest.session.getAttribute("username") as String
        val board = boardService.create(username, request.toServiceDto())
        return ResponseEntity(SingleResponse.successOf(board), HttpStatus.OK)
    }

    @PutMapping("/v2/boards/{boardId}")
    fun update(
        httpRequest: HttpServletRequest,
        @PathVariable boardId: Long,
        @Valid @RequestBody request: BoardUpdateRequest
    ): ResponseEntity<SingleResponse<BoardDto>> {
        if (boardId != request.boardId) {
            throw BadRequestException("수정하려는 게시글이 아닙니다.")
        }
        val username = httpRequest.session.getAttribute("username") as String
        val board = boardService.update(username, request.toServiceDto())
        return ResponseEntity(SingleResponse.successOf(board), HttpStatus.OK)
    }

    @DeleteMapping("/v2/boards/{boardId}")
    fun delete(
        httpRequest: HttpServletRequest,
        @PathVariable boardId: Long
    ): ResponseEntity<SingleResponse<String>> {
        val username = httpRequest.session.getAttribute("username") as String
        boardService.delete(username, boardId)
        return ResponseEntity(SingleResponse.success(), HttpStatus.OK)
    }
}