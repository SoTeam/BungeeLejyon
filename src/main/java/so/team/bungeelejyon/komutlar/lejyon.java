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
			sender.sendMessage("Kullanýmý");
		} else if (args.length == 1){
			if (args[0].toLowerCase().equalsIgnoreCase("ekle")){
				sender.sendMessage("Kullanýmý: /lejyon ekle <isim>");
			} else if (args[0].toLowerCase().equalsIgnoreCase("sil")){
				sender.sendMessage("Kullanýmý: /lejyon sil <isim>");
			}
		} else if (args.length == 2){
			if (args[0].toLowerCase().equalsIgnoreCase("ekle")){
				//Ekleme yetkisi varmý ?
				if (BL.la.cekOyuncuLejyonu(sender.getName()) == null){
					sender.sendMessage("Bu iþlem için bir lejyonunuz olmalý.");
					return;
				}
				if (BL.ra.EgerOnline(args[1]) == false){
					sender.sendMessage("Eklemeye çalýþtýðýnýz oyuncu online deðil.");
					return;
				}
				if (BL.la.lejyonaSahipmi(args[1]) == true){
					sender.sendMessage("Eklemeye çalýþtýðýnýz kiþi zaten lejyonda.");
					return;
				}
				if (BL.la.AktifLejyonTeklifleri.containsKey(args[1])){
					sender.sendMessage("Eklemeye çalýþtýðýnýz kiþiye zaten bir teklif sunulmuþ.");
					return;
				}
				
				BL.la.lejyonisteðiGönder(args[1], BL.la.cekOyuncuLejyonu(sender.getName()), (ProxiedPlayer) sender);
				sender.sendMessage("Belirttiðiniz oyuncuya lejyon isteði gönderildi.");
			} else if (args[0].toLowerCase().equalsIgnoreCase("kabul")){
				if (!BL.la.AktifLejyonTeklifleri.containsKey(sender.getName())){
					sender.sendMessage("Aktif lejyon teklifiniz bulunmuyor.");
					return;
				}
				
				BL.la.AktifLejyonTeklifleri.remove(sender.getName());
				try {
					BL.la.lejyonaEkle(sender.getName(), BL.la.AktifLejyonTeklifleri.get(sender.getName()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (args[0].toLowerCase().equalsIgnoreCase("sil")){
				//Silme yetkisi varmý ?
				if (BL.la.cekOyuncuLejyonu(sender.getName()) == null){
					sender.sendMessage("Bu iþlem için bir lejyonunuz olmalý.");
					return;
				}
				if (BL.la.cekOyuncuLejyonu(sender.getName()) != BL.la.cekOyuncuLejyonu(args[1])){
					sender.sendMessage("Belirttiðiniz kiþi sizin lejyonunuzda deðil.");
					return;
				}
				if (BL.la.lejyonaSahipmi(args[1]) == false){
					sender.sendMessage("Eklemeye çalýþtýðýnýz kiþi zaten lejyonda deðil.");
					return;
				}
				
				try {
					BL.la.lejyondanSil(args[1], BL.la.cekOyuncuLejyonu(sender.getName()));
					sender.sendMessage("Baþarýyla kiþiyi lejyondan çýkardýnýz.");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
    	
   }

}