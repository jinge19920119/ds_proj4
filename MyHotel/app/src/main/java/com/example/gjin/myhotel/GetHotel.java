package com.example.gjin.myhotel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * get the hotel information from the api
 * Created by gjin on 11/13/2015.
 */
public class GetHotel {

    public GetHotel(){

    }

    /**
     * send the http request to the server and get the
     * @param city
     * @return
     */
    public String[] searchHotel(String city){
        HttpURLConnection connection;
        int status=0;
        String[] result = new String[]{};
        try{
            URL url= new URL("https://young-headland-3634.herokuapp.com/MyHotels/"+city);
            //connect to the server on heroku
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "text/plain");
            //get the connection status
            status= connection.getResponseCode();
            if(status!=200){
                return null;
            }
            BufferedReader br= new BufferedReader( new InputStreamReader(connection.getInputStream()));
            String input= "";
            String line= "";
            //read from the stream
            while((line= br.readLine())!=null){
                input+= line;
            }
            System.out.println(input);
            br.close();//close the buffer
            result = parseXML(input);
            connection.disconnect();//close the connection
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * parse from the XML input, return an array of String
     * @param input
     * @return
     */
    private static String[] parseXML(String input){
        if(!input.contains("<hotel>"))
            return null;
        String[] results= new String[8];
        int left= 0;
        int right= 0;
        for(int i=0;i<8;i++){
            left= input.indexOf("<hotel>",right);
            right= input.indexOf("</hotel>",left);
            String result= input.substring(left+7,right);//cut the string from left to right index
            results[i]= result;//save the result to the array
        }
        return results;
    }


}
