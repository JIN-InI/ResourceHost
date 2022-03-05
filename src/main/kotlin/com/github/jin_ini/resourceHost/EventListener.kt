package com.github.jin_ini.resourceHost

import com.github.jin_ini.resourceHost.util.calcHash
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class EventListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val address = ResourceHost.INSTANCE.address
        val defaultPackName = ResourceHost.INSTANCE.defaultPackName
        event.player.setResourcePack(
            "$address/$defaultPackName",
            PackDB.getFile(defaultPackName)?.calcHash("SHA-1") ?: return
        )
    }
}
