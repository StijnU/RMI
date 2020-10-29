package rentalAgency;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Set;

import rental.CarRentalCompany;
import rental.CarType;
import rental.ICarRentalCompany;

public class ManagerSession implements  Serializable{


	/***************
	 * CONSTRUCTOR *
	 ***************/
	
	private String clientName;
	private RentalAgency rentalAgency;
	
 	public ManagerSession(String clientName) {
 		this.clientName = clientName;
	}


	public void registerCarRentalCompany(String carRentalCompany, RentalAgency rentalAgency) {
		rentalAgency.setRegisteredCarRentalCompany(carRentalCompany);
	}
	
	public void unregisterCarRentalCompany(String carRentalCompany,  RentalAgency rentalAgency) {
		rentalAgency.unsetRegisteredCarRentalCompany(carRentalCompany);
	}
	
	public Set<String> getAllRegisteredCarRentalCompanies(RentalAgency rentalAgency){
		return rentalAgency.getAllRegisteredCarRentalCompanies();
	}
	
	public int getNumberOfReservations(String carTypeName, String carRentalCompany, IRentalAgency rentalAgency) throws RemoteException {
		ICarRentalCompany rentalCompany = (ICarRentalCompany) rentalAgency.getCarRentalCompany(carRentalCompany);
		CarType carType = rentalCompany.getCarType(carTypeName);
		return rentalCompany.getReservationAmount(carType);
		
	}
	
	
	
	
}
