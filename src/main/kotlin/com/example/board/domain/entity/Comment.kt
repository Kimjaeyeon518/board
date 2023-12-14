package com.example.board.domain.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Comment(board: Board, writer: String, content: String) {

    init {
        board.addComment(this)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val commentId: Long? = null   // 댓글 PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    var board: Board = board

    @Column(name = "writer")
    var writer: String = writer   // 댓글 작성자

    @Column(name = "content")
    var content: String = content   // 댓글 내용

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @Column(name = "updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        private set
}