package tk.kolyanov.reminder.Objects;


import java.util.Calendar;

public class Remind {
    private Long mId;
    private String mHeader;
    private String mDescription;
    private Long mDateTime;

    public Remind(Long id, String header, String description){
        mId = id;
        mHeader = header;
        mDescription = description;
    }

    public Remind(Long id, String header, String description, Long dateTime) {
        mId = id;
        mHeader = header;
        mDescription = description;
        mDateTime = dateTime;
    }

    public Remind(String header, String description, Calendar calendar){
        mHeader = header;
        mDescription = description;
        mDateTime = calendar.getTime().getTime();
    }

    public Long getId() {
        return mId;
    }

    public String getHeader() {
        return mHeader;
    }

    public void setHeader(String header) {
        mHeader = header;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Long getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Long dateTime) {
        mDateTime = dateTime;
    }
}
