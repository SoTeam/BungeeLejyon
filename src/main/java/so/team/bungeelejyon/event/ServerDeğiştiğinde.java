package so.team.bungeelejyon.event;

import so.team.bungeelejyon.BL;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerDeðiþtiðinde implements Listener {
		
	 @SuppressWarnings("deprecation")
	 @EventHandler
	  public void LobiyeGirdiðinde(ServerSwitchEvent e)
	  {
		 if (OyunaGirdiðinde.oyunaGirenLejyonÜyeleri.contains(e.getPlayer()))
		 {
			 if (e.getPlayer().getServer().getInfo().getName().startsWith("Lobi"))
			 {
				 OyunaGirdiðinde.oyunaGirenLejyonÜyeleri.remove(OyunaGirdiðinde.oyunaGirenLejyonÜyeleri.indexOf(e.getPlayer()));
				 String lejyonAdý = BL.la.OyuncuLejyonu.get(e.getPlayer().getName());
				 e.getPlayer().sendMessage(BL.prefix + BL.la.MOTD.get(lejyonAdý));
			 }
		 }
	  }

}
