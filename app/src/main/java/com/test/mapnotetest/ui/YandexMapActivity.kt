package com.test.mapnotetest.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.mapnotetest.MapsActivity
import com.test.mapnotetest.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import kotlinx.android.synthetic.main.activity_yandex_map.*

class YandexMapActivity : AppCompatActivity() {

    private val PITER_POINT = Point(59.945933, 30.320045)
    private val MOSCOW_POINT = Point(55.751574, 37.573856)

    private lateinit var mapView: MapView

    private val mapListener = object : InputListener{
        override fun onMapLongTap(p0: Map, p1: Point) {
            //Toast.makeText(this@YandexMapActivity, "long tap", Toast.LENGTH_SHORT).show()
            mapView.map.move(CameraPosition(
                mapView.map.cameraPosition.target,
                mapView.map.cameraPosition.zoom,
                0f,
                0f
            ),
                Animation(Animation.Type.LINEAR, 0.2f),
                null
            )
        }

        override fun onMapTap(p0: Map, p1: Point) {
            //Toast.makeText(this@YandexMapActivity, "tap: ${p1.latitude}; ${p1.longitude}", Toast.LENGTH_SHORT).show()
            val dialog = AlertDialog.Builder(this@YandexMapActivity)
            dialog.setTitle(R.string.marker_title)
                .setMessage(R.string.marker_message)
                .setPositiveButton(R.string.yes
                ) { d, _ ->
                    val marker = ImageView(this@YandexMapActivity)
                    marker.setImageResource(R.drawable.map_marker_1)
                    marker.setOnClickListener {
                        Toast.makeText(this@YandexMapActivity, "marker tap", Toast.LENGTH_SHORT).show()
                    }
                    mapView.map.mapObjects.addPlacemark(p1, ViewProvider(marker))
                    d?.dismiss()
                }
                .setNegativeButton(R.string.no) { d, _ ->
                    d.dismiss()
                }
                .create()
                .show()
        }

    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_markers -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("304d9781-da4d-4a0c-8e6c-955899883551")
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_yandex_map)

        mapView = (yandex_map_view as MapView)
        mapView.map.move(
            CameraPosition(
                PITER_POINT,
                11.0f,
                0.0f,
                0.0f
            ),
            Animation(Animation.Type.LINEAR, 2f),
            null
        )

        mapView.map.addInputListener(mapListener)

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }
}