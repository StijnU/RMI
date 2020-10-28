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
	
	
	
	

	public Set<ICarRentalCompany> getAllCarRentalCompanies(){	
		
		
		Set <ICarRentalCompany> AllCarRentalCompanies = new HashSet<>();

		try {	
			Registry registry = LocateRegistry.getRegistry();
			String[] allCarRentalCompanyNames = registry.list();
			
			for (String crc : allCarRentalCompanyNames) {
				try {
						ICarRentalCompany companyRemote = (ICarRentalCompany) registry.lookup(crc);
						AllCarRentalCompanies.add(companyRemote);
						}
				catch(NotBoundException e) 
				{System.err.println("Could not find car rental company with given name " + crc);
				}
			}
		}
		catch(RemoteException e) {
				System.err.println(e.getMessage());	}
	
		return AllCarRentalCompanies;
		
	}
}
