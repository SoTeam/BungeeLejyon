package so.team.bungeelejyon.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql {
		
	public Connection baglanti = null;
	public Statement statement = null;
		
	
	
	public void mysqlBaslangic(){		
		try {
			String yol = "xxxx";
	    	String port = "3306";
	    	String kullaniciadi = "xxxx";
	    	String sifre = "xxxx";
	    	String tablo = "xxxx";
	    	
			baglanti = DriverManager.getConnection("jdbc:mysql://" + yol + ":" + port + "/" + tablo, kullaniciadi , sifre);
			statement = baglanti.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS `Lejyonlar` (`LejyonAd�` varchar(64) NOT NULL,`A��lmaTarihi` varchar(64) NOT NULL,`ToplamPuan` int(64),`Ayl�kPuan` int(64),`LejyonSeviyesi` int(64),`LejyonDurumu` varchar(64) NOT NULL,`LejyonuKuran` varchar(64) NOT NULL,`MOTD` varchar(255) NOT NULL) ENGINE=MyISAM DEFAULT CHARSET=UTF8;");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS `Oyuncular` (`OyuncuAd�` varchar(64) NOT NULL,`Lejyon` varchar(64) NOT NULL,`R�tbe` varchar(64),`Bildirimler` int(64)) ENGINE=MyISAM DEFAULT CHARSET=UTF8;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
