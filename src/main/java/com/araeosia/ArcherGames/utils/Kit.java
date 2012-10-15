package com.araeosia.ArcherGames.utils;

import com.araeosia.ArcherGames.ArcherGames;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit {
	private HashMap<String, ItemStack> armor;
	private ArrayList<ItemStack> itemsToGive;
	private String ability;
	private String description;
	private String permission;
	private String name;
	
	public void giveToPlayer(Player player){
		for(String key : armor.keySet()){
			if(key.equalsIgnoreCase("head")){
				player.getInventory().setHelmet(armor.get(key));
			}else if(key.equalsIgnoreCase("chest")){
				player.getInventory().setChestplate(armor.get(key));
			}else if(key.equalsIgnoreCase("pants")){
				player.getInventory().setLeggings(armor.get(key));
			}else if(key.equalsIgnoreCase("boots")){
				player.getInventory().setBoots(armor.get(key));
			}
		}
		for(ItemStack key : itemsToGive){
			player.getInventory().addItem(key);
		}
		Archer.getByName(player.getName()).setAbility(ability);
	}
	public HashMap<String, ItemStack> getArmor(){
		return armor;
	}
	public void setArmor(HashMap<String, ItemStack> armor){
		this.armor = armor;
	}
	public ArrayList<ItemStack> getItems(){
		return itemsToGive;
	}
	public void setItems(ArrayList<ItemStack> itemsToGive){
		this.itemsToGive = itemsToGive;
	}
	public String getAbility(){
		return ability;
	}
	public void setAbility(String ability){
		this.ability = ability;
	}
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getPermission(){
		return permission;
	}
	public void setPermission(String permission){
		this.permission = permission;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	@Override
	public String toString(){
		String output = new String();
		output = "Name: "+name+", Ability: "+ability+", Description: "+description+", Permission: "+permission+", itemsToGive: "+itemsToGive.toString()+", armor: "+armor.toString();
		return output;
	}
}