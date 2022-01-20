package application;

import java.util.ArrayList;

/**
 * The interface for MyArrayList
 * @author Zilin
 *
 */
public interface ArrayListADT {
	
	/**
	 * Add the element into array list
	 * @param node the node that is going to be added. 
	 */
	public void add(MilkWeight node);
	
	/**
	 * Remove the intended milk weight from array list
     * @param year   the specified year of the MilkWeight object
     * @param month  the specified month of the MilkWeight object
     * @param day    the specified day of the MilkWeight object
     * @param farmID the specified farm ID of the MilkWeight object
     * @param weight the specified weight of the MilkWeight object
     * @return the MilkWeight object which is removed by executing this method. If the specified 
     * MilkWeight object is not found, return null.
	 */
	public MilkWeight remove(int year, int month, int day, String farmID, double weight);
	
	/**
	 * Get the specific milk weight from my array list
     * @param year   the specified year of the MilkWeight object
     * @param month  the specified month of the MilkWeight object
     * @param day    the specified day of the MilkWeight object
     * @param farmID the specified farm ID of the MilkWeight object
     * @param weight the specified weight of the MilkWeight object
     * @return the MilkWeight object whose information matches the input parameters. If no object in
     *         this MyArrayList matches, return null.
	 */
	public MilkWeight get(int year, int month, int day, String farmID, double weight);
	
	/**
	 * get the year list from the array list
	 * @param year the year that is needed
	 * @return the list which only contains the intended year
	 */
	public ArrayList<MilkWeight> getYearList(int year);
	
	/**
	 * get the month list from the array list
	 * @param year the year that is needed
	 * @param month the month that is needed
	 * @return the list which only contains the intended month
	 */
	public ArrayList<MilkWeight> getMonthList(int year, int month);
	
	/**
	 * get the ID list from the array list
	 * @param year the year that is needed
	 * @param ID the ID that is needed
	 * @return the list which only contains the intended farm ID
	 */
	public ArrayList<MilkWeight> getFarmList(int year, String ID);
	
	/**
	 * get the intended range of time from the array list
	 * @param year the intended year
	 * @param startMonth the start of the month for the time range
	 * @param startDay the start of the day for the time range
	 * @param endMonth the end of the month for the time range
	 * @param endDay the end of the day for the time range 
	 * @return the list only containing time that is in the range
	 */
	public ArrayList<MilkWeight> getDateRangeList(int year, int startMonth, int startDay,
			int endMonth, int endDay);
	
	/**
	 * get the size of arraylist
	 * @return
	 */
	public int size();
}
