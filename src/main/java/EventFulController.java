import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;

import java.util.List;

/**
 * Created by paulk4ever on 5/10/17.
 */
public class EventFulController {
    ApiConnector apiConnector;

    EventFulController(){
        apiConnector = new ApiConnector();
    }
    public List<Event> search(String keyWord) throws EVDBRuntimeException, EVDBAPIException {
        return apiConnector.getEvents(keyWord);
    }
}
