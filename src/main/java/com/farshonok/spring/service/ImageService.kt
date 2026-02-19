package com.farshonok.spring.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.awt.Image
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.Optional
import kotlin.io.path.inputStream

@Service
class ImageService(
    @Value("\${app.images.bucket:./.images}")
    private val bucket: String,
) {
    fun upload(imagePath: String, stream: InputStream) {
        val fullPath = Path.of(bucket, imagePath)
        Files.createDirectories(fullPath.parent)
        Files.write(fullPath, stream.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
        stream.close()
    }

    operator fun get(imagePath: String) =
        Optional.of(Path.of(bucket, imagePath))
            .filter(Files::exists)
            .map { Files.readAllBytes(it) }
}

