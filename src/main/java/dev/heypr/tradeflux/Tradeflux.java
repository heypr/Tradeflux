package dev.heypr.tradeflux;

import dev.heypr.tradeflux.commands.ReloadCommand;
import dev.heypr.tradeflux.config.Util;
import dev.heypr.tradeflux.scanner.VillagerScanner;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tradeflux extends JavaPlugin {

    private static Tradeflux instance;
    private VillagerScanner scanner;

    @Override
    public void onEnable() {
        instance = this;
        saveResource("config.yml", false);
        scanner = new VillagerScanner(this);
        scanner.setVillagerBrainTaskLoopTime(getUtil().getBrainTaskTime());
        scanner.setVillagerRestockingTaskLoopTime(getUtil().getRestockingTaskTime());
        scanner.startTaskTimer();
        registerCommand("tradefluxreload", new ReloadCommand(this));
    }

    @Override
    public void onDisable() {
        scanner.cancelTaskTimers();
        saveConfig();
    }

    public void registerCommand(String command, CommandExecutor executor) {
        getCommand(command).setExecutor(executor);
    }

    public static Tradeflux getInstance() {
        return instance;
    }

    public Util getUtil() {
        return new Util();
    }

    public VillagerScanner getScanner() {
        return scanner;
    }
}
