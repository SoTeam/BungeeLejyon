package so.team.bungeelejyon;

import net.md_5.bungee.api.ChatColor;

// Mesaj Y�neticisi Class�d�r.
public class MY {

	
	public static String iyiMesaj(String mesaj){
		return BL.prefix + ChatColor.GREEN + mesaj;
	}
	
	
	public static String normalMesaj(String mesaj){
		return BL.prefix + ChatColor.GOLD + mesaj;
	}
	
	public static String k�t�Mesaj(String mesaj){
		return BL.prefix + ChatColor.RED + mesaj;
	}
	
	
	public static String uyar�Mesaj�(String mesaj){
		return BL.prefix + ChatColor.DARK_RED + mesaj;
	}
	

}
