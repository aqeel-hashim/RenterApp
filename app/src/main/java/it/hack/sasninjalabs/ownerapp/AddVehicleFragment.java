package it.hack.sasninjalabs.ownerapp;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import it.hack.sasninjalabs.ownerapp.model.ODBDataSnapshot;
import it.hack.sasninjalabs.ownerapp.model.Owner;
import it.hack.sasninjalabs.ownerapp.model.Vehicle;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddVehicleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddVehicleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CURRENT_OWNER = "CURRENT_OWNER";
    public static final int PICK_IMAGE = 1;
    public static final int PLACE_PICKER_REQUEST  = 4;


    // TODO: Rename and change types of parameters
    private Owner owner;

    @BindView(R.id.vin_input)
    public EditText vin;

    @BindView(R.id.number_plate_input)
    public EditText numberPlate;

    @BindView(R.id.description_input)
    public EditText description;

    @BindView(R.id.model_input)
    public EditText model;

    @BindView(R.id.model_year_input)
    public EditText modelYear;

    @BindView(R.id.body_type_input)
    public EditText bodyType;

    @BindView(R.id.fuel_type_input)
    public EditText fuelType;

    @BindView(R.id.engine_capacity_type_input)
    public EditText engineCapacity;

    @BindView(R.id.radius_input)
    public EditText radius;

    @BindView(R.id.browse_image_url)
    public TextView browseImageUrl;

    private String picturePath;

    private double lon, lat;

    public AddVehicleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param owner Current Owner.
     * @return A new instance of fragment AddVehicleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddVehicleFragment newInstance(Owner owner) {
        AddVehicleFragment fragment = new AddVehicleFragment();
        fragment.owner = owner;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_vehicle, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.browse_image)
    public void browseImage(){
        Toast.makeText(getContext(), "Browse Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @OnClick(R.id.getfencecenter)
    public void fenceCenter(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            //TODO: action
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            browseImageUrl.setText(picturePath);


        }

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getContext());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();
                lon = place.getLatLng().longitude;
                lat = place.getLatLng().latitude;
            }
        }
    }

    @OnClick(R.id.btn_add_vehicle)
    public void addVechicle(){
        final Vehicle vehicle = new Vehicle();
        vehicle.setVinNumber(vin.getText().toString());
        vehicle.setNumberPlate(numberPlate.getText().toString());
        vehicle.setDescription(description.getText().toString());
        vehicle.setModel(model.getText().toString());
        vehicle.setModelYear(modelYear.getText().toString());
        vehicle.setFuelType(fuelType.getText().toString());
        vehicle.setEngineCapacity(engineCapacity.getText().toString());
        Uri file = Uri.fromFile(new File(picturePath));
        StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("images/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+vin.getText().toString()+"/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                vehicle.setImageUrl(downloadUrl.toString());
                vehicle.setYellowIssues(0);
                vehicle.setRedIssues(0);
                vehicle.setGreenIssues(0);
                vehicle.setGeoFenceCenterLongitude(lon);
                vehicle.setGeoFenceCenterLatitude(lat);
                vehicle.setRadiusMeters(Double.parseDouble(radius.getText().toString()));
                FirebaseDatabase.getInstance().getReference("owners").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        owner = dataSnapshot.getValue(Owner.class);
                        FirebaseDatabase.getInstance().getReference("vehicles").child(vehicle.getVinNumber()).setValue(vehicle,
                                new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        if(databaseError == null || databaseError.getCode() > 0){

                                            vehicle.setDataSnapShots(new ArrayList<ODBDataSnapshot>());
                                            if(owner.getOwnedVehicles() == null)
                                                owner.setOwnedVehicles(new ArrayList<Vehicle>());
                                            owner.getOwnedVehicles().add(vehicle);
                                            FirebaseDatabase.getInstance().getReference("owners")
                                                    .child(owner.getUID()).child("ownedVehicles")
                                                    .setValue(owner.getOwnedVehicles())
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {

                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            System.out.println("success bitch");
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    System.out.println("Faiuled : "+e.toString());
                                                }
                                            });
                                            getActivity().getSupportFragmentManager().beginTransaction().remove(AddVehicleFragment.this).commit();
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("ERROR NIGGa "+databaseError.getMessage());
                    }
                });


            }
        });
    }

}
