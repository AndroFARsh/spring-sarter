package com.farshonok.spring.validators

import com.farshonok.spring.dto.UserCreateEditDto
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils.hasText

@Component
class UserInfoValidator : ConstraintValidator<UserInfo, UserCreateEditDto> {
    override fun isValid(value: UserCreateEditDto, context: ConstraintValidatorContext): Boolean {
        return hasText(value.firstName) || hasText(value.lastName)
    }
}