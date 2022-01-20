package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This class is used to process .csv input data files, generates reports, modify the data loaded,
 * and create some outputs to files.
 * 
 * @author Haozhe Yang, Xuancheng Tu
 */
public class MilkWeightManager {

  private MyArrayList data; // contains data of all milk weight records.

  /**
   * Defalult no-argument constructor.
   * 
   */
  public MilkWeightManager() {
    data = new MyArrayList();
  }

  /**
   * Read .csv files, store data in the file into the data field. Handle error cases and generate an
   * error message.
   * 
   * @param fileName The name of the file which is going to be read, which is entered by the user.
   * @return a String which contains the error message while executing this method.
   * @throws FileNotFoundException if the file is not found.
   */
  public String readCSVFile(String fileName) throws FileNotFoundException {
    File csv = new File(fileName);
    Scanner scnr = new Scanner(csv);
    boolean isTitleRead = false;
    String errmsg0 = "";
    String errmsg1 = "";
    String errmsg2 = "";
    String errmsg3 = "";
    String errmsg4 = "";
    String errmsg5 = "";
    String errmsg6 = "";
    while (scnr.hasNextLine()) { // process each line of the csv file.
      String line = scnr.nextLine();
      if (!isTitleRead) { // skip the header.
        isTitleRead = true;
        continue;
      }
      String[] info = line.split(","); // separate cells divided by ,
      // Those if and try-catch blocks below handles different kinds of error cases as described
      // by the corresponding error messages.
      if (info.length < 3) {
        errmsg0 = "Some lines in this .csv file have less than 3 cells\n";
        continue;
      }
      if (info.length > 3) {
        errmsg1 = "Some lines in this .csv file have more than 3 cells\n";
        continue;
      }
      String[] date = info[0].split("-"); // extract year, month and day information from a yyyy-m-d
                                          // format date string.
      if (date.length != 3) {
        errmsg2 =
            "Some records contain incorrect date format. Correct format should be yyyy-m-d. Year, month and day should be integers\n";
        continue;
      }
      int year;
      int month;
      int day;
      try {
        year = Integer.parseInt(date[0]);
        month = Integer.parseInt(date[1]);
        day = Integer.parseInt(date[2]);
      } catch (Exception e) {
        errmsg2 =
            "Some records contain incorrect date format. Correct format should be yyyy-m-d, and year, month and day should be integers\n";
        continue;
      }
      if (year < 0) {
        errmsg3 = "Some records contain invalid year information. Year should not be less than 0\n";
        continue;
      }
      if (month < 1 || month > 12) {
        errmsg4 =
            "Some records contain invalid month information. Month should not be less than 1 or greater than 12\n";
        continue;
      }
      if (day < 1 || day > 31) {
        errmsg5 =
            "Some records contain invalid day information. Day should not be less than 1 or greater than 31\n";
        continue;
      }
      String farmID = info[1];
      double weight;
      try {
        weight = Double.parseDouble(info[2]);
      } catch (Exception e) {
        errmsg6 =
            "Some records contain invalid weight information. Weight should be a non-negative number.";
        continue;
      }
      if (weight < 0) {
        errmsg6 =
            "Some records contain invalid weight information. Weight should be a non-negative number.";
        continue;
      }
      data.add(new MilkWeight(year, month, day, farmID, weight)); // If no error detected, load this
                                                                  // record.
    }
    scnr.close();
    // generate a complete error message.
    String errmsg = errmsg0 + errmsg1 + errmsg2 + errmsg3 + errmsg4 + errmsg5 + errmsg6;
    if (errmsg.equals("")) {
      return "This .csv file is successfully loaded without error!";
    } else {
      String warning =
          "This .csv file is loaded, but some records are ignored since they are erroneous. Errors in this file are listed below:\n";
      return warning + errmsg;
    }
  }

  /**
   * This method add a new record (as a MilkWeight object) into the data field.
   * 
   * @param year   the year of the new record
   * @param month  the month of the new record
   * @param day    the day of the new record
   * @param farmID the farm id of the new record
   * @param weight the weight of the new record
   * @return a message which will show in the alert window in the GUI.
   */
  public String addRecord(int year, int month, int day, String farmID, double weight) {
    data.add(new MilkWeight(year, month, day, farmID, weight));
    return "This record is successfully added!";
  }

