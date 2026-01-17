package com.farshonok.spring.configs

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("db")
data class DatabaseProperties(
    val username: String?,
    val driver: String?,
    val url: String?,
    val hosts: String?,
    val pool: PoolProperties?,
    val pools: List<PoolProperties>,
) {
    data class PoolProperties(
        val size: Int,
        val timeout: Int,
    )
}