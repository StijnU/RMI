package rental;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import rentalAgency.RentalAgencyServer;



public class RentalServer {
	
	private final static int LOCAL = 0;
	private final static int REMOTE = 1;
	private String rentalAgency = "rental-agency";

	private static Logger logger = Logger.getLogger(RentalServer.class.getName());

	
	public static void main(String[] args, String name) throws ReservationException,
			NumberFormatException, IOException, AlreadyBoundException {
		// The first argument passed to the `main` method (if present)
		// indicates whether the application is run on the remote setup or not.
		int localOrRemote = (args.length == 1 && args[0].equals("REMOTE")) ? REMOTE : LOCAL;

		
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
	
		ICarRentalCompany stub;
	    String fileName = name + ".csv";
	    
	    CrcData data  = loadData(fileName);
	    CarRentalCompany crc = new CarRentalCompany(data.name, data.regions, data.cars);
		    
	    try {
	    	stub = (ICarRentalCompany) UnicastRemoteObject.exportObject(crc, 0);
	    	registry.rebind(crc.getName(), stub);
	    } catch (RemoteException e) {
			logger.log(Level.SEVERE, "could not register stub");
			logger.log(Level.SEVERE, e.getMessage());
	    }	
	}

	public static CrcData loadData(String datafile)
			throws ReservationException, NumberFormatException, IOException {

		CrcData out = new CrcData();
		int nextuid = 0;

		// open file
		InputStream stream = MethodHandles.lookup().lookupClass().getClassLoader().getResourceAsStream(datafile);
		if (stream == null) {
			System.err.println("Could not find data file " + datafile);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		StringTokenizer csvReader;
		
		try {
			// while next line exists
			while (in.ready()) {
				String line = in.readLine();
				
				if (line.startsWith("#")) {
					// comment -> skip					
				} else if (line.startsWith("-")) {
					csvReader = new StringTokenizer(line.substring(1), ",");
					out.name = csvReader.nextToken();
					out.regions = Arrays.asList(csvReader.nextToken().split(":"));
				} else {
					// tokenize on ,
					csvReader = new StringTokenizer(line, ",");
					// create new car type from first 5 fields
					CarType type = new CarType(csvReader.nextToken(),
							Integer.parseInt(csvReader.nextToken()),
							Float.parseFloat(csvReader.nextToken()),
							Double.parseDouble(csvReader.nextToken()),
							Boolean.parseBoolean(csvReader.nextToken()));
					System.out.println(type);
					// create N new cars with given type, where N is the 5th field
					for (int i = Integer.parseInt(csvReader.nextToken()); i > 0; i--) {
						out.cars.add(new Car(nextuid++, type));
					}
				}
			}
		} finally {
			in.close();
		}

		return out;
	}
	
	static class CrcData {
		public List<Car> cars = new LinkedList<Car>();
		public String name;
		public List<String> regions =  new LinkedList<String>();
	}

}
