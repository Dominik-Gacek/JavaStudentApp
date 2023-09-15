package application.control;

public enum Month {// creating enums for each month with correct days for leap and normal years
	JANUARY("January", 31),
	FEBRUARY("February", 28, 29),
	MARCH("March", 31),
	APRIL("April", 30),
	MAY("May", 31),
	JUNE("June", 30),
	JULY("July", 31),
	AUGUST("August", 31),
	SEPTEMBER("September", 30),
	OCTOBER("October", 31),
	NOVEMBER("November", 30),
	DECEMBER("December", 31);

	private final int days;
	private final int leap;
	private final String name;
	
	
	Month(String name, int days, int leap){// constructor for february
		this.name = name;
		this.days = days;
		this.leap = leap;
	}
	Month(String name, int days){//constructor for regular months
		this(name, days, days);
	}
	
	public static boolean isLeap(int year) {// check if given year is a leap year
		if(year%100!=0){
			if(year%4==0)return true;
			}
		if(year%400==0)return true;
		return false;	
	}
	
	public int getDays(int year) {// returns how many days in given month in given year
		if(isLeap(year))return this.leap;
		return this.days;
	}
	
	public static Month monthNum(int month) {// returns month from corresponding integer
		switch(month) {
		case 1:return JANUARY;
		case 2:return FEBRUARY;
		case 3:return MARCH;
		case 4:return APRIL;
		case 5:return MAY;
		case 6:return JUNE;
		case 7:return JULY;
		case 8:return AUGUST;
		case 9:return SEPTEMBER;
		case 10:return OCTOBER;
		case 11:return NOVEMBER;
		case 12:return DECEMBER;
		default:return null;
		}
	}
	
	public static int getDays(int year, int month) {//getDays but takes int for month value
		return monthNum(month).getDays(year);
	}
	
	public static int monthNum(Month month) {// does opposite on monthNum(int)
		switch(month) {
		case JANUARY:return 1;
		case FEBRUARY:return 2;
		case MARCH:return 3;
		case APRIL:return 4;
		case MAY:return 5;
		case JUNE:return 6;
		case JULY:return 7;
		case AUGUST:return 8;
		case SEPTEMBER:return 9;
		case OCTOBER:return 10;
		case NOVEMBER:return 11;
		default:return 12;
		}
	}
	
	@Override
	public String toString() {
        return name;
   }
}
	
	
