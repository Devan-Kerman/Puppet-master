package net.devtech.puppetmaster;

import com.sk89q.worldedit.EditSessionFactory;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.devtech.puppetmaster.animation.items.ItemBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public final class PuppetMaster extends JavaPlugin {
	public static WorldEditPlugin plugin;
	public static WorldEdit worldEdit;
	public static EditSessionFactory editSessionFactory;
	public static ItemBuilder animationWand;

	@Override
	public void onEnable() {
		// Plugin startup logic
		WorldEditPlugin plugin = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");
		if(plugin == null) {
			getLogger().severe("World edit was not found!");
			throw new RuntimeException("World edit not found");
		}
		PuppetMaster.plugin = plugin;
		PuppetMaster.worldEdit = plugin.getWorldEdit();
		PuppetMaster.editSessionFactory = worldEdit.getEditSessionFactory();
		animationWand = new ItemBuilder(this, 10101).onUse(e -> {

		}, false);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
}
