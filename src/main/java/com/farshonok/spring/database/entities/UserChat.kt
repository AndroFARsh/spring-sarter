package com.farshonok.spring.database.entities

import jakarta.persistence.*

@Entity
@Table(name = "users_chat")
data class UserChat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Int = 0,
) : BaseEntity<Int> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
        set(value) {
            field = value
            value?.userChats?.add(this)
        }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    var chat: Chat? = null
        set(value) {
            field = value
            value?.userChats?.add(this)
        }




}