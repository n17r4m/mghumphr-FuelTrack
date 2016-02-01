package ca.mygen.mghumphr_fueltrack;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 20/01/16.
 * Model Representing a Fuel up.
 */
public class FuelUp {

    public static List<Fueling> LIST = new ArrayList<Fueling>();
    public static Map<String, Fueling> MAP = new HashMap<String, Fueling>();
    public static Fueling current;

    private static final String LIST_KEY = "json_list";

    public static Fueling create(){
        return new Fueling();
    }

    public static void add(Fueling fueling){
        FuelUp.LIST.add(fueling);
        Collections.sort(FuelUp.LIST);
        FuelUp.MAP.put(fueling.getDate().toString(), fueling);
    }

    public static void remove(Fueling fueling){
        FuelUp.MAP.remove(fueling.getDate());
        FuelUp.LIST.remove(fueling);
    }

    public static String total(){
        Double total = 0.0;
        for (Fueling fueling : FuelUp.LIST) {
            total += Double.valueOf(fueling.getTotalCost());
        }
        NumberFormat format = new DecimalFormat("0.00");
        return format.format(total);
    }

    public static void save(Context ctx){
        Gson gson = new Gson();
        ctx.getSharedPreferences("appData", Context.MODE_PRIVATE)
            .edit()
            .putString(FuelUp.LIST_KEY, gson.toJson(FuelUp.LIST))
            .apply();
    }

    public static void load(Context ctx){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Fueling>>() {}.getType();
        Type mapType = new TypeToken<Map<String, Fueling>>() {}.getType();
        SharedPreferences prefs = ctx.getSharedPreferences("appData", Context.MODE_PRIVATE);
        FuelUp.LIST = gson.fromJson(prefs.getString(FuelUp.LIST_KEY, "[]"), listType);
        Collections.sort(FuelUp.LIST);
        FuelUp.MAP.clear();
        for (Fueling fueling : FuelUp.LIST) {
            FuelUp.MAP.put(fueling.getId(), fueling);
        }
    }

    public static class Fueling implements Comparable {

        private Date date;
        private String station;
        private Number odometer;
        private String grade;
        private Number amount;
        private Number unitCost;

        public Fueling() {
            this.date = new Date(System.currentTimeMillis());
            this.station = "";
            this.odometer = 0;
            this.grade = "Regular";
            this.amount = 0;
            this.unitCost = 0;
        }

        public String getId(){
            return this.getDate().toString();
        }

        public Date getDate() {
            return this.date;
        }
        public String getShortDate() {
            SimpleDateFormat format = new SimpleDateFormat("MMM d yy");
            return format.format(this.date);
        }

        public void setDate(Date date) {
            FuelUp.MAP.remove(this.getId());
            this.date = date;
            FuelUp.MAP.put(this.getId(), this);
        }

        public String getStation() {
            return this.station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public String getOdometer() {
            NumberFormat format = new DecimalFormat("0");
            return format.format(Math.round(this.odometer.doubleValue()));
        }

        public void setOdometer(Number odometer) { this.odometer = odometer; }

        public String getGrade() {
            return this.grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getAmount() {
            NumberFormat format = new DecimalFormat("0.000");
            return format.format(this.amount);
        }

        public void setAmount(Number amount) {
            this.amount = amount;
        }

        public String getUnitCost() {
            NumberFormat format = new DecimalFormat("0.000");
            return format.format(this.unitCost);
        }

        public void setUnitCost(Number cost) {
            this.unitCost = cost;
        }

        public String getTotalCost() {
            Number cost = this.unitCost.doubleValue() * this.amount.doubleValue();
            NumberFormat format = new DecimalFormat("0.00");
            return format.format(cost);
        }

        public String getListDetails(){
            return "$" + this.getTotalCost() + " @ " + this.getStation() + "\n"
                + this.getGrade() + "\n"
                + "$" + this.getUnitCost() + "/L " + this.getAmount() + "L ";
        }

        @Override
        public int compareTo(Object another){
            return ((Fueling)another).getDate().compareTo(this.getDate());
        }

        @Override
        public String toString(){
            return this.getId();
        }
    }
}