  /**
   * This method edit the weight of the specified record stored in the data field.
   * 
   * @param year      the year of the specified record
   * @param month     the month of the specified record
   * @param day       the day of the specified record
   * @param farmID    the farm id of the specified record
   * @param oldWeight the weight of the specified record
   * @param newWeight the edited weight of the specified record
   * @return a message which will show in the alert window in the GUI.
   */
  public String editRecord(int year, int month, int day, String farmID, double oldWeight,
      double newWeight) {
    MilkWeight temp = data.get(year, month, day, farmID, oldWeight);
    if (temp != null) {
      temp.setWeight(newWeight);
      return "The weight of this record is successfully changed!";
    } else {
      return "Edition failed: This record is not found!";
    }
  }

  /**
   * This method removes the specified record stored in the data field.
   * 
   * @param year   the year of the specified record
   * @param month  the month of the specified record
   * @param day    the day of the specified record
   * @param farmID the farm id of the specified record
   * @param weight the weight of the specified record
   * @return a message which will show in the alert window in the GUI.
   */
  public String removeRecord(int year, int month, int day, String farmID, double weight) {
    MilkWeight removed = data.remove(year, month, day, farmID, weight);
    if (removed != null) {
      return "This record is successfully removed!";
    } else {
      return "Removal failed: This record is not found!";
    }
  }

  /**
   * This helper method categorizes milk weight data according to their months.
   * 
   * @param input a list contains MilkWeight objects
   * @return an array with size 12. Each element stores an ArrayList. That ArrayList stores milk
   *         weight records of that month. For example, element at index 0 stores January data.
   */
  private ArrayList<MilkWeight>[] farmReportBucketizeHelper(List<MilkWeight> input) {
    ArrayList<MilkWeight>[] monthsData = new ArrayList[12];
    for (MilkWeight i : input) {
      int month = i.getMonth();
      if (monthsData[month - 1] == null) {
        monthsData[month - 1] = new ArrayList<MilkWeight>();
      }
      monthsData[month - 1].add(i);
    }
    return monthsData;
  }

  /**
   * This method generates a farm report as a list of strings.
   * 
   * @param ID   the id of the farm
   * @param year the specified year
   * @return the report as a list of strings.
   * @throws IllegalArgumentException if no record is found for the specified month and year.
   */
  public List<String> farmReportGenerator(String ID, int year) throws IllegalArgumentException {
    ArrayList<String> report = new ArrayList<String>();
    // get a list contains all data of the specified year.
    ArrayList<MilkWeight> yearList = data.getYearList(year);
    if (yearList.size() == 0) { // error case1: no record found for the specified year
      throw new IllegalArgumentException("No record found for the specified year!");
    }
    // get a list contains only data of the specified farm in the specified year.
    ArrayList<MilkWeight> farmList = data.getFarmList(year, ID);
    if (farmList.size() == 0) { // error case2:no record found for the specified farm in this year
      throw new IllegalArgumentException("No record found for the specified farm in this year!");
    }
    // categorize each milk weight record according to its month.
    ArrayList<MilkWeight>[] farmListBucket = farmReportBucketizeHelper(farmList);
    ArrayList<MilkWeight>[] yearListBucket = farmReportBucketizeHelper(yearList);
    String title = "Farm Report of " + ID + "\n" + "Meaning of columns (from left to right):\n"
        + "Month number, total weight, % of all farms";
    report.add(title);
    int[] farmStat = new int[12]; // stores weight total of each month for the specified farm.
    int[] yearStat = new int[12]; // stores weight total of each month for all farms.
    for (int i = 0; i < 12; i++) {
      ArrayList<MilkWeight> thismonth = farmListBucket[i];
      if (thismonth == null) {
        continue;// if no data found for this month, skip it, in order to avoid NullPointerException
      }
      int totalWeight = 0;
      for (MilkWeight j : thismonth) {
        totalWeight += j.getWeight();
      }
      farmStat[i] = totalWeight;
    }
    for (int i = 0; i < 12; i++) {
      ArrayList<MilkWeight> thismonth = yearListBucket[i];
      if (thismonth == null) {
        continue;// if no data found for this month, skip it, in order to avoid NullPointerException
      }
      int totalWeight = 0;
      for (MilkWeight j : thismonth) {
        totalWeight += j.getWeight();
      }
      yearStat[i] = totalWeight;
    }
    String[] percent = new String[12]; // stores that farm's percent of the total milk received for
                                       // each month.
    for (int i = 0; i < 12; i++) {
      String thismonth;
      if (farmStat[i] == 0 || yearStat[i] == 0) {
        thismonth = "0%";
      } else {
        double temp1 = farmStat[i];
        double temp2 = yearStat[i];
        thismonth = Double.toString((temp1 / temp2) * 100);
        if (thismonth.length() > 6) { // keep 5 valid numbers, in order to keep the style
                                      // consistent.
          thismonth = thismonth.substring(0, 6);
        }
        thismonth = thismonth + "%";
      }
      percent[i] = thismonth;
    }
    // add information of each month into the report line by line.
    for (int i = 0; i < 12; i++) {
      String line = "Month" + (i + 1) + ":" + " ";
      line = line + farmStat[i] + " " + percent[i];
      report.add(line);
    }
    // this part of code computes some general statistics, like minimum month, maximum month, and
    // month average.
    int minMonthIndex = 0;
    int minMonthTotal = 0;
    int maxMonthIndex = 0;
    int maxMonthTotal = 0;
    int total = 0;
    int monthAverage = 0;
    for (int i = 0; i < 12; i++) {
      if (i == 0 || farmStat[i] < minMonthTotal) {
        minMonthTotal = farmStat[i];
        minMonthIndex = i;
      }
      if (i == 0 || farmStat[i] > maxMonthTotal) {
        maxMonthTotal = farmStat[i];
        maxMonthIndex = i;
      }
      total += farmStat[i];
    }
    monthAverage = total / 12;
    String min =
        "min: Month" + (minMonthIndex + 1) + " " + minMonthTotal + " " + percent[minMonthIndex];
    report.add(min);
    String max =
        "max: Month" + (maxMonthIndex + 1) + " " + maxMonthTotal + " " + percent[maxMonthIndex];
    report.add(max);
    String average = "average weight per month: " + monthAverage;
    report.add(average);
    return report;
  }

