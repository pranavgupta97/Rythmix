package com.example.pranav.karaoke;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class groupSelectionActivity extends AppCompatActivity {

    private SearchView searchBar;
    private TextView result;
    private DatabaseReference databaseRef;
    private RecyclerView recyclerView;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selection);

        searchBar = (SearchView) findViewById(R.id.searchBarCreateGroup);
        result = (TextView) findViewById(R.id.usernameDisplay);
        recyclerView = (RecyclerView) findViewById(R.id.groupSelectionRecyclerView);
        submitButton = (Button) findViewById(R.id.submitList);
        final ArrayList<String> usersList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                //databaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                //Query usernameQuery = databaseRef.orderByValue().equalTo(query);

                Query usernameQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").equalTo(query);
                usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0) {
                            result.setText(query);
                            result.setVisibility(View.VISIBLE);
                            result.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (usersList.contains(query)) {
                                        Toast.makeText(groupSelectionActivity.this, "This user is already in the session!", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        usersList.add(query + "  ");
                                        String[] array = new String[usersList.size()];
                                        usersList.toArray(array);
                                        recyclerView.setAdapter(new VerticalAdapter(array));
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(groupSelectionActivity.this, "This username does not exist. Choose a different username.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        databaseRef = FirebaseDatabase.getInstance().getReference().child("Karaoke_Session").child(currentUser.getUid()).child("Session_Users");
                        HashMap<String, String> userMap = new HashMap<>();
                        for(int i = 0; i<usersList.size(); i++){
                            userMap.put("user" + i, usersList.get(i));
                        }

                        databaseRef.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(groupSelectionActivity.this, "Users added to session", Toast.LENGTH_LONG).show();
                                    Intent youtubeIntent = new Intent(groupSelectionActivity.this, youtubeSearchActivity.class);
                                    youtubeIntent.putStringArrayListExtra("USERS", usersList);
                                    startActivity(youtubeIntent);
                                    finish();
                                }
                            }
                        });
                    }
                });



                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


}
