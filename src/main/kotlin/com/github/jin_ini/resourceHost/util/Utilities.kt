package com.github.jin_ini.resourceHost.util

import java.io.File
import java.security.MessageDigest

fun File.calcSHA1(): String {
    return MessageDigest.getInstance("SHA-1").digest(this.readBytes()).toString()
}
