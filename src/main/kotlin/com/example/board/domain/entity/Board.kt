package com.example.board.domain.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Board(title: String, writer: String, content: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardId: Long? = null   // 게시글 PK

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="user_id")
//    val user: User? = null  // 게시글 작성자 PK

    @OneToMany(mappedBy = "board", cascade = arrayOf(CascadeType.ALL))
    @OrderBy("created_at desc")
    var comments: MutableList<Comment> = mutableListOf()

    @Column(name = "title")
    var title: String = title   // 게시글 제목

    @Column(name = "writer")
    var writer: String = writer   // 게시글 작성자

    @Column(name = "content")
    var content: String = content   // 게시글 본문 내용

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @Column(name = "updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.MIN
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