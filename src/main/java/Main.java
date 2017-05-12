/**
 * Created by paulk4ever on 5/8/17.
 */

import com.evdb.javaapi.data.Event;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import spark.ModelAndView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.delete;

public class Main {
    public static void main(String[] args) throws SQLException {


        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        String databaseUrl = "jdbc:mysql://localhost:3306/javaPlay";
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        ((JdbcConnectionSource)connectionSource).setUsername("root");
        ((JdbcConnectionSource)connectionSource).setPassword("mdbh6548");
        Dao<EventModal,String> eventModalsDao = DaoManager.createDao(connectionSource, EventModal.class);

        EventFulController eventFulController = new EventFulController();


        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/search.vtl" );
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/eventList", (request, response) -> {
            String keyWord = request.queryParams("event");
            List<Event> events = eventFulController.search(keyWord);
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("EventsList", events);
            model.put("template", "templates/eventView.vtl" );
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


        post("/liked", (request, response) -> {

            String title = request.queryParams("title");
            String venue = request.queryParams("venue");
            String seid = request.queryParams("seid");
            EventModal eventModal = new EventModal();
            if (eventModalsDao.queryForId(seid) == null) {
                eventModal.setTitle(title);
                eventModal.setVenue(venue);
                eventModal.setSeID(seid);
                eventModalsDao.create(eventModal);
            }
            List<EventModal> modals = eventModalsDao.queryForAll();
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("modals", modals);

            model.put("template", "templates/liked.vtl" );
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


        get("/remove", (request, response) -> {
            String seid = request.queryParams("seid");

            DeleteBuilder<EventModal, String> deleteBuilder = eventModalsDao.deleteBuilder();
            deleteBuilder.where().eq(EventModal.SEID_FIELD, seid);
            int count = deleteBuilder.delete();

            List<EventModal> modals = eventModalsDao.queryForAll();
            Map<String, Object> model = new HashMap<String, Object>();

            model.put("modals", modals);
            model.put("template", "templates/liked.vtl" );
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //RESTFUL
        //Delete
        delete("remove/:seid", (request, response) -> {
            String seid = request.params(":seid");
            DeleteBuilder<EventModal, String> deleteBuilder = eventModalsDao.deleteBuilder();
            deleteBuilder.where().eq(EventModal.SEID_FIELD, seid);

            if (deleteBuilder.delete() > 0) {
                return "Event with SEID: " + request.params(":seid") + " deleted.";
            } else {
                return "Event with SEID: " + request.params(":seid") + "Not found.";
            }
        });

        //POST
        post("/addAll/:keyword", (request, response) -> {
            String keyword = request.params(":keyword");
            EventModal eventModal = new EventModal();
            for (Event e: eventFulController.search(keyword)){
                eventModal.setTitle(e.getTitle());
                eventModal.setVenue(e.getVenueAddress());
                eventModal.setSeID(e.getSeid());
                eventModalsDao.create(eventModal);
            }

            List<EventModal> modals = eventModalsDao.queryForAll();
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("modals", modals);

            model.put("template", "templates/liked.vtl" );
            return "All Events matching the search word "+keyword+ "have been added";
        });



    }


}