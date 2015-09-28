package tk.kolyanov.reminder;


public class Remind {
    private String mHeader;
    private String mDescription;

    Remind(String header, String description){
        mHeader = header;
        mDescription = description;
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