  /**
   * This helper method categorizes milk weight data according to their farm ID.
   * 
   * @param input a list contains MilkWeight objects
   * @return a hashmap which maps a string representing a farm id to an ArrayList which contains
   *         milk weight record for that farm.
   */
  private HashMap<String, ArrayList<MilkWeight>> bucketizeHelper(List<MilkWeight> input) {
    HashMap<String, ArrayList<MilkWeight>> bucket = new HashMap<String, ArrayList<MilkWeight>>();
    for (MilkWeight i : input) {
      String id = i.getFarmID();
      if (bucket.containsKey(id) == false) {
        bucket.put(id, new ArrayList<MilkWeight>());
      }
      bucket.get(id).add(i);
    }
    return bucket;
  }

  /**
   * This helper method sort farm IDs.
   * 
   * @param lines is a list of lines of the report generated, which needs to be sorted according to
   *              the farm id.
   * @return a sorted list.
   */
  private ArrayList<String> sortByID(List<String> lines) {
    // if the farm ID is "Farm xxx", xxx is an integer, this farm id is considered to be a
    // "formatted" farm id.
    ArrayList<String> formatted = new ArrayList<String>();
    // All farm ids which don't match the rule of "formatted" will be considered as "unformatted".
    ArrayList<String> unformatted = new ArrayList<String>();
    for (String line : lines) { // this loop categorizes farm id as formatted or unformatted.
      if (line.startsWith("Farm ")) {
        String[] words = line.split(" ");
        try {
          Integer.parseInt(words[1]);
          formatted.add(line);
        } catch (Exception excpt) {
          unformatted.add(line);
        }
      } else {
        unformatted.add(line);
      }
    }
    // comparator allows comparison of formatted farm id according to the integer number in the id.
    Comparator<String> comp = new Comparator<String>() {
      @Override
      public int compare(String s1, String s2) {
        int id1 = Integer.parseInt(s1.split(" ")[1]);
        int id2 = Integer.parseInt(s2.split(" ")[1]);
        return id1 - id2;
      }
    };
    // sort formatted farm id according to the integer number in the id.
    Collections.sort(formatted, comp);
    Collections.sort(unformatted); // sort unformatted farm id in alphabetical order.
    for (String i : unformatted) {
      formatted.add(i); // append unformatted id list at the end of formatted id list.
    }
    return formatted;
  }

