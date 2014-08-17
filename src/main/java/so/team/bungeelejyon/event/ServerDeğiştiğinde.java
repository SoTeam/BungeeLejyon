package so.team.bungeelejyon.event;

import java.text.SimpleDateFormat;
import so.team.bungeelejyon.BL;
import so.team.bungeelejyon.MY;
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
					 String MOTD = BL.la.MOTD.get(lejyonAd�);
					 e.getPlayer().sendMessage(BL.prefix + MOTD.replaceAll("&", "�"));
				 }
				 if (BL.la.faturaTarihiGe�mi�mi(lejyonAd�) == true && BL.la.OyuncuR�tbesi.get(e.getPlayer().getName()).equalsIgnoreCase("Tu�general")){
					 long faturaTarihi = BL.la.FaturaTarihi.get(lejyonAd�);
				     SimpleDateFormat zamancevir = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				     
					 e.getPlayer().sendMessage(MY.hataMesaj�("Lejyon harc�n�z�n son �deme tarihi ge�mi�tir."));
					 e.getPlayer().sendMessage(MY.hataMesaj�(zamancevir.format(faturaTarihi) + " tarihine kadar �deme yapmazsan�z lejyonunuz tamamen kapat�lacakt�r."));
					 e.getPlayer().sendMessage(MY.hataMesaj�("Bu s�re boyunca lejyonunuz k�s�tl� fonksiyonlar ile aktif kalacakt�r."));
					 return;
				 }
				 if (BL.la.faturaTarihiYakla�M��m�(lejyonAd�) == true && BL.la.OyuncuR�tbesi.get(e.getPlayer().getName()).equalsIgnoreCase("Tu�general")){
					 long faturaTarihi = BL.la.FaturaTarihi.get(lejyonAd�);
				     SimpleDateFormat zamancevir = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				     
					 e.getPlayer().sendMessage(MY.hataMesaj�("Lejyon harc�n�z�n son �deme tarihi yakla�m��t�r."));
					 e.getPlayer().sendMessage(MY.hataMesaj�(zamancevir.format(faturaTarihi) + " tarihine kadar �deme yapmazsan�z gerekmektedir."));
					 e.getPlayer().sendMessage(MY.hataMesaj�("Bu s�re boyunca lejyon harc�n� �demezseniz lejyonunuz k�s�tlanacakt�r."));
				 }
			 }
		 }
	  }

}
