package so.team.bungeelejyon.komutlar;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry;

import so.team.bungeelejyon.BL;
import so.team.bungeelejyon.MY;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
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
			sender.sendMessage(MY.uyarıMesajı("Burada bu komutu kullanamazsın."));
			return;
		}
		if (args.length == 0) {
			yardımMesajı((ProxiedPlayer) sender);
		} else if (args.length == 1){
			if (args[0].toLowerCase().equalsIgnoreCase("davet")){
				sender.sendMessage(MY.normalMesaj("Kullanımı: /lejyon davet <isim> - Oyuncuya lejyon daveti gönderir."));
			} else if (args[0].toLowerCase().equalsIgnoreCase("tasfiye")){
				sender.sendMessage(MY.normalMesaj("Kullanımı: /lejyon tasfiye <isim> - Lejyon üyesini lejyondan atar."));
			} else if (args[0].toLowerCase().equalsIgnoreCase("kabul")){
				kabulet((ProxiedPlayer) sender);
			} else if (args[0].toLowerCase().equalsIgnoreCase("ret")){
				reddet((ProxiedPlayer) sender);
			} else if (args[0].toLowerCase().equalsIgnoreCase("motd")){
				motdGöster((ProxiedPlayer) sender);
			} else if (args[0].toLowerCase().equalsIgnoreCase("top10")){
				top10Göster(((ProxiedPlayer) sender));
			} else if (args[0].toLowerCase().equalsIgnoreCase("üyeler")){
				üyeleriGöster((ProxiedPlayer) sender);
			} else if (args[0].toLowerCase().equalsIgnoreCase("ayrıl")){
				ayrıl((ProxiedPlayer) sender);
			} else if (args[0].toLowerCase().equalsIgnoreCase("bilgi")){
				if (BL.la.cekOyuncuLejyonu(sender.getName()) != null){
					bilgi((ProxiedPlayer) sender,BL.la.cekOyuncuLejyonu(sender.getName()));
				} else {
					sender.sendMessage(MY.kötüMesaj("Bu işlem için lejyonda olmalısınız."));
				}
			} else if (args[0].toLowerCase().equalsIgnoreCase("yenile")){
				if (sender.hasPermission("bungeeplus.aktar")){
					try {
						BL.la.bilgileriYukle();
						BL.m.seviyeleriGüncelle();
						sender.sendMessage(MY.iyiMesaj("Güncelleme işlemi başarılı."));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				yardımMesajı((ProxiedPlayer) sender);
			}
			
		} else if (args.length >= 2){
			if (args[0].toLowerCase().equalsIgnoreCase("davet")){
				davet((ProxiedPlayer) sender, args[1]);
			} else if (args[0].toLowerCase().equalsIgnoreCase("tasfiye")){
				tasfiye((ProxiedPlayer) sender, args[1]);
			} else if (args[0].toLowerCase().equalsIgnoreCase("yarat")){
				if (sender.hasPermission("bungeeplus.aktar")){
					lejyonyarat((ProxiedPlayer) sender, args[1]);
				}
			} else if (args[0].toLowerCase().equalsIgnoreCase("ver")){
				if (sender.hasPermission("bungeeplus.alert")){
					ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
					lejyonyarat(p, args[1]);
				}
			} else if (args[0].toLowerCase().equalsIgnoreCase("bilgi")){
				if (BL.la.LejyonSeviyesi.get(args[1]) != null){
					bilgi((ProxiedPlayer) sender, args[1]);
				} else {
					sender.sendMessage(MY.kötüMesaj(args[1] + " isminde bir lejyon bulunamadı."));
					sender.sendMessage(MY.kötüMesaj("Lejyon isimleri büyük - küçük harf duyarlıdır."));
				}
			} else if (args[0].toLowerCase().equalsIgnoreCase("motd")){
				StringBuilder sb = new StringBuilder();
				for (int n = 1; n < args.length; n++){
					sb.append(args[n] + " ");
				}	
				motdAta((ProxiedPlayer) sender, sb.toString());
			} else if (args[0].toLowerCase().equalsIgnoreCase("geribildirim")){
				StringBuilder sb = new StringBuilder();
				for (int n = 1; n < args.length; n++){
					sb.append(args[n] + " ");
				}
				geriBildirimEkle((ProxiedPlayer) sender,sb.toString());
			} else {
				yardımMesajı((ProxiedPlayer) sender);
			}
		}
    }
    
    @SuppressWarnings("deprecation")
	private void lejyonyarat(ProxiedPlayer sender, String lejyonAdı) {
	    Date simdi = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(simdi);
		
		cal.add(Calendar.DAY_OF_MONTH, 90);
		try {
			BL.ms.statement.executeUpdate("INSERT INTO Lejyonlar (`LejyonAdi`,`SonFaturaTarihi`,`ToplamPuan`,`AylikPuan`,`LejyonSeviyesi`,`LejyonuKuran`) VALUES ('" + lejyonAdı + "','" + cal.getTime().getTime() / 1000 + "','0','0','0','" + sender.getName() + "');");
			BL.ms.statement.executeUpdate("INSERT INTO Oyuncular (`OyuncuAdi`,`Lejyon`,`Rutbe`) VALUES ('" + sender.getName() + "','" + lejyonAdı + "','Tuğgeneral');");
			sender.sendMessage(MY.iyiMesaj(ChatColor.DARK_GREEN + lejyonAdı + ChatColor.GREEN + " adlı lejyonun oluşturuldu."));
			sender.sendMessage(MY.iyiMesaj("Kullanabileceğin lejyon komutları için /lejyon yaz."));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void bilgi(ProxiedPlayer sender, String lejyon) {
    	sender.sendMessage("§c§l" + lejyon.toUpperCase() + " LEJYONU");
    	for (String oyuncu : BL.la.cekLejyonOyunculari(lejyon)){
    		if (BL.la.OyuncuRütbesi.get(oyuncu).contains("Tuğgeneral")){
    	    	sender.sendMessage("§6Lejyon Tuğgenerali: §e " + oyuncu);
    			break;
    		}
    	}
    	sender.sendMessage("§6Lejyon Puanı: §e" + BL.la.ToplamPuan.get(lejyon));
    	sender.sendMessage("§6Lejyon Seviyesi: §e" + BL.la.LejyonSeviyesi.get(lejyon));
    	sender.sendMessage("§6Üye Sayısı: §e" + BL.la.cekLejyonOyunculari(lejyon).size());
    	if (BL.la.MOTD.get(lejyon) != null){
    		sender.sendMessage("§6Lejyon Mesajı: §b" + BL.la.MOTD.get(lejyon).replaceAll("&", "§"));
    	}
	}

	@SuppressWarnings("deprecation")
	private void ayrıl(ProxiedPlayer sender) {
		if (BL.la.lejyonaSahipmi(sender.getName()) == false){
			sender.sendMessage(MY.kötüMesaj("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
		if (BL.la.OyuncuRütbesi.get(sender.getName()).contains("Tuğgeneral")){
			sender.sendMessage(MY.kötüMesaj("Kendi lejyonundan çıkamazsın."));
			return;
		}
		
		try {
			BL.la.lejyondanÇık(sender.getName(), BL.la.cekOyuncuLejyonu(sender.getName()));
			sender.sendMessage(MY.normalMesaj("Bulunduğunuz lejyondan ayrıldınız."));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void üyeleriGöster(ProxiedPlayer sender) {
		if (BL.la.lejyonaSahipmi(sender.getName()) == false){
			sender.sendMessage(MY.kötüMesaj("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
    	int lejyonerSayısı = 0;
    	int onlineLejyonerSayısı = 0;
    	int bk = 0;
    	int önder = 0;
    	String lejyonAdı = BL.la.cekOyuncuLejyonu(sender.getName());
    	sender.sendMessage("§c" + lejyonAdı.toUpperCase() + " LEJYONU ÜYELERİ");
    	sender.sendMessage("§6[=== Tuğgeneral ===]");
    	for (String oyuncu : BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(sender.getName()))){
    		if (BL.la.OyuncuRütbesi.get(oyuncu).contains("Tuğgeneral")){
    			if (BL.ra.EgerOnline(oyuncu) == true){
    	   			sender.sendMessage("§a§l ✔ §2" + oyuncu);
    			} else {
        			sender.sendMessage("§4§l ✖ §c" + oyuncu);
    			}
    		}
    	}
    	sender.sendMessage("§6[=== Önderler ===]");
    	for (String oyuncu : BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(sender.getName()))){
    		if (BL.la.OyuncuRütbesi.get(oyuncu).contains("Önder")){
    			if (BL.ra.EgerOnline(oyuncu) == true){
    	   			sender.sendMessage("§a§l ✔ §2" + oyuncu);
    			} else {
        			sender.sendMessage("§4§l ✖ §c" + oyuncu);
    			}
    			önder++;
    		}
    	}
    	sender.sendMessage("§6[=== Bölük Komutanları ===]");
    	for (String oyuncu : BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(sender.getName()))){
    		if (BL.la.OyuncuRütbesi.get(oyuncu).contains("Bölük Komutanı")){
    			if (BL.ra.EgerOnline(oyuncu) == true){
    	   			sender.sendMessage("§a§l ✔ §2" + oyuncu);
    			} else {
        			sender.sendMessage("§4§l ✖ §c" + oyuncu);
    			}
    			bk++;
    		}
    	}
    	sender.sendMessage("§6[=== Lejyonerler ===]");
    	int i = 0;
    	for (String oyuncu : BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(sender.getName()))){
    		if (BL.la.OyuncuRütbesi.get(oyuncu).contains("Lejyoner")){
    			if (i < 10){
	    			if (BL.ra.EgerOnline(oyuncu) == true){
	    	   			sender.sendMessage("§a§l ✔ §2" + oyuncu);
	    	   			i++;
	    			}
    			} else {
    				break;
    			}
    		}
    	}
    	for (String oyuncu : BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(sender.getName()))){
    		if (BL.la.OyuncuRütbesi.get(oyuncu).contains("Lejyoner")){
    			if (i < 10){
	    			if (!BL.ra.EgerOnline(oyuncu) == true){
	    	   			sender.sendMessage("§4§l ✖ §c" + oyuncu);
	    	   			i++;
	    			}
    			} else {
    				break;
    			}
    		}
    	}
    	int diger = BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(sender.getName())).size()-i-1-bk-önder;
    	if (i > 10){
    		sender.sendMessage("§eVe diğer " + diger + " lejyoner");
    	}
    	sender.sendMessage("");
    	for (String oyuncu : BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(sender.getName()))){
    		lejyonerSayısı++;
    		if (BL.ra.EgerOnline(oyuncu)){
    			onlineLejyonerSayısı++;
    		}
    	}
    	sender.sendMessage("§aÇevrimiçi lejyoner: §f" + onlineLejyonerSayısı);
    	sender.sendMessage("§6Toplam Lejyoner: §f" + lejyonerSayısı);
    	//sender.sendMessage("Lejyon listesini görmek için buraya tıklayın.");
	}

	@SuppressWarnings("deprecation")
	private void geriBildirimEkle(ProxiedPlayer sender, String bildirimMesajı) {
		try {
		    long simdi = new Date().getTime();
			SimpleDateFormat zamancevir = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
			BL.ms.statement.executeUpdate("INSERT INTO GeriBildirim (`Nick`,`Bildirim`,`Tarih`) VALUES ('" + sender + "','" + bildirimMesajı + "','" + zamancevir.format(simdi) + "');");
			sender.sendMessage(MY.iyiMesaj("Geri bildiriminiz tarafımıza ulaşmıştır. En kısa zamanda kontrol edilecektir."));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void top10Göster(ProxiedPlayer sender) {
		int i = 1;
		sender.sendMessage("§c§k|||| §aSonOyuncu Lejyon Top 10 §c§k||||");
		for (Entry<String, Integer> entry : BL.m.entriesSortedByValues(BL.la.ToplamPuan)){
			sender.sendMessage(""+ChatColor.GOLD + ChatColor.BOLD + i + " - " + ChatColor.YELLOW + entry.getKey() + ChatColor.GOLD + ChatColor.BOLD + " || " + ChatColor.YELLOW +  entry.getValue());
			i++;
		}
		sender.sendMessage("§7§o30 dakikada bir yenilenir.");
		sender.sendMessage("§c§k|||| §aSonOyuncu Lejyon Top 10 §c§k||||");
	}

	@SuppressWarnings("deprecation")
	private void motdAta(ProxiedPlayer sender,String yeniMotd){
		if (BL.la.lejyonaSahipmi(sender.getName()) == false){
			sender.sendMessage(MY.kötüMesaj("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
		
		if (!(BL.la.OyuncuRütbesi.get(sender.getName()).contains("Tuğgeneral") ||
			BL.la.OyuncuRütbesi.get(sender.getName()).contains("Önder"))){
			sender.sendMessage(MY.kötüMesaj("Lejyon mesajını belirleyecek rütbede değilsiniz."));
			return;
		}
		String lejyonAdı = BL.la.cekOyuncuLejyonu(sender.getName());
		ArrayList<String> lejyonOyuncuListesi = BL.la.cekLejyonOyunculari(lejyonAdı);
		
		try {
			BL.la.motdAta(lejyonAdı, yeniMotd);
			sender.sendMessage(MY.iyiMesaj("Lejyon mesajı düzenlendi: " + yeniMotd.replaceAll("&", "§")));			
			for (String lejyoner : lejyonOyuncuListesi){
				if (BL.ra.EgerOnline(lejyoner) == true && !lejyoner.contains(sender.getName())){
					BL.ra.mesajGönder(lejyoner, MY.iyiMesaj("Yeni lejyon mesajı: " + yeniMotd.replaceAll("&", "§")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}

	@SuppressWarnings("deprecation")
	private void davet(ProxiedPlayer sender,String davetli){
		if (BL.la.lejyonaSahipmi(sender.getName()) == false){
			sender.sendMessage(MY.kötüMesaj("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
		if (!(BL.la.OyuncuRütbesi.get(sender.getName()).contains("Tuğgeneral") ||
				BL.la.OyuncuRütbesi.get(sender.getName()).contains("Önder") ||
				BL.la.OyuncuRütbesi.get(sender.getName()).contains("Bölük Komutanı"))){
				sender.sendMessage(MY.kötüMesaj("Lejyona birini davet edecek rütbede değilsiniz."));
				return;
		}
		if (BL.la.dahaFazlaAlabilirmi(BL.la.cekOyuncuLejyonu(sender.getName())) == false){
			sender.sendMessage(MY.kötüMesaj("Lejyonunuzun seviyesine göre maksimum kişi sayısına ulaştınız."));
			sender.sendMessage(MY.kötüMesaj("Lejyona daha fazla kişi davet edemezsiniz."));
			return;
		}
		if (BL.ra.EgerOnline(davetli) == false){
			sender.sendMessage(MY.kötüMesaj("Lejyona davet ettiğiniz kişi oyunda değil."));
			return;
		}
		if (BL.la.lejyonaSahipmi(davetli) == true){
			sender.sendMessage(MY.kötüMesaj("Lejyona davet ettiğiniz kişi zaten lejyonunuzda."));
			return;
		}
		if (BL.la.AktifLejyonTeklifleri.containsKey(davetli)){
			sender.sendMessage(MY.kötüMesaj("Lejyona davet ettiğiniz kişiye zaten bir teklif sunulmuş."));
			return;
		}
		
		BL.la.lejyonisteğiGönder(davetli, BL.la.cekOyuncuLejyonu(sender.getName()), sender);
		sender.sendMessage(MY.normalMesaj("§e" + davetli + " §6isimli oyuncuya lejyon daveti gönderildi."));
		
    }
    
    @SuppressWarnings("deprecation")
	private void tasfiye(ProxiedPlayer sender,String atılanOyuncu){
		if (BL.la.lejyonaSahipmi(sender.getName()) == false){
			sender.sendMessage(MY.kötüMesaj("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
		if (!(BL.la.OyuncuRütbesi.get(sender.getName()).contains("Tuğgeneral") ||
			BL.la.OyuncuRütbesi.get(sender.getName()).contains("Önder"))){
			sender.sendMessage(MY.kötüMesaj("Bir lejyon üyesini lejyondan atacak rütbede değilsiniz."));
			return;
		}
		if (!BL.la.cekOyuncuLejyonu(sender.getName()).equalsIgnoreCase(BL.la.cekOyuncuLejyonu(atılanOyuncu))){
			sender.sendMessage(MY.kötüMesaj("Lejyondan atmaya çalıştığınız kişi sizin lejyonunuzda değil."));
			return;
		}
		if (BL.la.lejyonaSahipmi(atılanOyuncu) == false){
			sender.sendMessage(MY.kötüMesaj("Lejyondan atmaya çalıştığınız kişi sizin lejyonunuzda değil."));
			return;
		}
		
		try {
			BL.la.lejyondanSil(atılanOyuncu, BL.la.cekOyuncuLejyonu(sender.getName()));
			sender.sendMessage(MY.normalMesaj("Lejyondan çıkarma işlemi tamamlandı."));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @SuppressWarnings("deprecation")
	private void motdGöster(ProxiedPlayer sender){
		if (BL.la.lejyonaSahipmi(sender.getName()) == false){
			sender.sendMessage(MY.kötüMesaj("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
		String MOTD = BL.la.MOTD.get(BL.la.cekOyuncuLejyonu(sender.getName()));
		if (MOTD == null){
			sender.sendMessage(MY.kötüMesaj("Lejyon yöneticileri tarafından lejyon mesajı tanımlanmamıştır."));
			return;
		}
		
		sender.sendMessage("§6Lejyon mesajı: §6" + MOTD.replaceAll("&", "§"));	
    }
    
    @SuppressWarnings("deprecation")
	private void reddet(ProxiedPlayer sender){
		if (!BL.la.AktifLejyonTeklifleri.containsKey(sender.getName())){
			sender.sendMessage(MY.kötüMesaj("Aktif lejyon teklifiniz bulunmuyor."));
			return;
		}

		BL.rb.sendChannelMessage("BungeeLejyon", "LejyonisteğiniReddet" + BL.split + sender.getName());
		sender.sendMessage(MY.normalMesaj("Lejyon teklifi reddedildi."));
		
    }
    
    @SuppressWarnings("deprecation")
	private void kabulet(ProxiedPlayer sender){
		if (!BL.la.AktifLejyonTeklifleri.containsKey(sender.getName())){
			sender.sendMessage(MY.kötüMesaj("Aktif lejyon teklifiniz bulunmuyor."));
			return;
		}
		
		try {
	        String[] kesilmiş = BL.la.AktifLejyonTeklifleri.get(sender.getName()).split("_");
	        String lejyon = kesilmiş[0];
			BL.la.lejyonaEkle(sender.getName(), lejyon);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @SuppressWarnings("deprecation")
	private void yardımMesajı(ProxiedPlayer sender){
    	if (BL.la.lejyonaSahipmi(sender.getName()) == false){
    		sender.sendMessage("§cLejyon kurmak için lobideki Lejyon Yöneticisi ile görüşün.");
    		sender.sendMessage("§c3 aylık lejyon harcı 20 So Kredidir.");
    		sender.sendMessage("§aKullanabileceğiniz lejyon komutları:");
    		sender.sendMessage("§6/lejyon kabul §f- §eLejyon davetini kabul eder.");
    		sender.sendMessage("§6/lejyon ret §f- §eLejyon davetini reddeder.");
    		sender.sendMessage("§6/lejyon top10 §f- §eEn iyi 10 lejyonu gösterir.");
    		sender.sendMessage("§6/lejyon bilgi <LejyonAdı> §f- §eBelirtilen lejyonun bilgilerini gösterir.");
    	} else {
    		sender.sendMessage("§6/lejyon motd §f- §eLejyon mesajını gösterir.");
    		sender.sendMessage("§6/lejyon top10 §f- §eEn iyi 10 lejyonu gösterir.");
    		sender.sendMessage("§6/lejyon üyeler §f- §eLejyon üyelerini gösterir.");
    		sender.sendMessage("§6/lejyon ayrıl §f- §eLejyondan ayrılırsınız.");
    		sender.sendMessage("§6/lejyon bilgi §f- §eLejyonun bilgilerini gösterir.");
    		sender.sendMessage("§6/lejyon bilgi <LejyonAdı> §f- §eBelirtilen lejyonun bilgilerini gösterir.");
    		if (BL.la.OyuncuRütbesi.get(sender.getName()).contains("Bölük Komutanı")){
        		sender.sendMessage("§6/lejyon davet <isim> §f- §eLejyona birini davet eder.");
    		} else if (BL.la.OyuncuRütbesi.get(sender.getName()).contains("Önder")){
        		sender.sendMessage("§6/lejyon davet <isim> §f- §eLejyona birini davet eder.");
        		sender.sendMessage("§6/lejyon tasfiye <isim> §f- §eLejyondan birini atar.");
        		sender.sendMessage("§6/lejyon motd <YeniLejyonMesajı> §f- §eLejyon mesajını değiştirir.");
    		} else if (BL.la.OyuncuRütbesi.get(sender.getName()).contains("Tuğgeneral")){
        		sender.sendMessage("§6/lejyon davet <isim> §f- §eLejyona birini davet eder.");
        		sender.sendMessage("§6/lejyon tasfiye <isim> §f- §eLejyondan birini atar.");
        		sender.sendMessage("§6/lejyon motd <YeniLejyonMesajı> §f- §eLejyon mesajını değiştirir.");
        		sender.sendMessage("§6/lejyon geribildirim <BildirimMesajı> §f- §eLejyon plugininde bug var ise admine bilgi veriniz.");
    		}
    	}
    }

}