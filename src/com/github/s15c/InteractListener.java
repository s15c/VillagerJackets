package com.github.s15c;

import com.github.s15c.VillagerJackets;

import java.util.Set;

import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Career;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener{
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
		ItemStack offHand = event.getPlayer().getInventory().getItemInOffHand();
		
		Set<String> jacketSet = VillagerJackets.getJacketMap().keySet();
		
		for(String name : jacketSet)
		{
			if(mainHand.hasItemMeta())
			{
				if(mainHand.getItemMeta().getDisplayName().equalsIgnoreCase(name))
				{
					Career career = VillagerJackets.getJacketMap().get(mainHand.getItemMeta().getDisplayName());
					rightClickWithJacket(event, mainHand, career);
					event.getPlayer().getInventory().removeItem(mainHand);
				}
				else if(offHand.hasItemMeta())
				{
					if(offHand.getItemMeta().getDisplayName().equalsIgnoreCase(name))
					{
						Career career = VillagerJackets.getJacketMap().get(mainHand.getItemMeta().getDisplayName());
						rightClickWithJacket(event, offHand, career);
						event.getPlayer().getInventory().removeItem(offHand);
					}
				}
			}
		}
	}
	
	private static void rightClickWithJacket(PlayerInteractEntityEvent event, ItemStack jacket, Career career)
	{
		if(event.getRightClicked() instanceof Villager) {
			setVillagerType((Villager) event.getRightClicked(), career);
		}
	}
	
	private static void setVillagerType(Villager villagerToChange, Career career)
	{
		//have to spawn a new villager and remove the other one because of a Spigot bug
		Villager newVillager = (Villager) villagerToChange.getWorld().spawnEntity(villagerToChange.getLocation(), villagerToChange.getType());
		villagerToChange.remove();
		newVillager.setProfession(career.getProfession());
		newVillager.setCareer(career, true);
	}
}
