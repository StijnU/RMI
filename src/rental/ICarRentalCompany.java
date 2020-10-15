package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Set;

public interface ICarRentalCompany extends Remote {
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException;
	
	
	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException;
	
	/**
	 * 
	 * @param carType
	 * @param start
	 * @param end
	 * @return
	 * @throws RemoteException
	 */
	public boolean isAvailable(String carType, Date start, Date end) throws RemoteException;
	
	/**
	 * 
	 * @param constraints
	 * @param client
	 * @return
	 * @throws RemoteException
	 */
	public Quote createQuote(ReservationConstraints constraints, String client) throws RemoteException, ReservationException;
	
	/**
	 * 
	 * @param quote
	 * @return
	 * @throws ReservationException
	 */
	public Reservation confirmQuote(Quote quote) throws RemoteException, ReservationException; 
	
	/**
	 * 
	 * @param carType
	 * @return
	 * @throws RemoteException
	 */
	public int getReservationAmount(String carType) throws RemoteException; 
}
