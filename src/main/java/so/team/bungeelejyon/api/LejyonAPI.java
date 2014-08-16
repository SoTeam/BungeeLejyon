package so.team.bungeelejyon.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import so.team.bungeelejyon.BL;

public class LejyonAPI {
	
	public HashMap<String,String> AktifLejyonTeklifleri = new HashMap<String,String>();
	
	//Lejyonlar DBsi
		public HashMap<String,Integer> ToplamPuan = new HashMap<String,Integer>();
		public HashMap<String,Integer> AylikPuan = new HashMap<String,Integer>();
		public HashMap<String,Integer> LejyonSeviyesi = new HashMap<String,Integer>();
		public HashMap<String,Integer> LejyonDurumu = new HashMap<String,Integer>();
		public HashMap<String,Integer> LejyonuKuran = new HashMap<String,Integer>();
		public HashMap<String,String> MOTD = new HashMap<String,String>();
	
	//Oyuncular DBsi
		public HashMap<String,String> OyuncuLejyonu = new HashMap<String,String>();
		public HashMap<String,String> OyuncuRütbesi = new HashMap<String,String>();
		public HashMap<String,Integer> OyuncuBildirimi = new HashMap<String,Integer>();
	
	public void bilgileriYukle() throws SQLException{
		ToplamPuan.clear();
		AylikPuan.clear();
		LejyonSeviyesi.clear();
		LejyonDurumu.clear();
		LejyonuKuran.clear();
		MOTD.clear();
		
		OyuncuLejyonu.clear();
		OyuncuRütbesi.clear();
		
		ResultSet lejyonlar = BL.ms.statement.executeQuery("SELECT * FROM Lejyonlar;");
		
		while (lejyonlar.next()){
			ToplamPuan.put(lejyonlar.getString("LejyonAdý"), lejyonlar.getInt("ToplamPuan"));
			AylikPuan.put(lejyonlar.getString("LejyonAdý"), lejyonlar.getInt("AylikPuan"));
			LejyonSeviyesi.put(lejyonlar.getString("LejyonAdý"), lejyonlar.getInt("LejyonSeviyesi"));
			LejyonDurumu.put(lejyonlar.getString("LejyonAdý"), lejyonlar.getInt("LejyonDurumu"));
			LejyonuKuran.put(lejyonlar.getString("LejyonAdý"), lejyonlar.getInt("LejyonuKuran"));
			if (lejyonlar.getString("MOTD") != null){
				MOTD.put(lejyonlar.getString("LejyonAdý"), lejyonlar.getString("MOTD"));
			}
		}
		ResultSet Oyuncular = BL.ms.statement.executeQuery("SELECT * FROM Oyuncular;");
		while (Oyuncular.next()){
			OyuncuLejyonu.put(Oyuncular.getString("OyuncuAdi"), Oyuncular.getString("Lejyon"));
			OyuncuRütbesi.put(Oyuncular.getString("OyuncuAdi"), Oyuncular.getString("Rutbe"));
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
		BL.ra.mesajGönder(oyuncu, Lejyon + " Lejyonuna katýldýn.");
		
		ArrayList<String> lejyonOyuncuListesi = BL.la.cekLejyonOyunculari(Lejyon);
		for (String lejyoner : lejyonOyuncuListesi){
			if (BL.ra.EgerOnline(lejyoner) == true && !lejyoner.contains(oyuncu)){
				BL.ra.mesajGönder(lejyoner, oyuncu + " lejyona katýldý.");
			}
		}
	}
	
	public void lejyonisteðiGönder(final String oyuncu,String Lejyon,final ProxiedPlayer sender){
		BL.ra.mesajGönder(oyuncu, Lejyon + " isimli lejyona davet edildiniz.");
		BL.ra.mesajGönder(oyuncu, "Onaylamak için /lejyon kabul komutunu girin. Reddetmek için /lejyon ret komutunu girin.");
		BL.ra.mesajGönder(oyuncu, "Lejyon davetini 1 dakika içinde onaylamazsanýz, otomatik olarak istek iptal olacak.");
		
		BL.rb.sendChannelMessage("BungeeLejyon", "LejyonisteðiGönder" + BL.split + oyuncu + BL.split + Lejyon);
		
		ProxyServer.getInstance().getScheduler().schedule(BL.instance,new Runnable(){
	        @SuppressWarnings("deprecation")
			public void run(){
	        	if (BL.la.AktifLejyonTeklifleri.containsKey(oyuncu)){
	        		BL.la.AktifLejyonTeklifleri.remove(oyuncu);
	        		BL.ra.mesajGönder(oyuncu, "Lejyon isteðini cevaplamadýðýnýz için, otomatik olarak reddedilmiþtir.");
	        		sender.sendMessage(oyuncu + " Lejyon isteðine cevap vermediði için, otomatik olarak reddedildi.");
	        	}
	        }
	      } , 30, TimeUnit.SECONDS);		
	}
	
	public boolean lejyonaSahipmi(String oyuncu){
		if (OyuncuLejyonu.containsKey(oyuncu)){
			return true;
		} else {
			return false;
		}
	}
	
	public void lejyondanSil(String oyuncu,String Lejyon) throws SQLException{
		BL.ms.statement.executeUpdate("DELETE FROM `Oyuncular` WHERE (`OyuncuAdi`='" + oyuncu + "');");
		BL.rb.sendChannelMessage("BungeeLejyon", "LejyondanSil" + BL.split + oyuncu);
		if (BL.ra.EgerOnline(oyuncu)){
			BL.ra.mesajGönder(oyuncu, Lejyon + " lejyonundan atýldýn.");
		}
		
		ArrayList<String> lejyonOyuncuListesi = BL.la.cekLejyonOyunculari(Lejyon);
		for (String lejyoner : lejyonOyuncuListesi){
			if (BL.ra.EgerOnline(lejyoner) == true && !lejyoner.contains(oyuncu)){
				BL.ra.mesajGönder(lejyoner, oyuncu + " lejyondan atýldý.");
			}
		}
	}

}
