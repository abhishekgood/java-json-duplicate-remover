

import java.util.Comparator;
import java.lang.Integer;

public class Entry implements Comparable<Entry >{
	private String _id;
	private String email;
	private String first_name;
	private String last_name;
	private String address;
	private String entry_date;
	private int serial;
	private static int serial_number = 0;

	public Entry(String Id, String Email, String Firstname, String Lastname, String Address, String Entrydate, int Serial) {
		_id = Id;
		email = Email;
		first_name = Firstname;
		last_name = Lastname;
		address = Address;
		entry_date = Entrydate;
		serial = Serial;
	}

	public String get_id() {
		return _id;
	}

	public String getEmail() {
		return email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getAddress() {
		return address;
	}

	public String getEntrydate() {
		return entry_date;
	}

	public int getSerial() {
		return serial;
	}
	
	public static int getSerialNumber() {
		serial_number = (serial_number+1) % (Integer.MAX_VALUE) ;
		return serial_number;
	}

	@Override
	public int compareTo(Entry entry) {
		//System.out.println(entry.get_id());
		if(_id.equals(entry.get_id())){	
			//System.out.println(" id already present");
	        return 0;
	     }
		else if(email.equalsIgnoreCase(entry.getEmail())) {
			//System.out.println(entry.getEmail() + " " + " email already present ");
			return 0;
		}
		else {
			return _id.compareTo(entry.get_id());
		}
		
	}
	
	public static Comparator<Entry> EntryComparator 
                                           = new Comparator<Entry>() {
	    @Override
	    public int compare(Entry o1, Entry o2) {
			if(o1.getEntrydate().equals(o2.getEntrydate())){
		        return o1.getSerial() - o2.getSerial();
		     }
			else {
				return o1.getEntrydate().compareTo(o2.getEntrydate());
			}
		}
	};

}
