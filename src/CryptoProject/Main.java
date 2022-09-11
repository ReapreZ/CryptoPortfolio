package CryptoProject;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Locale;

import static CryptoProject.Cryptocurrency.*;
/* TODO:
    Bei negativen Zahlen rote Schrift, bei positiven Zahlen grüne Schrift
    Graphen
    Textdatei umwandeln in JSON-Datei
    Load/Save
    Bei Top100: Pricechanges einbinden
    Bei hohen Zahlen M und B statt ganze Zahl
    Skalierbarer alles zB Methoden umschreiben, sodass diese mehrfach genutzt werden könnne
    Code-Cleanup
    Einfache Suche nach Kryptowährungen, also Kryptoname eingeben und Daten erhalten
    Bei sehr kleinen Werten E^Zahl entfernen
    Bei Crypto-Portfolio:Kommastellen abscheiden
    Bessere Variablen-Namen
    Auf Github hochladen
 */


public class Main {

    private static final String apiKey = "CMC_PRO_API_KEY=9e3670de-ef6a-42e2-994e-83b240b1cb87";
    static GUI gui;
    int[] idlist;
    static Cryptocurrency[] CryptoList = new Cryptocurrency[150];
    static Cryptocurrency[] EmptyObjArray = new Cryptocurrency[150];
    static DefaultTableModel model2;
    static DefaultTableModel model;

    public static void main(String[] args) throws MalformedURLException {
        gui = new GUI();
        //Method 2: java.net.http.HttpClient
        //String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=9e3670de-ef6a-42e2-994e-83b240b1cb87";
        //String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?id=1,1027,1839,2010,4172,3635,1975,4030,7080,3155,9436,4189,9764,17081,16432,15142,10804,13382,13503,10307&CMC_PRO_API_KEY=9e3670de-ef6a-42e2-994e-83b240b1cb87";
    }

    public static void top100() {
        //URL
        clear(CryptoList);
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        StringBuilder sb = new StringBuilder();
        sb.append("https://pro-api.coinmarketcap.com");
        sb.append("/v1/cryptocurrency/listings/latest?");
        sb.append(apiKey);
        String url = sb.toString();
        //API-AUFRUF
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(Main::parse)
                .join();// führt letzten befehl/sout aus

        for (int i = 0; i < 100; i++) {
            String price = CryptoList[i].price;
            if (!(CryptoList[i].price.matches("0.000[0-9]+"))) {
                price = price.replace(",", ".");
                String scap = addComma(CryptoList[i].cap);
                Object[] newCrypto = {String.valueOf(CryptoList[i].rank), CryptoList[i].name,
                        price + "$", scap};
                model.addRow(newCrypto);
            }
        }
    }

        public static String parse (String responseBody){
            String segments[] = responseBody.split(",\"data\":");
            responseBody = segments[segments.length - 1];
            JSONArray tokens = new JSONArray(responseBody);
            for (int i = 0; i < 100; i++) {
                JSONObject token = tokens.getJSONObject(i);
                JSONObject quote = token.getJSONObject("quote");
                JSONObject prices = quote.getJSONObject("USD");
                int id = token.getInt("id");
                //System.out.println(id);
                String name = token.getString("name");
                //System.out.println(name);
                String symbol = token.getString("symbol");
                long circulating_supply = token.getLong("circulating_supply");
                int rank = token.getInt("cmc_rank");
                double price = prices.getDouble("price");
                String sprice = Cryptocurrency.rounds(price);
                Double p_change_1h = prices.getDouble("percent_change_1h");
                Double p_change_24h = prices.getDouble("percent_change_24h");
                Double p_change_7d = prices.getDouble("percent_change_7d");
                Double p_change_30d = prices.getDouble("percent_change_30d");
                Double p_change_90d = prices.getDouble("percent_change_90d");
                long marketcap = prices.getLong("market_cap");

                CryptoList[i] = new Cryptocurrency(id, rank, name, sprice, marketcap, p_change_24h, p_change_7d, p_change_30d);

                System.out.println("ID: " + id + " Name: " + name + " Symbol: " + symbol + " Price: " + price
                        + " Circulating Supply: " + circulating_supply + " Rank: " + rank);

            }
            return null;
        }

