package rentalAgency;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import rental.CarRentalCompany;
import rental.ICarRentalCompany;
import rental.RentalServer;

public class RentalAgency implements IRentalAgency, Serializable{
	
	private String name;
	private Set<String> allRegisteredCarCompanies;

	private static Logger logger = Logger.getLogger(RentalServer.class.getName());


	public RentalAgency(String name) {
		this.name = name;
		this.allRegisteredCarCompanies = new HashSet<String>();
	}

	
	
	protected synchronized void setRegisteredCarRentalCompany(String carRentalCompany) {
		allRegisteredCarCompanies.add(carRentalCompany);
	}
	
	protected synchronized void unsetRegisteredCarRentalCompany(String carRentalCompany) {
		allRegisteredCarCompanies.remove(carRentalCompany);
	}
	
	public Set<String> getAllRegisteredCarRentalCompanies(){
		return allRegisteredCarCompanies;
	}
	
	public ICarRentalCompany getCarRentalCompany(String carRentalCompany) {
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
	public Map<String,ICarRentalCompany> getAllCarRentalCompanies(){	
		
		Map <String, ICarRentalCompany> AllCarRentalCompanies = new HashMap<String, ICarRentalCompany>();

		try {	
			Registry registry = LocateRegistry.getRegistry();
			String[] allCarRentalCompanyNames = registry.list();
			for (String crc : allCarRentalCompanyNames) {
				if(allRegisteredCarCompanies.contains(crc)) {
					try {
							ICarRentalCompany companyRemote = (ICarRentalCompany) registry.lookup(crc);
	
							AllCarRentalCompanies.put(companyRemote.getName(), companyRemote);
							}
					catch(NotBoundException e) 
					{System.err.println("Could not find car rental company with given name " + crc);
					}
				}
			}
		}
		catch(RemoteException e) {
				System.err.println(e.getMessage());	}
	
		return AllCarRentalCompanies;
		
	}



	@Override
	public ReservationSession createReservationSession(String clientName) throws RemoteException {
			return new ReservationSession(clientName);
	}
	
	
	public ManagerSession createManagerSession(String clientName) throws RemoteException {
			return new ManagerSession(clientName);
	}
	
//	public void createManagerSession(String clientName) {
//		// locate registry
//		ManagerSession managersession = new ManagerSession(clientName, this);
//		
//					Registry registry = null;
//					registry = LocateRegistry.getRegistry();
//					
//					// register car rental company
//					IManagerSession stub;
//					System.out.println("Manager session is starting...");
//					try {
//						stub = (IManagerSession) UnicastRemoteObject.exportObject(managersession, null);
////						registry.rebind(clientName, stub);
//					} catch (RemoteException e) {
//						logger.log(Level.SEVERE, "could not register stub");
//						logger.log(Level.SEVERE, e.getMessage());
//					}
//	}




	@Override
	public String getName() throws RemoteException {
		return this.name;
	}
}
