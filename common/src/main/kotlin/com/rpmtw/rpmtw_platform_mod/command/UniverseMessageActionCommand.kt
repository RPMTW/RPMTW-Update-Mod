package com.rpmtw.rpmtw_platform_mod.command

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.rpmtw.rpmtw_api_client.models.universe_chat.UniverseChatMessage
import com.rpmtw.rpmtw_platform_mod.gui.UniverseMessageActionScreen
import com.rpmtw.rpmtw_platform_mod.handlers.UniverseChatHandler
import kotlinx.coroutines.runBlocking
import net.minecraft.client.Minecraft
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.TranslatableComponent

class UniverseMessageActionCommand : RPMTWCommand() {
    override fun register(): LiteralArgumentBuilder<SharedSuggestionProvider> {
        val argumentName = "message_uuid"

        val command = literal("universeChatAction").then(argument(argumentName, StringArgumentType.string()).executes {
            val uuid: String = StringArgumentType.getString(it, argumentName)
            execute(uuid)

            return@executes CommandHandler.success
        })

        return command
    }

    companion object {
        fun execute(uuid: String) {
            runBlocking {
                val message: UniverseChatMessage? = UniverseChatHandler.getMessageAsync(uuid)
                val exception =
                    SimpleCommandExceptionType(TranslatableComponent("command.rpmtw_platform_mod.universeChatAction.getMessage.failed")).create()
                if (message == null) {
                    throw exception
                } else {
                    Minecraft.getInstance().executeBlocking {
                        Minecraft.getInstance().setScreen(UniverseMessageActionScreen(message))
                    }
                }
            }
        }
    }
}