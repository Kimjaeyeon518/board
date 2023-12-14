package com.example.board.repository

import com.example.board.domain.entity.Board
import com.example.board.domain.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {

}