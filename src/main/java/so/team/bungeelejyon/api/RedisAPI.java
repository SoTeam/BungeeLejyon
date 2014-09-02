package so.team.bungeelejyon.api;

import java.util.UUID;

import so.team.bungeelejyon.BL;

public class RedisAPI {
	
	public boolean EgerOnline(String oyuncuismi){
		UUID uuid = BL.rb.getUuidFromName(oyuncuismi);
		if (BL.rb.isPlayerOnline(uuid) == true){
			return true;
		} else {
			return false;
		}
	}
	

	  public void mesajG�nder(String mesajiAlan, String mesaj){
		  BL.rb.sendChannelMessage("BungeeLejyon", "MesajG�nder" + BL.split + mesajiAlan + BL.split + mesaj);
	  }
	  
	  public void lejyonVerileriniYenile(){
		  BL.rb.sendChannelMessage("BungeeLejyon", "VerileriYenile");
	  }

}
