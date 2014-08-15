package so.team.bungeelejyon.metotlar;

import java.sql.SQLException;

public class MetotÇalýþtýr {
	
	private SeviyeGüncelle sg = new SeviyeGüncelle();
	
	public void seviyeleriGüncelle() throws SQLException{
		sg.güncelle();
	}

}
