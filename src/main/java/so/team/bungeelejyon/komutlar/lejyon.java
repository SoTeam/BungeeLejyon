package so.team.bungeelejyon.komutlar;

import java.sql.SQLException;

import so.team.bungeelejyon.BL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class lejyon extends Command{
	public lejyon(BL name){
	  super("lejyon");
	}
	
    @SuppressWarnings("deprecation")
	@Override
    public void execute(CommandSender sender, String[] args){
		if(((ProxiedPlayer) sender).getServer().getInfo().getName().equalsIgnoreCase("cakmalobi")){
			sender.sendMessage("Bu komut burada yasak.");
			return;
		}
		if (args.length == 0) {
			sender.sendMessage("Kullan�m�");
		} else if (args.length == 1){
			if (args[0].toLowerCase().equalsIgnoreCase("ekle")){
				sender.sendMessage("Kullan�m�: /lejyon ekle <isim>");
			} else if (args[0].toLowerCase().equalsIgnoreCase("sil")){
				sender.sendMessage("Kullan�m�: /lejyon sil <isim>");
			}
		} else if (args.length == 2){
			if (args[0].toLowerCase().equalsIgnoreCase("ekle")){
				//Ekleme yetkisi varm� ?
				if (BL.ra.EgerOnline(args[1]) == false){
					sender.sendMessage("Eklemeye �al��t���n�z oyuncu online de�il.");
					return;
				}
				try {
					BL.la.lejyonaEkle(args[1], BL.la.cekOyuncuLejyonu(sender.getName()), (ProxiedPlayer) sender);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (args[0].toLowerCase().equalsIgnoreCase("sil")){
				//Silme yetkisi varm� ?
				if (BL.la.cekOyuncuLejyonu(sender.getName()) != BL.la.cekOyuncuLejyonu(args[1])){
					sender.sendMessage("Belirtti�iniz ki�i sizin lejyonunuzda de�il.");
					return;
				}
				try {
					BL.la.lejyondanSil(args[1], BL.la.cekOyuncuLejyonu(sender.getName()), (ProxiedPlayer) sender);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
    	
   }

}