package CryptoProject;

import java.lang.Math;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Cryptocurrency {
    final double eur = 1;
    String name;
    double amount;
    double dollarinvested;
    String price;
    int id;
    int rank;
    long cap;
    double twentyfour;
    double sevendays;
    double thirtydays;


    public Cryptocurrency(int id, int rank, String name, String price, long cap, double twentyfour, double sevendays, double thirtydays) {
        this.id = id;
        this.rank = rank;
        this.name = name;
        this.price = price;
        this.cap = cap;
        this.twentyfour = twentyfour;
        this.sevendays = sevendays;
        this.thirtydays = thirtydays;
        double amount = 0;
        double dollarinvested = 0;


    }
    public static String addComma(String d) {
        String newstring = d.replace(",",".");
        double dd = Double.parseDouble(newstring);
        String s = String.format(java.util.Locale.US,"%,f", dd);
        return s = s + "$";
    }

    public static String addComma(long l) {
        String s = String.format(java.util.Locale.US,"%,d", l);
        return s = s + "$";
    }

    public double dollar2euro(Cryptocurrency c) { // $ -> €
        return Double.parseDouble(c.price) * eur;
    }

    public static double worth(Cryptocurrency c) {
        String newstring = c.price.replace(",", ".");
        double d = c.amount * Double.parseDouble(newstring);
        return Math.round(d*100.0)/100.0;
    }

    public static void addworth(Cryptocurrency c, double d) {
        c.amount += d;
        //c.dollarinvested += d * c.dollarprice;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        //System.out.println(dtf.format(now));
    }

    public static String winlos(Cryptocurrency c) {
        return rounds(worth(c) - c.dollarinvested);
    }

    public static double round(double d) {
        String s = Double.toString(d);
        //System.out.println(d);
        if(s.matches("[0-9].[0-9]+E-[0-9]+")) {
            String[] split = s.split("E");
            //b = ^...
            double b = Double.parseDouble(split[split.length - 1].substring(split[split.length - 1].length() - 1));
            b = b * (-1);
            double c = Double.parseDouble(split[0]);
            c = Double.parseDouble(String.format(java.util.Locale.US, "%.5f", c));
            d = c * Math.pow(10, b);
            DecimalFormat df = new DecimalFormat("0.#");
            df.setMaximumFractionDigits(10);
            s = df.format(d);
            return d;
        }
        return Math.round(d*1000.0)/1000.0;
    }


    public static String rounds(double d) {
        String s = Double.toString(d);
        if(s.matches("[0-9].[0-9]+E-[0-9]+")) {
            String[] split = s.split("E");
            //b = ^...
            double b = Double.parseDouble(split[split.length - 1].substring(split[split.length - 1].length() - 1));
            b = b * (-1);
            //c = d without E-...
            double c = Double.parseDouble(split[0]);
            c = Double.parseDouble(String.format(java.util.Locale.US, "%.5f", c));
            //d = c * E^b
            d = c * Math.pow(10, b);
            DecimalFormat df = new DecimalFormat("0.#");
            df.setMaximumFractionDigits(10);
            return df.format(d);
        }
        if(s.matches("0.0[0-9]+")) {
            s = String.format(java.util.Locale.US, "%f.4f", d);
            if(s.contains("f")) {
                String[] split = s.split(".[0-9]f");
                return split[0];
            } else {
                return s;
            }
        }
        return String.valueOf(Math.round(d*1000.0)/1000.0);
    }
}