  /**
   * This method generates a year report as a list of strings.
   * 
   * @param year the specified year
   * @return report as a list of strings.
   * @throws IllegalArgumentException if no record is found for the specified year.
   */
  public List<String> annualReportGenerator(int year) throws IllegalArgumentException {
    ArrayList<String> report = new ArrayList<String>();
    // create a list containing all data of the specified year.
    ArrayList<MilkWeight> yearList = data.getYearList(year);
    if (yearList.size() == 0) {// error case: no data found for the specified year.
      throw new IllegalArgumentException("No record found for the specified year!");
    }
    // categorize data according to the farm id.
    HashMap<String, ArrayList<MilkWeight>> yearListBucket = bucketizeHelper(yearList);
    // since hashmaps cannot be iterated, create a set containing all key-value pairs in the
    // hashmap, which supports iteration operation. The purpose of all sets in this class are the
    // same.
    Set<Map.Entry<String, ArrayList<MilkWeight>>> records = yearListBucket.entrySet();
    String title = "Year Report of " + year + "\n" + "Meaning of columns (from left to right):\n"
        + "farm ID, total weight, % of all farms";
    report.add(title);
    // Maps farm id to that farm's total weight in this year.
    HashMap<String, Integer> yearStat = new HashMap<String, Integer>();
    // Maps farm id to that farm's percent of total weight of all farms for the year.
    HashMap<String, String> percent = new HashMap<String, String>();
    int yearTotal = 0;
    for (Map.Entry<String, ArrayList<MilkWeight>> record : records) {
      // compute each farm's total weight in this year. All farm's total weight is also computed.
      int farmTotal = 0;
      ArrayList<MilkWeight> thisFarm = record.getValue();
      for (MilkWeight i : thisFarm) {
        farmTotal += i.getWeight();
        yearTotal += i.getWeight();
      }
      yearStat.put(record.getKey(), farmTotal);
    }
    Set<Map.Entry<String, Integer>> eachFarm = yearStat.entrySet();
    for (Map.Entry<String, Integer> farm : eachFarm) {
      // compute each farm's percent of total weight of all farms for the year.
      double weight = yearStat.get(farm.getKey());
      String percentage = Double.toString((weight / yearTotal) * 100);
      if (percentage.length() > 6) { // keep 5 valid numbers, in order to keep the style consistent.
        percentage = percentage.substring(0, 6);
      }
      percentage = percentage + "%";
      percent.put(farm.getKey(), percentage);
    }
    ArrayList<String> part = new ArrayList<String>();
    // generate report for each farms line by line.
    for (Map.Entry<String, Integer> farm : eachFarm) {
      String line = farm.getKey() + " ";
      line = line + yearStat.get(farm.getKey()) + " " + percent.get(farm.getKey());
      part.add(line);
    }
    part = sortByID(part); // sort lines of the report according to the farm id.
    for (String i : part) {
      report.add(i);
    }
    // this part of code computes some general statistics, like farm with minimum total weight, farm
    // with maximum total weight, and farm average.
    String minFarm = "";
    int minTotal = 0;
    String maxFarm = "";
    int maxTotal = 0;
    int farmAverage = yearTotal / eachFarm.size();
    for (Map.Entry<String, Integer> farm : eachFarm) {
      String key = farm.getKey();
      if (minTotal == 0 || yearStat.get(key) < minTotal) {
        minTotal = yearStat.get(key);
        minFarm = key;
      }
      if (maxTotal == 0 || yearStat.get(key) > maxTotal) {
        maxTotal = yearStat.get(key);
        maxFarm = key;
      }
    }
    String min = "min: " + minFarm + " " + minTotal + " " + percent.get(minFarm);
    report.add(min);
    String max = "max: " + maxFarm + " " + maxTotal + " " + percent.get(maxFarm);
    report.add(max);
    String average = "average weight of all farms: " + farmAverage;
    report.add(average);
    return report;
  }

