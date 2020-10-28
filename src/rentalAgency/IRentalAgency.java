package rentalAgency;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import rental.ICarRentalCompany;

public interface IRentalAgency extends Remote{

	
	
	public Set<ICarRentalCompany> getAllCarRentalCompanies() throws RemoteException;
}