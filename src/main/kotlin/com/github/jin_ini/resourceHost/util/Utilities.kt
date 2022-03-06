package com.github.jin_ini.resourceHost.util

import org.apache.commons.codec.digest.DigestUtils
import java.io.File

fun File.calcSHA1(): String {
    return DigestUtils.sha1Hex(this.readBytes())
}
