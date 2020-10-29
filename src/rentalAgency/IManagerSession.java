package rentalAgency;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IManagerSession extends Remote{
	
	public void registerCarRentalCompany(String carRentalCompany) throws RemoteException;

}
