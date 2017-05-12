import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by paulk4ever on 5/10/17.
 */
@DatabaseTable(tableName = "events")
public class EventModal {

    public static final String SEID_FIELD = "SeID";
    public static final String TITLE_FIELD = "title";
    public static final String VENUE_FIELD = "venue";

    EventModal(){

    }


    @DatabaseField(columnName = TITLE_FIELD)
    private String title;

    @DatabaseField(columnName = SEID_FIELD, unique = true, id = true)
    private String seid;

    @DatabaseField(columnName = VENUE_FIELD)
    private String venue;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setSeID(String seid){
        this.seid = seid;
    }
    public String getSeid(){
        return this.seid;
    }

    public void setVenue(String venue){
        this.venue = venue;
    }
    public String getVenue(){
        return this.venue;
    }



}
