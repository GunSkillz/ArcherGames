package com.araeosia.ArcherGames.utils;

import com.araeosia.ArcherGames.ArcherGames;
import org.bukkit.entity.Player;

public class Archer {

	private String name;
	private boolean playing;
	private int place;
	private Kit kit;

	public Archer(String playerName) {
		this.name = playerName;
		playing = false;
	}
	
	public void setPlaying(Boolean playing){
		this.playing = playing;
	}

	public Kit getKit() {
		return kit;
	}

	public void selectKit(Kit kit) {
		this.kit = kit;
	}

	public boolean getPlaying() {
		return playing;
	}
	
	public void setPlace(int place){
		this.place = place;
	}
	
	public int getPlace(){
		return place;
	}
	public String getName(){
		return name;
	}
}
