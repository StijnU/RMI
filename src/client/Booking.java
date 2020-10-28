package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;

import rental.Reservation;
import rentalAgency.IRentalAgency;
import rentalAgency.ManagerSession;
import rentalAgency.ReservationSession;

public class Booking extends AbstractTestBooking<ReservationSession, ManagerSession>{

	
	//Voorlopig leeg laten (LOUIS)
	
	private String rentalAgencyName;
	
	public Booking(String scriptFile, String rentalAgencyName) {
		super(scriptFile);
		this.rentalAgencyName = rentalAgencyName;
	}

	protected ReservationSession getNewReservationSession(String name) throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		IRentalAgency agencyRemote = (IRentalAgency) registry.lookup(rentalAgencyName);
		return agencyRemote.createReservationSession(name);
	}

	protected ManagerSession getNewManagerSession(String name) throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		IRentalAgency agencyRemote = (IRentalAgency) registry.lookup(rentalAgencyName);
		
		return agencyRemote.createManagerSession(name);
	}

	@Override
	protected void checkForAvailableCarTypes(ReservationSession session, Date start, Date end) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addQuoteToSession(ReservationSession session, String name, Date start, Date end, String carType,
			String region) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<Reservation> confirmQuotes(ReservationSession session, String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getNumberOfReservationsByRenter(ManagerSession ms, String clientName) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getNumberOfReservationsForCarType(ManagerSession ms, String carRentalName, String carType)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
