package so.team.bungeelejyon.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import so.team.bungeelejyon.BL;

public class LejyonAPI {
	
	public HashMap<String,String> AktifLejyonTeklifleri = new HashMap<String,String>();

	public HashMap<String,ScheduledTask> LejyonTeklifTasklari = new HashMap<String,ScheduledTask>();
	
	//Lejyonlar DBsi
		public HashMap<String,Integer> ToplamPuan = new HashMap<String,Integer>();
		public HashMap<String,Integer> AylikPuan = new HashMap<String,Integer>();
		public HashMap<String,Integer> LejyonSeviyesi = new HashMap<String,Integer>();
		public HashMap<String,String> LejyonuKuran = new HashMap<String,String>();
		public HashMap<String,String> MOTD = new HashMap<String,String>();
		public HashMap<String,Long> FaturaTarihi = new HashMap<String,Long>();
	
	//Oyuncular DBsi
		public HashMap<String,String> OyuncuLejyonu = new HashMap<String,String>();
		public HashMap<String,String> OyuncuR�tbesi = new HashMap<String,String>();
	
	public void bilgileriYukle() throws SQLException{
		ToplamPuan.clear();
		AylikPuan.clear();
		LejyonSeviyesi.clear();
		LejyonuKuran.clear();
		MOTD.clear();
		
		OyuncuLejyonu.clear();
		OyuncuR�tbesi.clear();
		
		ResultSet lejyonlar = BL.ms.statement.executeQuery("SELECT * FROM Lejyonlar;");
		
		while (lejyonlar.next()){
			ToplamPuan.put(lejyonlar.getString("LejyonAd�"), lejyonlar.getInt("ToplamPuan"));
			AylikPuan.put(lejyonlar.getString("LejyonAd�"), lejyonlar.getInt("AylikPuan"));
			LejyonSeviyesi.put(lejyonlar.getString("LejyonAd�"), lejyonlar.getInt("LejyonSeviyesi"));
			LejyonuKuran.put(lejyonlar.getString("LejyonAd�"), lejyonlar.getString("LejyonuKuran"));
			FaturaTarihi.put(lejyonlar.getString("LejyonAd�"), lejyonlar.getLong("SonFaturaTarihi") * 1000);
			if (lejyonlar.getString("MOTD") != null){
				MOTD.put(lejyonlar.getString("LejyonAd�"), lejyonlar.getString("MOTD"));
			}
		}
		ResultSet Oyuncular = BL.ms.statement.executeQuery("SELECT * FROM Oyuncular;");
		while (Oyuncular.next()){
			OyuncuLejyonu.put(Oyuncular.getString("OyuncuAdi"), Oyuncular.getString("Lejyon"));
			OyuncuR�tbesi.put(Oyuncular.getString("OyuncuAdi"), Oyuncular.getString("Rutbe"));
		}
	}
	
	public String cekOyuncuLejyonu(String oyuncu){
		String oyuncuLejyonu = null;
		for (Entry<String, String> entry : OyuncuLejyonu.entrySet()){
			if (entry.getKey().equals(oyuncu)){
				oyuncuLejyonu = entry.getValue();
				break;
			}
		}
		return oyuncuLejyonu;
	}
	
	public ArrayList<String> cekLejyonOyunculari(String lejyon){
		ArrayList<String> oyuncuListesi = new ArrayList<String>();
		
		for (Entry<String, String> entry : OyuncuLejyonu.entrySet()){
			if (entry.getValue().equals(lejyon)){
				oyuncuListesi.add(entry.getKey());
			}
		}
		return oyuncuListesi;
	}
	
	public void lejyonaEkle(String oyuncu,String Lejyon) throws SQLException{
		BL.ms.statement.executeUpdate("INSERT INTO Oyuncular (`OyuncuAdi`,`Lejyon`,`Rutbe`) VALUES ('" + oyuncu + "','" + Lejyon + "','Lejyoner');");
		BL.rb.sendChannelMessage("BungeeLejyon", "LejyonaEkle" + BL.split + oyuncu + BL.split + Lejyon);
		BL.ra.mesajG�nder(oyuncu, Lejyon + " Lejyonuna kat�ld�n.");
		
		ArrayList<String> lejyonOyuncuListesi = BL.la.cekLejyonOyunculari(Lejyon);
		for (String lejyoner : lejyonOyuncuListesi){
			if (BL.ra.EgerOnline(lejyoner) == true && !lejyoner.contains(oyuncu)){
				BL.ra.mesajG�nder(lejyoner, oyuncu + " lejyona kat�ld�.");
			}
		}
	}
	
