package rentalAgency;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Set;

import rental.CarRentalCompany;
import rental.CarType;
import rental.ICarRentalCompany;

public class ManagerSession implements IManagerSession, Serializable{


	/***************
	 * CONSTRUCTOR *
	 ***************/
	
	private String clientName;
	private RentalAgency rentalAgency;
	
 	public ManagerSession(String clientName, RentalAgency rentalAgency) {
 		this.clientName = clientName;
 		this.rentalAgency = rentalAgency;
	}


	public void registerCarRentalCompany(String carRentalCompany) {
		rentalAgency.setRegisteredCarRentalCompany(carRentalCompany);
	}
	
	public void unregisterCarRentalCompany(String carRentalCompany) {
		rentalAgency.unsetRegisteredCarRentalCompany(carRentalCompany);
	}
	
	public Set<String> getAllRegisteredCarRentalCompanies(){
		return rentalAgency.getAllRegisteredCarRentalCompanies();
	}
	
//	public int getNumberOfReservations(CarType carType, String carRentalCompany) throws RemoteException {
//		ICarRentalCompany rentalCompany = (ICarRentalCompany) rentalAgency.getCarRentalCompany(carRentalCompany);
//		return rentalCompany.getReservationAmount(carType);
//		
//	}
	
	
	
	
}
