package iss.team6.android;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity implements ImageAnalysis.Analyzer, View.OnClickListener {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private PreviewView previewView;
    private ImageCapture imageCapture;
    private Button btn_TakePhoto;
    private Button btn_Trashify;
    private final static int SELECT_PICTURE = 200;
    int glassCount;
    int metalCount;
    int paperCount;
    int plasticCount;
    int points = 10;
    String trashType = "";
    String description;
    String user;
    SharedPreferences pref;
    MainActivity mainActivity;
    private static final String apiendpoint = "https://filesamples.com/samples/code/json/sample2.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        previewView = findViewById(R.id.previewView);
        btn_TakePhoto = findViewById(R.id.btn_camera_take_photo);
        btn_Trashify = findViewById(R.id.btn_camera_trashify);

        btn_TakePhoto.setOnClickListener(this);
        btn_Trashify.setOnClickListener(this);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());

    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    @SuppressLint("RestrictedApi")
    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        Preview preview = new Preview.Builder()
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // Image capture use case
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        // Image analysis use case
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(getExecutor(), CameraActivity.this);

        //bind to lifecycle:
        cameraProvider.bindToLifecycle((LifecycleOwner) CameraActivity.this, cameraSelector, preview, imageCapture);
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        // image processing here for the current frame
        Log.d("TAG", "analyze: got the frame at: " + image.getImageInfo().getTimestamp());
        image.close();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera_take_photo:
                capturePhoto();
                break;
            case R.id.btn_camera_trashify:
                trashify();
             break;

        }
    }

    private void capturePhoto() {
        long timestamp = System.currentTimeMillis();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        imageCapture.takePicture(
            new ImageCapture.OutputFileOptions.Builder(
                    getContentResolver(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
            ).build(),
            getExecutor(),
            new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    Toast.makeText(CameraActivity.this, "Photo has been saved successfully.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    Toast.makeText(CameraActivity.this, "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        );
    }

    private void trashify() {
        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);
        selectImageIntent.setType("image/*");
        startActivityForResult(selectImageIntent, SELECT_PICTURE);

        pref = getSharedPreferences("trashTypeCount", Context.MODE_PRIVATE);
        if (trashType.toLowerCase().contains("glass")) {
            glassCount = pref.getInt("glassCount", glassCount);
            glassCount++;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("glassCount", glassCount);
            editor.apply();
            Toast.makeText(this, "Count Updated", Toast.LENGTH_SHORT).show();
            postDataUsingVolley("glass", points, "glass", user);
        } else if (trashType.toLowerCase().contains("metal")) {
            metalCount = pref.getInt("metalCount", metalCount);
            metalCount++;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("metalCount", metalCount);
            editor.apply();
            Toast.makeText(this, "Count Updated", Toast.LENGTH_SHORT).show();
            postDataUsingVolley("metal", points, "metal", user);
        } else if (trashType.toLowerCase().contains("paper")) {
            paperCount = pref.getInt("paperCount", paperCount);
            paperCount++;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("paperCount", paperCount);
            editor.apply();
            Toast.makeText(this, "Count Updated", Toast.LENGTH_SHORT).show();
            postDataUsingVolley("paper", points, "paper", user);
        } else if (trashType.toLowerCase().contains("plastic")) {
            plasticCount = pref.getInt("plasticCount", plasticCount);
            plasticCount++;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("plasticCount", plasticCount);
            editor.apply();
            Toast.makeText(this, "Count Updated", Toast.LENGTH_SHORT).show();
            postDataUsingVolley("plastic", points, "plastic", user);
        } else {
            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void postDataUsingVolley(String description, int points, String trashType, String user) {
        String url = "https://reqres.in/api/users";
        RequestQueue queue = Volley.newRequestQueue(CameraActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, apiendpoint, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(CameraActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(CameraActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("desription", description);
                params.put("points", String.valueOf(points));
                params.put("trashType", trashType);
                params.put("user", user);
                return params;
            }
        };
        queue.add(request);
    }
}