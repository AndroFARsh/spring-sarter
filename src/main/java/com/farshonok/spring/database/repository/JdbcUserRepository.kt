package com.farshonok.spring.database.repository

import com.farshonok.spring.database.entities.Role
import com.farshonok.spring.dto.PersonaInfo
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDate

interface JdbcUserRepository {
    fun findAllByCompanyAndRole(companyId: Int, role: Role): List<PersonaInfo>
}

class JdbcUserRepositoryImpl(
    val jdbcTemplate: JdbcTemplate,
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
    }

    override fun findAllByCompanyAndRole(companyId: Int, role: Role): List<PersonaInfo> =
        jdbcTemplate.query(QUERY_USERS_BY_COMPANY_AND_ROLE, { rs, rowNum ->
            PersonaInfo(
                firstName = rs.getString("firstname"),
                lastName = rs.getString("lastname"),
                birthDate = rs.getObject<LocalDate>("birth_date", LocalDate::class.java)
            )
        }, companyId, role.name)

}