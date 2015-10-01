package tk.kolyanov.reminder;

import android.app.DialogFragment;

public interface StartDialog {

    void startDialog(DialogFragment fragment, String tag);
    void noyifyAdapter();
}
