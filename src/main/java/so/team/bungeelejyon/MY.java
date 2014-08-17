package so.team.bungeelejyon;

import net.md_5.bungee.api.ChatColor;

// Mesaj Yöneticisi Classýdýr.
public class MY {

	
	public static String iyiMesaj(String mesaj){
		return BL.prefix + ChatColor.GREEN + mesaj;
	}
	
	
	public static String normalMesaj(String mesaj){
		return BL.prefix + ChatColor.GOLD + mesaj;
	}
	
	public static String kötüMesaj(String mesaj){
		return BL.prefix + ChatColor.RED + mesaj;
	}
	
	
	public static String uyarýMesajý(String mesaj){
		return BL.prefix + ChatColor.DARK_RED + mesaj;
	}
	

}
