package com.github.jin_ini.resourceHost.util

import java.io.File
import java.nio.file.Files
import java.security.MessageDigest

fun File.calcHash(algorithm: String): String {
    return MessageDigest.getInstance(algorithm).digest(Files.readAllBytes(this.toPath())).toString()
}
