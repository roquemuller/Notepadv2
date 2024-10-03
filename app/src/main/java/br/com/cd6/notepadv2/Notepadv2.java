package br.com.cd6.notepadv2;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;
import android.widget.Toolbar;


public class Notepadv2 extends ListActivity {

    private int mNoteNumber = 1;
    private NotesDbAdapter mDbHelper;
    public static final int INSERT_ID = Menu.FIRST;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notepad_list);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setActionBar(toolbar);

        mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        getMenuInflater();
        menu.add(0, INSERT_ID, 0, R.string.menu_insert);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNote() {
        String noteName = "Nota " + mNoteNumber++;
        mDbHelper.createNote(noteName, "");
        fillData();
    }

    private void fillData() {
//      Capture todas as notas do BD e crie uma lista de itens
        Cursor c = mDbHelper.fetchAllNotes();
        startManagingCursor(c);
        String[] from = new String[]{NotesDbAdapter.KEY_TITLE};
        int[] to = new int[]{R.id.text1};
//      Agora crie um array adapter e configure-o para ser mostrado usando nossa linha
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.notes_row, c,
                        from, to);
        setListAdapter(notes);
    }
}
