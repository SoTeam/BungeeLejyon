package so.team.bungeelejyon;

import net.md_5.bungee.api.ChatColor;

// Mesaj Y�neticisi Class�d�r.
public class MY {

	
	//REDIS MESAJ
	
	public static String normalMesaj(String mesaj){
		return BL.prefix + ChatColor.GOLD + mesaj;
	}
	
	public static String hataMesaj�(String mesaj){
		return BL.prefix + ChatColor.RED + mesaj;
	}
	
	
	public static String uyar�Mesaj�(String mesaj){
		return BL.prefix + ChatColor.DARK_RED + mesaj;
	}
	

}
