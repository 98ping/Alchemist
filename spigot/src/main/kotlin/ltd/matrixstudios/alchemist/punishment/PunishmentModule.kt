package ltd.matrixstudios.alchemist.punishment

import co.aikar.commands.BaseCommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.module.PluginModule
import ltd.matrixstudios.alchemist.punishment.commands.create.*
import ltd.matrixstudios.alchemist.punishment.commands.menu.ExecutedPunishmentHistoryCommand
import ltd.matrixstudios.alchemist.punishment.commands.menu.HistoryCommand
import ltd.matrixstudios.alchemist.punishment.commands.menu.PunishmentLookupCommands
import ltd.matrixstudios.alchemist.punishment.commands.redo.RebanCommand
import ltd.matrixstudios.alchemist.punishment.commands.remove.*
import ltd.matrixstudios.alchemist.punishment.limitation.PunishmentLimitationUnderstander
import ltd.matrixstudios.alchemist.util.Chat

/**
 * Class created on 7/21/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object PunishmentModule : PluginModule {
    override fun onLoad() {
        val punishmentStart = System.currentTimeMillis()

        PunishmentLimitationUnderstander.load()

        Chat.sendConsoleMessage("&6[Punishments] &fAll profile events loaded in &6" + System.currentTimeMillis().minus(punishmentStart) + "ms")
    }

    override fun getCommands(): MutableList<BaseCommand> {
        val list = mutableListOf<BaseCommand>()

        list.add(MuteCommand())
        list.add(BanCommand())
        list.add(BlacklistCommand())
        list.add(TempBanCommand())
        list.add(TempMuteCommand())
        list.add(KickCommand())
        list.add(WarnCommand())
        list.add(GhostMuteCommand())
        list.add(TempGhostMuteCommand())

        list.add(HistoryCommand())

        list.add(UnghostmuteCommand())
        list.add(UnbanCommand())
        list.add(UnmuteCommand())
        list.add(UnblacklistCommand())
        list.add(PunishmentLookupCommands())
        list.add(RebanCommand())

        list.add(WipePunishmentsCommand)
        list.add(ExecutedPunishmentHistoryCommand())

        return list
    }

    override fun getModularConfigOption(): Boolean {
        return AlchemistSpigotPlugin.instance.config.getBoolean("modules.punishments")
    }
}