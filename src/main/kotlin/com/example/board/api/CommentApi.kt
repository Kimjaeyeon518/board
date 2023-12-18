package com.example.board.api

import com.example.board.domain.dto.CommentDto
import com.example.board.exception.BadRequestException
import com.example.board.service.CommentService
import com.example.board.web.request.BoardCreateRequest
import com.example.board.web.request.BoardUpdateRequest
import com.example.board.web.request.CommentCreateRequest
import com.example.board.web.request.CommentUpdateRequest
import com.example.board.web.response.ListResponse
import com.example.board.web.response.SingleResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class CommentApi(private val commentService: CommentService) {

    @PostMapping("/v2/boards/{boardId}/comments")
    fun create(
        httpRequest: HttpServletRequest,
        @PathVariable boardId: Long,
        @Valid @RequestBody request: CommentCreateRequest
    ): ResponseEntity<SingleResponse<CommentDto>> {
        val username = httpRequest.session.getAttribute("username") as String
        val board = commentService.create(username, boardId, request.toServiceDto())
        return ResponseEntity(SingleResponse.successOf(board), HttpStatus.OK)
    }

    @PutMapping("/v2/boards/{boardId}/comments/{commentId}")
    fun update(
        httpRequest: HttpServletRequest,
        @PathVariable boardId: Long,
        @PathVariable commentId: Long,
        @Valid @RequestBody request: CommentUpdateRequest
    ): ResponseEntity<SingleResponse<CommentDto>> {
        if (commentId != request.commentId) {
            throw BadRequestException("수정하려는 댓글이 아닙니다.")
        }
        val username = httpRequest.session.getAttribute("username") as String
        val comment = commentService.update(username, request.toServiceDto())
        return ResponseEntity(SingleResponse.successOf(comment), HttpStatus.OK)
    }

    @DeleteMapping("/v2/boards/{boardId}/comments/{commentId}")
    fun delete(
        httpRequest: HttpServletRequest,
        @PathVariable boardId: Long,
        @PathVariable commentId: Long,
    ): ResponseEntity<SingleResponse<String>> {
        val username = httpRequest.session.getAttribute("username") as String
        commentService.delete(username, commentId)
        return ResponseEntity(SingleResponse.success(), HttpStatus.OK)
    }
}