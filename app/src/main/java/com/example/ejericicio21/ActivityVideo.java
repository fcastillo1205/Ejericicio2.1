package com.example.ejericicio21;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ejericicio21.configuracion.SQLiteConexion;
import com.example.ejericicio21.configuracion.transacciones;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityVideo extends AppCompatActivity {
    VideoView videoView;
    String currentPhotoPath;
    Uri videoUri;
    static final int REQUEST_VIDEO_CAPTURE = 104;
    static final int PETICION_ACCESO_CAN = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = (VideoView) findViewById(R.id.VideoView);
        Button btnTomarVideo = (Button) findViewById(R.id.btnTomatVideo);
        Button btnGuardarVideo = (Button) findViewById(R.id.btnGuardarVideo);

        btnTomarVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });

        btnGuardarVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarVideo();
            }
        });
    }

    private void guardarVideo() {
        SQLiteConexion conexion = new SQLiteConexion(this, transacciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();


        String sql = "INSERT INTO tableVideo (video) VALUES(?)";
        SQLiteStatement insert = db.compileStatement(sql);
        insert.clearBindings();
        insert.bindString(1, videoUri.toString());
        Long resultado = insert.executeInsert();
        Toast.makeText(getApplicationContext(), "Video ingresado : Id " + resultado.toString(), Toast.LENGTH_LONG).show();
        db.close();
    }

    private byte[] imagemTratada(byte[] video) {
        while (video.length > 500000){
            Bitmap bitmap = BitmapFactory.decodeByteArray(video, 0, video.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            video = stream.toByteArray();
        }
        return video;
    }

    private void permisos(){
        //VALIDAR PERMISO QUE ESTA OTORGADO
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            //otorga el permiso si no lo tengo
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PETICION_ACCESO_CAN);
        }else{
            tomarvideo();
        }
    }
    @Override
    public void onRequestPermissionsResult(int RequestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(RequestCode, permissions, grantResults);

        if (RequestCode == PETICION_ACCESO_CAN){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                tomarvideo();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Se necesita el permiso de camara", Toast.LENGTH_LONG).show();
        }
    }


    private void tomarvideo() {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (videoIntent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(videoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK){
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
    }
}