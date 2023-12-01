package uk.ac.ed.inf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private RestClient restClient;
    private String BASE_URL; // REST Service URL

    public DataService(String baseUrl) {
        this.BASE_URL = baseUrl;
        this.restClient = new RestClient();
    }

    public DataService() {
        this.restClient = new RestClient();
    }

    public List<RestaurantRep> fetchRestaurants() throws Exception {
        String url = BASE_URL + "/restaurants"; //Formats URL for 'restaurants'
        String jsonResponse = restClient.sendRequest(url);
        Type restaurantListType = new TypeToken<List<RestaurantRep>>(){}.getType();
        return new Gson().fromJson(jsonResponse, restaurantListType);
    }


    public List<OrderRep> fetchOrders(String date) throws Exception {
        String url = BASE_URL + "/orders/" + date; // Formats URL for 'orders' on a specific date
        String jsonResponse = restClient.sendRequest(url);
        Type orderListType = new TypeToken<List<OrderRep>>(){}.getType();
        return new Gson().fromJson(jsonResponse, orderListType);
    }


    public CentralAreaRep fetchCentralArea() throws Exception {
        String url = BASE_URL + "/centralArea"; //Formats URL for 'centralArea'
        String jsonResponse = restClient.sendRequest(url);
        return new Gson().fromJson(jsonResponse, CentralAreaRep.class);
    }


    public List<NoFlyZoneRep> fetchNoFlyZones() throws Exception {
        String url = BASE_URL + "/noFlyZones"; //Formats URL for 'noFlyZones'
        String jsonResponse = restClient.sendRequest(url);
        Type noFlyZoneListType = new TypeToken<List<NoFlyZoneRep>>(){}.getType();
        return new Gson().fromJson(jsonResponse, noFlyZoneListType);
    }



}
