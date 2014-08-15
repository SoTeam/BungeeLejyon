package so.team.bungeelejyon;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import com.imaginarycode.minecraft.redisbungee.RedisBungeeAPI;
import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;

import so.team.bungeelejyon.api.LejyonAPI;
import so.team.bungeelejyon.api.Mysql;
import so.team.bungeelejyon.api.RedisAPI;
import so.team.bungeelejyon.api.YmlAPI;
import so.team.bungeelejyon.komutlar.l;
import so.team.bungeelejyon.metotlar.MetotÇalıştır;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

public class BL extends Plugin implements Listener {
	
	public static BL instance;
	public static String prefix = "�8[�aSo-Lejyon�8]�r ";
	
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
			  		  
	    getProxy().getPluginManager().registerListener(this, this);
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new l(this));
	    rb.registerPubSubChannels("BungeeLejyon");
	    
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
        } 
      }
    }
    
}