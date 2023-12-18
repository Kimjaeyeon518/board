package com.example.board.domain.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
class Board(title: String, writer: String, content: String): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardId: Long? = null   // 게시글 PK

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="user_id")
//    val user: User? = null  // 게시글 작성자 PK

    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL])     // CascadeType.ALL - Board 삭제 시, 달려있는 댓글들까지 한꺼번에 삭제
    @OrderBy("created_at desc")
    var comments: MutableList<Comment> = mutableListOf()
        private set

    @Column(name = "title")
    var title: String = title   // 게시글 제목
        private set

    @Column(name = "writer")
    var writer: String = writer   // 게시글 작성자
        private set

    @Column(name = "content")
    var content: String = content   // 게시글 본문 내용
        private set

    fun update(title: String, writer: String, content: String) {
        this.title = title
        this.writer = writer
        this.content = content
    }

    fun addComment(comment: Comment) {
        this.comments.add(comment)
    }
}