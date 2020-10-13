package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICarRentalCompany extends Remote {
	
	public Quote createQuote(ReservationConstraints constraints, String client) throws RemoteException, ReservationException;
	
	public Reservation confirmQuote(Quote quote) throws RemoteException, ReservationException;

}
