package com.example.board.service

import com.example.board.util.JwtUtil
import com.example.board.domain.entity.User
import com.example.board.exception.DuplicateException
import com.example.board.exception.NotFoundException
import com.example.board.repository.UserRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun register(username: String, password: String) {
        val result = userRepository.existsByUsername(username)
        if (result) {
            throw DuplicateException("중복된 username 입니다.")
        }
        val user = User(username, password)
        userRepository.save(user)
    }

    fun login(username: String, password: String): String {
        val user = userRepository.findByUsernameAndPassword(username, password)
            .orElseThrow { NotFoundException("회원을 찾을 수 없습니다.") }

        return JwtUtil.generateToken(user.username)
    }
}