package so.team.bungeelejyon;

import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import com.imaginarycode.minecraft.redisbungee.RedisBungeeAPI;
import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;

import so.team.bungeelejyon.api.Mysql;
import so.team.bungeelejyon.api.RedisAPI;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class BL extends Plugin implements Listener {
	
	public static BL instance;
	public static String prefix = "§8[§aSo-Lejyon§8]§r ";
	
	public static String split = "######";
	
	//Classlar
		public static Metotlar m;
		public static Mysql ms;
		public static RedisAPI ra;
		public static RedisBungeeAPI rb;
	//Classlar
	
    @Override
    public void onEnable() {
    	instance = this;
		
		//Classlar
			m = new Metotlar();
			ms = new Mysql();
			ra = new RedisAPI();
			rb = RedisBungee.getApi();
		//Classlar
			
		
	    //ms.mysqlBaslangic();
			  		  
	    getProxy().getPluginManager().registerListener(this, this);
	    rb.registerPubSubChannels("BungeeLejyon");
    }
    
    @SuppressWarnings("deprecation")
	@EventHandler
    public void pluginMessageEvent(PubSubMessageEvent event){
      if (event.getChannel().equals("BungeeLejyon")) {
        String[] mesaj = event.getMessage().split(split);
        if (mesaj[0].equalsIgnoreCase("MesajGonder")) {
          if (ProxyServer.getInstance().getPlayer(mesaj[1]) != null) {
            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(mesaj[1]);
            p.sendMessage(mesaj[2]);
          }
        } 
      }
    }
    
}