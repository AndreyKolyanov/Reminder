package tk.kolyanov.reminder.DataBase;


import java.util.List;

import tk.kolyanov.reminder.Objects.Remind;

public interface IDataBaseHelper {

    void add(Remind object);
    Remind getElementById(int id);
    List<Remind> getAllElements();
    void update(Remind object);
    void deleteElement(Remind object);
}
