package so.team.bungeelejyon;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import com.imaginarycode.minecraft.redisbungee.RedisBungeeAPI;
import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;

import so.team.bungeelejyon.api.LejyonAPI;
import so.team.bungeelejyon.api.Mysql;
import so.team.bungeelejyon.api.RedisAPI;
import so.team.bungeelejyon.api.YmlAPI;
import so.team.bungeelejyon.event.OyunaGirdiğinde;
import so.team.bungeelejyon.event.ServerDeğiştiğinde;
import so.team.bungeelejyon.komutlar.l;
import so.team.bungeelejyon.komutlar.lejyon;
import so.team.bungeelejyon.metotlar.MetotÇalıştır;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

public class BL extends Plugin implements Listener {
	
	public static BL instance;
	public static String prefix = "§8[§aSo-Lejyon §3§oBETA§8]§r ";
	
	public static String split = "######";
	
	//Classlar
		public static MetotÇalıştır m;
		public static YmlAPI ya;
		public static Configuration getConfig;
		public static Mysql ms;
		public static RedisAPI ra;
		public static LejyonAPI la;
		public static RedisBungeeAPI rb;
	//Classlar
	
    @Override
    public void onEnable() {
    	instance = this;
		
		//Classlar
    		getConfig = new YmlAPI().config;
    		ms = new Mysql();
    		ya = new YmlAPI();
			m = new MetotÇalıştır();
			ra = new RedisAPI();
			la = new LejyonAPI();
			rb = RedisBungee.getApi();
		//Classlar
			
		ya.file = new File(BL.instance.getDataFolder(), "config.yml");
		try {
			getConfig = ya.provider.load(ya.file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

			
		ya.ymlOlustur();
		ms.defaultConfigOlustur();
	    try {
			ms.mysqlBaslangic();
			la.bilgileriYukle();
			m.seviyeleriGüncelle();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    //Dinleyiciler
	    	getProxy().getPluginManager().registerListener(this, this);
	    	getProxy().getPluginManager().registerListener(this, new OyunaGirdiğinde());
	    	getProxy().getPluginManager().registerListener(this, new ServerDeğiştiğinde());
	    //Dinleyiciler
	    	
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new l(this));
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new lejyon(this));
	    rb.registerPubSubChannels("BungeeLejyon");
	    getProxy().registerChannel("BungeeLejyon");
	    m.mysqlDöngüsü();
    }
    
    @SuppressWarnings("deprecation")
	@EventHandler
    public void pluginMessageEvent(PubSubMessageEvent event){
      if (event.getChannel().equals("BungeeLejyon")) {
        String[] mesaj = event.getMessage().split(split);
        if (mesaj[0].equalsIgnoreCase("MesajGönder")) {
          if (ProxyServer.getInstance().getPlayer(mesaj[1]) != null) {
            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(mesaj[1]);
            p.sendMessage(mesaj[2]);
          }
        } else if (mesaj[0].equalsIgnoreCase("LejyonaEkle")) {   
        	String lejyonaAlinan = mesaj[1];
        	String lejyon = mesaj[2];
        	
    		ProxyServer.getInstance().getScheduler().cancel(BL.la.LejyonTeklifTasklari.get(lejyonaAlinan));
    		BL.la.AktifLejyonTeklifleri.remove(lejyonaAlinan);
    		BL.la.LejyonTeklifTasklari.remove(lejyonaAlinan);
        	
        	la.OyuncuLejyonu.put(lejyonaAlinan, lejyon);
        	la.OyuncuRütbesi.put(lejyonaAlinan, "Lejyoner");
        } else if (mesaj[0].equalsIgnoreCase("LejyonisteğiGönder")) {
        	final String istekGönderilen = mesaj[1];
        	String lejyon = mesaj[2];
        	final String sender = mesaj[3];
        	
        	la.AktifLejyonTeklifleri.put(istekGönderilen, lejyon + "_" + sender);
    		BL.la.LejyonTeklifTasklari.put(istekGönderilen, ProxyServer.getInstance().getScheduler().schedule(BL.instance,new Runnable(){
    			public void run(){
    	        	if (BL.la.AktifLejyonTeklifleri.containsKey(istekGönderilen)){
    	        		BL.la.AktifLejyonTeklifleri.remove(istekGönderilen);
    	        		if (getProxy().getPlayer(istekGönderilen) != null){
    	        			getProxy().getPlayer(istekGönderilen).sendMessage(MY.normalMesaj("Lejyon isteğini cevaplamadığınız için otomatik olarak reddedilmiştir."));
    	            	}
    	        		if (getProxy().getPlayer(sender) != null){
    	        			getProxy().getPlayer(sender).sendMessage(MY.normalMesaj(istekGönderilen + " Lejyon isteğine cevap vermediği için otomatik olarak reddedildi."));
    	            	}

    	        	}
    	        }
    	      } , 60, TimeUnit.SECONDS));	
        } else if (mesaj[0].equalsIgnoreCase("LejyondanSil")) {
        	String lejyondanAtılan = mesaj[1];
        	
        	if (la.OyuncuLejyonu.containsKey(lejyondanAtılan)){
        		la.OyuncuLejyonu.remove(lejyondanAtılan);
        	}
        	if (la.OyuncuRütbesi.containsKey(lejyondanAtılan)){
        		la.OyuncuRütbesi.remove(lejyondanAtılan);
        	}
        } else if (mesaj[0].equalsIgnoreCase("MotdAta")) {
        	String lejyon = mesaj[1];
        	String yeniMotd = mesaj[2];
        	
        	la.MOTD.put(lejyon, yeniMotd);
        } else if (mesaj[0].equalsIgnoreCase("LejyonisteğiniReddet")) {
        	String reddeden = mesaj[1];
        	
            String[] kesilmiş = BL.la.AktifLejyonTeklifleri.get(reddeden).split("_");
            String davetEden = kesilmiş[1];
    		
    		ProxyServer.getInstance().getScheduler().cancel(BL.la.LejyonTeklifTasklari.get(reddeden));
    		BL.la.AktifLejyonTeklifleri.remove(reddeden);
    		BL.la.LejyonTeklifTasklari.remove(reddeden);
    		if (getProxy().getPlayer(davetEden) != null){
    			getProxy().getPlayer(davetEden).sendMessage(MY.normalMesaj(reddeden + " isimli oyuncu lejyon davetinizi reddetti."));
        	}
        } else if (mesaj[0].equalsIgnoreCase("VerileriYenile")) {
        	try {
				la.bilgileriYukle();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
      }
    }
    
    @SuppressWarnings("deprecation")
	@EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (event.getTag().equals("BungeeLejyon") && event.getSender() instanceof Server) {
            final byte[] data = Arrays.copyOf(event.getData(), event.getData().length);
            ByteArrayDataInput in = ByteStreams.newDataInput(data);

            String subchannel = in.readUTF();
            ByteArrayDataOutput out = ByteStreams.newDataOutput();

            if (subchannel.equalsIgnoreCase("LejyonuCek")){
	            out.writeUTF("LejyonuCek");
	            String oyuncu = in.readUTF();
	            out.writeUTF(oyuncu);
	            if (la.lejyonaSahipmi(oyuncu) == true){
		            out.writeUTF(la.cekOyuncuLejyonu(oyuncu));
	            } else {
	            	out.writeUTF("Yok");
	            }
	            
	            ((Server)event.getSender()).sendData("BungeeLejyon", out.toByteArray());
            } else if (subchannel.equalsIgnoreCase("RütbeCek")){
	            out.writeUTF("RütbeCek");
	            String oyuncu = in.readUTF();
	            out.writeUTF(oyuncu);
	            if (la.lejyonaSahipmi(oyuncu) == true){
		            out.writeUTF(la.OyuncuRütbesi.get(oyuncu));
	            } else {
	            	out.writeUTF("Yok");
	            }
	            
	            ((Server)event.getSender()).sendData("BungeeLejyon", out.toByteArray());
            } else if (subchannel.equalsIgnoreCase("YeniLejyon")){
	            out.writeUTF("YeniLejyon");
	            String oyuncu = in.readUTF();
	            String lejyonAdı = in.readUTF();
	            
	            if (la.lejyonVarmı(lejyonAdı) == false){
	            	la.yeniLejyon(oyuncu, lejyonAdı);
	            } else {
	        		if (getProxy().getPlayer(oyuncu) != null){
	        			getProxy().getPlayer(oyuncu).sendMessage(MY.kötüMesaj(lejyonAdı + " ismi zaten kullanılıyor."));
	            	}
	            }
            } else if (subchannel.equalsIgnoreCase("LejyonSeviyesiniCek")){
	            out.writeUTF("LejyonSeviyesiniCek");
	            String oyuncu = in.readUTF();
	            
	            if (la.lejyonaSahipmi(oyuncu) == true){
		            out.writeUTF(la.cekOyuncuLejyonu(oyuncu));
		            out.writeInt(la.LejyonSeviyesi.get(la.cekOyuncuLejyonu(oyuncu)));
	            }
	            
	            ((Server)event.getSender()).sendData("BungeeLejyon", out.toByteArray());
            }
        }
    }
    
}