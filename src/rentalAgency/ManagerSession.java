package rentalAgency;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	
	public Set<String> getBestClients(IRentalAgency rentalAgency) throws RemoteException {
		Map<String, ICarRentalCompany> rentalCompanies = rentalAgency.getAllCarRentalCompanies();
		Integer bestReservationAmount = 0;
		Set<String> bestClients = new HashSet<>();
		for(Map.Entry<String, ICarRentalCompany> company: rentalCompanies.entrySet()) {
			Map<String, Integer> clientReservationAmount = company.getValue().getClientReservationAmount();
			
			for(Map.Entry<String, Integer> client: clientReservationAmount.entrySet()) {
				if(client.getValue() > bestReservationAmount) {
					bestClients.clear();
					bestClients.add(client.getKey());
					bestReservationAmount = client.getValue();
				}if(client.getValue() == bestReservationAmount) {
					bestClients.add(client.getKey());
				}
			}
			
		}
		return bestClients;
	}
	
	//TODO: helper moet nog weg
	public List <Map<String, Integer>> getClientReservationAmount(IRentalAgency rentalAgency) throws RemoteException{
		Map<String, ICarRentalCompany> rentalCompanies = rentalAgency.getAllCarRentalCompanies();
		List <Map<String, Integer>> clientReservationAmountList = new ArrayList(); 
		for(Map.Entry<String, ICarRentalCompany> company: rentalCompanies.entrySet()) {
			Map<String, Integer> clientReservationAmount = company.getValue().getClientReservationAmount();
			clientReservationAmountList.add(clientReservationAmount);
		}
		return clientReservationAmountList;
	}
	
	
}
