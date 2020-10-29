package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import rental.CarType;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rentalAgency.IRentalAgency;
import rentalAgency.ManagerSession;
import rentalAgency.ReservationSession;

public class Client extends AbstractTestManagement<ReservationSession, ManagerSession> {

	/********
	 * MAIN *
	 ********/

	private final static int LOCAL = 0;
	private final static int REMOTE = 1;
	
	//constructor args
	private String rentalAgencyName;
	private int localOrRemote;


	/**
	 * The `main` method is used to launch the client application and run the test
	 * script.
	 */
	public static void main(String[] args) throws Exception {
		// The first argument passed to the `main` method (if present)
		// indicates whether the application is run on the remote setup or not.
		int localOrRemote = (args.length == 1 && args[0].equals("REMOTE")) ? REMOTE : LOCAL;


		Client client = new Client("trips", localOrRemote);
		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/

	public Client(String scriptFile, int localOrRemote) {
		super(scriptFile);
		this.localOrRemote = localOrRemote; 
		this.rentalAgencyName = "rental-agency";
	}


	
	@Override
	protected Set<String> getBestClients(ManagerSession ms) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getCheapestCarType(ReservationSession session, Date start, Date end, String region)
			throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		IRentalAgency rentalAgency = (IRentalAgency) registry.lookup(rentalAgencyName);
		
		CarType cheapestCarType = session.getCheapestCarType(rentalAgency, start, end, region);
		String carName = cheapestCarType.getName();
		return carName;
	}

	@Override
	protected CarType getMostPopularCarTypeInCRC(ManagerSession ms, String carRentalCompanyName, int year)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ReservationSession getNewReservationSession(String name) throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		IRentalAgency rentalAgency = (IRentalAgency) registry.lookup(rentalAgencyName);
		return rentalAgency.createReservationSession(name);
	}

	@Override
	protected ManagerSession getNewManagerSession(String name) throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		IRentalAgency rentalAgency = (IRentalAgency) registry.lookup(rentalAgencyName);
		return rentalAgency.createManagerSession(name);
	}

	@Override
	protected void checkForAvailableCarTypes(ReservationSession session, Date start, Date end) throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		IRentalAgency rentalAgency = (IRentalAgency) registry.lookup(rentalAgencyName);
		System.out.println("Available cartypes: \n");
		Set<CarType> carTypes = session.getAvailableCartypes(rentalAgency, start, end, null);
		for(CarType carType: carTypes) {
			System.out.println(carType+"\n");
		}
	}

	@Override
	protected void addQuoteToSession(ReservationSession session, String name, Date start, Date end, String carType,
			String region) throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		IRentalAgency rentalAgency = (IRentalAgency) registry.lookup(rentalAgencyName);
		ReservationConstraints constraints = new ReservationConstraints(start, end, carType, region);
		session.createQuote(constraints, name, rentalAgency);	
	}

	@Override
	protected List<Reservation> confirmQuotes(ReservationSession session, String name) throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		IRentalAgency rentalAgency = (IRentalAgency) registry.lookup(rentalAgencyName);
		List<Reservation> reservations = session.confirmQuotes(rentalAgency);
		return reservations;
	}

	@Override
	protected int getNumberOfReservationsByRenter(ManagerSession ms, String clientName) throws Exception {
		return 0;
	}

	@Override
	protected int getNumberOfReservationsForCarType(ManagerSession ms, String carRentalName, String carType)
			throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		IRentalAgency rentalAgency = (IRentalAgency) registry.lookup(rentalAgencyName);
		return ms.getNumberOfReservations(carType, carRentalName, rentalAgency);
	}

//	@Override
//	protected void processLine(String name, String cmd, List<Character> flags, StringTokenizer scriptLineTokens)
//			throws ApplicationException {
//		// TODO Auto-generated method stub
//		
//	}

	
	
}