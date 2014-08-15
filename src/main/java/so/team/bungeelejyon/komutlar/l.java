package so.team.bungeelejyon.komutlar;

import java.util.ArrayList;

import so.team.bungeelejyon.BL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class l extends Command{
	public l(BL name){
	  super("l");
	}
	
    @SuppressWarnings("deprecation")
	@Override
    public void execute(CommandSender sender, String[] args){
		if(((ProxiedPlayer) sender).getServer().getInfo().getName().equalsIgnoreCase("cakmalobi")){
			sender.sendMessage("Bu komut burada yasak.");
			return;
		}
		if(((ProxiedPlayer) sender).getServer().getInfo().getName().startsWith("khg") || ((ProxiedPlayer) sender).getServer().getInfo().getName().startsWith("sg")){
			sender.sendMessage("Bu komut burada yasak.");
			return;
		}
		if (args.length == 0) {
			sender.sendMessage("Kullanýmý: /l <mesaj>");
		} else if (args.length >= 1){
			if (BL.la.cekOyuncuLejyonu(sender.getName()) == null){
				sender.sendMessage("Bu komutu kullanabilmek için, bir lejyon üyesi olmalýsýnýz.");
				return;
			}
			StringBuilder sb = new StringBuilder();
			for (int n = 0; n < args.length; n++){
				sb.append(args[n] + " ");
		}		
			
		ArrayList<String> lejyonOyuncuListesi = BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(sender.getName()));
		
		for (String oyuncu : lejyonOyuncuListesi){
			if (BL.ra.EgerOnline(oyuncu)){
				BL.ra.mesajGönder(oyuncu, sender.getName() + ": " + sb.toString());
			}
		}
		
	}
    	
   }

}