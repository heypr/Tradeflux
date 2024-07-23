package dev.heypr.tradeflux.enums;

import org.bukkit.Material;

public enum VillagerProfessionBlocks {

    ARMORER(Material.BLAST_FURNACE),
    BUTCHER(Material.SMOKER),
    CARTOGRAPHER(Material.CARTOGRAPHY_TABLE),
    CLERIC(Material.BREWING_STAND),
    FARMER(Material.COMPOSTER),
    FISHERMAN(Material.BARREL),
    FLETCHER(Material.FLETCHING_TABLE),
    LEATHERWORKER(Material.CAULDRON),
    LIBRARIAN(Material.LECTERN),
    MASON(Material.STONECUTTER),
    SHEPHERD(Material.LOOM),
    TOOLSMITH(Material.SMITHING_TABLE),
    WEAPONSMITH(Material.GRINDSTONE);

    private final Material block;

    VillagerProfessionBlocks(Material block) {
        this.block = block;
    }

    public Material getBlock() {
        return block;
    }
}
