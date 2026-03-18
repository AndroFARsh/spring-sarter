package com.farshonok.spring.dto

import com.farshonok.spring.database.entities.Role
import com.farshonok.spring.validators.UserCreateAction
import com.farshonok.spring.validators.UserInfo
import com.farshonok.spring.validators.UserUpdateAction
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import org.postgresql.util.LruCache
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.util.Optional
import java.util.Optional.empty

//@UserInfo(groups = [UserUpdateAction::class])
@UserInfo(groups = [UserCreateAction::class])
class UserCreateEditDto(
    // validation should be explicitly defined on the getter, otherwise it will not work for some reason
    @get:Email
    @get:NotBlank
    val email: String?,

    @get:NotBlank(groups = [UserCreateAction::class])
    val rawPassword: String?,

    @get:NotBlank
    @get:Size(min = 3, max = 64)
    val firstName: String?,

    @get:NotBlank
    @get:Size(min = 3, max = 64)
    val lastName: String?,

//    @PastOrPresent
    val birthDate: LocalDate?,

//    @get:NotNull
    val role: Role?,

    @get:PositiveOrZero
    val companyId: Int = 0,

    val image: Optional<MultipartFile> = empty(),
)

