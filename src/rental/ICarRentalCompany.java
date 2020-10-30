package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
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
	 * @throws RemoteException
	 */
	public Reservation confirmQuote(Quote quote) throws RemoteException, ReservationException; 
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public List<Car> getAllCars() throws RemoteException;
	
	/**
	 * 
	 * @param carType
	 * @return
	 * @throws RemoteException
	 */
	public Integer getReservationAmount(CarType carType) throws RemoteException;
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public List<String> getRegions() throws RemoteException;
	
	public CarType getCarType(String carTypeName) throws RemoteException;
}
