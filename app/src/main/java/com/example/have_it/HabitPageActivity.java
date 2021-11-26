package com.example.have_it;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *This is the activity for main page of viewing habits
 * @author yulingshen
 */
public class HabitPageActivity extends AppCompatActivity implements  FirestoreGetCollection, DatabaseUserReference{
    /**
     *A reference to today habit list view, of class {@link ListView}
     */
    ListView todayHabitList;
    /**
     *A reference to all habit list view, of class {@link ListView}
     */
    ListView habitList;
    /**
     *habit adapter, of class {@link HabitList}
     */
    HabitList habitAdapter;
    /**
     *today habit adapter, of class {@link HabitList}
     */
    HabitList todayHabitAdapter;
    /**
     *habit data list, of class {@link ArrayList}
     */
    ArrayList<Habit> habitDataList;
    /**
     *today data list, of class {@link ArrayList}
     */
    ArrayList<Habit> todayHabitDataList;
    /**
     * Reference to the bottom navigation menu, of class {@link BottomNavigationView}
     */
    BottomNavigationView bottomNavigationView;
    /**
     *The extra message used for intent, of class {@link String}
     */
    public static final String EXTRA_MESSAGE = "com.example.have_it.MESSAGE";

    /**
     *This is the method invoked when the activity starts
     * @param savedInstanceState {@link Bundle} used for its super class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_homepage);

        habitList = findViewById(R.id.all_habit_list);
        todayHabitList = findViewById(R.id.today_habit_list);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.habit_menu);

        final FloatingActionButton addHabitButton = findViewById(R.id.add_habit_button);
        final Intent addHabitIntent = new Intent(this, AddHabitActivity.class);
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addHabitIntent);
            }
        });

        habitDataList = new ArrayList<>();
        habitAdapter = new HabitList(this, habitDataList);
        todayHabitDataList = new ArrayList<>();
        todayHabitAdapter = new HabitList(this, todayHabitDataList);

        habitList.setAdapter(habitAdapter);
        todayHabitList.setAdapter(todayHabitAdapter);

        getCollection();

        final Intent viewEditHabitIntent = new Intent(this, ViewEditHabitActivity.class);
        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                viewEditHabitIntent.putExtra("habit", habitDataList.get(position).getTitle());

                startActivity(viewEditHabitIntent);
            }
        });
        todayHabitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                viewEditHabitIntent.putExtra("habit", todayHabitDataList.get(position).getTitle());

                startActivity(viewEditHabitIntent);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.habit_menu:
                        break;

                    case R.id.following_menu:
                        final Intent followingIntent = new Intent(HabitPageActivity.this, FollowingPageActivity.class);
                        startActivity(followingIntent);
                        break;

                    case R.id.account_menu:
                        final Intent accountIntent = new Intent(HabitPageActivity.this, AccountPageActivity.class);
                        startActivity(accountIntent);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * This is the method for getting a reference to collection
     */
    @Override
    public void getCollection() {

        final CollectionReference habitListReference = db.collection("Users")
                .document(logged.getUID()).collection("HabitList");
        HabitController.getCollectionHabit(habitListReference,habitDataList,habitAdapter,todayHabitDataList,todayHabitAdapter);

    }
}
