package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.ICarRentalCompany;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;

public class Client extends AbstractTestBooking {

	/********
	 * MAIN *
	 ********/

	private final static int LOCAL = 0;
	private final static int REMOTE = 1;
	
	//constructor args
	private String carRentalCompanyName;
	private int localOrRemote;
	private List<Reservation> reservations;


	/**
	 * The `main` method is used to launch the client application and run the test
	 * script.
	 */
	public static void main(String[] args) throws Exception {
		// The first argument passed to the `main` method (if present)
		// indicates whether the application is run on the remote setup or not.
		int localOrRemote = (args.length == 1 && args[0].equals("REMOTE")) ? REMOTE : LOCAL;

		String carRentalCompanyName = "Hertz";

		// An example reservation scenario on car rental company 'Hertz' would be...
		Client client = new Client("simpleTrips", carRentalCompanyName, localOrRemote);
		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/

	public Client(String scriptFile, String carRentalCompanyName, int localOrRemote) {
		super(scriptFile);
		
		this.carRentalCompanyName = carRentalCompanyName;
		this.localOrRemote = localOrRemote; 
	}

	/**
	 * Check which car types are available in the given period (across all companies
	 * and regions) and print this list of car types.
	 *
	 * @param start start time of the period
	 * @param end   end time of the period
	 * @throws Exception if things go wrong, throw exception
	 */
	@Override
	protected void checkForAvailableCarTypes(Date start, Date end) throws Exception {
		try {
			Registry registry = LocateRegistry.getRegistry();
			ICarRentalCompany rental = (ICarRentalCompany) registry.lookup(carRentalCompanyName);
			System.out.println("Car rental company " + rental.getName() + " is found." );
			
			Set<CarType> carTypes = rental.getAvailableCarTypes(start, end);
			
			System.out.println("Available car types: ");
			
			for (CarType carType : carTypes){
			System.out.println(carType.getName());
			}
			
		}
		catch(NotBoundException e) {
			System.err.println("Could not find car rental company with given name");
		}
		catch (RemoteException e) {
			System.err.println(e.getMessage());	
		}
		
	}

	/**
	 * Retrieve a quote for a given car type (tentative reservation).
	 * 
	 * @param clientName name of the client
	 * @param start      start time for the quote
	 * @param end        end time for the quote
	 * @param carType    type of car to be reserved
	 * @param region     region in which car must be available
	 * @return the newly created quote
	 * 
	 * @throws Exception if things go wrong, throw exception
	 */
	@Override
	protected Quote createQuote(String clientName, Date start, Date end, String carType, String region)
			throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		ICarRentalCompany rental = (ICarRentalCompany) registry.lookup(carRentalCompanyName);
		System.out.println("Car rental company " + rental.getName() + " is found." );
		
		ReservationConstraints constraints = new ReservationConstraints(start, end, carType, region);
		
		Quote quote =  rental.createQuote(constraints, clientName);
		
		return quote;
		
	}

	/**
	 * Confirm the given quote to receive a final reservation of a car.
	 * 
	 * @param quote the quote to be confirmed
	 * @return the final reservation of a car
	 * 
	 * @throws Exception if things go wrong, throw exception
	 */
	@Override
	protected Reservation confirmQuote(Quote quote) throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		ICarRentalCompany rental = (ICarRentalCompany) registry.lookup(carRentalCompanyName);
		
		Reservation reservation = rental.confirmQuote(quote);
		this.reservations.add(reservation);
		return reservation;
		
	}

	/**
	 * Get all reservations made by the given client.
	 *
	 * @param clientName name of the client
	 * @return the list of reservations of the given client
	 * 
	 * @throws Exception if things go wrong, throw exception
	 */
	@Override
	protected List<Reservation> getReservationsByRenter(String clientName) throws Exception {
		return this.reservations;
	}

	/**
	 * Get the number of reservations for a particular car type.
	 * 
	 * @param carType name of the car type
	 * @return number of reservations for the given car type
	 * 
	 * @throws Exception if things go wrong, throw exception
	 */
	@Override
	protected int getNumberOfReservationsForCarType(String carType) throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		ICarRentalCompany rental = (ICarRentalCompany) registry.lookup(carRentalCompanyName);
		
		Integer reservationAmount = rental.getReservationAmount(carType);
		return reservationAmount;
	}
}