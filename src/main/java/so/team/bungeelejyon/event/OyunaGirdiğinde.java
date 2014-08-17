package so.team.bungeelejyon.event;

import java.util.ArrayList;

import so.team.bungeelejyon.BL;
import so.team.bungeelejyon.MY;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OyunaGirdiðinde implements Listener {
	
	public static ArrayList<ProxiedPlayer> oyunaGirenLejyonÜyeleri = new ArrayList<>();
	
	 @EventHandler
	  public void GiriþYaptýðýnda(final PostLoginEvent e)
	  {
		 if (BL.la.cekOyuncuLejyonu(e.getPlayer().getName()) != null)
		 {
			 if (!oyunaGirenLejyonÜyeleri.contains(e.getPlayer()))
			 {
				 oyunaGirenLejyonÜyeleri.add(e.getPlayer());
				 ArrayList<String> lejyonOyuncuListesi = BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(e.getPlayer().getName()));
				 for (String oyuncu : lejyonOyuncuListesi)
				 {
					 if (BL.ra.EgerOnline(oyuncu) == true && !oyuncu.contains(e.getPlayer().getName()))
					 {
						 BL.ra.mesajGönder(oyuncu, MY.normalMesaj("§e" + e.getPlayer().getName() + " §6isimli lejyon üyesi oyuna giriþ yaptý."));
					 }
				 }
			 }
		 }
	  }

}
