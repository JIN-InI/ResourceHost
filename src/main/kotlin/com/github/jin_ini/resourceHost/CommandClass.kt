package com.github.jin_ini.resourceHost

import com.github.jin_ini.resourceHost.util.calcSHA1
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandClass(private val plugin: ResourceHost) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("Wrong subcommand; please try /rh help.")
            return true
        }

        when {
            args[0] == "list" && sender is Player -> {
                val packNames = PackDB.getPacksDataNameSet()
                sender.sendMessage("o---Available packs----o")
                if (packNames.isEmpty()) {
                    sender.sendMessage("* There is nothing here...")
                } else {
                    for (elem in packNames) {
                        sender.sendMessage("* $elem")
                    }
                }
            }

            args[0] == "apply" && sender is Player -> {
                if (args.size < 2) {
                    sender.sendMessage("Need more arguments")
                    return true
                }
                PackDB.getFile(args[1])?.calcSHA1()?.let {
                    sender.setResourcePack(PackDB.getAddress(args[1]), it)
                    plugin.logger.info("${args[1]} applied to ${sender.name}")
                } ?: sender.sendMessage("No such name resource pack")
            }

            args[0] == "help" -> {
                sender.sendMessage("""o---Resource Host's help----o
                    * help - Show this help.
                    * list - Show list all available Resource-packs.
                    * apply - Apply Resource-packs to player.
                """.trimMargin())
            }

            else -> {
                sender.sendMessage("Wrong subcommand; please try /rh help.")
            }
        }
        return true
    }
}