  /**
   * This method generates a monthly report as a list of strings.
   * 
   * @param year  the specified year
   * @param month the specified month
   * @return report as a list of strings.
   * @throws IllegalArgumentException if no record is found for the specified month.
   */
  public List<String> monthlyReportGenerator(int year, int month) throws IllegalArgumentException {
    ArrayList<String> report = new ArrayList<String>();
    // get a list contains all data of the specified month.
    ArrayList<MilkWeight> monthList = data.getMonthList(year, month);
    if (monthList.size() == 0) {// error case: no data found for the specified month.
      throw new IllegalArgumentException("No record found for the specified month!");
    }
    // categorizes milk weight records according to farm id.
    HashMap<String, ArrayList<MilkWeight>> monthListBucket = bucketizeHelper(monthList);
    Set<Map.Entry<String, ArrayList<MilkWeight>>> records = monthListBucket.entrySet();
    String title = "Month Report of " + year + "-" + month + "\n"
        + "Meaning of columns (from left to right):\n" + "farm ID, total weight, % of all farms";
    report.add(title);
    // Maps farm id to that farm's total weight in this month.
    HashMap<String, Integer> monthStat = new HashMap<String, Integer>();
    // Maps farm id to that farm's percent of total weight of all farms for the month.
    HashMap<String, String> percent = new HashMap<String, String>();
    int monthTotal = 0;
    for (Map.Entry<String, ArrayList<MilkWeight>> record : records) {
      // compute each farm's percent of total weight of all farms for the month.
      int farmTotal = 0;
      ArrayList<MilkWeight> thisFarm = record.getValue();
      for (MilkWeight i : thisFarm) {
        farmTotal += i.getWeight();
        monthTotal += i.getWeight();
      }
      monthStat.put(record.getKey(), farmTotal);
    }
    Set<Map.Entry<String, Integer>> eachFarm = monthStat.entrySet();
    for (Map.Entry<String, Integer> farm : eachFarm) {
      // compute each farm's percent of total weight of all farms for the month.
      double weight = monthStat.get(farm.getKey());
      String percentage = Double.toString((weight / monthTotal) * 100);
      if (percentage.length() > 6) { // keep 5 valid numbers, in order to keep the style consistent.
        percentage = percentage.substring(0, 6);
      }
      percentage = percentage + "%";
      percent.put(farm.getKey(), percentage);
    }
    ArrayList<String> part = new ArrayList<String>();
    // generate report for each farms line by line.
    for (Map.Entry<String, Integer> farm : eachFarm) {
      String line = farm.getKey() + " ";
      line = line + monthStat.get(farm.getKey()) + " " + percent.get(farm.getKey());
      part.add(line);
    }
    part = sortByID(part); // sort lines of the report according to the farm id.
    for (String i : part) {
      report.add(i);
    }
    // this part of code computes some general statistics, like farm with minimum total weight, farm
    // with maximum total weight, and farm average.
    String minFarm = "";
    int minTotal = 0;
    String maxFarm = "";
    int maxTotal = 0;
    int farmAverage = monthTotal / eachFarm.size();
    for (Map.Entry<String, Integer> farm : eachFarm) {
      String key = farm.getKey();
      if (minTotal == 0 || monthStat.get(key) < minTotal) {
        minTotal = monthStat.get(key);
        minFarm = key;
      }
      if (maxTotal == 0 || monthStat.get(key) > maxTotal) {
        maxTotal = monthStat.get(key);
        maxFarm = key;
      }
    }
    String min = "min: " + minFarm + " " + minTotal + " " + percent.get(minFarm);
    report.add(min);
    String max = "max: " + maxFarm + " " + maxTotal + " " + percent.get(maxFarm);
    report.add(max);
    String average = "average weight of all farms: " + farmAverage;
    report.add(average);
    return report;
  }

