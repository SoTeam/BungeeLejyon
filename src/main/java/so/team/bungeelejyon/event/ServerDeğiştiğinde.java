package so.team.bungeelejyon.event;

import java.text.SimpleDateFormat;
import so.team.bungeelejyon.BL;
import so.team.bungeelejyon.MY;
import net.md_5.bungee.api.ChatColor;
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
				 if (BL.la.MOTD.containsKey(lejyonAdý)){
					 e.getPlayer().sendMessage(BL.prefix + BL.la.MOTD.get(lejyonAdý));
				 }
				 if (BL.la.faturaTarihiYaklaþMýþmý(lejyonAdý) == true && BL.la.OyuncuRütbesi.get(e.getPlayer().getName()).equalsIgnoreCase("Tuðgeneral")){
					 long faturaTarihi = BL.la.FaturaTarihi.get(lejyonAdý);
				     SimpleDateFormat zamancevir = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				     
					 e.getPlayer().sendMessage(MY.uyarýMesajý("Lejyon harcýnýzýn son ödeme tarihi yaklaþmýþtýr."));
					 e.getPlayer().sendMessage(ChatColor.RED + zamancevir.format(faturaTarihi) + MY.uyarýMesajý("tarihine kadar ödeme yapmazsanýz gerekmektedir."));
					 e.getPlayer().sendMessage(MY.uyarýMesajý("Bu süre boyunca lejyon harcýný ödemezseniz lejyonunuz kýsýtlanacaktýr."));
				 }
				 if (BL.la.faturaTarihiGeçmiþmi(lejyonAdý) == true && BL.la.OyuncuRütbesi.get(e.getPlayer().getName()).equalsIgnoreCase("Tuðgeneral")){
					 long faturaTarihi = BL.la.FaturaTarihi.get(lejyonAdý);
				     SimpleDateFormat zamancevir = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				     
					 e.getPlayer().sendMessage(MY.uyarýMesajý("Lejyon harcýnýzýn son ödeme tarihi geçmiþtir."));
					 e.getPlayer().sendMessage(ChatColor.RED + zamancevir.format(faturaTarihi) + MY.uyarýMesajý("tarihine kadar ödeme yapmazsanýz lejyonunuz tamamen kapatýlacaktýr."));
					 e.getPlayer().sendMessage(MY.uyarýMesajý("Bu süre boyunca lejyonunuz kýsýtlý fonksiyonlar ile aktif kalacaktýr."));
				 }
			 }
		 }
	  }

}
