package com.mike4christ.tisvdigital.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.mike4christ.tisvdigital.C0506R;
import com.mike4christ.tisvdigital.Constant;
import com.mike4christ.tisvdigital.ProfileActivity;
import com.wang.avi.AVLoadingIndicatorView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.IOException;
import java.util.HashMap;

public class ProfileEditFragment extends Fragment implements OnClickListener {
    int PICK_IMAGE_REQUEST = 1;
    FirebaseUser auth;
    @BindView(2131296313)
    AVLoadingIndicatorView avi1;
    private ConnectivityManager connectivityManager;
    String designation;
    @BindView(2131296358)
    EditText designation_editxt;
    String firstname;
    @BindView(2131296394)
    EditText firstname_editxt;
    private Uri imageUri;
    @BindView(2131296417)
    TextInputLayout inputLayoutDesignationP;
    @BindView(2131296421)
    TextInputLayout inputLayoutFirstnameP;
    @BindView(2131296423)
    TextInputLayout inputLayoutLastnameP;
    @BindView(2131296425)
    TextInputLayout inputLayoutPhone_NumP;
    String lastname;
    @BindView(2131296435)
    EditText lastname_editxt;
    StorageTask mUploadTask;
    private NetworkInfo networkInfo;
    String phone_num;
    @BindView(2131296474)
    EditText phone_num_editxt;
    @BindView(2131296475)
    CircleImageView pick_photo;
    DatabaseReference reference;
    StorageReference storageReference;
    @BindView(2131296594)
    Button update_btn;

    /* renamed from: com.mike4christ.tisvdigital.fragment.ProfileEditFragment$1 */
    class C08141 implements OnFailureListener {
        C08141() {
        }

        public void onFailure(@NonNull Exception e) {
            Toast.makeText(ProfileEditFragment.this.getContext(), e.getMessage(), 0).show();
            ProfileEditFragment.this.avi1.setVisibility(8);
            ProfileEditFragment.this.update_btn.setVisibility(0);
        }
    }

    /* renamed from: com.mike4christ.tisvdigital.fragment.ProfileEditFragment$2 */
    class C08152 implements OnCompleteListener<Uri> {
        C08152() {
        }

        public void onComplete(@NonNull Task<Uri> task) {
            if (task.isSuccessful()) {
                String mUri = ((Uri) task.getResult()).toString();
                ProfileEditFragment.this.reference = FirebaseDatabase.getInstance().getReference(Constant.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ","));
                HashMap<String, Object> map = new HashMap();
                map.put("link", mUri);
                map.put("firstname", ProfileEditFragment.this.firstname);
                map.put("lastname", ProfileEditFragment.this.lastname);
                map.put("designation", ProfileEditFragment.this.designation);
                map.put("phone_num", ProfileEditFragment.this.phone_num);
                ProfileEditFragment.this.reference.updateChildren(map);
                ProfileEditFragment.this.avi1.setVisibility(8);
                Toast.makeText(ProfileEditFragment.this.getContext(), "Updated Successfully", 0).show();
                ProfileEditFragment profileEditFragment = ProfileEditFragment.this;
                profileEditFragment.startActivity(new Intent(profileEditFragment.getContext(), ProfileActivity.class));
                return;
            }
            Toast.makeText(ProfileEditFragment.this.getContext(), "Update not Failed", 0).show();
            ProfileEditFragment.this.avi1.setVisibility(8);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(C0506R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind((Object) this, fragmentView);
        Context context = getContext();
        getContext();
        this.connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.networkInfo = this.connectivityManager.getActiveNetworkInfo();
        setViewActions();
        return fragmentView;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), this.PICK_IMAGE_REQUEST);
    }

    private void setViewActions() {
        this.update_btn.setOnClickListener(this);
        this.pick_photo.setOnClickListener(this);
    }

    private void ValidateProfile() {
        NetworkInfo networkInfo = this.networkInfo;
        if (networkInfo != null && networkInfo.isAvailable() && this.networkInfo.isConnected()) {
            boolean isValid = true;
            if (this.firstname_editxt.getText().toString().isEmpty()) {
                this.inputLayoutFirstnameP.setError("First Name is required");
                isValid = false;
            } else {
                this.inputLayoutFirstnameP.setErrorEnabled(false);
            }
            if (this.lastname_editxt.getText().toString().isEmpty()) {
                this.inputLayoutLastnameP.setError("Last Name is required");
                isValid = false;
            } else {
                this.inputLayoutLastnameP.setErrorEnabled(false);
            }
            if (this.designation_editxt.getText().toString().isEmpty()) {
                this.inputLayoutDesignationP.setError("Designation is required");
                isValid = false;
            } else {
                this.inputLayoutDesignationP.setErrorEnabled(false);
            }
            if (this.phone_num_editxt.getText().toString().isEmpty()) {
                this.inputLayoutPhone_NumP.setError("Phone Number is required");
                isValid = false;
            } else {
                this.inputLayoutPhone_NumP.setErrorEnabled(false);
            }
            if (isValid) {
                StorageTask storageTask = this.mUploadTask;
                if (storageTask == null || !storageTask.isInProgress()) {
                    UploadProfile();
                } else {
                    Toast.makeText(getContext(), "Updating", 0).show();
                }
            }
            return;
        }
        showMessage("No Internet connection discovered!");
    }

    private void UploadProfile() {
        this.update_btn.setVisibility(4);
        this.avi1.setVisibility(0);
        this.firstname = this.firstname_editxt.getText().toString();
        this.lastname = this.lastname_editxt.getText().toString();
        this.designation = this.designation_editxt.getText().toString();
        this.phone_num = this.phone_num_editxt.getText().toString();
        this.storageReference = FirebaseStorage.getInstance().getReference("profile_photos");
        if (this.imageUri != null) {
            StorageReference fileReference = this.storageReference;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(System.currentTimeMillis());
            stringBuilder.append(".");
            stringBuilder.append(getFileExtension(this.imageUri));
            fileReference = fileReference.child(stringBuilder.toString());
            this.mUploadTask = fileReference.putFile(this.imageUri);
            this.mUploadTask.continueWithTask(new Continuation<TaskSnapshot, Task<Uri>>() {
                public Task<Uri> then(@NonNull Task<TaskSnapshot> task) throws Exception {
                    if (task.isSuccessful()) {
                        return fileReference.getDownloadUrl();
                    }
                    throw task.getException();
                }
            }).addOnCompleteListener(new C08152()).addOnFailureListener(new C08141());
            return;
        }
        Toast.makeText(getContext(), "Select Image", 0).show();
        this.avi1.setVisibility(8);
        this.update_btn.setVisibility(0);
    }

    private void showMessage(String s) {
        Toast.makeText(getContext(), s, 0).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0 || data == null || data.getData() == null) {
            Toast.makeText(getContext(), "No image is selected, try again", 0).show();
            return;
        }
        this.imageUri = data.getData();
        try {
            this.pick_photo.setImageBitmap(Media.getBitmap(getContext().getContentResolver(), this.imageUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContext().getContentResolver().getType(uri));
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C0506R.id.pick_photo) {
            chooseImage();
        } else if (id == C0506R.id.update_btn) {
            ValidateProfile();
        }
    }
}