	public void lejyoniste�iG�nder(final String oyuncu,String Lejyon,final ProxiedPlayer sender){
		BL.ra.mesajG�nder(oyuncu, Lejyon + " isimli lejyona davet edildiniz.");
		BL.ra.mesajG�nder(oyuncu, "Onaylamak i�in /lejyon kabul komutunu girin. Reddetmek i�in /lejyon ret komutunu girin.");
		BL.ra.mesajG�nder(oyuncu, "Lejyon davetini 1 dakika i�inde onaylamazsan�z, otomatik olarak istek iptal olacak.");
		
		BL.rb.sendChannelMessage("BungeeLejyon", "Lejyoniste�iG�nder" + BL.split + oyuncu + BL.split + Lejyon + BL.split + sender.getName());
	}
	
	public boolean lejyonaSahipmi(String oyuncu){
		if (OyuncuLejyonu.containsKey(oyuncu)){
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean faturaTarihiYakla�M��m�(String lejyon){
	    long simdi = new Date().getTime();
		Date faturaTarihi = new Date();
		faturaTarihi.setTime(FaturaTarihi.get(lejyon));

		Calendar cal = Calendar.getInstance();
		cal.setTime(faturaTarihi);
		
		cal.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH - 10);
		
		if (simdi < faturaTarihi.getTime()){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean faturaTarihiGe�mi�mi(String lejyon){
	    long simdi = new Date().getTime();
		long faturaTarihi = FaturaTarihi.get(lejyon);
		
		if (simdi < faturaTarihi){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean dahaFazlaAlabilirmi(String lejyon){
		if (LejyonSeviyesi.get(lejyon) == 1 && cekLejyonOyunculari(lejyon).size() >= 16){
			return false;
		} else if (LejyonSeviyesi.get(lejyon) == 2 && cekLejyonOyunculari(lejyon).size() >= 20){
			return false;
		} else if (LejyonSeviyesi.get(lejyon) == 3 && cekLejyonOyunculari(lejyon).size() >= 28){
			return false;
		} else if (LejyonSeviyesi.get(lejyon) == 4 && cekLejyonOyunculari(lejyon).size() >= 40){
			return false;
		} else if (LejyonSeviyesi.get(lejyon) == 5 && cekLejyonOyunculari(lejyon).size() >= 60){
			return false;
		} else if (LejyonSeviyesi.get(lejyon) == 6 && cekLejyonOyunculari(lejyon).size() >= 90){
			return false;
		} else if (LejyonSeviyesi.get(lejyon) == 7 && cekLejyonOyunculari(lejyon).size() >= 150){
			return false;
		} else if (LejyonSeviyesi.get(lejyon) == 8 && cekLejyonOyunculari(lejyon).size() >= 250){
			return false;
		} else {
			return true;
		}
	}
	
	public void lejyondanSil(String oyuncu,String Lejyon) throws SQLException{
		BL.ms.statement.executeUpdate("DELETE FROM `Oyuncular` WHERE (`OyuncuAdi`='" + oyuncu + "');");
		BL.rb.sendChannelMessage("BungeeLejyon", "LejyondanSil" + BL.split + oyuncu);
		if (BL.ra.EgerOnline(oyuncu)){
			BL.ra.mesajG�nder(oyuncu, Lejyon + " lejyonundan at�ld�n.");
		}
		
		ArrayList<String> lejyonOyuncuListesi = BL.la.cekLejyonOyunculari(Lejyon);
		for (String lejyoner : lejyonOyuncuListesi){
			if (BL.ra.EgerOnline(lejyoner) == true && !lejyoner.contains(oyuncu)){
				BL.ra.mesajG�nder(lejyoner, oyuncu + " lejyondan at�ld�.");
			}
		}
	}
	
	public void lejyondan��k(String oyuncu,String Lejyon) throws SQLException{
		BL.ms.statement.executeUpdate("DELETE FROM `Oyuncular` WHERE (`OyuncuAdi`='" + oyuncu + "');");
		BL.rb.sendChannelMessage("BungeeLejyon", "LejyondanSil" + BL.split + oyuncu);
		
		ArrayList<String> lejyonOyuncuListesi = BL.la.cekLejyonOyunculari(Lejyon);
		for (String lejyoner : lejyonOyuncuListesi){
			if (BL.ra.EgerOnline(lejyoner) == true && !lejyoner.contains(oyuncu)){
				BL.ra.mesajG�nder(lejyoner, oyuncu + " lejyondan ��kt�.");
			}
		}
	}
	
	public void motdAta(String lejyon, String yeniMotd) throws SQLException{
		BL.ms.statement.executeUpdate("UPDATE Lejyonlar SET MOTD='" + yeniMotd + "' WHERE LejyonAdi='" + lejyon +"'" );
		BL.rb.sendChannelMessage("BungeeLejyon", "MotdAta" + BL.split + lejyon + BL.split + yeniMotd);
	}

}
