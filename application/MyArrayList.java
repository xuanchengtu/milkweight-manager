package application;

/**
 * MyArrayList created by Jiaqi Zhang on MacBook
 * 
 * Author: Jiaqi Zhang(jzhang2247@wisc.edu) Date: @4.23
 * 
 * Course: CS400 Semester: Spring 2020 Lecture: 001
 * 
 * IDE: Eclipse IDE for Java Developers Version: 2019-12(4.14.0) Build id: 20191212-1212
 * 
 * Device: Jonathan's MACBOOK OS: macOS Catalina Version: 10.15 OS Build: 1.1 GHz
 * 
 * List Collaborators: None
 * 
 * Other Credits: None
 * 
 * Known Bugs: None
 */

import java.util.ArrayList;

/**
 * MyArrayList- for the final project Stores milk weight records as MilkWeight objects. This class
 * uses ArrayList as internal data structure.
 * 
 * @author Jiaqi Zhang(2020)
 *
 */
public class MyArrayList implements ArrayListADT {

  ArrayList<MilkWeight> array;// create an empty array

  /**
   * default constructor
   */
  public MyArrayList() {
    array = new ArrayList<MilkWeight>();

  }

  /**
   * This method add the node into the list
   * 
   * @param MilkWeight the new MilkWeight object to be added.
   */
  public void add(MilkWeight node) {

    if (node == null) {
      return;
    }

    array.add(node);

  }

  /**
   * This method returns the MilkWeight object whose information matches the input parameters.
   * 
   * @param year   the specified year of the MilkWeight object
   * @param month  the specified month of the MilkWeight object
   * @param day    the specified day of the MilkWeight object
   * @param farmID the specified farm ID of the MilkWeight object
   * @param weight the specified weight of the MilkWeight object
   * @return the MilkWeight object whose information matches the input parameters. If no object in
   *         this MyArrayList matches, return null.
   */
  public MilkWeight get(int year, int month, int day, String farmID, double weight) {
    for (int i = 0; i < array.size(); i++) {
      MilkWeight node = array.get(i);
      if (node.getYear() == year) {
        if (node.getMonth() == month) {
          if (node.getDate() == day) {
            if (node.getFarmID().equals(farmID)) {
              if (node.getWeight() == weight)
                return node;
            }
          }
        }
      }
    }
    return null;
  }

  /**
   * This method remove the node from the list
   * 
   * @param year   the specified year of the MilkWeight object
   * @param month  the specified month of the MilkWeight object
   * @param day    the specified day of the MilkWeight object
   * @param farmID the specified farm ID of the MilkWeight object
   * @param weight the specified weight of the MilkWeight object
   * @return the MilkWeight object which is removed by executing this method. If the specified 
   * MilkWeight object is not found, return null.
   */
  public MilkWeight remove(int year, int month, int day, String farmID, double weight) {
    for (int i = 0; i < array.size(); i++) {
      MilkWeight node = array.get(i);
      if (node.getFarmID().equals(farmID)) {// if equals, remove
        if (node.getYear() == year && node.getDate() == day && node.getMonth() == month
            && node.getWeight() == weight) {
          array.remove(i);
          return node;
        }
      }
    }
    return null;

  }

  /**
   * @return the internal ArrayList containing all MilkWeight objects.
   */
  public ArrayList<MilkWeight> getAllData() {
    return array;
  }

  /**
   * This method create the yearList
   * 
   * @param year the specified year.
   * @return list the arraylist which only contains MilkWeight objects which represent milk weight
   *         records in the specified year.
   */
  public ArrayList<MilkWeight> getYearList(int year) {
    ArrayList<MilkWeight> yearList = new ArrayList<MilkWeight>();
    // create the list
    for (int i = 0; i < array.size(); i++) {
      if (array.get(i).getYear() == year) {
        yearList.add(array.get(i));
      }
    }
    return yearList;
  }

  /**
   * This method create the monthList
   * 
   * @param year  the specified year
   * @param month the specified month
   * @return list the arraylist which only contains MilkWeight objects which represent milk weight
   *         records in the specified year-month.
   */
  public ArrayList<MilkWeight> getMonthList(int year, int month) {
    ArrayList<MilkWeight> monthList = new ArrayList<MilkWeight>();

    for (int i = 0; i < array.size(); i++) {
      if (array.get(i).getYear() == year) {
        if (array.get(i).getMonth() == month) {
          monthList.add(array.get(i));
        }
      }
    }
    return monthList;
  }

  /**
   * This method create the farmList
   * 
   * @param year   the specified year
   * @param farmID the specified farm id
   * @return list the arraylist which only contains MilkWeight objects which represent milk weight
   *         records of the specified farm in the specified year.
   */
  public ArrayList<MilkWeight> getFarmList(int year, String ID) {
    ArrayList<MilkWeight> farmList = new ArrayList<MilkWeight>();

    for (int i = 0; i < array.size(); i++) {
      if (array.get(i).getYear() == year) {
        if (array.get(i).getFarmID().equals(ID)) {
          farmList.add(array.get(i));
        }
      }
    }
    return farmList;
  }

  /**
   * This method create the DateRangeList
   * 
   * @param year       the specified year
   * @param startMonth the specified month of the start date
   * @param startDay   the specified day of the start date
   * @param endMonth   the specified month of the end date
   * @param endDay     the specified day of the end date.
   * @return list the arraylist which only contains MilkWeight objects which represent milk weight
   *         records in the specified date range. The end date is in the same year as the start
   *         date.
   */
  public ArrayList<MilkWeight> getDateRangeList(int year, int startMonth, int startDay,
      int endMonth, int endDay) {
    ArrayList<MilkWeight> datarangeList = new ArrayList<MilkWeight>();
    for (int i = 0; i < array.size(); i++) {
      MilkWeight node = array.get(i);
      // two basic cases
      if (node.getYear() == year) {
        if (startMonth < endMonth) { // case1 : the start month is less than the end month
          if (node.getMonth() == startMonth) {// first case: the node has the same month as the
                                              // start month
            if (node.getDate() >= startDay) {
              datarangeList.add(node);
            }
          }
          // second case: the month of the node is between the start and end month
          if (node.getMonth() > startMonth && node.getMonth() < endMonth) {
            datarangeList.add(node);
          }

          if (node.getMonth() == endMonth) {// third case: the node has the same month as the end
                                            // month
            if (node.getDate() <= endDay) {
              datarangeList.add(node);
            }
          }
        }
        if (startMonth == endMonth) { // case 2 : The start month and the end month are the same.
          if (node.getMonth() == startMonth) {
            if (node.getDate() >= startDay && node.getDate() <= endDay) {
              datarangeList.add(node);
            }
          }
        }
      }

    }
    return datarangeList;
  }

  /**
   * @return the count of all MilkWeight object stored.
   */
  public int size() {
    return array.size();
  }
}
