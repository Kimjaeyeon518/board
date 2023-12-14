package com.example.board.repository

import com.example.board.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun existsByUsername(username: String): Boolean
    fun findByUsernameAndPassword(username: String, password:String): Optional<User>
}