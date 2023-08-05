package ltd.matrixstudios.alchemist.grants.configure.menu

import ltd.matrixstudios.alchemist.grants.GrantConfigurationService
import ltd.matrixstudios.alchemist.grants.configure.menu.duration.DurationEditorMenu
import ltd.matrixstudios.alchemist.grants.models.GrantDurationModel
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Class created on 8/3/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class GrantConfigureMenu(val player: Player, val category: GrantConfigCategory) : BorderedPaginatedMenu(player) {

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        val headers = mutableMapOf<Int, Button>(
            1 to SimpleActionButton(Material.COMPASS, mutableListOf(), GrantConfigCategory.DURATIONS.display, 0).setBody { player, i, clickType ->
                GrantConfigureMenu(
                    player,
                    GrantConfigCategory.DURATIONS
                ).updateMenu()
            },
            2 to SimpleActionButton(Material.PAPER, mutableListOf(), GrantConfigCategory.REASONS.display, 0).setBody { player, i, clickType ->
                GrantConfigureMenu(
                    player,
                    GrantConfigCategory.REASONS
                ).updateMenu()
            },
            3 to SimpleActionButton(Material.DIAMOND_SWORD, mutableListOf(), GrantConfigCategory.SCOPE_PRESETS.display, 0).setBody { player, i, clickType ->
                GrantConfigureMenu(
                    player,
                    GrantConfigCategory.SCOPE_PRESETS
                ).updateMenu()
            },
            4 to Button.placeholder(),
            5 to Button.placeholder(),
            6 to Button.placeholder(),
            7 to Button.placeholder(),
            17 to Button.placeholder(),
            18 to Button.placeholder(),
            26 to Button.placeholder(),
            35 to Button.placeholder(),
            36 to Button.placeholder(),
            37 to Button.placeholder(),
            38 to Button.placeholder(),
            39 to Button.placeholder(),
            40 to Button.placeholder(),
            41 to Button.placeholder(),
            42 to Button.placeholder(),
            43 to Button.placeholder(),
            44 to Button.placeholder(),
            9 to Button.placeholder(),
            27 to Button.placeholder(),
            10 to BooleanButton(category == GrantConfigCategory.DURATIONS),
            11 to BooleanButton(category == GrantConfigCategory.REASONS),
            12 to BooleanButton(category == GrantConfigCategory.SCOPE_PRESETS),
            13 to BooleanButton(false),
            14 to BooleanButton(false),
            15 to BooleanButton(false),
            16 to BooleanButton(false),
            17 to BooleanButton(false)
        )

        return headers
    }
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        if (category == GrantConfigCategory.DURATIONS) {
            for (dur in GrantConfigurationService.grantDurationModels.values)
            {
                buttons[i++] = DurationButton(dur)
            }
        }

        return buttons
    }

    override fun getButtonPositions(): List<Int> {
        return listOf(
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34
        )
    }

    override fun getTitle(player: Player): String {
        return "Configuring Grants"
    }

    class BooleanButton(val entry: Boolean) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.STAINED_GLASS_PANE
        }

        override fun getDescription(player: Player): MutableList<String>? {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("&f")
        }

        override fun getData(player: Player): Short {
            return if (entry) {
                5
            } else 7
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {

        }

    }

    class DurationButton(val model: GrantDurationModel) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.getMaterial(model.item) ?: Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()

            desc.add(" ")
            desc.add(Chat.format("&6Duration: &f" + model.duration))
            desc.add(Chat.format("&6Item: &f" + model.item))
            desc.add(Chat.format("&6Display Name: &f" + model.displayName))
            desc.add(Chat.format("&6Display Orientation: &f" + model.menuSlot))
            desc.add(Chat.format("&6Data: &f" + model.data))
            desc.add(" ")

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(model.displayName)
        }

        override fun getData(player: Player): Short {
            return model.data.toShort()
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            DurationEditorMenu(model, player).openMenu()
        }

    }

}