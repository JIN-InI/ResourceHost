package com.github.jin_ini.resourceHost

import com.github.jin_ini.resourceHost.util.calcSHA1
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable

class EventListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val address = ResourceHost.INSTANCE.address
        val port = ResourceHost.INSTANCE.port
        val defaultPackName = ResourceHost.INSTANCE.defaultPackName

        PackDB.getFile(defaultPackName)?.calcSHA1()?.let {
            object : BukkitRunnable() {
                override fun run() {
                    event.player.setResourcePack("http://$address:$port/?name=$defaultPackName", it)
                }
            }.runTaskLater(ResourceHost.INSTANCE, 1)
            ResourceHost.INSTANCE.logger.info("$defaultPackName applied to ${event.player.name}")
        }
    }
}
