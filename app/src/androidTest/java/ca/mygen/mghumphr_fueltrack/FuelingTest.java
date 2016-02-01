package ca.mygen.mghumphr_fueltrack;

import android.test.ActivityInstrumentationTestCase2;
import java.util.Date;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 *
 */
public class FuelingTest extends ActivityInstrumentationTestCase2 {

    public FuelingTest() {
        super(FuelingListActivity.class);
    }

    public void testDate() {
        FuelUp.Fueling fueling = new FuelUp.Fueling();
        Date date = new Date(System.currentTimeMillis());
        fueling.setDate(date);
        assertEquals(date, fueling.getDate());
    }

    public void testStation() {
        FuelUp.Fueling fueling = new FuelUp.Fueling();
        fueling.setStation("Esso");
        assertEquals(fueling.getStation(), "Esso");
    }

    public void testOdometer() {
        FuelUp.Fueling fueling = new FuelUp.Fueling();
        fueling.setOdometer(123456.12345);
        assertEquals(fueling.getOdometer(), "123456");
    }

    public void testGrade() {
        FuelUp.Fueling fueling = new FuelUp.Fueling();
        fueling.setGrade("Extra");
        assertEquals(fueling.getGrade(), "Extra");
    }

    public void testAmount(){
        FuelUp.Fueling fueling = new FuelUp.Fueling();
        fueling.setAmount(20.12345);
        assertEquals(fueling.getAmount(), "20.123");
    }

    public void testUnitCost(){
        FuelUp.Fueling fueling = new FuelUp.Fueling();
        fueling.setUnitCost(0.654321);
        assertEquals(fueling.getUnitCost(), "0.654");
    }

    public void testTotalCost(){
        FuelUp.Fueling fueling = new FuelUp.Fueling();
        fueling.setAmount(20.0);
        fueling.setUnitCost(0.50);
        assertEquals(fueling.getTotalCost(), "10.00");
    }
}