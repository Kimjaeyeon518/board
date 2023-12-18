package com.example.board.api

import com.example.board.web.request.SignInRequest
import com.example.board.web.request.SignUpRequest
import com.example.board.service.UserService
import com.example.board.web.response.SingleResponse
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
class AuthApi(private val userService: UserService) {

    @PostMapping("/sign-up")
    fun register(
        @Valid @RequestBody request: SignUpRequest
    ): ResponseEntity<SingleResponse<String>> {
        userService.register(request.username, request.password)
        return ResponseEntity(SingleResponse.success("회원가입 성공 !"), HttpStatus.OK)
    }

    @PostMapping("/sign-in")
    fun login(
        @Valid @RequestBody request: SignInRequest
    ): ResponseEntity<SingleResponse<String>> {
        val token = userService.login(request.username, request.password)
        return ResponseEntity(SingleResponse.successOf("로그인 성공 !", token), HttpStatus.OK)
    }
}