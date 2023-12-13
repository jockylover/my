package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.data.DataDownload;
import com.example.myapplication.data.ShopLocation;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private com.tencent.tencentmap.mapsdk.maps.MapView mapView = null;
    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public ArrayList<ShopLocation> parseJsonObjects(String content) {
        ArrayList<ShopLocation> locations = new ArrayList<>();
        try {
            //这里的text就是上边获取到的数据，一个String.
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonDatas = jsonObject.getJSONArray("shops");
            int length = jsonDatas.length();
            String test;
            for (int i = 0; i < length; i++) {
                JSONObject shopJson = jsonDatas.getJSONObject(i);
                ShopLocation shop = new ShopLocation();
                shop.setName(shopJson.getString("name"));
                shop.setLatitude(shopJson.getDouble("latitude"));
                shop.setLongitude(shopJson.getDouble("longitude"));
                shop.setMemo(shopJson.getString("memo"));
                locations.add(shop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = rootView.findViewById(R.id.mapView);

        setupMap();
        fetchAndDisplayShopLocations();

        return rootView;
    }

    private void setupMap() {
        TencentMap tencentMap = mapView.getMap();
        LatLng initialPosition = new LatLng(22.255453, 113.54145);
        tencentMap.moveCamera(CameraUpdateFactory.newLatLng(initialPosition));
    }

    private void fetchAndDisplayShopLocations() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String responseData = new DataDownload().download("http://file.nidama.net/class/mobile_develop/data/bookstore.json");
                    ArrayList<ShopLocation> shopLocations = new DataDownload().parseJsonObjects(responseData);

                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            displayShopMarkers(shopLocations);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace(); // Handle appropriately, e.g., show an error message
                }
            }
        }).start();
    }

    private void displayShopMarkers(ArrayList<ShopLocation> shopLocations) {
        TencentMap tencentMap = mapView.getMap();

        // Clear existing markers if needed
        tencentMap.clear();

        for (ShopLocation shopLocation : shopLocations) {
            LatLng shopLatLng = new LatLng(shopLocation.getLatitude(), shopLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions(shopLatLng)
                    .title(shopLocation.getName()); // Customize as needed
            Marker marker = tencentMap.addMarker(markerOptions);

            if (marker != null) {
                Log.d("MarkerAdded", "Marker added for " + shopLocation.getName());
            } else {
                Log.e("MarkerError", "Failed to add marker for " + shopLocation.getName());
            }
        }
    }


    //    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
//        mapView = rootView.findViewById(R.id.mapView);
//
//        TencentMap tencentMap = mapView.getMap();
//
//        LatLng point1 = new LatLng(22.255453, 113.54145);
//        tencentMap.moveCamera(CameraUpdateFactory.newLatLng(point1));
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String responseData=new DataDownload().download("http://file.nidama.net/class/mobile_develop/data/bookstore.json");
//                ArrayList<ShopLocation> shopLocations= new DataDownload().parseJsonObjects(responseData);
//                requireActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        TencentMap tencentMap = mapView.getMap();
//                        for (ShopLocation shopLocation : shopLocations) {
//                            LatLng point1 = new LatLng(shopLocation.getLatitude(), shopLocation.getLongitude());
//                            MarkerOptions markerOptions = new MarkerOptions(point1)
//                                    .title(shopLocation.getName());
//                            Marker marker = tencentMap.addMarker(markerOptions);
//
//
//                        }
//                    }
//                });
//            }
//        }).start();
//
//        return rootView;
//    }
//    public class DataDownloadTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//            return new DataDownload().download(urls[0]);
//        }
//        @Override
//        protected void onPostExecute(String responseData) {
//            super.onPostExecute(responseData);
//            if (responseData != null) {
//                ArrayList<ShopLocation> shopLocations= new DataDownload().parseJsonObjects(responseData);
//                TencentMap tencentMap = mapView.getMap();
//                for (ShopLocation shopLocation : shopLocations) {
//                    LatLng point1 = new LatLng(shopLocation.getLatitude(), shopLocation.getLongitude());
//                    MarkerOptions markerOptions = new MarkerOptions(point1)
//                            .title(shopLocation.getName());
//                    Marker marker = tencentMap.addMarker(markerOptions);
//                }
//            }
//        }
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
//        mapView = rootView.findViewById(R.id.mapView);
//        DataDownload dataDownload = new DataDownload();
//        String content = dataDownload.download("http://file.nidama.net/class/mobile_develop/data/bookstore2023.json");
//        ArrayList<ShopLocation> shopLocations = dataDownload.parseJsonObjects(content);
//        TencentMap tencentMap = mapView.getMap();
//        LatLng point1 = new LatLng(22.255453, 113.54145);
//        CameraUpdate cameraSigma =
//                CameraUpdateFactory.newCameraPosition(new CameraPosition(
//                        point1, //中心点坐标，地图目标经纬度
//                        1,  //目标缩放级别
//                        0, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
//                        0)); //目标旋转角 0~360° (正北方为0)
//        tencentMap.moveCamera(cameraSigma); //移动地图
//        // 创建一个Marker对象
//        MarkerOptions markerOptions = new MarkerOptions(point1)
//                .title("暨南大学珠海校区");
//        //        // 添加标记到地图上
//        Marker marker = tencentMap.addMarker(markerOptions);
//        MarkerOptions options = new MarkerOptions(point1);
//        options.infoWindowEnable(false);//默认为true
//        options.title("暨南大学珠海校区")//标注的InfoWindow的标题
//                .snippet("地址: ");//标注的InfoWindow的内容
//       tencentMap.addMarker(options);
//       marker.setInfoWindowEnable(true);
//       marker.showInfoWindow();
//        tencentMap.setOnMarkerClickListener(new TencentMap.OnMarkerClickListener(){
//            @Override
//            public boolean onMarkerClick(Marker Mmarker) {
//                if(Mmarker.getId().equals(marker.getId())) {
//                    //自定义Marker被点击
//                    Toast.makeText(MapFragment.this.getContext(),"Marker clicked",Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//        });
////设置Marker支持点击
//        marker.setClickable(true);
//        return rootView;
//    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
//        mMapView = rootView.findViewById(R.id.mapView);
//        mBaiduMap = mMapView.getMap();
//        LatLng point1 = new LatLng(22.255453,113.54145);
//        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(point1);
//        mBaiduMap.addOverlay();
//        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//            public boolean onMapClick(Marker marker) {
//                Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onMapPoiClick(MapPoi mapPoi) {
//
//            }
//        });
//        return rootView;
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
//        mMapView.onResume();
//    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
//        mMapView.onPause();
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
//        mMapView.onDestroy();
//    }
}