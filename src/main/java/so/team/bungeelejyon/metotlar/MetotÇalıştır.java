package so.team.bungeelejyon.metotlar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;
import so.team.bungeelejyon.BL;

public class Metot�al��t�r {
	
	private SeviyeG�ncelle sg = new SeviyeG�ncelle();
	
	public void seviyeleriG�ncelle() throws SQLException{
		sg.g�ncelle();
	}
	
	public <K,V extends Comparable<? super V>> 
	List<Entry<K, V>> entriesSortedByValues(Map<K,V> map) {

		List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());

		Collections.sort(sortedEntries, 
			new Comparator<Entry<K,V>>() {
			@Override
			public int compare(Entry<K,V> e1, Entry<K,V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		}
				);

		return sortedEntries;
	}
	
	public void mysqlD�ng�s�(){
		ProxyServer.getInstance().getScheduler().schedule(BL.instance,new Runnable(){
			public void run(){
				try {
					if (BL.ms.statement.isClosed()){
						BL.ms.mysqlBaslangic();
					}
					seviyeleriG�ncelle();
					System.out.println("Mysql kontrol� yap�ld�.");
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	      } ,100, 1000 * 60 * 10, TimeUnit.MICROSECONDS);	
	}

}
