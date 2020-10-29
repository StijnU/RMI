package rentalAgency;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Set;

import rental.CarType;
import rental.ICarRentalCompany;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

public class ReservationSession implements Serializable{

 	/***************
	 * CONSTRUCTOR *
	 ***************/
	
	private String clientName;
	private Set<Quote> currentQuotes;
	
	public ReservationSession(String clientName) {
		this.clientName = clientName;
	}
	
	public void createQuote(ReservationConstraints constraints, String client, IRentalAgency rentalAgency) throws RemoteException, ReservationException {
		Map<String, ICarRentalCompany> companySet = rentalAgency.getAllCarRentalCompanies();

		for (Entry<String, ICarRentalCompany> crc: companySet.entrySet()) {
			ICarRentalCompany company = crc.getValue();
			try {
				Quote quote = company.createQuote(constraints, client);
				currentQuotes.add(quote);
			}catch (ReservationException e){
				System.out.println("No quote available at "+ company.getName());
			}
		}
	}
	
	public Set<Quote> getCurrentQuotes(){
		return this.currentQuotes;
	}
	
	
	public List<Reservation> confirmQuotes(IRentalAgency rentalAgency) throws RemoteException, ReservationException {
		Map<String, ICarRentalCompany> companySet = rentalAgency.getAllCarRentalCompanies();
		List<Reservation> reservations = new ArrayList<Reservation>();
		for (Quote quote: currentQuotes) {
			String carRenter = quote.getRentalCompany();
			for (Entry<String, ICarRentalCompany> crc: companySet.entrySet()) {
				ICarRentalCompany company = crc.getValue();
				String companyName = company.getName();
				if (companyName == carRenter) {
					Reservation reservation = company.confirmQuote(quote);
					reservations.add(reservation); 
			
				}
			}
		}
		return reservations;
	}
	
	public Set<CarType> getAvailableCartypes(IRentalAgency rentalAgency, Date start, Date end, String region) throws RemoteException{
		Map<String, ICarRentalCompany> companySet = rentalAgency.getAllCarRentalCompanies();
		Set<CarType> carTypes = new HashSet<>();
		
		for (Entry<String, ICarRentalCompany> crc: companySet.entrySet()) {
			ICarRentalCompany company = crc.getValue();
			List<String> companyRegions = company.getRegions();
			
			if(region != null) {
				if(companyRegions.contains(region)) {
					Set<CarType> companyCarTypes = company.getAvailableCarTypes(start, end);
					for (CarType carType: companyCarTypes) {
						carTypes.add(carType);
					}
				}
			}else {
				Set<CarType> companyCarTypes = company.getAvailableCarTypes(start, end);
				for (CarType carType: companyCarTypes) {
					carTypes.add(carType);
				}
			}
		}
		return carTypes;
	}
	
	public CarType getCheapestCarType(IRentalAgency rentalAgency, Date start, Date end, String region) throws RemoteException {
		Set<CarType> carTypes = this.getAvailableCartypes(rentalAgency, start, end, region);
		Iterator<CarType> iterator = carTypes.iterator();
		CarType cheapestCarType = iterator.next();
		double cheapestPrice = cheapestCarType.getRentalPricePerDay();
		
		for (CarType carType: carTypes) {
			double price = carType.getRentalPricePerDay();
			if(price < cheapestPrice) {
				cheapestPrice = price;
				cheapestCarType = carType;
			}
		}
		return cheapestCarType;
	}
}
