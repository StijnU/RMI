package rentalAgency;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;

import rental.CarType;
import rental.ICarRentalCompany;
import rental.Quote;
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
	
	public Quote createQuote(ReservationConstraints constraints, String client, RentalAgency rentalAgency) throws RemoteException, ReservationException {
		Set<ICarRentalCompany> companySet = rentalAgency.getAllCarRentalCompanies();
		for (ICarRentalCompany company: companySet) {
			try {
				Quote quote = company.createQuote(constraints, client);
				currentQuotes.add(quote);
				return quote;
			}catch (ReservationException e){
				System.out.println("No quote available at "+ company.getName());
			}
		}
		// TODO: wat returnen als geen enkele company een quote kan maken voor gegeven constraints 
		return null;
	}
	
	
	public Set<Quote> getCurrentQuotes(){
		return this.currentQuotes;
	}
	
	
	public void confirmQuotes(RentalAgency rentalAgency) throws RemoteException, ReservationException {
		Set<ICarRentalCompany> companySet = rentalAgency.getAllCarRentalCompanies();
		for (Quote quote: currentQuotes) {
			String carRenter = quote.getRentalCompany();
			for (ICarRentalCompany company: companySet) {
				String companyName = company.getName();
				if (companyName == carRenter) {
					company.confirmQuote(quote);
				}
			}
		}
	}
	
	public Set<CarType> getAvailableCartypes(RentalAgency rentalAgency, Date start, Date end) throws RemoteException{
		Set<ICarRentalCompany> companySet = rentalAgency.getAllCarRentalCompanies();
		Set<CarType> carTypes = new HashSet<>();
		
		for (ICarRentalCompany company: companySet) {
			Set<CarType> companyCarTypes = company.getAvailableCarTypes(start, end);
			for (CarType carType: companyCarTypes) {
				carTypes.add(carType);
			}
		}
		return carTypes;
	}
	
	public CarType getCheapestCarType(RentalAgency rentalAgency, Date start, Date end) throws RemoteException {
		Set<CarType> carTypes = this.getAvailableCartypes(rentalAgency, start, end);
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
