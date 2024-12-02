package dev.heypr.tradeflux;

import dev.heypr.tradeflux.commands.EAVCommand;
import dev.heypr.tradeflux.commands.ReloadCommand;
import dev.heypr.tradeflux.config.Util;
import dev.heypr.tradeflux.scanner.VillagerScanner;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tradeflux extends JavaPlugin {

    private static Tradeflux instance;
    private VillagerScanner scanner;
    private boolean scannerActive = true;

    @Override
    public void onEnable() {
        instance = this;
        if (!getConfig().contains("brain-task-time") && !getConfig().contains("restocking-task-time")) {
            saveResource("config.yml", true);
        }
        scanner = new VillagerScanner(this);
        scanner.setVillagerBrainTaskLoopTime(getUtil().getBrainTaskTime());
        scanner.setVillagerRestockingTaskLoopTime(getUtil().getRestockingTaskTime());
        scanner.startTaskTimer();
        registerCommand("tradefluxreload", new ReloadCommand(this));
        registerCommand("tradefluxenableallvillagers", new EAVCommand(this));
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

    public void save() {
        saveConfig();
    }

    public Util getUtil() {
        return new Util();
    }

    public VillagerScanner getScanner() {
        return scanner;
    }

    public boolean isScannerActive() {
        return scannerActive;
    }

    public void setScannerActive(boolean scannerActive) {
        this.scannerActive = scannerActive;
    }
}
