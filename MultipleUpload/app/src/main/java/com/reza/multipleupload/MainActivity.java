package com.reza.multipleupload;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.reza.multipleupload.adapter.ImageAdapter;
import com.reza.multipleupload.getterSetter.ListImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.reza.multipleupload.Api.URL_UPLOAD;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ListImage> listImage;
    private ImageAdapter adapter;
    @BindView(R.id.list_image)
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //RecycleVIew
        loadingDialog = new LoadingDialog(MainActivity.this);
        listImage = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new GridLayoutManager(MainActivity.this,2);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        //Check Permission
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();

    }

    @OnClick(R.id.btnChoose) void btnChoose(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }


    @OnClick(R.id.btnUpload) void btnUpload(){
        loadingDialog.startLoadingDialog();
        prosesUpload();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            //add to List
            File imageFile = new File(picturePath);
            ListImage imageData = new ListImage();
            imageData.setPicturePath(picturePath);
            imageData.setImagFile(imageFile);
            listImage.add(imageData);
            recyclerViewadapter = new ImageAdapter(listImage, getApplicationContext());
            recyclerView.setAdapter(recyclerViewadapter);
            recyclerViewadapter.notifyDataSetChanged();
        }


    }


    private void prosesUpload(){
        ANRequest.MultiPartBuilder getMultipartBuilder = new ANRequest.MultiPartBuilder(URL_UPLOAD);
        for (int i=0; i<listImage.size(); i++) {
            getMultipartBuilder.addMultipartFile("file"+i,listImage.get(i).getImagFile());
        }

        //POST COUNT IMAGES
        getMultipartBuilder.addMultipartParameter("count", String.valueOf(listImage.size()));

        ANRequest anRequest = getMultipartBuilder.build();
        anRequest.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                loadingDialog.dismissDialog();
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onError(ANError error) {
                loadingDialog.dismissDialog();
                Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_LONG).show();
            }
        });
    }
}
