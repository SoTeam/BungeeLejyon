package so.team.bungeelejyon.metotlar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import so.team.bungeelejyon.BL;

public class SeviyeGüncelle{
	HashMap<String,Integer> lejyonPuanları = new HashMap<String,Integer>();
	HashMap<String,Integer> lejyonSeviyeleri = new HashMap<String,Integer>();
	
	public void güncelle() throws SQLException
	{
		lejyonPuanları.clear();
		lejyonSeviyeleri.clear();
		ResultSet res = BL.ms.statement.executeQuery("SELECT LejyonAdi,ToplamPuan FROM Lejyonlar ORDER BY ToplamPuan DESC;");
		while (res.next()){
			lejyonPuanları.put(res.getString("LejyonAdı"), res.getInt("ToplamPuan"));
		}
		for (Entry<String, Integer> entry : lejyonPuanları.entrySet()){
			lejyonSeviyeleri.put(entry.getKey(), seviyeyiHesapla(entry.getValue()));
		}
		
		seviyeleriAta();
		
	}
	
	private void seviyeleriAta() throws SQLException{
		for (Entry<String, Integer> entry : lejyonPuanları.entrySet()){
			BL.ms.statement.executeUpdate("UPDATE Lejyonlar SET LejyonSeviyesi="+ entry.getValue() +" WHERE LejyonAdı='"+ entry.getKey()+"'");
		}
	}

	private int seviyeyiHesapla(int i)
	{
		i = 1;
			 if (i >=   1000 && i <  10000)  {i=2;}
		else if (i >=  10000 && i <  30000)  {i=3;}
		else if (i >=  30000 && i <  50000)  {i=4;}
		else if (i >=  50000 && i <  80000)  {i=5;}
		else if (i >=  80000 && i < 100000)  {i=6;}
		else if (i >= 100000 && i < 250000)  {i=7;}
		else if (i >= 100000 )  			 {i=8;}
		return i;
		
	}
	
}
