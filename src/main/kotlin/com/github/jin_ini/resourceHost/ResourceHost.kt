package com.github.jin_ini.resourceHost

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class ResourceHost : JavaPlugin() {
    private lateinit var server: NettyApplicationEngine
    lateinit var defaultPackName: String
    lateinit var address: String
    var port = 8080

    companion object {
        lateinit var INSTANCE: ResourceHost
    }

    override fun onEnable() {
        INSTANCE = this
        loadConfig()
        PackDB.dataFolder = dataFolder
        PackDB.loadPacks()

        server = setupServer(port)
        server.start()
        logger.info("Internal Server (Ktor powered) is enable")

        getServer().pluginManager.registerEvents(EventListener(), this)
    }

    private fun loadConfig() {
        saveDefaultConfig()
        address = config.getString("address") ?: Bukkit.getIp()
        port = config.getInt("port")
        defaultPackName = config.getString("default")?.also { logger.info("Default is $it") } ?: ""
    }

    private fun setupServer(port: Int): NettyApplicationEngine {
        return embeddedServer(Netty, port) {
            routing {
                get("/") {
                    call.request.queryParameters["name"]?.let {
                        val file = File(dataFolder.absolutePath + File.separator + "$it.zip")
                        if (file.exists()) {
                            call.respondFile(file)
                        } else {
                            call.respond(404)
                        }
                    }
                }
            }
        }
    }
}
