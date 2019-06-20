package com.mike4christ.tisvdigital.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.mike4christ.tisvdigital.Constant;
import com.mike4christ.tisvdigital.ProfileActivity;
import com.mike4christ.tisvdigital.R;
import com.wang.avi.AVLoadingIndicatorView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.IOException;
import java.util.HashMap;

public class ProfileEditFragment extends Fragment implements View.OnClickListener {
    int PICK_IMAGE_REQUEST = 1;
    FirebaseUser auth;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView avi1;
    private ConnectivityManager connectivityManager;
    String designation;
    @BindView(R.id.designation_editxt)
    EditText designation_editxt;
    String firstname;
    @BindView(R.id.firstname_editxt)
    EditText firstname_editxt;
    private Uri imageUri;
    @BindView(R.id.inputLayoutDesignationP)
    TextInputLayout inputLayoutDesignationP;
    @BindView(R.id.inputLayoutFirstnameP)
    TextInputLayout inputLayoutFirstnameP;
    @BindView(R.id.inputLayoutLastnameP)
    TextInputLayout inputLayoutLastnameP;
    @BindView(R.id.inputLayoutPhone_NumP)
    TextInputLayout inputLayoutPhone_NumP;
    String lastname;
    @BindView(R.id.lastname_editxt)
    EditText lastname_editxt;
    StorageTask mUploadTask;
    private NetworkInfo networkInfo;
    String phone_num;
    @BindView(R.id.phone_num_editxt)
    EditText phone_num_editxt;
    @BindView(R.id.pick_photo)
    CircleImageView pick_photo;
    DatabaseReference reference;
    StorageReference storageReference;
    @BindView(R.id.update_btn)
    Button update_btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind( this, fragmentView);
        Context context = getContext();
        getContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        setViewActions();
        return fragmentView;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void setViewActions() {
        update_btn.setOnClickListener(this);
        pick_photo.setOnClickListener(this);
    }

    private void ValidateProfile() {

        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            boolean isValid = true;
            if (firstname_editxt.getText().toString().isEmpty()) {
                inputLayoutFirstnameP.setError("First Name is required");
                isValid = false;
            } else {
                inputLayoutFirstnameP.setErrorEnabled(false);
            }
            if (lastname_editxt.getText().toString().isEmpty()) {
                inputLayoutLastnameP.setError("Last Name is required");
                isValid = false;
            } else {
                inputLayoutLastnameP.setErrorEnabled(false);
            }
            if (designation_editxt.getText().toString().isEmpty()) {
                inputLayoutDesignationP.setError("Designation is required");
                isValid = false;
            } else {
                inputLayoutDesignationP.setErrorEnabled(false);
            }
            if (phone_num_editxt.getText().toString().isEmpty()) {
                inputLayoutPhone_NumP.setError("Phone Number is required");
                isValid = false;
            } else {
                inputLayoutPhone_NumP.setErrorEnabled(false);
            }
            if (isValid) {
                StorageTask storageTask = mUploadTask;
                if (storageTask == null || !storageTask.isInProgress()) {
                    UploadProfile();
                } else {
                    Toast.makeText(getContext(), "Updating", Toast.LENGTH_SHORT).show();
                }
            }
            return;
        }
        showMessage("No Internet connection discovered!");
    }

    private void UploadProfile() {
        update_btn.setVisibility(View.INVISIBLE);
        avi1.setVisibility(View.VISIBLE);
        firstname = firstname_editxt.getText().toString();
        lastname = lastname_editxt.getText().toString();
        designation = designation_editxt.getText().toString();
        phone_num = phone_num_editxt.getText().toString();
        storageReference = FirebaseStorage.getInstance().getReference("profile_photos");

        if (imageUri != null) {
            StorageReference fileReference = storageReference;

            fileReference = fileReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

            mUploadTask = fileReference.putFile(imageUri);


            final StorageReference finalFileReference = fileReference;
            mUploadTask.continueWithTask(new Continuation<TaskSnapshot, Task<Uri>>() {
                public Task<Uri> then(@NonNull Task<TaskSnapshot> task) throws Exception {
                    if (task.isSuccessful()) {
                        return finalFileReference.getDownloadUrl();
                    }
                    throw task.getException();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                                         @Override
                                         public void onComplete(@NonNull Task task) {
                                             if (task.isSuccessful()) {
                                                 String mUri = (task.getResult()).toString();
                                                 reference = FirebaseDatabase.getInstance().getReference(Constant.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ","));
                                                 HashMap<String, Object> map = new HashMap();
                                                 map.put("link", mUri);
                                                 map.put("firstname", firstname);
                                                 map.put("lastname", lastname);
                                                 map.put("designation", designation);
                                                 map.put("phone_num", phone_num);
                                                reference.updateChildren(map);
                                                avi1.setVisibility(View.GONE);
                                                 Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                                 ProfileEditFragment profileEditFragment = ProfileEditFragment.this;
                                                 profileEditFragment.startActivity(new Intent(profileEditFragment.getContext(), ProfileActivity.class));
                                                 return;
                                             }
                                             Toast.makeText(getContext(), "Update not Failed", Toast.LENGTH_SHORT).show();
                                             avi1.setVisibility(View.GONE);
                                             
                                             
                                         }
                                     }
            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                   avi1.setVisibility(View.GONE);
                   update_btn.setVisibility(View.GONE);
                }
            });
            return;
        }
        Toast.makeText(getContext(), "Select Image", Toast.LENGTH_SHORT).show();
        avi1.setVisibility(View.GONE);
        update_btn.setVisibility(View.VISIBLE);
    }

    private void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0 || data == null || data.getData() == null) {
            Toast.makeText(getContext(), "No image is selected, try again", Toast.LENGTH_SHORT).show();
            return;
        }
        imageUri = data.getData();
        try {
            pick_photo.setImageBitmap(Media.getBitmap(getContext().getContentResolver(), imageUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContext().getContentResolver().getType(uri));
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.pick_photo) {
            chooseImage();
        } else if (id == R.id.update_btn) {
            ValidateProfile();
        }
    }
}
