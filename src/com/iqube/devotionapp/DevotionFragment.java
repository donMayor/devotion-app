package com.iqube.devotionapp;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.iqube.api.BaseSampleActivity;
import com.iqube.api.DataBaseHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by don_mayor on 5/23/14.
 */
public class DevotionFragment extends ListFragment {

    public String[] ListArray = { "Morning Prayer", "Intercession", "Concluding Prayer", "Entrance Antiphon", "Opening Prayer",
            "Suggested Prayer for the Faithful", "Prayer over Offering", "Communion Antiphon", "Prayer after Communion"
            , "Meditation of the day",  "Midday Prayer", "Evening Prayer_II", "Intercession_II" };



    public static String LIST_TITLE = "TITLE";
    public static String LIST_CONTENT = "CONTENT";
    private DatePickerDialog.OnDateSetListener date;
    private DataBaseHandler db;
    private ListView listview;

    private MainActivity mainactivity;
    //Calendar
    public static int Date_num=0;
    private Calendar myCalendar = Calendar.getInstance();
    private DateFormat fmtDateAndTime = new SimpleDateFormat("EEEE MMM dd, yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.devotion, container, false);
        Log.d("splendid", "enjoy");
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity(), "Number"+getID(fmtDateAndTime.format(myCalendar.getTime())), Toast.LENGTH_LONG).show();
        MainActivity.ArrayListHashMapList = SelectedDateList(MainActivity.All_DailyDevotions, getID(fmtDateAndTime.format(myCalendar.getTime())));

        MainActivity.devotion_adapter = new SimpleAdapter(getActivity(), MainActivity.ArrayListHashMapList,
                R.layout.devotion_list, new String[] { LIST_TITLE },
                new int[] { R.id.content_header });

        setListAdapter(MainActivity.devotion_adapter);
        getActivity().setTitle(MainActivity.All_DailyDevotions.get(Date_num).get(DataBaseHandler.KEY_DATE));

        listview = getListView();
        listview.setOnItemClickListener(new ListOnClick());
        setDateListener();

    }

    @Override
    public void onPause() {

        super.onPause();
        Log.d("sweet dream", "nightmare");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DataBaseHandler(getActivity());
        mainactivity = new MainActivity();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.findItem(R.id.action_settings).setVisible(!MainActivity.drawerOpen);

        menu.findItem(R.id.action_calendar).setVisible(!MainActivity.drawerOpen);
        menu.findItem(R.id.action_take_note).setVisible(!MainActivity.drawerOpen);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub

        //inflater.inflate(R.menu.daily_devotional, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (MainActivity.mDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_calendar:
            {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                return true;
            }


            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public ArrayList<HashMap<String, String>> SelectedDateList(ArrayList<HashMap<String, String>> arrayList, int i){


        ArrayList<HashMap<String, String>> arrayHashMap = new ArrayList<HashMap<String, String>>();

        Log.d("firstArrrayValue", ""+arrayList.get(i));

        HashMap<String, String> hashMap = arrayList.get(i);
            for(int k=2; k<hashMap.size()-2; k++){

                String value = hashMap.get(BaseSampleActivity.devotion_Key[k]);
                if(value.length()>1){
                    HashMap<String, String> tempHashMap = new HashMap<String, String>();

                    tempHashMap.put(LIST_TITLE, ListArray[k-2]);
                    tempHashMap.put(LIST_CONTENT, value);


                    arrayHashMap.add(tempHashMap);

                    Log.d("valueHashMap", tempHashMap+"");
                }
            }


        return arrayHashMap;
    }

    public int setDateListener(){

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {



                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                UpdateView();

            }

        };

        return  Date_num;
    }

    public String trimDate(String date){


        return date;
    }

    public int getID(String date){
        Cursor cursor = db.getSelectedRecord(DataBaseHandler.Devotion_Table_NAME, 1, new String[]{DataBaseHandler.KEY_ID, DataBaseHandler.KEY_DATE}, new String[]{date});
        if(cursor.getCount()!=0){
            Log.d("cursor", "" + cursor.getColumnName(0));
            cursor.moveToFirst();
            Date_num =new  Integer(cursor.getString(0)) -1;

            return Date_num;
        }
        else{
            return 0;
        }
    }

    private class ListOnClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity(), DailyDevotionalFragment.class);
            Bundle arg = new Bundle();
            arg.putInt(DailyDevotionalFragment.ListPosition, position);
            intent.putExtras(arg);
            startActivity(intent);

        }
    }

    public void UpdateView(){
        //Toast.makeText(getActivity(), fmtDateAndTime.format(myCalendar.getTime()), Toast.LENGTH_LONG ).show();
        MainActivity.ArrayListHashMapList = SelectedDateList(MainActivity.All_DailyDevotions, getID(fmtDateAndTime.format(myCalendar.getTime())));

        MainActivity.devotion_adapter = new SimpleAdapter(getActivity(), MainActivity.ArrayListHashMapList,
                R.layout.devotion_list, new String[] { LIST_TITLE },
                new int[] { R.id.content_header });

        setListAdapter(MainActivity.devotion_adapter);
        getActivity().setTitle(MainActivity.All_DailyDevotions.get(Date_num).get(DataBaseHandler.KEY_DATE));

    }
}