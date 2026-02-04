package com.farshonok.spring.database.repository.integrations

import com.farshonok.spring.database.entities.Role.ADMIN
import com.farshonok.spring.database.entities.Role.USER
import com.farshonok.spring.database.entities.UserSearch
import com.farshonok.spring.database.entities.User_
import com.farshonok.spring.database.repository.UserRepository
import com.farshonok.spring.service.annotations.IT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@IT
@Transactional
class UserRepositoryIT(
    val userRepository: UserRepository
) {
    @Test
    fun findAllBy() {
        val users = userRepository.findAllBy("a", "ov")
        assertThat(users).hasSize(3)
    }

    @Test
    fun findAllByUsername() {
        // native query not allowed operation contains
        val users = userRepository.findAllByUsername("petr@gmail.com")
        assertThat(users).hasSize(1)
    }

    @Test
    fun updateRole() {
        val date = LocalDate.now()
        val originUser2 = userRepository.getReferenceById(2)
        originUser2.birthDate = date
        assertEquals(originUser2.role, USER)

        val result = userRepository.updateRole(ADMIN, 2, 3, 4)
        assertTrue(result == 3)

        val updatedUser2 = userRepository.getReferenceById(2)
        assertEquals(updatedUser2.role, ADMIN)
        assertEquals(updatedUser2.birthDate, date)
    }


    @Test
    fun findTopFirstByOrderByIdDesc() {
        val maybeUser = userRepository.findFirstByOrderByIdDesc()
        assertTrue(maybeUser.isPresent)
        maybeUser.ifPresent { assertEquals(5, it.id) }

        val topUsers = userRepository.findTopByOrderByIdDesc()
        assertThat(topUsers).hasSize(1)
        assertEquals(5, topUsers[0].id)
    }

    @Test
    fun findTop3ByOrderByIdDesc() {
        val top3Users = userRepository.findTop3ByOrderByIdDesc()
        assertThat(top3Users).hasSize(3)
        assertEquals(5, top3Users[0].id)
        assertEquals(4, top3Users[1].id)
        assertEquals(3, top3Users[2].id)
    }

    @Test
    fun findTopBySort() {
        val sort1: Sort = Sort.by(User_.FIRST_NAME).descending()
                .and(Sort.by(User_.LAST_NAME))

        val maybeUser1 = userRepository.findTopBy(sort1)
        assertTrue { maybeUser1.isPresent }

        // TypeSort doesn't work with kotlin classes props so as workaround create search interface and use it
        val sortBy = Sort.sort(UserSearch::class.java)
        val sort2 = sortBy.by(UserSearch::firstName).descending()
            .and(sortBy.by(UserSearch::lastName))

        val maybeUser2 = userRepository.findTopBy(sort2)
        assertTrue { maybeUser1.isPresent }

        assertEquals(maybeUser1.get(), maybeUser2.get())
    }

    @Test
    fun findByPageable() {
        val sort1: Sort = Sort.by(User_.FIRST_NAME).descending()
            .and(Sort.by(User_.LAST_NAME))
        val pageable1 = PageRequest.of(1, 2, sort1)

        val users1 = userRepository.findBy(pageable1)

        val sortBy = Sort.sort(UserSearch::class.java)
        val sort2 = sortBy.by(UserSearch::firstName).descending()
            .and(sortBy.by(UserSearch::lastName))

        val pageable2 = PageRequest.of(1, 2, sort2)

        val users2 = userRepository.findBy(pageable2)

        assertEquals(users1.size, users2.size)
        for (i in users1.indices) {
            assertEquals(users1[i], users2[i])
        }
    }
}
