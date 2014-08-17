package so.team.bungeelejyon.event;

import java.text.SimpleDateFormat;
import so.team.bungeelejyon.BL;
import so.team.bungeelejyon.MY;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerDe�i�ti�inde implements Listener {
		
	 @SuppressWarnings("deprecation")
	 @EventHandler
	 public void LobiyeGirdi�inde(ServerSwitchEvent e)
	  {
		 if (OyunaGirdi�inde.oyunaGirenLejyon�yeleri.contains(e.getPlayer()))
		 {
			 if (e.getPlayer().getServer().getInfo().getName().startsWith("Lobi"))
			 {
				 OyunaGirdi�inde.oyunaGirenLejyon�yeleri.remove(OyunaGirdi�inde.oyunaGirenLejyon�yeleri.indexOf(e.getPlayer()));
				 String lejyonAd� = BL.la.OyuncuLejyonu.get(e.getPlayer().getName());
				 if (BL.la.MOTD.containsKey(lejyonAd�)){
					 e.getPlayer().sendMessage(BL.prefix + BL.la.MOTD.get(lejyonAd�));
				 }
				 if (BL.la.faturaTarihiYakla�M��m�(lejyonAd�) == true && BL.la.OyuncuR�tbesi.get(e.getPlayer().getName()).equalsIgnoreCase("Tu�general")){
					 long faturaTarihi = BL.la.FaturaTarihi.get(lejyonAd�);
				     SimpleDateFormat zamancevir = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				     
					 e.getPlayer().sendMessage(MY.uyar�Mesaj�("Lejyon harc�n�z�n son �deme tarihi yakla�m��t�r."));
					 e.getPlayer().sendMessage(ChatColor.RED + zamancevir.format(faturaTarihi) + MY.uyar�Mesaj�("tarihine kadar �deme yapmazsan�z gerekmektedir."));
					 e.getPlayer().sendMessage(MY.uyar�Mesaj�("Bu s�re boyunca lejyon harc�n� �demezseniz lejyonunuz k�s�tlanacakt�r."));
				 }
				 if (BL.la.faturaTarihiGe�mi�mi(lejyonAd�) == true && BL.la.OyuncuR�tbesi.get(e.getPlayer().getName()).equalsIgnoreCase("Tu�general")){
					 long faturaTarihi = BL.la.FaturaTarihi.get(lejyonAd�);
				     SimpleDateFormat zamancevir = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				     
					 e.getPlayer().sendMessage(MY.uyar�Mesaj�("Lejyon harc�n�z�n son �deme tarihi ge�mi�tir."));
					 e.getPlayer().sendMessage(ChatColor.RED + zamancevir.format(faturaTarihi) + MY.uyar�Mesaj�("tarihine kadar �deme yapmazsan�z lejyonunuz tamamen kapat�lacakt�r."));
					 e.getPlayer().sendMessage(MY.uyar�Mesaj�("Bu s�re boyunca lejyonunuz k�s�tl� fonksiyonlar ile aktif kalacakt�r."));
				 }
			 }
		 }
	  }

}
