package so.team.bungeelejyon.event;

import so.team.bungeelejyon.BL;
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
				 e.getPlayer().sendMessage(BL.prefix + BL.la.MOTD.get(lejyonAd�));
			 }
		 }
	  }

}
