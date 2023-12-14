package com.example.board.api

import com.example.board.web.request.SignInRequest
import com.example.board.web.request.SignUpRequest
import com.example.board.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController("/v1")
@RequiredArgsConstructor
class AuthApi(private val userService: UserService) {

    @PostMapping("/sign-up")
    fun register(
        @Validated @RequestBody request: SignUpRequest
    ): ResponseEntity<String> {
        userService.register(request.username, request.password)
        return ResponseEntity("회원가입 성공 !", HttpStatus.OK)
    }

    @PostMapping("/sign-in")
    fun login(
        @Validated @RequestBody request: SignInRequest
    ): ResponseEntity<String> {
        userService.login(request.username, request.password)
        return ResponseEntity("로그인 성공 !", HttpStatus.OK)
    }
}