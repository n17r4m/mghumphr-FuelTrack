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

    private static final String LIST_KEY = "json_list";
    private static final String MAP_KEY = "json_map";

    public static Fueling create(){
        return new Fueling();
    }

    public static void add(Fueling fueling){
        FuelUp.LIST.add(fueling);
        Collections.sort(FuelUp.LIST);
        FuelUp.MAP.put(fueling.getDate().toString(), fueling);
    }

    public static void remove(String date){
        Fueling fueling = FuelUp.MAP.get(date);
        if(fueling != null){
            FuelUp.MAP.remove(date);
            FuelUp.LIST.remove(fueling);
        }
    }

    public static void remove(Fueling fueling){
        FuelUp.MAP.remove(fueling.getDate());
        FuelUp.LIST.remove(fueling);
    }


    public static void save(Context ctx){
        Gson gson = new Gson();
        ctx.getSharedPreferences("appData", Context.MODE_PRIVATE)
            .edit()
            .putString(FuelUp.LIST_KEY, gson.toJson(FuelUp.LIST))
            .putString(FuelUp.MAP_KEY, gson.toJson(FuelUp.MAP))
            .apply();
    }

    public static void load(Context ctx){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Fueling>>() {}.getType();
        Type mapType = new TypeToken<Map<String, Fueling>>() {}.getType();
        SharedPreferences prefs = ctx.getSharedPreferences("appData", Context.MODE_PRIVATE);
        FuelUp.LIST = gson.fromJson(prefs.getString(FuelUp.LIST_KEY, "[]"), listType);
        FuelUp.MAP = gson.fromJson(prefs.getString(FuelUp.MAP_KEY, "{}"), mapType);
    }

    public static class Fueling implements Comparable {

        private String id;
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

        public Date getDate() {
            return this.date;
        }
        public String getShortDate() {
            SimpleDateFormat format = new SimpleDateFormat("MMM d");
            return format.format(this.date);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getStation() {
            return this.station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public Number getOdometer() {
            return this.odometer;
        }

        public void setOdometer(Number odometer) {
            this.odometer = odometer;
        }

        public String getGrade() {
            return this.grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public Number getAmount() {
            return this.amount;
        }

        public void setAmount(Number amount) {
            this.amount = amount;
        }

        public Number getUnitCost() {
            return this.unitCost;
        }

        public void setUnitCost(Number cost) {
            this.unitCost = cost;
        }

        public String getTotalCost() {
            Number cost = this.unitCost.doubleValue() * this.amount.doubleValue();
            NumberFormat format = NumberFormat.getInstance();
            format.setMaximumFractionDigits(2);
            return format.format(cost);
        }

        public String getListDetails(){
            return "$" + this.getTotalCost() + " @ " + this.getStation() + " (" + this.getGrade() + ")\n"
                + "$" + this.getUnitCost() + "/L " + this.getAmount() + "L ";
        }

        @Override
        public int compareTo(Object another){
            return -1;
        }
        public int compareTo(Fueling another) {
            return this.getDate().compareTo(another.getDate());
        }

        @Override
        public String toString(){
            return this.getDate().toString();
        }
    }
}
