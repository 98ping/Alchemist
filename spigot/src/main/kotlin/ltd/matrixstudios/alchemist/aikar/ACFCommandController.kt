package ltd.matrixstudios.alchemist.aikar

import co.aikar.commands.BukkitMessageFormatter
import co.aikar.commands.MessageType
import co.aikar.commands.PaperCommandManager
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.aikar.context.*
import ltd.matrixstudios.alchemist.chatcolors.ChatColorLoader
import ltd.matrixstudios.alchemist.chatcolors.commands.ChatColorCommands
import ltd.matrixstudios.alchemist.commands.admin.AdminChatCommand
import ltd.matrixstudios.alchemist.commands.alts.AltsCommand
import ltd.matrixstudios.alchemist.commands.branding.AlchemistCommand
import ltd.matrixstudios.alchemist.commands.filter.FilterCommands
import ltd.matrixstudios.alchemist.commands.coins.CoinsCommand
import ltd.matrixstudios.alchemist.commands.disguise.RankDisguiseCommand
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
import ltd.matrixstudios.alchemist.commands.sibling.SiblingCommands
import ltd.matrixstudios.alchemist.commands.staff.JumpToPlayerCommand
import ltd.matrixstudios.alchemist.commands.staff.OnlineStaffCommand
import ltd.matrixstudios.alchemist.commands.staff.StaffchatCommand
import ltd.matrixstudios.alchemist.commands.staff.TimelineCommand
import ltd.matrixstudios.alchemist.commands.tags.TagAdminCommand
import ltd.matrixstudios.alchemist.commands.tags.TagCommand
import ltd.matrixstudios.alchemist.commands.tags.grants.TagGrantCommand
import ltd.matrixstudios.alchemist.commands.tags.grants.TagGrantsCommand
import ltd.matrixstudios.alchemist.commands.vouchers.VoucherCommand
import ltd.matrixstudios.alchemist.convert.luckperms.LuckPermsConverterCommand
import ltd.matrixstudios.alchemist.models.grant.types.scope.GrantScope
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.queue.command.QueueCommands
import ltd.matrixstudios.alchemist.staff.mode.commands.FreezeCommand
import ltd.matrixstudios.alchemist.staff.mode.commands.StaffCommands
import ltd.matrixstudios.alchemist.staff.mode.commands.VanishCommands
import ltd.matrixstudios.alchemist.staff.requests.commands.RequestCommand
import ltd.matrixstudios.alchemist.staff.settings.edit.EditModModeCommand
import ltd.matrixstudios.alchemist.staff.settings.toggle.SettingsCommand
import ltd.matrixstudios.alchemist.themes.commands.ThemeSelectCommand
import org.bukkit.ChatColor
import java.util.UUID

object ACFCommandController {

    private val config = AlchemistSpigotPlugin.instance.config

    fun registerAll()
    {
        AlchemistSpigotPlugin.instance.commandManager = PaperCommandManager(AlchemistSpigotPlugin.instance).apply {

            this.commandContexts.registerContext(GameProfile::class.java, GameProfileContextResolver())
            this.commandContexts.registerContext(Rank::class.java, RankContextResolver())
            this.commandContexts.registerContext(PunishmentType::class.java, PunishmentTypeResolver())
            this.commandContexts.registerContext(UUID::class.java, UUIDContextResolver())
            this.commandContexts.registerContext(GrantScope::class.java, GrantScopeContextResolver())


            this.commandCompletions.registerCompletion("gameprofile") {
                return@registerCompletion AlchemistSpigotPlugin.instance.server.onlinePlayers.map { it.name }.toCollection(arrayListOf())
            }

            this.enableUnstableAPI("help")

            this.setFormat(MessageType.SYNTAX, BukkitMessageFormatter(ChatColor.GOLD, ChatColor.YELLOW, ChatColor.WHITE))
            this.setFormat(MessageType.HELP, BukkitMessageFormatter(ChatColor.GOLD, ChatColor.YELLOW, ChatColor.WHITE))

            if (config.getBoolean("modules.ranks")) {
                registerCommand(GenericRankCommands())
                registerCommand(GrantCommand())
                registerCommand(GrantsCommand())
                registerCommand(CGrantCommand())
                registerCommand(NonModelGrantCommand())
                registerCommand(GrantHistoryCommand())
                registerCommand(LuckPermsConverterCommand())
                registerCommand(WipeGrantsCommand)
            }

            if (config.getBoolean("modules.vouchers")) {
                registerCommand(VoucherCommand())
            }

            registerCommand(SiblingCommands())

            registerCommand(CoinsCommand())

            if (config.getBoolean("modules.themeCommands")) {
                registerCommand(ThemeSelectCommand())
            }

            registerCommand(QueueCommands())

            registerCommand(RankDisguiseCommand())

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

                registerCommand(WipePunishmentsCommand)
                registerCommand(ExecutedPunishmentHistoryCommand())
            }

            if (config.getBoolean("modules.chatcolors")) {
                ChatColorLoader.loadAllChatColors()
                registerCommand(ChatColorCommands())
            }

            registerCommand(AuditCommand)

            registerCommand(AltsCommand())
            registerCommand(HistoryCommand())

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