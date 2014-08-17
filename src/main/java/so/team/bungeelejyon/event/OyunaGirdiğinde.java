package so.team.bungeelejyon.event;

import java.util.ArrayList;

import so.team.bungeelejyon.BL;
import so.team.bungeelejyon.MY;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OyunaGirdi�inde implements Listener {
	
	public static ArrayList<ProxiedPlayer> oyunaGirenLejyon�yeleri = new ArrayList<>();
	
	 @EventHandler
	  public void Giri�Yapt���nda(final PostLoginEvent e)
	  {
		 if (BL.la.cekOyuncuLejyonu(e.getPlayer().getName()) != null)
		 {
			 if (!oyunaGirenLejyon�yeleri.contains(e.getPlayer()))
			 {
				 oyunaGirenLejyon�yeleri.add(e.getPlayer());
				 ArrayList<String> lejyonOyuncuListesi = BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(e.getPlayer().getName()));
				 for (String oyuncu : lejyonOyuncuListesi)
				 {
					 if (BL.ra.EgerOnline(oyuncu) == true && !oyuncu.contains(e.getPlayer().getName()))
					 {
						 BL.ra.mesajG�nder(oyuncu, MY.normalMesaj("�e" + e.getPlayer().getName() + " �6isimli lejyon �yesi oyuna giri� yapt�."));
					 }
				 }
			 }
		 }
	  }

}
