package so.team.bungeelejyon.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import so.team.bungeelejyon.BL;

public class Mysql {
		
	public Connection bağlantı = null;
	public Statement statement = null;

	public void defaultConfigOlustur(){
        if (BL.getConfig.get("Mysql.Adres") == null){
        	BL.getConfig.set("Mysql.Adres", "localhost");
        	BL.getConfig.set("Mysql.Port", "3306");
        	BL.getConfig.set("Mysql.KullaniciAdi", "id");
        	BL.getConfig.set("Mysql.Sifre", "sifre");
        	BL.getConfig.set("Mysql.Database", "database");
        	BL.ya.kaydet();
        }
	}
	public void mysqlBaslangic() throws SQLException{		
		String yol = BL.getConfig.getString("Mysql.Adres");
	    String port = BL.getConfig.getString("Mysql.Port");
	    String kullaniciadi = BL.getConfig.getString("Mysql.KullaniciAdi");
	    String sifre = BL.getConfig.getString("Mysql.Sifre");
	    String tablo = BL.getConfig.getString("Mysql.Database");
	    	
		bağlantı = DriverManager.getConnection("jdbc:mysql://" + yol + ":" + port + "/" + tablo, kullaniciadi , sifre);
		statement = bağlantı.createStatement();
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS `Lejyonlar` (`LejyonAdi` varchar(64) NOT NULL,`SonFaturaTarihi` varchar(64) NOT NULL,`ToplamPuan` int(64),`AylikPuan` int(64),`LejyonSeviyesi` int(64),`LejyonDurumu` varchar(64) NOT NULL,`LejyonuKuran` varchar(64) NOT NULL,`MOTD` varchar(255));");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS `Oyuncular` (`OyuncuAdi` varchar(64) NOT NULL,`Lejyon` varchar(64) NOT NULL,`Rutbe` varchar(64));");
	}

}
