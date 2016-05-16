public class Event 
	{
	private String eventname = "Etkinlik bulunamadý.";
	private int price = -1;
	private String locationname = "Etkinlik bulunamadý.";
	private String date = "Etkinlik bulunamadý.";
	
	public Event(){
		
	}
	
	public Event(String name, int ticketprice, String location, String date1){
		eventname = name;
		price = ticketprice;
		locationname = location;
		date = date1;
	}

	public String getEventname() {
		return eventname;
	}

	public int getPrice() {
		return price;
	}

	public String getLocationname() {
		return locationname;
	}
	
	public String getDate() {
		return date;
	}
}
