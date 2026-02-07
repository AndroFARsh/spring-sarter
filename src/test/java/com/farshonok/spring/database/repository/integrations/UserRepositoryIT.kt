package com.farshonok.spring.database.repository.integrations

import com.farshonok.spring.database.entities.Role.ADMIN
import com.farshonok.spring.database.entities.Role.USER
import com.farshonok.spring.database.entities.UserSearch
import com.farshonok.spring.database.entities.User_
import com.farshonok.spring.database.repository.UserRepository
import com.farshonok.spring.database.repository.UserRepository.Companion.findAllNativeByCompanyId
import com.farshonok.spring.database.repository.UserRepository.Companion.queryAllByCompanyId
import com.farshonok.spring.dto.IPersonaInfo
import com.farshonok.spring.dto.PersonaInfo
import com.farshonok.spring.service.annotations.IT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
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

        val it1 = users1.iterator()
        val it2 = users2.iterator()
        while (it1.hasNext() && it2.hasNext()) {
            assertEquals(it1.next(), it2.next())
        }

        assertEquals(users1.number, 1)
        assertEquals(users1.totalPages, 3)
        assertEquals(users1.totalElements, 5)

        var slice = users1
        var sliceCount = 1
        while (slice.hasNext()) {
            slice = userRepository.findBy(slice.nextPageable())
            sliceCount++
        }
        assertEquals(sliceCount, 2)

        val pageable3 = PageRequest.of(0, 2, sort1)
        val page = userRepository.findBy(pageable3)
        assertEquals(page.number, 0)
        assertEquals(page.totalPages, 3)
        assertEquals(page.totalElements, 5)
    }

    @Test
    fun findWithNamedGraphBy() {
        val sort: Sort = Sort.by(User_.FIRST_NAME).descending()
            .and(Sort.by(User_.LAST_NAME))
        val pageable = PageRequest.of(0, 2, sort)

        val users = userRepository.findWithNamedGraphBy(pageable)
        users.forEach { u -> assertTrue { u.company != null } }

        assertEquals(users.size, 2)
        assertEquals(users.number, 0)
        assertEquals(users.totalPages, 3)
        assertEquals(users.totalElements, 5)
    }

    @Test
    fun findWithAttrGraphBy() {
        val sort: Sort = Sort.by(User_.FIRST_NAME).descending()
            .and(Sort.by(User_.LAST_NAME))
        val pageable = PageRequest.of(0, 2, sort)

        val users = userRepository.findWithAttrGraphBy(pageable)
        users.forEach { u -> println(u.company?.name) }

        // limit and offset not work roperly with @EntityGraph and @OneToMany relations

        assertEquals(users.size, 2)
        assertEquals(users.number, 0)
        assertEquals(users.totalPages, 3)
        assertEquals(users.totalElements, 5)
    }

    @Test
    fun findAllByCompanyId_Data_PersonInfo() {
        val persons1 = userRepository.findAllByCompanyId(1)
        assertTrue { persons1.isNotEmpty() }

        assertEquals(persons1[0].firstName, "Ivan")
        assertNotNull(persons1[0].lastName)
        assertNotNull(persons1[0].birthDate)

        val persons2 = userRepository.queryAllByCompanyId<PersonaInfo>(1)
        assertTrue { persons2.isNotEmpty() }

        assertEquals(persons1.size, persons2.size)
        assertEquals(persons1, persons2)

        val persons3 = userRepository.findAllNativeByCompanyId<PersonaInfo>(1)
        assertTrue { persons3.isNotEmpty() }

        assertEquals(persons1.size, persons3.size)
        assertEquals(persons1, persons3)

        val persons4 = userRepository.findAllNativeByCompanyId<IPersonaInfo>(1)
        assertTrue { persons4.isNotEmpty() }

        assertEquals(persons1.size, persons4.size)
        for (i in 0..<persons1.size) {
            assertEquals(persons1[i].firstName, persons4[i].firstName)
            assertEquals(persons1[i].lastName, persons4[i].lastName)
            assertEquals(persons1[i].birthDate, persons4[i].birthDate)
        }
    }
}
