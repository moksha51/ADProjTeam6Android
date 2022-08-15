package iss.team6.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.pytorch.IValue;
import org.pytorch.MemoryFormat;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class IdentifyActivity extends AppCompatActivity {

    int glassCount;
    int metalCount;
    int paperCount;
    int plasticCount;
    int points = 10;
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);

        Button selectbtn=findViewById(R.id.Select);
        // creating bitmap from packaged into app android asset 'image.jpg',
        // app/src/main/assets/image.jpg
//            Intent intent=getIntent();
//            String path=intent.getStringExtra("path");
        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, 200);
            }
        });


        Button returnBtn=findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IdentifyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Module module=null;
            try {
                module = Module.load(assetFilePath(this, "model.pt"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // showing image on UI
            ImageView imageView = findViewById(R.id.image);
            imageView.setImageBitmap(bitmap);

            // preparing input tensor
            final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(bitmap,
                    TensorImageUtils.TORCHVISION_NORM_MEAN_RGB, TensorImageUtils.TORCHVISION_NORM_STD_RGB, MemoryFormat.CHANNELS_LAST);

            // running the model
            final Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();

            // getting tensor content as java array of floats
            final float[] scores = outputTensor.getDataAsFloatArray();

            // searching for the index with maximum score
            float maxScore = -Float.MAX_VALUE;
            int maxScoreIdx = -1;
            for (int i = 0; i < scores.length; i++) {
                if (scores[i] > maxScore) {
                    maxScore = scores[i];
                    maxScoreIdx = i;
                }
            }
            System.out.println(maxScoreIdx);
            className = ImageNetClasses.IMAGENET_CLASSES[maxScoreIdx].toUpperCase();

            // showing className on UI
            TextView textView = findViewById(R.id.text);
            textView.setText("Trashified a piece of " + className);
            //showImage(imagePath);
            c.close();

            SharedPreferences pref = getSharedPreferences("trashTypeCount", Context.MODE_PRIVATE);
            if (className.toUpperCase().contains("GLASS")) {
                glassCount = pref.getInt("glassCount", glassCount);
                glassCount++;
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("glassCount", glassCount);
                editor.apply();
                Toast.makeText(this, "Glass Count Updated", Toast.LENGTH_SHORT).show();
                //postDataUsingVolley("glass", points, "glass", user);
            } else if (className.toUpperCase().contains("METAL")) {
                metalCount = pref.getInt("metalCount", metalCount);
                metalCount++;
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("metalCount", metalCount);
                editor.apply();
                Toast.makeText(this, "Metal Count Updated", Toast.LENGTH_SHORT).show();
                //postDataUsingVolley("metal", points, "metal", user);
            } else if (className.toUpperCase().contains("PAPER")) {
                paperCount = pref.getInt("paperCount", paperCount);
                paperCount++;
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("paperCount", paperCount);
                editor.apply();
                Toast.makeText(this, "Paper Count Updated", Toast.LENGTH_SHORT).show();
                //postDataUsingVolley("paper", points, "paper", user);
            } else if (className.toUpperCase().contains("PLASTIC")) {
                plasticCount = pref.getInt("plasticCount", plasticCount);
                plasticCount++;
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("plasticCount", plasticCount);
                editor.apply();
                Toast.makeText(this, "Plastic Count Updated", Toast.LENGTH_SHORT).show();
                //postDataUsingVolley("plastic", points, "plastic", user);
            } else {
                Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Copies specified asset to the file in /files app directory and returns this file absolute path.
     *
     * @return absolute file path
     */
    public static String assetFilePath(Context context, String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }

    private void postDataUsingVolley(String description, int points, String trashType, String user) {
        String url = "https://reqres.in/api/users";
        RequestQueue queue = Volley.newRequestQueue(IdentifyActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(IdentifyActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(IdentifyActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("desription", description);
                params.put("points", String.valueOf(points));
                params.put("trashType", trashType);
                return params;
            }
        };
        queue.add(request);
    }
}

