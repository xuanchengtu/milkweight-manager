package application;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * this class is to test MyArrayList
 * @author Zilin
 *
 */
public class MyArrayListTest {
	
	private MyArrayList test;
	
	@Before
    public void setUp() throws Exception {
    	test=new MyArrayList();
    	test.add(new MilkWeight(2020,1,1,"01",35));
    	test.add(new MilkWeight(2020,1,1,"02",47));
    	test.add(new MilkWeight(2020,1,2,"01",22));
    	test.add(new MilkWeight(2020,1,2,"02",36));
    	test.add(new MilkWeight(2020,1,3,"01",37));
    	test.add(new MilkWeight(2020,1,3,"02",34));
    	test.add(new MilkWeight(2020,1,4,"01",33));
    	test.add(new MilkWeight(2020,1,4,"01",32));
    	test.add(new MilkWeight(2020,2,1,"01",55));
    	test.add(new MilkWeight(2020,2,1,"03",67));
    	test.add(new MilkWeight(2020,2,2,"01",42));
    	test.add(new MilkWeight(2020,2,2,"03",56));
    	test.add(new MilkWeight(2020,2,3,"01",57));
    	test.add(new MilkWeight(2020,2,3,"03",54));
    	test.add(new MilkWeight(2020,2,4,"01",53));
    	test.add(new MilkWeight(2020,2,4,"03",52));
    	test.add(new MilkWeight(2020,2,5,"01",51));
    }

    
    @After
    public void tearDown() throws Exception {
    	test=new MyArrayList();
    }
    
    /**
     * test add and remove method
     */
    @Test
    public void test000_add_remove_100000_items() {
    	try {
    		for(int i=0;i<100000;i++) {
    			test.add(new MilkWeight(2020+i,(1+i)%12,(1+i)%30,"01",i));
    		}
    		for(int i=0;i<100000;i++) {
    			test.remove(2020+i,(1+i)%12,(1+i)%30,"01", i);
    		}
    	}
    	catch(Exception e) {
    		fail("Exception caught while adding and removing 100000 items");
    	}
    }
    
    /**
     * test get method
     */
    @Test
    public void test001_get_items() {
    	MilkWeight temp=test.get(2020,1,1,"01",35);
    	if(temp.compareTo(new MilkWeight(2020,1,1,"01",35))!=0) {
    		fail("wrong output from get method");
    	}
    	temp=test.get(2020,1,2,"01",22);
    	if(temp.compareTo(new MilkWeight(2020,1,2,"01",22))!=0) {
    		fail("wrong output from get method");
    	}
    	temp=test.get(2020,1,3,"01",37);
    	if(temp.compareTo(new MilkWeight(2020,1,3,"01",37))!=0) {
    		fail("wrong output from get method");
    	}
    }
    
    /**
     * test size method
     */
    @Test
    public void test002_size() {
    	if(test.size()!=17) {
    		fail("Wrong Size Method");
    	}
    }
    
    /**
     * test add method
     */
    @Test
    public void test003_add_items() {
    	test.add(new MilkWeight(2020,1,1,"04",100));
    	if(test.size()!=18) {
    		fail("Adding item fails: size didn't change");
    	}
    	if(test.get(2020,1,1,"04",100).compareTo(new MilkWeight(2020,1,1,"04",100)) != 0) {
    		fail("Adding item fails: content wrong");
    	}
    	test.add(new MilkWeight(2020,3,20,"05",1000));
    	if(test.size()!=19) {
    		fail("Adding item fails: size didn't change");
    	}
    	if(test.get(2020,3,20,"05", 1000).compareTo(new MilkWeight(2020,3,20,"05",1000)) != 0) {
    		fail("Adding item fails: content wrong");
    	}
    }
    
    /**
     * test remove method
     */
    @Test
    public void test004_remove_items() {
    	MilkWeight temp=test.remove(2020, 1, 1, "01", 35);
    	if(test.size()!=16) {
    		fail("Removing items fails: size didn't change");
    	}
    	if(temp.compareTo(new MilkWeight(2020,1,1,"01",35))!=0) {
    		fail("Removing items fails: the item removed has wrong content");
    	}
    	temp=test.remove(2020, 1, 2, "01", 22);
    	if(test.size()!=15) {
    		fail("Removing items fails: size didn't change");
    	}
    	if(temp.compareTo(new MilkWeight(2020,1,2,"01",22))!=0) {
    		fail("Removing items fails: the item removed has wrong content");
    	}
    }
    
    /**
     * test getYearList method
     */
    @Test
    public void test005_get_year_list() {
    	ArrayList<MilkWeight> temp=test.getYearList(2020);
    	if(temp.size()!=17) {
    		fail("Getting year list failed");
    	}
    }
    
    /**
     * test getMonthList method
     */
    @Test
    public void test006_get_month_list() {
    	ArrayList<MilkWeight> temp=test.getMonthList(2020,1);
    	if(temp.size()!=8) {
    		fail("Getting month list failed");
    	}
    	temp=test.getMonthList(2020,2);
    	if(temp.size()!=9) {
    		fail("Getting month list failed");
    	}
    }
    
    /**
     * test getFarmIdList method
     */
    @Test
    public void test007_get_Farm_ID_list() {
    	ArrayList<MilkWeight> temp=test.getFarmList(2020,"01");
    	if(temp.size()!=10) {
    		fail("Getting farm Id list failed");
    	}
    	temp=test.getFarmList(2020,"02");
    	if(temp.size()!=3) {
    		fail("Getting farm Id list failed");
    	}
    	temp=test.getFarmList(2020,"02");
    	if(temp.size()!=3) {
    		fail("Getting farm Id list failed");
    	}
    }
    
    /**
     * test the getDataRangeList method
     */
    @Test
    public void test008_get_date_range_list() {
    	ArrayList<MilkWeight> temp=test.getDateRangeList(2020,2,2,2,5);
    	if(temp.size()!=7) {
    		fail("Getting date range list failed");
    	}
    	temp=test.getDateRangeList(2020,1,4,2,1);
    	if(temp.size()!=4) {
    		fail("Getting date range list failed");
    	}
    }
    
}
