package rentalAgency;

import java.io.Serializable;

public class ReservationSession implements Serializable{

 	/***************
	 * CONSTRUCTOR *
	 ***************/
	
	private String clientName;
	
	public ReservationSession(String clientName) {
		this.clientName = clientName;
	}
	
	
}
