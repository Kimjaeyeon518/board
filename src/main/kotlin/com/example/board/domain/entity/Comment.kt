package com.example.board.domain.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
class Comment(board: Board, writer: String, content: String?): BaseTimeEntity() {

    // 객체 레벨 양방향 연관 관계 설정.
    init {
        board.addComment(this)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val commentId: Long? = null   // 댓글 PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    var board: Board = board      // 댓글이 달린 게시물
        private set

    @Column(name = "writer")
    var writer: String = writer   // 댓글 작성자
        private set

    @Column(name = "content")
    var content: String? = content   // 댓글 내용
        private set

    fun update(writer: String, content: String?) {
        this.writer = writer
        this.content = content
    }
}