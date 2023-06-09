package ltd.matrixstudios.alchemist.aikar

import co.aikar.commands.PaperCommandManager
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.chatcolors.ChatColorLoader
import ltd.matrixstudios.alchemist.chatcolors.commands.ChatColorCommands
import ltd.matrixstudios.alchemist.commands.admin.AdminChatCommand
import ltd.matrixstudios.alchemist.commands.alts.AltsCommand
import ltd.matrixstudios.alchemist.aikar.context.GameProfileContextResolver
import ltd.matrixstudios.alchemist.aikar.context.PunishmentTypeResolver
import ltd.matrixstudios.alchemist.aikar.context.RankContextResolver
import ltd.matrixstudios.alchemist.commands.branding.AlchemistCommand
import ltd.matrixstudios.alchemist.commands.filter.FilterCommands
import ltd.matrixstudios.alchemist.commands.gems.CoinsCommand
import ltd.matrixstudios.alchemist.friends.commands.FriendCommands
import ltd.matrixstudios.alchemist.commands.grants.*
import ltd.matrixstudios.alchemist.commands.metrics.MetricCommand
import ltd.matrixstudios.alchemist.commands.notes.PlayerNotesCommands
import ltd.matrixstudios.alchemist.commands.party.PartyCommands
import ltd.matrixstudios.alchemist.commands.permission.PermissionEditCommand
import ltd.matrixstudios.alchemist.commands.player.*
import ltd.matrixstudios.alchemist.commands.punishments.create.*
import ltd.matrixstudios.alchemist.commands.punishments.menu.ExecutedPunishmentHistoryCommand
import ltd.matrixstudios.alchemist.commands.punishments.menu.HistoryCommand
import ltd.matrixstudios.alchemist.commands.punishments.menu.PunishmentLookupCommands
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnbanCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnblacklistCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnmuteCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.WipePunishmentsCommand
import ltd.matrixstudios.alchemist.commands.rank.GenericRankCommands
import ltd.matrixstudios.alchemist.commands.server.ServerEnvironmentCommand
import ltd.matrixstudios.alchemist.commands.sessions.SessionCommands
import ltd.matrixstudios.alchemist.commands.staff.JumpToPlayerCommand
import ltd.matrixstudios.alchemist.commands.staff.OnlineStaffCommand
import ltd.matrixstudios.alchemist.commands.staff.StaffchatCommand
import ltd.matrixstudios.alchemist.commands.staff.TimelineCommand
import ltd.matrixstudios.alchemist.commands.tags.TagAdminCommand
import ltd.matrixstudios.alchemist.commands.tags.TagCommand
import ltd.matrixstudios.alchemist.commands.tags.grants.TagGrantCommand
import ltd.matrixstudios.alchemist.commands.tags.grants.TagGrantsCommand
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.staff.mode.commands.FreezeCommand
import ltd.matrixstudios.alchemist.staff.mode.commands.StaffCommands
import ltd.matrixstudios.alchemist.staff.mode.commands.VanishCommands
import ltd.matrixstudios.alchemist.staff.requests.commands.RequestCommand
import ltd.matrixstudios.alchemist.staff.settings.edit.EditModModeCommand
import ltd.matrixstudios.alchemist.staff.settings.toggle.SettingsCommand
import ltd.matrixstudios.alchemist.themes.commands.ThemeSelectCommand

object ACFCommandController {

    private val config = AlchemistSpigotPlugin.instance.config

    fun registerAll()
    {
        AlchemistSpigotPlugin.instance.commandManager = PaperCommandManager(AlchemistSpigotPlugin.instance).apply {

            this.commandContexts.registerContext(GameProfile::class.java, GameProfileContextResolver())
            this.commandContexts.registerContext(Rank::class.java, RankContextResolver())
            this.commandContexts.registerContext(PunishmentType::class.java, PunishmentTypeResolver())


            this.commandCompletions.registerCompletion("gameprofile") {
                return@registerCompletion AlchemistSpigotPlugin.instance.server.onlinePlayers.map { it.name }.toCollection(arrayListOf())
            }
            if (config.getBoolean("modules.ranks")) {
                registerCommand(GenericRankCommands())
                registerCommand(GrantCommand())
                registerCommand(GrantsCommand())
                registerCommand(CGrantCommand())
            }

            registerCommand(CoinsCommand())

            if (config.getBoolean("modules.themeCommands")) {
                registerCommand(ThemeSelectCommand())
            }

            if (config.getBoolean("modules.staffmode")) {
                registerCommand(VanishCommands())
                registerCommand(StaffCommands())
                registerCommand(RequestCommand())
                registerCommand(EditModModeCommand())
                registerCommand(SettingsCommand())
                registerCommand(FreezeCommand())
            }


            registerCommand(AlchemistCommand())

            registerCommand(TimelineCommand())

            registerCommand(PermissionEditCommand())

            if (config.getBoolean("modules.punishments")) {
                registerCommand(MuteCommand())
                registerCommand(BanCommand())
                registerCommand(BlacklistCommand())
                registerCommand(TempBanCommand())
                registerCommand(TempMuteCommand())
                registerCommand(WarnCommand())

                registerCommand(UnbanCommand())
                registerCommand(UnmuteCommand())
                registerCommand(UnblacklistCommand())
                registerCommand(PunishmentLookupCommands())

                registerCommand(WipePunishmentsCommand())
                registerCommand(ExecutedPunishmentHistoryCommand())
            }

            if (config.getBoolean("modules.chatcolors")) {
                ChatColorLoader.loadAllChatColors()
                registerCommand(ChatColorCommands())
            }

            registerCommand(AuditCommand)

            registerCommand(AltsCommand())
            registerCommand(HistoryCommand())
            registerCommand(GrantHistoryCommand())

            if (config.getBoolean("modules.notes")) {
                registerCommand(PlayerNotesCommands())
            }

            if (config.getBoolean("modules.prefixes")) {
                registerCommand(TagAdminCommand())
                registerCommand(TagCommand())
                registerCommand(TagGrantCommand())
                registerCommand(TagGrantsCommand())
            }

            if (config.getBoolean("freeRank.enabled")) {
                registerCommand(FreerankCommand())
            }


            if (config.getBoolean("modules.filters")) {
                registerCommand(FilterCommands(), true)
            }

            if (config.getBoolean("modules.friends")) {
                registerCommand(FriendCommands(), true)
            }

            registerCommand(ServerEnvironmentCommand())
            registerCommand(ListCommand(), true)
            registerCommand(SudoCommand())
            registerCommand(StaffchatCommand())
            registerCommand(AdminChatCommand())
            registerCommand(JumpToPlayerCommand())
            registerCommand(OnlineStaffCommand())
            registerCommand(PlayerAdminCommand())

            registerCommand(MetricCommand())

            registerCommand(WipeProfileCommand())
            registerCommand(SessionCommands())

            if (config.getBoolean("modules.parties")) {
                registerCommand(PartyCommands())
            }

            registerCommand(LookupCommand())
        }

    }
}