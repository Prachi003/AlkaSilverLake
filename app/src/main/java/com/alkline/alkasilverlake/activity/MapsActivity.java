package com.alkline.alkasilverlake.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alkline.alkasilverlake.Constant;
import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.Session;
import com.alkline.alkasilverlake.base.Alkasilverlake;
import com.alkline.alkasilverlake.helper.PDialog;
import com.alkline.alkasilverlake.roomdatabase.AppDataManager;
import com.alkline.alkasilverlake.roomdatabasemodel.UserInfoModel;
import com.alkline.alkasilverlake.utils.AddressLocationTask;
import com.alkline.alkasilverlake.utils.OnSwipeListener;
import com.alkline.alkasilverlake.utils.Permission;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Objects;

import static com.alkline.alkasilverlake.Constant.MY_PERMISSIONS_REQUEST_LOCATION;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback, View.OnTouchListener, View.OnClickListener {

    private GoogleMap mMap;
    private LinearLayout llPickUp;
    private LinearLayout llDelivery;
    private GestureDetector detector;
    private ImageView ivPickUp;
    private ImageView ivDelivery;
    private TextView txtDelivery;
    private TextView txtPickUp;
    private Session session;
    private TextView txtAddress;
    boolean isChangedAddress = false;
    private Handler handler = new Handler(Looper.myLooper());
    private String latitudeBilling = "";
    private String longitudeBilling = "";
    private String addressBilling = "";
    private PDialog pDialog;
    boolean isShowViewAddress = false;
    private TextView txtCurrentAddress;
    private UserInfoModel localUserInfo;
    private String isSelect = "";
    private RelativeLayout rlAddressView;
    private TextView txtAddressDelivery;
    private LinearLayout llViewAddress;
    protected FusedLocationProviderClient mFusedLocationClient;
    protected LocationRequest locationRequest;


    @NonNull
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);

                setLatLng(location);
            }
        }
    };


    public MapsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        initView();
        if (session.isLoggedIn()) {
            new Thread(() -> {
                UserInfoModel userInfoModel = dataManager().userinfo(session.getRegistration().getId());
                localUserInfo = dataManager().userinfo(session.getRegistration().getId());
                addressBilling = userInfoModel.getDeliveryAddress();

                handler.post(() -> {
                    txtAddressDelivery.setText(addressBilling);
                    // txtAddress.setText(addressBilling);
                });

            }).start();

        }



    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        llPickUp = findViewById(R.id.llPickUp);
        txtAddressDelivery = findViewById(R.id.txtAddressDelivery);
        txtCurrentAddress = findViewById(R.id.txtCurrentAddress);
        llViewAddress = findViewById(R.id.llViewAddress);
        llViewAddress = findViewById(R.id.llViewAddress);
        RelativeLayout rlAddress = findViewById(R.id.rlAddress);
        RelativeLayout rlClose = findViewById(R.id.rlClose);

        txtAddressDelivery.setOnClickListener(this);
        txtCurrentAddress.setOnClickListener(this);
        txtAddress = findViewById(R.id.txtAddress);
        rlAddressView = findViewById(R.id.rlAddressView);
        ivPickUp = findViewById(R.id.ivPickUp);
        txtPickUp = findViewById(R.id.txtPickUp);
        llDelivery = findViewById(R.id.llDelivery);
        ivDelivery = findViewById(R.id.ivDelivery);

        txtDelivery = findViewById(R.id.txtDelivery);
        llPickUp.setOnClickListener(this);
        llDelivery.setOnClickListener(this);
        session = new Session(MapsActivity.this);
        txtAddress.setText(session.getAddress());
        pDialog = new PDialog();
        rlAddress.setOnClickListener(this);
        rlClose.setOnClickListener(this);
        findViewById(R.id.rlParent).setOnTouchListener(this);
        findViewById(R.id.rlParent).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Toast.makeText(this, "Down", Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        detector = new GestureDetector(MapsActivity.this, new OnSwipeListener() {

            @Override
            public boolean onSwipe(Direction direction) {

                if (direction == Direction.down) {
                    Toast.makeText(MapsActivity.this, "Down", Toast.LENGTH_SHORT).show();

                }

                return true;
            }


        });


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

        handlePermissionTask();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            setupMap();
            return;
        }
        //this.mMap.setMyLocationEnabled(true);
        setupMap();



        // Add a marker in Sydney and move the camera

    }


        private void setupMap() {
            if (Alkasilverlake.LATITUDE != 0.0) {
                updateMapUI();
            } else {
                updateLocation();
            }
        }

    private void updateMapUI() {
        @SuppressLint("InflateParams") View markerView = LayoutInflater.from(this).inflate(R.layout.custom_marker_layout, null);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        markerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        markerView.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        markerView.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        markerView.buildDrawingCache();
        Bitmap finalBitmap = Bitmap.createBitmap(markerView.getMeasuredWidth(), markerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(finalBitmap);
        markerView.draw(canvas);
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng point;
        double newLat = Alkasilverlake.LATITUDE;
        double newLng = Alkasilverlake.LONGITUDE;
        point = new LatLng(newLat, newLng);

        markerOptions.position(point);

        mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromBitmap(finalBitmap)));

        @SuppressLint("InflateParams") View markerViewPickUp = LayoutInflater.from(this).inflate(R.layout.custom_marker_layout_new, null);
        DisplayMetrics displayMetricsPickUp = new DisplayMetrics();

        markerViewPickUp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        markerViewPickUp.measure(displayMetricsPickUp.widthPixels, displayMetricsPickUp.heightPixels);
        markerViewPickUp.layout(0, 0, displayMetricsPickUp.widthPixels, displayMetricsPickUp.heightPixels);
        markerViewPickUp.buildDrawingCache();
        Bitmap finalBitmapPickUp = Bitmap.createBitmap(markerViewPickUp.getMeasuredWidth(), markerViewPickUp.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasPickUp = new Canvas(finalBitmapPickUp);
        markerViewPickUp.draw(canvasPickUp);
        MarkerOptions markerOptionsPickUp = new MarkerOptions();
        LatLng pointPickUp;
        double newLatPickUp = Double.valueOf(session.getLatitude());
        double newLngPickUp = Double.valueOf(session.getLongitude());
        pointPickUp = new LatLng(newLatPickUp, newLngPickUp);

        markerOptionsPickUp.position(pointPickUp);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(pointPickUp, 13);

        mMap.animateCamera(cameraUpdate);
/*
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
*/
        mMap.addMarker(markerOptionsPickUp.icon(BitmapDescriptorFactory.fromBitmap(finalBitmapPickUp)));


    }


    private void handlePermissionTask() {
        new Thread(() -> {
            final boolean isPermissionAllow = Permission.checkLocationPermission(MapsActivity.this);

            this.runOnUiThread(() -> {
                if (isPermissionAllow && Alkasilverlake.LATITUDE != 0.0) {
                    updateLocation();
                    updateMapUI();
                }
            });
        }).start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llDelivery:
                llPickUp.setBackgroundResource(R.drawable.button_pickup_placeholder);
                ivPickUp.setColorFilter(ContextCompat.getColor(MapsActivity.this, R.color.black_new));
                txtPickUp.setTextColor(ContextCompat.getColor(MapsActivity.this, R.color.black_new));
                llDelivery.setBackgroundResource(R.drawable.button_blue_bag);
                llViewAddress.setVisibility(View.VISIBLE);
                rlAddressView.setVisibility(View.VISIBLE);
                if (isShowViewAddress) {
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            rlAddressView.getHeight(),
                            0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    rlAddressView.startAnimation(animate);
                    txtCurrentAddress.setText("");

                }


                isSelect = "delivery";
                ivDelivery.setColorFilter(ContextCompat.getColor(MapsActivity.this, R.color.whiteColor));
                txtDelivery.setTextColor(ContextCompat.getColor(MapsActivity.this, R.color.whiteColor));
                if (!session.isLoggedIn()) {
                    showLoginSignUpDialog();
                } else {
                    new Thread(() -> {
                        double newLatPickUp = 0, newLngPickUp = 0;
                        UserInfoModel userInfoModel = dataManager().userinfo(session.getRegistration().getId());
                        addressBilling = userInfoModel.getDeliveryAddress();
                        if (userInfoModel.getDeliveryLatitude() != null) {
                            newLatPickUp = Double.valueOf(userInfoModel.getDeliveryLatitude());
                            newLngPickUp = Double.valueOf(userInfoModel.getDeliveryLongitude());


                        }


                        double finalNewLatPickUp = newLatPickUp;
                        double finalNewLngPickUp = newLngPickUp;
                        handler.post(() -> {
                            //  txtAddressDelivery.setText(addressBilling);
                            LatLng pointPickUp;
                            pointPickUp = new LatLng(finalNewLatPickUp, finalNewLngPickUp);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(pointPickUp, 13);
                            @SuppressLint("InflateParams") View markerView = LayoutInflater.from(this).inflate(R.layout.custom_marker_layout, null);
                            DisplayMetrics displayMetrics = new DisplayMetrics();

                            markerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            markerView.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
                            markerView.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
                            markerView.buildDrawingCache();
                            Bitmap finalBitmap = Bitmap.createBitmap(markerView.getMeasuredWidth(), markerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(finalBitmap);
                            markerView.draw(canvas);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(pointPickUp);

                            mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromBitmap(finalBitmap)));


                            mMap.animateCamera(cameraUpdate);
                            if (isChangedAddress) {
                                txtCurrentAddress.setText(addressBilling);
                            } else {
                                txtCurrentAddress.setText("");
                            }

                            txtAddress.setText(addressBilling);
                        });

                    }).start();

                }

                break;

            case R.id.llPickUp:

                llPickUp.setBackgroundResource(R.drawable.button_blue_bag);
                ivPickUp.setColorFilter(ContextCompat.getColor(MapsActivity.this, R.color.whiteColor));
                txtPickUp.setTextColor(ContextCompat.getColor(MapsActivity.this, R.color.whiteColor));
                llDelivery.setBackgroundResource(R.drawable.button_pickup_placeholder);
                isSelect = "";
                ivDelivery.setColorFilter(ContextCompat.getColor(MapsActivity.this, R.color.black_new));
                txtDelivery.setTextColor(ContextCompat.getColor(MapsActivity.this, R.color.black_new));
                txtAddress.setText(session.getAddress());
                LatLng pointPickUp;
                pointPickUp = new LatLng(Double.valueOf(session.getLatitude()), Double.valueOf(session.getLongitude()));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(pointPickUp, 13);
                mMap.animateCamera(cameraUpdate);

                break;

            case R.id.rlAddress:
                if (isSelect.equals("delivery")) {
                    try {
                        Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(MapsActivity.this);
                        startActivityForResult(intent, Constant.PLACE_AUTOCOMPLETE_REQUEST_BILLING);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }

                break;

            case R.id.rlClose:

                onBackPressed();

                break;

            case R.id.txtCurrentAddress:
                new Thread(() -> {
                    double newLatPickUp = 0, newLngPickUp = 0;
                    UserInfoModel userInfoModel = dataManager().userinfo(session.getRegistration().getId());
                    addressBilling = userInfoModel.getDeliveryAddress();
                    if (userInfoModel.getDeliveryLatitude() != null) {
                        newLatPickUp = Double.valueOf(userInfoModel.getDeliveryLatitude());
                        newLngPickUp = Double.valueOf(userInfoModel.getDeliveryLongitude());

                    }
                    dataManager().updateDeliveryAddress(addressBilling, userInfoModel.getDeliveryCountry(), userInfoModel.getDeliveryState(), String.valueOf(newLatPickUp), String.valueOf(newLngPickUp), Integer.parseInt(session.getRegistration().getId()));
                    handler.post(this::onBackPressed);
                }).start();

                break;

            case R.id.txtAddressDelivery:
                new Thread(() -> {
                    double newLatPickUp = 0, newLngPickUp = 0;
                    addressBilling = localUserInfo.getDeliveryAddress();
                    if (localUserInfo.getDeliveryLatitude() != null) {
                        newLatPickUp = Double.valueOf(localUserInfo.getDeliveryLatitude());
                        newLngPickUp = Double.valueOf(localUserInfo.getDeliveryLongitude());

                    }
                    dataManager().updateDeliveryAddress(addressBilling, localUserInfo.getDeliveryCountry(), localUserInfo.getDeliveryState(), String.valueOf(newLatPickUp), String.valueOf(newLngPickUp), Integer.parseInt(session.getRegistration().getId()));
                    double finalNewLatPickUp = newLatPickUp;
                    double finalNewLngPickUp = newLngPickUp;
                    handler.post(() -> {

                        LatLng latLng;
                        latLng = new LatLng(finalNewLatPickUp, finalNewLngPickUp);
                        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(latLng, 13);
                        mMap.animateCamera(cameraUpdate1);

                        @SuppressLint("InflateParams") View markerView = LayoutInflater.from(this).inflate(R.layout.custom_marker_layout, null);
                        DisplayMetrics displayMetrics = new DisplayMetrics();

                        markerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        markerView.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
                        markerView.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
                        markerView.buildDrawingCache();
                        Bitmap finalBitmap = Bitmap.createBitmap(markerView.getMeasuredWidth(), markerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(finalBitmap);
                        markerView.draw(canvas);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);

                        mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromBitmap(finalBitmap)));

                        onBackPressed();

                    });
                }).start();


        }


    }


    private void showLoginSignUpDialog() {
        final Dialog dialog = new Dialog(MapsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_login_signup);
        LinearLayout llSignup = dialog.findViewById(R.id.llSignup);
        LinearLayout llSignIn = dialog.findViewById(R.id.llSignIn);
        CardView cardView=dialog.findViewById(R.id.cardCancel);
        cardView.setOnClickListener(v -> dialog.dismiss());
        llSignup.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, NewAccActivity.class);
            startActivity(intent);
        });

        llSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, SigninActivity.class);
            startActivity(intent);
        });
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.PLACE_AUTOCOMPLETE_REQUEST_BILLING) {
            Place place = PlaceAutocomplete.getPlace(MapsActivity.this, data);
            getAddressDelivery(place);

        }
    }


    private void getAddressDelivery(final Place place) {

        if (pDialog.checkInternetConnection(MapsActivity.this)) {
            new AddressLocationTask(MapsActivity.this, place, (cty, st, cntry, locAddress) -> {
                addressBilling = Objects.requireNonNull(place.getAddress()).toString();
                if (place.getLatLng() != null) {
                    txtCurrentAddress.setText(addressBilling);
                    txtAddress.setText(place.getAddress());
                    latitudeBilling = "" + place.getLatLng().latitude;
                    longitudeBilling = "" + place.getLatLng().longitude;
                    new Thread(() -> dataManager().updateDeliveryAddress(addressBilling, cntry, st, latitudeBilling, longitudeBilling, Integer.parseInt(session.getRegistration().getId()))).start();
                    LatLng pointPickUp;
                    double newLatPickUp = Double.valueOf(latitudeBilling);
                    double newLngPickUp = Double.valueOf(longitudeBilling);
                    pointPickUp = new LatLng(newLatPickUp, newLngPickUp);
                    isChangedAddress = true;

                    @SuppressLint("InflateParams") View markerView = LayoutInflater.from(this).inflate(R.layout.custom_marker_layout, null);
                    DisplayMetrics displayMetrics = new DisplayMetrics();

                    markerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    markerView.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
                    markerView.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
                    markerView.buildDrawingCache();
                    Bitmap finalBitmap = Bitmap.createBitmap(markerView.getMeasuredWidth(), markerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(finalBitmap);
                    markerView.draw(canvas);
                    MarkerOptions markerOptions = new MarkerOptions();


                    markerOptions.position(pointPickUp);

                    mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromBitmap(finalBitmap)));
                    CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(pointPickUp, 13);
                    mMap.animateCamera(cameraUpdate1);



                }


            }).execute();
        }
    }

    public AppDataManager dataManager() {
        return Alkasilverlake.getDataManager();
    }

    public void updateLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        setLatLng(location);
                    } else {
                        //Location not available
                        onGpsAutomatic();
                    }
                });
    }

    protected void setLatLng(@NonNull Location location) {
        Alkasilverlake.LATITUDE = location.getLatitude();
        Alkasilverlake.LONGITUDE = location.getLongitude();

    }

    protected void onGpsAutomatic() {
        int permissionLocation = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            locationRequest = new LocationRequest();
            locationRequest.setInterval(3000);
            locationRequest.setFastestInterval(3000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);
            builder.setNeedBle(true);
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
            Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());
            task.addOnCompleteListener(task1 -> {
                int permissionLocation1 = ContextCompat
                        .checkSelfPermission(this,
                                Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation1 == PackageManager.PERMISSION_GRANTED) {

                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, location -> {
                                if (location != null) {
                                    setLatLng(location);
                                }

                            });
                }
            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MY_PERMISSIONS_REQUEST_LOCATION){
            updateLocation();
            updateMapUI();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        detector.onTouchEvent(motionEvent);
        return true;
    }


}
