package ltd.matrixstudios.alchemist.themes

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.themes.types.*

object ThemeLoader
{
    var themes = mutableMapOf<String, Theme>()

    lateinit var defaultTheme: Theme

    fun loadAllThemes()
    {
        themes["MMC"] = MMC()
        themes["hydrogen"] = Hydrogen()
        themes["neutron"] = Neutron()
        themes["mcore"] = mCore()
        themes["custom"] = Custom()

        val config = AlchemistSpigotPlugin.instance.config

        val theme = config.getString("defaultTheme")

        val foundTheme = themes.getOrDefault(theme, MMC())

        this.defaultTheme = foundTheme
    }

    fun setFallbackTheme(theme: Theme)
    {
        defaultTheme = theme

        val config = AlchemistSpigotPlugin.instance.config
        config.set("defaultTheme", theme.id)

        AlchemistSpigotPlugin.instance.saveConfig()
    }
}