        public static void portfolio () throws FileNotFoundException {
            //URL
            clear(CryptoList);
            model2.getDataVector().removeAllElements();
            model2.fireTableDataChanged();
            StringBuilder sb = new StringBuilder();
            sb.append("https://pro-api.coinmarketcap.com");
            sb.append("/v1/cryptocurrency/quotes/latest?");
            sb.append("id=1,1027,1839,2010,4172,3635,1975,4030,7080,3155,9436,4189,9764,17081,16432,15142,10804,13382,13503,10307&");
            sb.append(apiKey);
            String url = sb.toString();
            //API-AUFRUF
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(Main::parse2)
                    .join();// führt letzten befehl/sout aus

            /*CryptoList[0].amount = 0.0143244;
            CryptoList[0].dollarinvested = 608.80;
            CryptoList[6].amount = 17;
            CryptoList[6].dollarinvested = 425;
            CryptoList[3].amount = 125;
            CryptoList[3].dollarinvested = 168;
            CryptoList[5].amount = 201.9;
            CryptoList[5].dollarinvested = 79.45;
            CryptoList[7].amount = 31.29;
            CryptoList[7].dollarinvested = 50;
            CryptoList[8].amount = 300;
            CryptoList[8].dollarinvested = 66.25;
            CryptoList[9].amount = 0.9272;
            CryptoList[9].dollarinvested = 107.62;
            CryptoList[10].amount = 40000000;
            CryptoList[10].dollarinvested = 50;
            CryptoList[12].amount = 376.48834;
            CryptoList[12].dollarinvested = 130;
            CryptoList[14].amount = 1359.05365;
            CryptoList[14].dollarinvested = 100;
            CryptoList[15].amount = 29135.38785;
            CryptoList[15].dollarinvested = 83;
            CryptoList[17].amount = 778990228;
            CryptoList[17].dollarinvested = 77;
            CryptoList[19].amount = 370713.16;
            CryptoList[19].dollarinvested = 75;*/
            Reader.readfile();
            double totalinvest = 0;
            for (int i = 0; i < 20; i++) {
                String price = CryptoList[i].price;
                if (!(CryptoList[i].price.matches("0.000[0-9]+"))) {
                    price = price.replace(",", ".");
                }
                Object[] newCrypto = {String.valueOf(CryptoList[i].rank), CryptoList[i].name,
                        price + "$", String.valueOf(CryptoList[i].twentyfour), String.valueOf(CryptoList[i].sevendays),
                        String.valueOf(CryptoList[i].thirtydays), String.valueOf(CryptoList[i].amount),
                        String.valueOf(worth(CryptoList[i])) + "$", winlos(CryptoList[i])};
                model2.addRow(newCrypto);
                totalinvest = totalinvest + CryptoList[i].dollarinvested;
            }
            gui.totalinvest.setText("Total Invest: " + totalinvest);
            double d = 0;
            for(int i = 0; i < 20; i++) {
                d += worth(CryptoList[i]);
            }
            d = round(d);
            gui.totalvalue.setText("Total Value: " + d);
            //Reader.createfile();
            //Reader.writefile(CryptoList);

        }
        public static String parse2 (String responseBody){
            int[] idlist = {1, 1027, 1839, 2010, 4172, 3635, 1975, 4030, 7080, 3155, 9436, 4189, 9764, 17081, 16432, 15142, 10804, 13382, 13503, 10307};
            String segments[] = responseBody.split("\"notice\":null},");
            segments[segments.length - 1] = "{" + segments[segments.length - 1];
            responseBody = segments[segments.length - 1];
            JSONObject tokens = new JSONObject(responseBody);
            for (int i = 0; i < idlist.length; i++) {
                //int j = idlist[i];
                //String s = String.valueOf(j);
                int id = tokens.getJSONObject("data").getJSONObject(Integer.toString(idlist[i])).getInt("id");
                //System.out.println(id);
                String name = tokens.getJSONObject("data").getJSONObject(Integer.toString(idlist[i])).getString("name");
                String symbol = name + " [" + tokens.getJSONObject("data").getJSONObject(Integer.toString(idlist[i])).getString("symbol") + "]";
                //System.out.println(symbol);
                int rank = tokens.getJSONObject("data").getJSONObject(Integer.toString(idlist[i])).getInt("cmc_rank");
                //System.out.println(rank);
                JSONObject numbers = tokens.getJSONObject("data").getJSONObject(Integer.toString(idlist[i])).getJSONObject("quote").getJSONObject("USD");
                double price = numbers.getDouble("price");
                String sprice = Cryptocurrency.rounds(price);
                //System.out.println("Price: " + sprice);
                double p_change_1h = numbers.getDouble("percent_change_1h");
                double p_change_24h = numbers.getDouble("percent_change_24h");
                p_change_24h = round(p_change_24h);
                double p_change_7d = numbers.getDouble("percent_change_7d");
                p_change_7d = round(p_change_7d);
                double p_change_30d = numbers.getDouble("percent_change_30d");
                p_change_30d = round(p_change_30d);
                long marketcap = numbers.getLong("market_cap");
                Cryptocurrency c = new Cryptocurrency(id, rank, name, sprice, marketcap, p_change_24h, p_change_7d, p_change_30d);
                CryptoList[i] = c;
            }
            return null;
        }
        public static void plusCrypto() {
            for(int i = 0; i < 20; i++) {
                if(gui.tfname.getText().equals(CryptoList[i].name)) {
                    try {
                        CryptoList[i].amount = CryptoList[i].amount + Double.parseDouble(GUI.tfamount.getText());
                        CryptoList[i].dollarinvested = CryptoList[i].dollarinvested + Double.parseDouble(GUI.tfinvest.getText());
                        Reader.writefile(CryptoList);
                        System.out.println("Cryptocurrency " + GUI.tfname.getText() + " has been added");
                    } catch(NumberFormatException e) {
                        System.out.println("Not a Number");
                    }
                }
            }
        }



        public static void clear (Cryptocurrency[]arr){
            final int arrlen = arr.length;
            clear(arr, arrlen);
        }

        public static void clear (Cryptocurrency[]arr,int arrlen){
            int count = 0;
            final int length = EmptyObjArray.length;
            while (arrlen - count > length) {
                System.arraycopy(EmptyObjArray, 0, arr, count, length);
                count += length;
            }
            System.arraycopy(EmptyObjArray, 0, arr, count, arrlen - count);
        }
    }

