package me.narwhql.spigotmc.commands;

import me.narwhql.spigotmc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;

public class ArmorStandCommand implements CommandExecutor {
    private final List<ArmorStand> armorStands = new ArrayList<>();

    public ArmorStandCommand() {
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> armorStands.forEach(armorStand -> armorStand.setHeadPose(new EulerAngle(0, armorStand.getHeadPose().getY() + 0.01, 0))), 1, 1);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        player.sendMessage("§aArmor Stand has been spawned.");

        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        skullMeta.setOwner("1Narwhql");
        itemStack.setItemMeta(skullMeta);

        armorStand.setHelmet(itemStack);
        armorStand.setVisible(false);
        armorStand.setCustomName("§e§lHello");
        armorStand.setCustomNameVisible(true);

        this.armorStands.add(armorStand);

        return true;
    }
}
