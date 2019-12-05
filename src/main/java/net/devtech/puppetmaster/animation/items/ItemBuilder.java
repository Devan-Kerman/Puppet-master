package net.devtech.puppetmaster.animation.items;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import java.util.function.Consumer;

public class ItemBuilder {
	private final Plugin plugin;
	private final PluginManager manager;
	private final int id;
	private ItemStack base;

	public ItemBuilder(Plugin plugin, int id) {
		this.plugin = plugin;
		this.id = id;
		this.manager = plugin.getServer().getPluginManager();
	}

	public ItemStack newStack() {
		return base.clone();
	}

	public boolean matches(ItemStack stack) {
		return stack.getItemMeta().getCustomModelData() == id;
	}

	public ItemBuilder setBase(ItemStack base) {
		ItemMeta meta = base.getItemMeta();
		meta.setCustomModelData(id);
		base.setItemMeta(meta);
		this.base = base;
		return this;
	}

	public ItemBuilder onUse(KewlListener<PlayerInteractEvent> interact, boolean cancelled) {
		manager.registerEvent(PlayerInteractEvent.class, interact, EventPriority.NORMAL, this::filtered, plugin, cancelled);
		return this;
	}

	public ItemBuilder onEntity(KewlListener<PlayerInteractAtEntityEvent> interact, boolean cancelled) {
		manager.registerEvent(PlayerInteractAtEntityEvent.class, interact, EventPriority.NORMAL, this::filtered, plugin, cancelled);
		return this;
	}

	private void filtered(Listener listener, Event event) {
		ItemStack stack;
		if (event instanceof PlayerEvent) // if player event
			stack = ((PlayerEvent) event).getPlayer().getInventory().getItemInMainHand();
		else
			throw new RuntimeException("Unsupported event type!");

		if (stack.getItemMeta().getCustomModelData() == id) // if correct
			((Consumer) listener).accept(event);
	}

	public static interface KewlListener<T extends Event> extends Listener, Consumer<T> {}

}