  /**
   * This method generates a date range report as a list of strings.
   * 
   * @param year       the specified year
   * @param startMonth the month of the specified start date
   * @param startDay   the day of the specified start date
   * @param endMonth   the month of the specified end date.
   * @param endDay     the day of the specified end date.
   * @return report as a list of strings.
   * @throws IllegalArgumentException if no record is found for the specified date range.
   */
  public List<String> dateRangeReportGenerator(int year, int startMonth, int startDay, int endMonth,
      int endDay) throws IllegalArgumentException {
    // get a list contains all data of the specified date range.
    ArrayList<String> report = new ArrayList<String>();
    ArrayList<MilkWeight> rangeList =
        data.getDateRangeList(year, startMonth, startDay, endMonth, endDay);
    if (rangeList.size() == 0) {// error case: no data found for the specified date range.
      throw new IllegalArgumentException("No record found for the specified date range!");
    }
    // categorize data according to the farm id.
    HashMap<String, ArrayList<MilkWeight>> rangeListBucket = bucketizeHelper(rangeList);
    Set<Map.Entry<String, ArrayList<MilkWeight>>> records = rangeListBucket.entrySet();
    String title = "Date Range Report of " + year + "-" + startMonth + "-" + startDay + " " + year
        + "-" + endMonth + "-" + endDay + "\n" + "Meaning of columns (from left to right):\n"
        + "farm ID, total weight, % of all farms";
    report.add(title);
    // Maps farm id to that farm's total weight in this date range.
    HashMap<String, Integer> rangeStat = new HashMap<String, Integer>();
    // Maps farm id to that farm's percent of total weight of all farms for the date range.
    HashMap<String, String> percent = new HashMap<String, String>();
    int rangeTotal = 0;
    for (Map.Entry<String, ArrayList<MilkWeight>> record : records) {
      // compute each farm's percent of total weiht of all farms for the date range.
      int farmTotal = 0;
      ArrayList<MilkWeight> thisFarm = record.getValue();
      for (MilkWeight i : thisFarm) {
        farmTotal += i.getWeight();
        rangeTotal += i.getWeight();
      }
      rangeStat.put(record.getKey(), farmTotal);
    }
    Set<Map.Entry<String, Integer>> eachFarm = rangeStat.entrySet();
    for (Map.Entry<String, Integer> farm : eachFarm) {
      // compute each farm's percent of total weight of all farms for the date range.
      double weight = rangeStat.get(farm.getKey());
      String percentage = Double.toString((weight / rangeTotal) * 100);
      if (percentage.length() > 6) { // keep 5 valid numbers, in order to keep the style consistent.
        percentage = percentage.substring(0, 6);
      }
      percentage = percentage + "%";
      percent.put(farm.getKey(), percentage);
    }
    ArrayList<String> part = new ArrayList<String>();
    // generate report for each farms line by line.
    for (Map.Entry<String, Integer> farm : eachFarm) {
      String line = farm.getKey() + " ";
      line = line + rangeStat.get(farm.getKey()) + " " + percent.get(farm.getKey());
      part.add(line);
    }
    part = sortByID(part); // sort lines of the report according to the farm id.
    for (String i : part) {
      report.add(i);
    }
    // this part of code computes some general statistics, like farm with minimum total weight, farm
    // with maximum total weight, and farm average.
    String minFarm = "";
    int minTotal = 0;
    String maxFarm = "";
    int maxTotal = 0;
    int farmAverage = rangeTotal / eachFarm.size();
    for (Map.Entry<String, Integer> farm : eachFarm) {
      String key = farm.getKey();
      if (minTotal == 0 || rangeStat.get(key) < minTotal) {
        minTotal = rangeStat.get(key);
        minFarm = key;
      }
      if (maxTotal == 0 || rangeStat.get(key) > maxTotal) {
        maxTotal = rangeStat.get(key);
        maxFarm = key;
      }
    }
    String min = "min: " + minFarm + " " + minTotal + " " + percent.get(minFarm);
    report.add(min);
    String max = "max: " + maxFarm + " " + maxTotal + " " + percent.get(maxFarm);
    report.add(max);
    String average = "average weight of all farms: " + farmAverage;
    report.add(average);
    return report;
  }

  /**
   * This method saves the milk weight record which currently stored in the data field into a local
   * file for future use.
   * 
   * @param fileName the file that data will be stored into.
   * @throws FileNotFoundException required by compiler.
   */
  public void saveData(String fileName) throws FileNotFoundException {
    PrintWriter pw = new PrintWriter(new File(fileName));
    pw.println("date,farm_id,weight");
    ArrayList<MilkWeight> backup = data.getAllData();
    for (MilkWeight i : backup) { // process data according to the csv format.
      String line = i.getYear() + "-" + i.getMonth() + "-" + i.getDate() + "," + i.getFarmID() + ","
          + i.getWeight();
      pw.println(line);
    }
    pw.close();
  }

  /*
   * This method saves a report into a local file.
   * 
   * @param report a list which represents the report that is going to be saved.
   * 
   * @param fileName the file that the report will be stored.
   * 
   * @throws FileNotFoundException required by the compiler.
   */
  public void saveReport(List<String> report, String fileName) throws FileNotFoundException {
    PrintWriter pw = new PrintWriter(new File(fileName));
    for (String i : report) {
      pw.println(i);
    }
    pw.close();
  }
}
