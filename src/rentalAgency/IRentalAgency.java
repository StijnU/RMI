package rentalAgency;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

import rental.ICarRentalCompany;

public interface IRentalAgency extends Remote{
	
	public String getName() throws RemoteException;
	
//	public Set<ICarRentalCompany> getAllCarRentalCompanies() throws RemoteException;
	
	public ReservationSession createReservationSession(String clientName) throws RemoteException;


	public ManagerSession createManagerSession(String clientName) throws RemoteException;
	
	public Map<String,ICarRentalCompany> getAllCarRentalCompanies() throws RemoteException;
}