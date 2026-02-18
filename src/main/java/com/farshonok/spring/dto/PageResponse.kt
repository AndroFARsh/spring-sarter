package com.farshonok.spring.dto

import org.springframework.data.domain.Page

data class Metadata(
    val total: Long,
    val page: Int,
    val size: Int
)

data class PageResponse<T>(
    val content: List<T>,
    val metadata: Metadata,
) {
    companion object {
        fun <C: Any> of(page: Page<C>): PageResponse<C> {
            return PageResponse(
                content = page.content,
                metadata = Metadata(
                    total = page.totalElements,
                    page = page.number,
                    size = page.size
                )
            )
        }
    }
}
