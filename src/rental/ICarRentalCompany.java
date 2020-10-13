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
	
	
	

}
