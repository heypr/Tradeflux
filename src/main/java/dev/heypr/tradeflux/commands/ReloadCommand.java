package dev.heypr.tradeflux.commands;

import dev.heypr.tradeflux.Tradeflux;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    private final Tradeflux plugin;

    public ReloadCommand(Tradeflux plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        plugin.getUtil().reload();
        sender.sendMessage(Component.text("Config reloaded!").color(TextColor.color(144, 238, 144)));
        return true;
    }
}
