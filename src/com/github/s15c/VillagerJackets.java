package com.github.s15c;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Villager.Career;

public class VillagerJackets extends JavaPlugin{

	private static String version = "[Villager Jackets v0.1]";
	private static Plugin myPlugin;
	
	private static HashMap<String, Career> jacketMap;
	private static HashMap<Career, String> careerMap;
	private static String jacketSuffix = " Jacket";
	
	public VillagerJackets()
	{
		myPlugin = this;
		createMaps();
		addAllRecipes();
	}
	
	@Override
	public void onEnable() {
	    getServer().getPluginManager().registerEvents(new InteractListener(), this);  
	}
	
	@Override
	public void onDisable() {
		//do nothing :D
	}
	
	private static void createMaps()
	{
		jacketMap = new HashMap<String, Career>();
		careerMap = new HashMap<Career, String>();
		
		for(Career c : Career.values())
		{
			StringBuilder name = new StringBuilder();
			
			name.append("§a");
			name.append(c.toString().charAt(0));
			name.append(c.toString().substring(1).toLowerCase());
			name.append(jacketSuffix);
			
			jacketMap.put(name.toString(), c);
			careerMap.put(c, name.toString());
		}
	}
	
	private static void addAllRecipes()
	{
		ItemStack jacket = new ItemStack(Material.LEATHER_CHESTPLATE);
		
		addOneRecipe(jacket, Career.ARMORER, Material.IRON_CHESTPLATE);
		addOneRecipe(jacket, Career.BUTCHER, Material.PORK);
		addOneRecipe(jacket, Career.CARTOGRAPHER, Material.EMPTY_MAP);
		addOneRecipe(jacket, Career.CLERIC, Material.GOLDEN_APPLE);
		addOneRecipe(jacket, Career.FARMER, Material.WHEAT);
		addOneRecipe(jacket, Career.FISHERMAN, Material.RAW_FISH);
		addOneRecipe(jacket, Career.FLETCHER, Material.ARROW);
		addOneRecipe(jacket, Career.LEATHERWORKER, Material.LEATHER_CHESTPLATE);	//don't ask questions
		addOneRecipe(jacket, Career.LIBRARIAN, Material.BOOK);
		addOneRecipe(jacket, Career.NITWIT, Material.SPONGE);
		addOneRecipe(jacket, Career.SHEPHERD, Material.WOOL);
		addOneRecipe(jacket, Career.TOOL_SMITH, Material.STONE_PICKAXE);
		addOneRecipe(jacket, Career.WEAPON_SMITH, Material.STONE_SWORD);
	}
	
	private static void addOneRecipe(ItemStack jacket, Career career, Material ingredient)
	{
		ItemMeta meta = jacket.getItemMeta();
		meta.setDisplayName(String.format("%s", careerMap.get(career)));
		jacket.setItemMeta(meta);
		
		NamespacedKey key = new NamespacedKey(myPlugin, careerMap.get(career).replaceAll(" ", ""));	//remove spaces
		
		ShapedRecipe recipe = new ShapedRecipe(key, jacket);
		recipe.shape("L L", "LXL", "LLL");
		recipe.setIngredient('L', Material.LEATHER);
		recipe.setIngredient('X', ingredient);
		Bukkit.addRecipe(recipe);
	}
	
	public static HashMap<String, Career> getJacketMap() {
		return jacketMap;
	}
	
	public static String getVersion() {
		return version;
	}
}
