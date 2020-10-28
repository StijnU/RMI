package server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.util.HashSet;
import java.util.Set;

import rental.RentalServer;
import rental.ReservationException;
import rentalAgency.RentalAgencyServer;

public class Server {
	
	public static void main(String[] args) throws ReservationException,
	NumberFormatException, IOException, AlreadyBoundException {
		
		//Start CarRentalAgencyServer
		RentalAgencyServer.main(args);
		
		//Start RentalServers
		Set <String> AllCarRentalCompanies = new HashSet<String>();
		AllCarRentalCompanies.add("dockx");
		AllCarRentalCompanies.add("hertz");
		
		for (String name : AllCarRentalCompanies) {
			RentalServer.main(args, name);
			}
	}

}
