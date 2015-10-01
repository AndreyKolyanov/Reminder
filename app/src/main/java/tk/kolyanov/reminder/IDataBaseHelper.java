package tk.kolyanov.reminder;


import java.util.List;

public interface IDataBaseHelper {

    void add(Remind object);
    Remind getElementById(int id);
    List<Remind> getAllElements();
    void update(Remind object);
    void deleteElement(Remind object);
}
