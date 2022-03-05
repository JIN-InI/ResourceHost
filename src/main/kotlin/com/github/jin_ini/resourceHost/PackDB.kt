package com.github.jin_ini.resourceHost

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object PackDB {
    private lateinit var dataFolder: File
    private val packsData = mutableMapOf<String, Path>()

    fun loadPacks() {
        if (!dataFolder.exists()) Files.createDirectories(Paths.get(dataFolder.absolutePath))
        dataFolder.listFiles()?.filter { it.extension == "zip" }?.forEach{ packsData[it.name] = Paths.get(it.path) }
        if(packsData.isEmpty()) ResourceHost.INSTANCE.logger.info("Pack does not exist")
    }

    fun getFile(name: String): File? {
        return packsData[name]?.toFile()
    }
}
