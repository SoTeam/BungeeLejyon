package so.team.bungeelejyon.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import so.team.bungeelejyon.BL;

public class LejyonAPI {
	
	//Lejyonlar DBsi
		public HashMap<String,Integer> ToplamPuan = new HashMap<String,Integer>();
		public HashMap<String,Integer> AylikPuan = new HashMap<String,Integer>();
		public HashMap<String,Integer> LejyonSeviyesi = new HashMap<String,Integer>();
		public HashMap<String,Integer> LejyonDurumu = new HashMap<String,Integer>();
	
	//Oyuncular DBsi
		public HashMap<String,String> OyuncuLejyonu = new HashMap<String,String>();
		public HashMap<String,String> OyuncuRütbesi = new HashMap<String,String>();
		public HashMap<String,Integer> OyuncuBildirimi = new HashMap<String,Integer>();
	
	public void bilgileriYukle() throws SQLException{
		ToplamPuan.clear();
		AylikPuan.clear();
		LejyonSeviyesi.clear();
		LejyonDurumu.clear();
		
		OyuncuLejyonu.clear();
		OyuncuRütbesi.clear();
		OyuncuBildirimi.clear();
		
		ResultSet lejyonlar = BL.ms.statement.executeQuery("SELECT * FROM Lejyonlar;");
		
		while (lejyonlar.next()){
			ToplamPuan.put(lejyonlar.getString("LejyonAdý"), lejyonlar.getInt("ToplamPuan"));
			AylikPuan.put(lejyonlar.getString("LejyonAdý"), lejyonlar.getInt("AylikPuan"));
			LejyonSeviyesi.put(lejyonlar.getString("LejyonAdý"), lejyonlar.getInt("LejyonSeviyesi"));
			LejyonDurumu.put(lejyonlar.getString("LejyonAdý"), lejyonlar.getInt("LejyonDurumu"));
		}
		ResultSet Oyuncular = BL.ms.statement.executeQuery("SELECT * FROM Oyuncular;");
		while (Oyuncular.next()){
			OyuncuLejyonu.put(Oyuncular.getString("OyuncuAdi"), Oyuncular.getString("Lejyon"));
			OyuncuRütbesi.put(Oyuncular.getString("OyuncuAdi"), Oyuncular.getString("Rutbe"));
			OyuncuBildirimi.put(Oyuncular.getString("OyuncuAdi"), Oyuncular.getInt("Bildirimler"));
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

}
