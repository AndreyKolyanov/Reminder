package tk.kolyanov.reminder.Objects;


public class Remind {
    private Long mId;
    private String mHeader;
    private String mDescription;

    public Remind(String header, String description){
        mHeader = header;
        mDescription = description;
    }

    public Remind(Long id, String header, String description){
        mId = id;
        mHeader = header;
        mDescription = description;
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
}
