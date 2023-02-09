package ltd.matrixstudios.alchemist.themes.types

class ConfigTheme : Theme("config", "&cConfig", mutableListOf(" ", "&eSelect the &bHydrogen &etheme.", " "), Material.STONE, 0) {

    
    override fun getGrantsLore(player: Player, rankGrant: RankGrant): MutableList<String> {
        val desc = mutableListOf<String>()



        return desc
    }

    override fun getGrantsDisplayName(player: Player, rankGrant: RankGrant): String {
        return Chat.format("&e" + Date(rankGrant.expirable.addedAt))
    }

    override fun getGrantsData(player: Player, rankGrant: RankGrant): Short {
        return if (rankGrant.expirable.isActive()) DyeColor.LIME.woolData.toShort() else DyeColor.RED.woolData.toShort()
    }

    override fun getGrantLore(player: Player, gameProfile: GameProfile, rank: Rank): MutableList<String> {
        val desc = mutableListOf<String>()
        desc.add(Chat.format("&7&m--------------------------------"))
        desc.add(Chat.format("&9Click to grant &f" + rank.color + rank.displayName + " &9to " + AlchemistAPI.getRankDisplay(gameProfile.uuid)))
        desc.add(Chat.format("&7&m--------------------------------"))

        return desc
    }

    override fun getGrantDisplayName(player: Player, rank: Rank): String {
        return Chat.format(rank.color + rank.displayName)
    }

    override fun getGrantData(player: Player, rank: Rank): Short {
        return Chat.getDyeColor(rank.color).woolData.toShort()
    }

    override fun getHistoryLore(player: Player, punishment: Punishment): MutableList<String> {
        val desc = mutableListOf<String>()
        desc.add(Chat.format("&7&m-------------------------"))
        desc.add(Chat.format("&eBy: &f" + AlchemistAPI.getRankDisplay(punishment.executor)))
        desc.add(Chat.format("&eReason: &c" + punishment.reason))
        desc.add(Chat.format("&eActor: &c" + punishment.actor.actorType.name + " &e: &c" + punishment.actor.executor.name))
        desc.add(Chat.format("&7&m-------------------------"))
        val expirable = punishment.expirable

        if (expirable.duration == Long.MAX_VALUE)
        {
            desc.add(Chat.format("&eThis is a permanent punishment!"))
        } else {
            desc.add(Chat.format("&eDuration: " + TimeUtil.formatDuration(punishment.expirable.duration)))
        }
        desc.add(" ")

        desc.add(Chat.format("&c&lClick to remove punishment"))
        desc.add(Chat.format("&7&m-------------------------"))

        return desc
    }

    override fun getHistoryDisplayName(player: Player, punishment: Punishment): String {
        return Chat.format("&e" + Date(punishment.expirable.addedAt))
    }

    override fun getHistoryData(player: Player, punishment: Punishment): Short {
        return if (punishment.expirable.isActive()) DyeColor.LIME.woolData.toShort() else DyeColor.RED.woolData.toShort()
    }

    override fun getHistoryPlaceholderLore(
        player: Player,
        profile: GameProfile,
        punishment: PunishmentType
    ): MutableList<String> {
        return mutableListOf()
    }

    override fun getHistoryPlaceholderName(player: Player, profile: GameProfile, punishment: PunishmentType): String {
        return Chat.format(punishment.color + punishment.niceName + "'s")
    }

    override fun getHistoryPlaceholderData(player: Player, profile: GameProfile, punishment: PunishmentType): Short {
        return Chat.getDyeColor(punishment.color).woolData.toShort()
    }
}