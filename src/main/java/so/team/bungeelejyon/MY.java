package so.team.bungeelejyon;

import net.md_5.bungee.api.ChatColor;

// Mesaj Yöneticisi Classýdýr.
public class MY {

	
	//REDIS MESAJ
	
	public static String normalMesaj(String mesaj){
		return BL.prefix + ChatColor.GOLD + mesaj;
	}
	
	public static String hataMesajý(String mesaj){
		return BL.prefix + ChatColor.RED + mesaj;
	}
	
	
	public static String uyarýMesajý(String mesaj){
		return BL.prefix + ChatColor.DARK_RED + mesaj;
	}
	

}
