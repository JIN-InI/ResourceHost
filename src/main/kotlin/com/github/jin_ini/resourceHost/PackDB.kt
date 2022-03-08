package com.github.jin_ini.resourceHost

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object PackDB {
    lateinit var dataFolder: File
    private val packsData = mutableMapOf<String, Path>()

    fun loadPacks() {
        if (!dataFolder.exists()) Files.createDirectories(Paths.get(dataFolder.absolutePath))
        dataFolder.listFiles()
            ?.filter { it.extension == "zip" }
            ?.forEach { packsData[it.name.removeSuffix(".zip")] = Paths.get(it.absolutePath) }
        if (packsData.isEmpty()) ResourceHost.INSTANCE.logger.info("Pack does not exist")
    }

    fun getAddress(name: String): String {
        return "https://${ResourceHost.INSTANCE.address}:${ResourceHost.INSTANCE.port}/?name=$name"
    }

    fun getPacksDataNameSet(): Set<String> {
        return packsData.keys
    }

    fun getFile(name: String): File? {
        return packsData[name]?.toFile()
    }
}
