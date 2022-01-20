package application;

/**
 * shenyijie created by shenyijie on Macbook Pro in Milk Weight
 *
 * Author: Yijie Shen (shen226@wisc.edu) Date: @date
 *
 * Course: CS400 Semester: Spring 2020 Lecture: 001
 *
 * IDE: Eclipse IDE for Java Developers Version: 2019-12 (4.14.0) Build id: 20191212-1212
 *
 * Device: YIJIE-MACBOOKPRO OS: MACOS Mojave Version: 10.14.6 OS Build: 18G3020
 *
 * List Collaborators: Name, email@wisc.edu, lecture number
 *
 * Other Credits: describe other sources (websites or people)
 *
 * Known Bugs: describe known unresolved bugs here
 */

/**
 * MilkWeight - Object class of a single milk weight that records the milk production on a specific
 * date of a farm, which serves as a node for data storage.
 * 
 * @author Yijie Shen (2020)
 *
 */
public class MilkWeight implements Comparable<MilkWeight>{

  private String farmID; // farm id of the record represented by this MilkWeight project
  private int year; // year of the record represented by this MilkWeight project
  private int month; // month of the record represented by this MilkWeight project
  private int date; // date of the record represented by this MilkWeight project
  private double weight; // weight of the record represented by this MilkWeight project

  /**
   * Default no-argument constructor.
   * 
   */
  public MilkWeight() {
  }

  /**
   * Constructor which sets the year, month, day, farm id, and weight of this MilkWeight object.
   * 
   * @param year   the year of this MilkWeight object.
   * @param month  the month of this MilkWeight object.
   * @param date   the day of this MilkWeight object.
   * @param farmID the farm id of this MilkWeight object.
   * @param weight the weight of this MilkWeight object.
   */
  public MilkWeight(int year, int month, int date, String farmID, double weight) {
    this.farmID = farmID;
    this.year = year;
    this.month = month;
    this.date = date;
    this.weight = weight;
  }

  /**
   * Getter method which gets the farm id of this MilkWeight object.
   * 
   * @return the farmID
   */
  public String getFarmID() {
    return farmID;
  }

  /**
   * Setter method which sets a new farm id for this MilkWeight object.
   * 
   * @param farmID the farmID to set
   */
  public void setFarmID(String farmID) {
    this.farmID = farmID;
  }

  /**
   * Getter method which gets the year of this MilkWeight object.
   * 
   * @return the year
   */
  public int getYear() {
    return year;
  }

  /**
   * Setter method which sets a new year for this MilkWeight object.
   * 
   * @param year the year to set
   */
  public void setYear(int year) {
    this.year = year;
  }

  /**
   * Getter method which gets the month of this MilkWeight object.
   * 
   * @return the month
   */
  public int getMonth() {
    return month;
  }

  /**
   * Setter method which sets a new month for this MilkWeight object.
   * 
   * @param month the month to set
   */
  public void setMonth(int month) {
    this.month = month;
  }

  /**
   * Getter method which gets the day of this MilkWeight object.
   * 
   * @return the date
   */
  public int getDate() {
    return date;
  }

  /**
   * Setter method which sets a new day for this MilkWeight object.
   * 
   * @param date the date to set
   */
  public void setDate(int date) {
    this.date = date;
  }

  /**
   * Getter method which gets the weight of this MilkWeight object.
   * 
   * @return the weight
   */
  public double getWeight() {
    return weight;
  }

  /**
   * Setter method which sets a new weight for this MilkWeight object.
   * 
   * @param weight the weight to set
   */
  public void setWeight(double weight) {
    this.weight = weight;
  }
  
  @Override
  public int compareTo(MilkWeight other) {
      if(this.year - other.year != 0) {
          return this.year - other.year;
      } else {
          if(this.month - other.month != 0) {
              return this.month - other.month;
          } else {
              if(this.date - other.date != 0) {
                  return this.date - other.date;
              }
              return 0;
          }
      }
  }
}
