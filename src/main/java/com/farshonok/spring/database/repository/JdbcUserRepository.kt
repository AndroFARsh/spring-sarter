package com.farshonok.spring.database.repository

import com.farshonok.spring.database.entities.Role
import com.farshonok.spring.database.entities.User
import com.farshonok.spring.dto.PersonaInfo
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.time.LocalDate
import java.util.Objects

interface JdbcUserRepository {
    fun findAllByCompanyAndRole(companyId: Int, role: Role): List<PersonaInfo>

    fun updateCompanyAndRole(users: Collection<User>): Int

    fun updateCompanyAndRoleNamed(users: Collection<User>): Int
}

class JdbcUserRepositoryImpl(
    val jdbcTemplate: JdbcTemplate,
    val namedJdbcTemplate: NamedParameterJdbcTemplate
) : JdbcUserRepository {

    companion object {
        const val QUERY_USERS_BY_COMPANY_AND_ROLE = """
            SELECT
            firstname,
            lastname,
            birth_date
            FROM users
            WHERE company_id = ? 
                AND role = ?
            
        """

        const val UPDATE_COMPANY_AND_ROLE = """
            UPDATE users
            SET company_id = ?,
                role = ?
            WHERE id = ?
        """

        const val UPDATE_NAMED_COMPANY_AND_ROLE = """
            UPDATE users
            SET company_id = :companyId,
                role = :role
            WHERE id = :id
        """
    }

    override fun findAllByCompanyAndRole(companyId: Int, role: Role): List<PersonaInfo> =
        jdbcTemplate.query(QUERY_USERS_BY_COMPANY_AND_ROLE, { rs, rowNum ->
            PersonaInfo(
                firstName = rs.getString("firstname"),
                lastName = rs.getString("lastname"),
                birthDate = rs.getObject<LocalDate>("birth_date", LocalDate::class.java)
            )
        }, companyId, role.name)

    override fun updateCompanyAndRole(users: Collection<User>): Int {
        // expected list of NOT null Any
        val args = users
            .map { arrayOf<Any>(it.company?.id ?: 0, it.role.name, it.id) }
            .toList()

        val result: IntArray = jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, args)
        return result.sum()
    }

    override fun updateCompanyAndRoleNamed(users: Collection<User>): Int {
        val args: Array<MapSqlParameterSource> = users
            .map {
                mapOf<String, Any>(
                    "id" to it.id,
                    "role" to it.role.name,
                    "companyId" to (it.company?.id ?: 0),
                )
            }.map { MapSqlParameterSource(it) }
            .toTypedArray<MapSqlParameterSource>()
        val result = namedJdbcTemplate.batchUpdate(UPDATE_NAMED_COMPANY_AND_ROLE, args)
        return result.sum()
    }

}