package so.team.bungeelejyon.komutlar;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry;

import so.team.bungeelejyon.BL;
import so.team.bungeelejyon.MY;
import net.md_5.bungee.api.ChatColor;
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
			yardımMesajı((ProxiedPlayer) sender);
		} else if (args.length == 1){
			if (args[0].toLowerCase().equalsIgnoreCase("davet")){
				sender.sendMessage("Kullanımı: /lejyon davet <isim> - Oyuncuya lejyon daveti gönderir.");
			} else if (args[0].toLowerCase().equalsIgnoreCase("tasfiye")){
				sender.sendMessage("Kullanımı: /lejyon tasfiye <isim> - Lejyon üyesini lejyondan atar.");
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
					sender.sendMessage(MY.hataMesajı("Bu işlem için lejyonda olmalısınız."));
				}
			} else if (args[0].toLowerCase().equalsIgnoreCase("yenile")){
				if (sender.hasPermission("bungeeplus.aktar")){
					try {
						BL.la.bilgileriYukle();
						BL.m.seviyeleriGüncelle();
						sender.sendMessage("§aGüncelleme işlemi başarılı.");
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
			} else if (args[0].toLowerCase().equalsIgnoreCase("bilgi")){
				if (BL.la.LejyonSeviyesi.get(args[1]) != null){
					bilgi((ProxiedPlayer) sender, args[1]);
				} else {
					sender.sendMessage(MY.hataMesajı("Böyle bir lejyon bulunamadı."));
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
			sender.sendMessage(ChatColor.GOLD + lejyonAdı + ChatColor.YELLOW + " isimli lejyonun oluşturuldu.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void bilgi(ProxiedPlayer sender, String lejyon) {
    	sender.sendMessage("Lejyon Adı: " + lejyon);
    	for (String oyuncu : BL.la.cekLejyonOyunculari(lejyon)){
    		if (BL.la.OyuncuRütbesi.get(oyuncu).contains("Tuğgeneral")){
    	    	sender.sendMessage("Lejyon Tuğgenerali: " + oyuncu);
    			break;
    		}
    	}
    	sender.sendMessage("Lejyon Puanı: " + BL.la.ToplamPuan.get(lejyon));
    	sender.sendMessage("Lejyon Seviyesi: " + BL.la.LejyonSeviyesi.get(lejyon));
    	sender.sendMessage("Üye Sayısı: " + BL.la.cekLejyonOyunculari(lejyon).size());
    	if (BL.la.MOTD.get(lejyon) != null){
    		sender.sendMessage("Lejyon Mesajı: " + BL.la.MOTD.get(lejyon).replaceAll("&", "§"));
    	}
	}

	@SuppressWarnings("deprecation")
	private void ayrıl(ProxiedPlayer sender) {
		if (BL.la.lejyonaSahipmi(sender.getName()) == false){
			sender.sendMessage(MY.hataMesajı("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
		if (BL.la.OyuncuRütbesi.get(sender.getName()).contains("Tuğgeneral")){
			sender.sendMessage(MY.hataMesajı("Kendi lejyonundan çıkamazsın."));
			return;
		}
		
		try {
			BL.la.lejyondanÇık(sender.getName(), BL.la.cekOyuncuLejyonu(sender.getName()));
			sender.sendMessage(MY.normalMesaj("Başarıyla lejyondan çıktınız."));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void üyeleriGöster(ProxiedPlayer sender) {
		if (BL.la.lejyonaSahipmi(sender.getName()) == false){
			sender.sendMessage(MY.hataMesajı("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
    	int lejyonerSayısı = 0;
    	int onlineLejyonerSayısı = 0;
    	int bk = 0;
    	int önder = 0;
    	sender.sendMessage("-- Tuğgeneral --");
    	for (String oyuncu : BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(sender.getName()))){
    		if (BL.la.OyuncuRütbesi.get(oyuncu).contains("Tuğgeneral")){
    			if (BL.ra.EgerOnline(oyuncu) == true){
    	   			sender.sendMessage("§a§l ✔ §2" + oyuncu);
    			} else {
        			sender.sendMessage("§4§l ✖ §c" + oyuncu);
    			}
    		}
    	}
    	sender.sendMessage("-- Önderler --");
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
    	sender.sendMessage("-- Bölük Komutanları --");
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
    	sender.sendMessage("-- Lejyonerler --");
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
    	if (i >= 10){
    		sender.sendMessage("Ve diğer: " + diger + " lejyoner");
    	}
    	sender.sendMessage("");
    	for (String oyuncu : BL.la.cekLejyonOyunculari(BL.la.cekOyuncuLejyonu(sender.getName()))){
    		lejyonerSayısı++;
    		if (BL.ra.EgerOnline(oyuncu)){
    			onlineLejyonerSayısı++;
    		}
    	}
    	sender.sendMessage("Çevrimiçi lejyoner: " + onlineLejyonerSayısı);
    	sender.sendMessage("Toplam Lejyoner: " + lejyonerSayısı);
    	//sender.sendMessage("Lejyon listesini görmek için buraya tıklayın.");
	}

	@SuppressWarnings("deprecation")
	private void geriBildirimEkle(ProxiedPlayer sender, String bildirimMesajı) {
		try {
		    long simdi = new Date().getTime();
			SimpleDateFormat zamancevir = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
			BL.ms.statement.executeUpdate("INSERT INTO GeriBildirim (`Nick`,`Bildirim`,`Tarih`) VALUES ('" + sender + "','" + bildirimMesajı + "','" + zamancevir.format(simdi) + "');");
			sender.sendMessage(MY.normalMesaj("Geri bildiriminiz tarafımıza ulaşmıştır, yardımlarınız için teşekkür ederiz."));
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
			sender.sendMessage(MY.hataMesajı("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
		
		if (!(BL.la.OyuncuRütbesi.get(sender.getName()).contains("Tuğgeneral") ||
			BL.la.OyuncuRütbesi.get(sender.getName()).contains("Önder"))){
			sender.sendMessage(MY.hataMesajı("MOTD mesajını değişecek yetkiye sahip değilsiniz."));
			return;
		}
		
		try {
			BL.la.motdAta(BL.la.cekOyuncuLejyonu(sender.getName()), yeniMotd);
			sender.sendMessage(MY.normalMesaj("Yeni MOTD Atandı: " + yeniMotd.replaceAll("&", "§")));
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}

	@SuppressWarnings("deprecation")
	private void davet(ProxiedPlayer sender,String davetli){
		if (BL.la.lejyonaSahipmi(sender.getName()) == false){
			sender.sendMessage(MY.hataMesajı("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
		if (!(BL.la.OyuncuRütbesi.get(sender.getName()).contains("Tuğgeneral") ||
				BL.la.OyuncuRütbesi.get(sender.getName()).contains("Önder") ||
				BL.la.OyuncuRütbesi.get(sender.getName()).contains("Bölük Komutanı"))){
				sender.sendMessage(MY.hataMesajı("Bir lejyon üyesini lejyondan davet edecek yetkiye sahip değilsiniz."));
				return;
		}
		if (BL.la.dahaFazlaAlabilirmi(BL.la.cekOyuncuLejyonu(sender.getName())) == false){
			sender.sendMessage(MY.hataMesajı("Lejyonunuzun seviyesine göre maksimum kişi sayısına ulaştınız."));
			sender.sendMessage(MY.hataMesajı("Lejyona daha fazla kişi davet edemezsiniz."));
			return;
		}
		if (BL.ra.EgerOnline(davetli) == false){
			sender.sendMessage(MY.hataMesajı("Eklemeye çalıştığınız oyuncu online değil."));
			return;
		}
		if (BL.la.lejyonaSahipmi(davetli) == true){
			sender.sendMessage(MY.hataMesajı("Eklemeye çalıştığınız kişi zaten lejyonda."));
			return;
		}
		if (BL.la.AktifLejyonTeklifleri.containsKey(davetli)){
			sender.sendMessage(MY.hataMesajı("Eklemeye çalıştığınız kişiye zaten bir teklif sunulmuş."));
			return;
		}
		
		BL.la.lejyonisteğiGönder(davetli, BL.la.cekOyuncuLejyonu(sender.getName()), sender);
		sender.sendMessage(MY.normalMesaj("Belirttiğiniz oyuncuya lejyon isteği gönderildi."));
		
    }
    
    @SuppressWarnings("deprecation")
	private void tasfiye(ProxiedPlayer sender,String atılanOyuncu){
		if (BL.la.lejyonaSahipmi(sender.getName()) == false){
			sender.sendMessage(MY.hataMesajı("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
		if (!(BL.la.OyuncuRütbesi.get(sender.getName()).contains("Tuğgeneral") ||
			BL.la.OyuncuRütbesi.get(sender.getName()).contains("Önder"))){
			sender.sendMessage(MY.hataMesajı("Bir lejyon üyesini lejyondan atacak yetkiye sahip değilsiniz."));
			return;
		}
		if (!BL.la.cekOyuncuLejyonu(sender.getName()).equalsIgnoreCase(BL.la.cekOyuncuLejyonu(atılanOyuncu))){
			sender.sendMessage(MY.hataMesajı("Belirttiğiniz kişi sizin lejyonunuzda değil."));
			return;
		}
		if (BL.la.lejyonaSahipmi(atılanOyuncu) == false){
			sender.sendMessage(MY.hataMesajı("Atmaya çalıştığınız kişi zaten lejyonda değil."));
			return;
		}
		
		try {
			BL.la.lejyondanSil(atılanOyuncu, BL.la.cekOyuncuLejyonu(sender.getName()));
			sender.sendMessage(MY.normalMesaj("Başarıyla kişiyi lejyondan çıkardınız."));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @SuppressWarnings("deprecation")
	private void motdGöster(ProxiedPlayer sender){
		if (BL.la.lejyonaSahipmi(sender.getName()) == false){
			sender.sendMessage(MY.hataMesajı("Bu işlem için bir lejyonunuz olmalı."));
			return;
		}
		String MOTD = BL.la.MOTD.get(BL.la.cekOyuncuLejyonu(sender.getName()));
		if (MOTD == null){
			sender.sendMessage(MY.hataMesajı("Lejyon yöneticileri tarafından lejyon mesajı tanımlanmamıştır."));
			return;
		}
		
		sender.sendMessage("Lejyon mesajı: " + MOTD.replaceAll("&", "§"));	
    }
    
    @SuppressWarnings("deprecation")
	private void reddet(ProxiedPlayer sender){
		if (!BL.la.AktifLejyonTeklifleri.containsKey(sender.getName())){
			sender.sendMessage("Aktif lejyon teklifiniz bulunmuyor.");
			return;
		}

		BL.rb.sendChannelMessage("BungeeLejyon", "LejyonisteğiniReddet" + BL.split + sender.getName());
		sender.sendMessage(MY.normalMesaj("Lejyon teklifi reddedildi."));
		
    }
    
    @SuppressWarnings("deprecation")
	private void kabulet(ProxiedPlayer sender){
		if (!BL.la.AktifLejyonTeklifleri.containsKey(sender.getName())){
			sender.sendMessage("Aktif lejyon teklifiniz bulunmuyor.");
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
    		sender.sendMessage("Lejyon kurmak için lobideki Lejyon Yöneticisi ile görüşün.");
    		sender.sendMessage("3 aylık lejyon harcı 20 So Kredidir.");
    		sender.sendMessage("Kullanabileceğiniz lejyon komutları:");
    		sender.sendMessage("/lejyon kabul - Lejyon davetini kabul eder.");
    		sender.sendMessage("/lejyon ret - Lejyon davetini reddeder.");
    		sender.sendMessage("/lejyon top10 - En iyi 10 lejyonu gösterir.");
    		sender.sendMessage("/lejyon bilgi <LejyonAdı> - Belirtilen lejyonun bilgilerini gösterir.");
    	} else {
    		sender.sendMessage("/lejyon motd - Lejyon mesajını gösterir.");
    		sender.sendMessage("/lejyon top10 - En iyi 10 lejyonu gösterir.");
    		sender.sendMessage("/lejyon üyeler - Lejyon üyelerini gösterir.");
    		sender.sendMessage("/lejyon ayrıl - Lejyondan ayrılırsınız.");
    		sender.sendMessage("/lejyon bilgi - Lejyonun bilgilerini gösterir.");
    		sender.sendMessage("/lejyon bilgi <LejyonAdı> - Belirtilen lejyonun bilgilerini gösterir.");
    		if (BL.la.OyuncuRütbesi.get(sender.getName()).contains("Bölük Komutanı")){
        		sender.sendMessage("/lejyon davet <isim> - Lejyona birini davet eder.");
    		} else if (BL.la.OyuncuRütbesi.get(sender.getName()).contains("Önder")){
        		sender.sendMessage("/lejyon davet <isim> - Lejyona birini davet eder.");
        		sender.sendMessage("/lejyon tasfiye <isim> - Lejyondan birini atar.");
        		sender.sendMessage("/lejyon motd <YeniLejyonMesajı> - Lejyon mesajını değiştirir.");
    		} else if (BL.la.OyuncuRütbesi.get(sender.getName()).contains("Tuğgeneral")){
        		sender.sendMessage("/lejyon davet <isim> - Lejyona birini davet eder.");
        		sender.sendMessage("/lejyon tasfiye <isim> - Lejyondan birini atar.");
        		sender.sendMessage("/lejyon motd <YeniLejyonMesajı> - Lejyon mesajını değiştirir.");
        		sender.sendMessage("/lejyon geribildirim <BildirimMesajı> - Lejyon plugininde bug var ise admine bilgi veriniz.");
    		}
    	}
    }

}