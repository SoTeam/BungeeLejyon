package so.team.bungeelejyon.api;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import so.team.bungeelejyon.BL;

public class YmlAPI {
	
	  public ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
	  public File file;
	  public Configuration config;
	
	public void ymlOlustur(){
	    
	    if (!BL.instance.getDataFolder().exists()) {
	    	BL.instance.getDataFolder().mkdir();
	    }

	    if (!file.exists())
	    {
	      try
	      {
	       file.createNewFile();
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	      }
	    }
	    provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
	  }
	
	  public void kaydet(){
	    try
	    {
	      provider.save(BL.getConfig, file);
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  }

}
