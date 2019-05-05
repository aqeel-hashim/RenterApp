package it.hack.sasninjalabs.ownerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import it.hack.sasninjalabs.ownerapp.model.DTCDescription;
import it.hack.sasninjalabs.ownerapp.model.DistanceAlert;
import it.hack.sasninjalabs.ownerapp.model.FaultAlert;
import it.hack.sasninjalabs.ownerapp.model.Owner;
import it.hack.sasninjalabs.ownerapp.model.Vehicle;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Arrays;


import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN =
            2437;
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.root)
    public View mRootView;

    @BindView(R.id.car_list)
    public RecyclerView carList;
    public static Owner owner ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null){
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                            .build(),
                    RC_SIGN_IN);
        }else{

            Query query = FirebaseDatabase.getInstance()
                    .getReference("owners")
                    .child(FirebaseAuth
                            .getInstance()
                            .getCurrentUser()
                            .getUid())
                    .child("ownedVehicles");

            FirebaseRecyclerOptions<Vehicle> options =
                    new FirebaseRecyclerOptions.Builder<Vehicle>()
                            .setQuery(query, Vehicle.class)
                            .build();

            FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Vehicle, VehicleHolder>(options){

                @Override
                public void onDataChanged() {
                    super.onDataChanged();
                    Toast.makeText(MainActivity.this, "New Cars Loading", Toast.LENGTH_SHORT).show();
                }

                @Override
                public VehicleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return new VehicleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.car_element, parent, false));
                }

                @Override
                protected void onBindViewHolder(@NonNull VehicleHolder holder, int position, @NonNull Vehicle model) {
                    holder.bind(model);
                }
            };

            carList.setLayoutManager(new LinearLayoutManager(this));
            carList.setAdapter(adapter);

            FirebaseDatabase.getInstance().getReference().child("faultAlerts").limitToFirst(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FaultAlert faultAlert = dataSnapshot.getValue(FaultAlert.class);
                    String code = faultAlert.getDtcCode();
                    FirebaseDatabase.getInstance().getReference("dtcDescriptions").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            DTCDescription dtc = dataSnapshot.getValue(DTCDescription.class);
                            AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                            } else {
                                builder = new AlertDialog.Builder(MainActivity.this);
                            }
                            final AlertDialog dialog = builder.setTitle("Error occured: "+dtc.getDtcCode())
                                    .setMessage(dtc.getDescription())
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .create();

                            dialog.show();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 100ms
                                    dialog.dismiss();
                                }
                            }, 3000);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            FirebaseDatabase.getInstance().getReference("distanceAlerts").limitToFirst(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DistanceAlert faultAlert = dataSnapshot.getValue(DistanceAlert.class);
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(MainActivity.this);
                    }
                    final AlertDialog dialog = builder.setTitle("Driver left range")
                            .setMessage("The user has exceeded by: "+faultAlert.getExceededDistance()+"m")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .create();

                    dialog.show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            dialog.dismiss();
                        }
                    }, 3000);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                showSnackbar(R.string.sign_is_success);
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                //startActivity(SignedInActivity.createIntent(this, response));
                //finish();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                showSnackbar(R.string.unknown_error);
                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
    }

    private void showSnackbar(@StringRes int errorMessageRes) {
        Snackbar.make(mRootView, errorMessageRes, Snackbar.LENGTH_LONG).show();
    }

    public class VehicleHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.price)
        public TextView price;

        @BindView(R.id.onHire)
        public TextView onhire;

        @BindView(R.id.car_image)
        public ImageView carImage;

        @BindView(R.id.open_price)
        public TextView price_open;

        @BindView(R.id.description_open)
        public TextView desc_oprn;

        @BindView(R.id.car_image_open)
        public ImageView carImageOpen;

        public VehicleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Vehicle model){
            price.setText("Rs. 200 / hr");

            onhire.setText("Yes");

            Picasso.get().load(model.getImageUrl()).into(carImage);

            price_open.setText("Rs. 200 / hr");

            desc_oprn.setText(model.getDescription());

            Picasso.get().load(model.getImageUrl()).into(carImageOpen);
        }
    }

    @OnClick(R.id.add_btn)
    public void addVehicle(){
        getSupportFragmentManager().beginTransaction().add(R.id.root, AddVehicleFragment.newInstance(owner)).addToBackStack(null).commit();
    }
}
