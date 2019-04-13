package com.example.dojo.mapapp03;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 航空写真に変更
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // 渋滞状況を表示
//        mMap.setTrafficEnabled(true);
        // 現在の位置情報を表示
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 1);
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //函館にピンを立てる
        LatLng hakodate = new LatLng(41.7737838, 140.7262966);
        mMap.addMarker(new MarkerOptions().position(hakodate).title("函館"));
        //カメラを函館に移動する。
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hakodate, 11));



        //地図に線を引く(バンコク―函館)    直線・測地線
        PolylineOptions opt = new PolylineOptions();
        opt.add(new LatLng(13.754214, 100.493025),hakodate);
        opt.geodesic(true);
        opt.color(Color.BLUE);
        opt.width(3);
        mMap.addPolyline(opt);

        opt.geodesic(false);
        opt.color(Color.MAGENTA);
        mMap.addPolyline(opt);

        //地図に図形(ポリゴン)を描く
        PolygonOptions poly = new PolygonOptions();
        poly.add(new LatLng(34.659526,135.57592), new LatLng(35.010362,135.768735), new LatLng(34.669835,135.163283));
        poly.strokeColor(Color.BLUE);
        poly.strokeWidth(4);
        poly.fillColor(Color.argb(0x80,0x00,0xff,0xff));
        mMap.addPolygon(poly);

        //地図に円を描く
        CircleOptions cir = new CircleOptions();
        cir.center(new LatLng(35.681401, 139.767211));
        cir.strokeColor(Color.rgb(0xff,0x80,0x00));
        cir.strokeWidth(2);
        cir.fillColor(Color.argb(0x80,0xff,0xff,0x00));
        cir.radius(20000);  //20km
        mMap.addCircle(cir);

        //InfoWindowをカスタマイズ
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.info_window,null);
                //タイトル設定
                TextView title = (TextView)view.findViewById(R.id.info_title);
                title.setText(marker.getTitle());
                //画像設定
                ImageView img = (ImageView)view.findViewById(R.id.info_image);
                img.setImageResource(R.drawable.common_google_signin_btn_icon_dark);
                return view;
            }
        });

        LatLng p1 = new LatLng(41.7737838, 140.7262966);
        MarkerOptions p1opt = new MarkerOptions();
        p1opt.position(p1);
        p1opt.title("函館");
        // マップにマーカー追加
        Marker p1m = mMap.addMarker(p1opt);
        p1m.showInfoWindow();
    }
}
