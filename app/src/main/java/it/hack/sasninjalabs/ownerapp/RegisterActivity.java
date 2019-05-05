package it.hack.sasninjalabs.ownerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import it.hack.sasninjalabs.ownerapp.model.Owner;
import it.hack.sasninjalabs.ownerapp.model.Vehicle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.nic)
    public TextView nic;

    @BindView(R.id.phone)
    public TextView phone;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        user = FirebaseAuth
                .getInstance()
                .getCurrentUser();
    }

    @OnClick(R.id.save_btn)
    public void save(){
        final Owner owner = new Owner();
        owner.setEmail(user.getEmail());
        owner.setName(user.getDisplayName());
        owner.setNicNumber(nic.getText().toString());
        owner.setPhoneNumber(phone.getText().toString());
        owner.setPhotoURL(user.getPhotoUrl().toString());
        owner.setUID(user.getUid());
        owner.setOwnedVehicles(new ArrayList<Vehicle>());

        FirebaseDatabase.getInstance().getReference("owners").child(user
                                                        .getUid()).setValue(owner, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError.getCode() > 0)
                    MainActivity.owner = owner;
                    finish();
            }
        });
    }
}
