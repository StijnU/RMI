package rentalAgency;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rental.CarRentalCompany;
import rental.ICarRentalCompany;

public class RentalAgency implements IRentalAgency{
	
	private String name;
	private Set<String> allRegisteredCarCompanies;


	public RentalAgency(String name) {
		this.name = name;
	}

	
	
	protected void setRegisteredCarRentalCompany(String carRentalCompany) {
		allRegisteredCarCompanies.add(carRentalCompany);
	}
	
	protected void unsetRegisteredCarRentalCompany(String carRentalCompany) {
		allRegisteredCarCompanies.remove(carRentalCompany);
	}
	
	public Set<String> getAllRegisteredCarRentalCompanies(){
		return allRegisteredCarCompanies;
	}
	
	protected ICarRentalCompany getCarRentalCompany(String carRentalCompany) {
		if(getAllRegisteredCarRentalCompanies().contains(carRentalCompany)) {
			ICarRentalCompany companyRemote = null;
			try {
				Registry registry;
				registry = LocateRegistry.getRegistry();
				companyRemote = (ICarRentalCompany) registry.lookup(carRentalCompany);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			return companyRemote;}
		else {
			throw new IllegalArgumentException("No " + carRentalCompany + " found as registered car rental company");
		}
	}
	
	
	
	//Helper
//	public Map<String,ICarRentalCompany> getAllCarRentalCompanies(){	
//		
//		Map <String, ICarRentalCompany> AllCarRentalCompanies = new HashMap<String, ICarRentalCompany>();
//
//		try {	
//			Registry registry = LocateRegistry.getRegistry();
//			String[] allCarRentalCompanyNames = registry.list();
//			
//			for (String crc : allCarRentalCompanyNames) {
//				try {
//						ICarRentalCompany companyRemote = (ICarRentalCompany) registry.lookup(crc);
//						AllCarRentalCompanies.put(companyRemote.getName(), companyRemote);
//						}
//				catch(NotBoundException e) 
//				{System.err.println("Could not find car rental company with given name " + crc);
//				}
//			}
//		}
//		catch(RemoteException e) {
//				System.err.println(e.getMessage());	}
//	
//		return AllCarRentalCompanies;
//		
//	}



	@Override
	public ReservationSession createReservationSession(String clientName) throws RemoteException {
			return new ReservationSession(clientName);
	}
	
	@Override
	public ManagerSession createManagerSession(String clientName) throws RemoteException {
			return new ManagerSession(clientName, this);
	}




	@Override
	public String getName() throws RemoteException {
		return this.name;
	}
}
