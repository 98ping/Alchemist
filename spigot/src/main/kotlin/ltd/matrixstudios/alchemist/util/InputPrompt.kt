package ltd.matrixstudios.alchemist.util

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import org.bukkit.ChatColor
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.ConversationFactory
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.StringPrompt
import org.bukkit.entity.Player

class InputPrompt : StringPrompt() {

    private var promptText: String = "${ChatColor.GREEN}Please input a value."
    private var charLimit: Int = -1
    private var regex: Regex? = null
    private var use: ((String) -> Unit)? = null
    private var fail: ((String) -> Unit)? = null

    fun withText(text: String): InputPrompt {
        this.promptText =  text
        return this
    }

    fun withLimit(limit: Int): InputPrompt {
        this.charLimit = limit
        return this
    }

    fun withRegex(regex: Regex): InputPrompt {
        this.regex = regex
        return this
    }

    fun acceptInput(use: (String) -> Unit): InputPrompt {
        this.use = use
        return this
    }

    fun onFail(use: (String) -> Unit): InputPrompt {
        this.fail = use
        return this
    }

    override fun getPromptText(context: ConversationContext): String {
        return promptText
    }

    override fun acceptInput(context: ConversationContext, input: String): Prompt? {
        if (charLimit != -1) {
            if (input.length > charLimit) {
                context.forWhom.sendRawMessage("${ChatColor.RED}Input text is too long! (${input.length} > ${charLimit})")
                fail?.invoke(input)
                return Prompt.END_OF_CONVERSATION
            }
        }

        if (regex != null) {
            if (!input.matches(regex!!)) {
                context.forWhom.sendRawMessage("${ChatColor.RED}Input text does not match regex pattern ${regex!!.pattern}.")
                fail?.invoke(input)
                return Prompt.END_OF_CONVERSATION
            }
        }

        try {
            use!!.invoke(input)
        } catch (e: Exception) {
            context.forWhom.sendRawMessage("${ChatColor.RED}Failed to handle input: ${ChatColor.WHITE}${input}")
            fail?.invoke(input)
        }

        return Prompt.END_OF_CONVERSATION
    }

    fun start(player: Player) {
        if (use == null) {
            throw IllegalStateException("No use function applied")
        }

        if (player.openInventory != null) {
            player.closeInventory()
        }

        val factory = ConversationFactory(AlchemistSpigotPlugin.instance)
            .withModality(false)
            .withFirstPrompt(this)
            .withLocalEcho(false)
            .thatExcludesNonPlayersWithMessage("Go away evil console!")

        player.beginConversation(factory.buildConversation(player))
    }

}