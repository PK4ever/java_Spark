
import com.evdb.javaapi.APIConfiguration;
import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;
import com.evdb.javaapi.data.Location;
import com.evdb.javaapi.data.request.EventSearchRequest;
import com.evdb.javaapi.operations.EventOperations;

import java.util.ArrayList;

/**
 * Created by paulk4ever on 4/27/17.
 */
public class ApiConnector {
    final private String API_KEY = "qX2dNH9TZGLvBT8B";
    final private String USER_NAME = "events420";
    final private String PASSWORD = "pass55word";

    public EventOperations eventOperations;
    public EventSearchRequest eventSearchRequest;
    public Location location;

    public ApiConnector(){
        APIConfiguration apiConfiguration = new APIConfiguration();
        apiConfiguration.setApiKey(API_KEY);
        apiConfiguration.setEvdbUser(USER_NAME);
        apiConfiguration.setEvdbPassword(PASSWORD);

        eventOperations = new EventOperations();
        eventSearchRequest = new EventSearchRequest();
        eventSearchRequest.setPageSize(10);
        location = new Location();
        location.setCountry("United Kingdom");
        eventSearchRequest.setCategory("soccer");
    }

    public ArrayList<Event> getEvents(String keyword) throws EVDBRuntimeException, EVDBAPIException {
        eventSearchRequest.setKeywords(keyword);
        eventSearchRequest.setLocation(location.getCountry());
        return (ArrayList<Event>)eventOperations.search(eventSearchRequest).getEvents();
    }
}