package com.araeosia.ArcherGames.utils;

import com.araeosia.ArcherGames.ArcherGames;

public class Economy {
	
	public ArcherGames plugin;
	
	public Economy(ArcherGames plugin){
		this.plugin = plugin;
	}
	
	public void givePlayer(String name, double give){
		plugin.db.setMoney(name, getBalance(name) + give);
	}
	
	public void takePlayer(String name, double balance){
		
	}
	
	public double getBalance(String name){
		return 0;
	}
}