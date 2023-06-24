package ltd.matrixstudios.alchemist.commands.vouchers

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.models.vouchers.VoucherTemplate
import ltd.matrixstudios.alchemist.service.vouchers.VoucherService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender

@CommandAlias("voucher|vouchers")
@CommandPermission("alchemist.vouchers.admin")
class VoucherCommand : BaseCommand() {

    @Subcommand("template create")
    fun create(sender: CommandSender, @Name("id")id: String) {
        val template = VoucherService.findVoucherTemplate(id.lowercase())

        if (template != null) {
            sender.sendMessage(Chat.format("&cA voucher with this id already exists"))
            return
        }

        val toCreate = VoucherTemplate(id.lowercase(), id, "", mutableListOf())
        VoucherService.insertTemplate(toCreate)
        sender.sendMessage(Chat.format("&aCreated a new voucher template with the name &f$id"))
    }

    @Subcommand("template delete")
    fun delete(sender: CommandSender, @Name("id")id: String) {
        val template = VoucherService.findVoucherTemplate(id.lowercase())

        if (template == null) {
            sender.sendMessage(Chat.format("&cA voucher with this id does not exist"))
            return
        }

        VoucherService.handlerTemplates.deleteAsync(template.id)
        VoucherService.voucherTemplates.remove(template.id)

        sender.sendMessage(Chat.format("&aDeleted a voucher template with the name &f$id"))
    }
}