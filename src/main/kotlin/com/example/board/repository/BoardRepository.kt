package com.example.board.repository

import com.example.board.domain.entity.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BoardRepository: JpaRepository<Board, Long> {

//    @Query("select b from Board b join fetch b.comments where b.boardId = :boardId")
//    override fun findById(boardId: Long): Optional<Board>
}