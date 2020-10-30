package rentalAgency;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import rental.CarRentalCompany;
import rental.ICarRentalCompany;
import rental.RentalServer;
import rental.ReservationException;

public class RentalAgencyServer {


		
		private final static int LOCAL = 0;
		private final static int REMOTE = 1;

		private static Logger logger = Logger.getLogger(RentalServer.class.getName());

		
		public static void main(String[] args) throws ReservationException,
				NumberFormatException, IOException, AlreadyBoundException {
			// The first argument passed to the `main` method (if present)
			// indicates whether the application is run on the remote setup or not.
			int localOrRemote = (args.length == 1 && args[0].equals("REMOTE")) ? REMOTE : LOCAL;

			String rentalAgency = "rental-agency";
			RentalAgency RA = new RentalAgency(rentalAgency);
			
			RA.setRegisteredCarRentalCompany("Dockx");
			RA.setRegisteredCarRentalCompany("Hertz");
			
			// set security manager if non existent
			if (System.getSecurityManager() != null)
				System.setSecurityManager(null);

			// locate registry
			Registry registry = null;
			try {
				registry = LocateRegistry.getRegistry();
				
			} catch (RemoteException e) {
				logger.log(Level.SEVERE, "Could not locate RMI registry");
				System.exit(-1);
			}

			// register car rental company
			IRentalAgency stub;
			System.out.println("Agency is starting...");
			try {
				stub = (IRentalAgency) UnicastRemoteObject.exportObject(RA, 0);
				registry.rebind("rental-agency", stub);
			} catch (RemoteException e) {
				logger.log(Level.SEVERE, "could not register stub");
				logger.log(Level.SEVERE, e.getMessage());
			}
			
		}
}
