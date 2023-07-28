package ltd.matrixstudios.alchemist.servers.menu.sub

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshServersPacket
import ltd.matrixstudios.alchemist.servers.menu.sub.menus.SelectRankMenu
import ltd.matrixstudios.alchemist.servers.packets.ServerShutdownPacket
import ltd.matrixstudios.alchemist.servers.packets.ServerWhitelistPacket
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class ServerOptionsMenu(val player: Player, val server: UniqueServer) : Menu(player) {

    init {
        staticSize = 27
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        buttons[10] = SkullButton(
            "eyJ0aW1lc3RhbXAiOjE1NzIyMjkwMDE5MjgsInByb2ZpbGVJZCI6ImZkNjBmMzZmNTg2MTRmMTJiM2NkNDdjMmQ4NTUyOTlhIiwicHJvZmlsZU5hbWUiOiJSZWFkIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kYzI4YzQ4MjcxMDU2MWIxMDdlYjgzMTc5ZGJlYjc0ZjJhMGNjNDFlZDQ5MGYzOWNkNGVkZmUwZTA0N2Q2ZjBjIn19fQ==",
            mutableListOf(" ", Chat.format("&eClick to shut down the server!"), " "),
            Chat.format("&6Restart " + server.displayName)
        ).setBody { player, i, clickType ->
            AsynchronousRedisSender.send(ServerShutdownPacket(server.id))
            player.sendMessage(Chat.format("&8[&eServer Monitor&8] &fSent remote shutdown request to " + server.id))
        }

        buttons[11] = SkullButton(
            "ewogICJ0aW1lc3RhbXAiIDogMTY0MDg5MDA0Nzg1OSwKICAicHJvZmlsZUlkIiA6ICIzNmMxODk4ZjlhZGE0NjZlYjk0ZDFmZWFmMjQ0MTkxMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJMdW5haWFuIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2RmNTliOGYwMTUyYjVmOWRmMWVmNzczMDk5NzExN2JhOWEyNTJlYTY2MzI1MjhlZWEyNWI5NzAyOGVhZjIxNDAiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==",
            mutableListOf(" ", Chat.format("&eClick to view plugins of the server"), " "),
            Chat.format("&6List Plugins Of " + server.displayName)
        ).setBody { player, i, clickType ->

        }

        buttons[12] = SkullButton(
            "ewogICJ0aW1lc3RhbXAiIDogMTY0MjgzNTczODQyNywKICAicHJvZmlsZUlkIiA6ICIwYTUzMDU0MTM4YWI0YjIyOTVhMGNlZmJiMGU4MmFkYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJQX0hpc2lybyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zODUxMzA4OWRlYmMzYmQ4M2YxYmRkMDZkZjI3Y2U5NTNhNDcyZGMwY2JmODc1YmRiNDUzNzgwYTdkMDBlYTEwIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=",
            mutableListOf(" ", Chat.format("&eClick to whitelist this server!"), " "),
            Chat.format("&6Whitelist " + server.displayName)
        ).setBody { player, i, clickType ->
            AsynchronousRedisSender.send(ServerWhitelistPacket(server.id))
            player.sendMessage(Chat.format("&8[&eServer Monitor&8] &fServer has been whitelisted forcefully"))
        }

        buttons[13] = SkullButton(
            "ewogICJ0aW1lc3RhbXAiIDogMTYwMTg0ODM0MDM2NiwKICAicHJvZmlsZUlkIiA6ICI3ZGEyYWIzYTkzY2E0OGVlODMwNDhhZmMzYjgwZTY4ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJHb2xkYXBmZWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkxY2E1NDJhZjA1NzYzZTViYjdhZTJiZjU4NmM2YWFjMDdmMjU3NDVhZTIwNTg1MWVkNjQ2MTdiOTRjYjA0MiIKICAgIH0KICB9Cn0=",
            mutableListOf(" ", Chat.format("&eSet rank lock status of the server."), " "),
            Chat.format("&6Set Rank Lock Of " + server.displayName)
        ).setBody { player, i, clickType ->
            val other = !server.lockedWithRank

            server.lockedWithRank = other
            UniqueServerService.save(server)
            AsynchronousRedisSender.send(RefreshServersPacket())
            Alchemist.globalServer = server

            if (other)
            {
                player.sendMessage(Chat.format("&8[&eServer Monitor&8] &fSet rank lock status of " + server.id + " to true"))
            } else {
                player.sendMessage(Chat.format("&8[&eServer Monitor&8] &fSet rank lock status of " + server.id + " to false"))
            }

        }

        buttons[14] = SkullButton(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDc1ZmQxZmQ2ZjA1NmNjY2NhYjJmZWRkZDY3Y2Q0Nzc2ZDdhYzI3YTBlNDM0Y2VlODMyNWIzZjQ5YjhjNGI3ZSJ9fX0=",
            mutableListOf(" ", Chat.format("&eSet minimum rank required to join the server."), " "),
            Chat.format("&6Set Min Lock Rank Of " + server.displayName)
        ).setBody { player, i, clickType ->
            SelectRankMenu(player, server).updateMenu()
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Editing a Server!"
    }
}