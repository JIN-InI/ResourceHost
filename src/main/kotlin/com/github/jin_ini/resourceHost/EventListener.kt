package com.github.jin_ini.resourceHost

import com.github.jin_ini.resourceHost.util.calcSHA1
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable

class EventListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val defaultPackName = ResourceHost.INSTANCE.defaultPackName

        PackDB.getFile(defaultPackName)?.calcSHA1()?.let {
            object : BukkitRunnable() {
                override fun run() {
                    event.player.setResourcePack(PackDB.getAddress(defaultPackName), it)
                }
            }.runTaskLater(ResourceHost.INSTANCE, 1)
            ResourceHost.INSTANCE.logger.info("$defaultPackName applied to ${event.player.name}")
        }
    }
}
