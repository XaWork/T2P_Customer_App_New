import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.order.OrderViewModel

@Composable
fun TrackLocationScreen(
    viewModel: OrderViewModel
) {
    val order = viewModel.selectedOrder!!
    val mapView = rememberMapViewWithLifecycle()

     var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AndroidView({ mapView }) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView
                /*database.child("userlocation${order.id}").addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        print(snapshot.toString())
                        val latitude = (snapshot.value as HashMap<*, *>)["latitude"] as? Double
                        val longitude = (snapshot.value as HashMap<*, *>)["longitude"] as? Double
                        if(latitude!=null && longitude!=null) {
                            updateMap(Driver(lat = latitude.toString(), lng = longitude.toString()))
                            map.drawRouteOnMap(
                                "AIzaSyBbpYO8zLmSh0-c5_MDiyPiQnMCl4Jc6ko", //your API key
                                source = LatLng(latitude, longitude),
                                markers = false,
                                destination = LatLng(
                                    order.address!!.position.coordinates[0],
                                    order.address!!.position.coordinates[1]
                                ),
                                context = this@TrackLocationActivity
                            )
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })*/
            }
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    // on below line initializing our maps view with id.
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    // Makes MapView follow the lifecycle of this composable
    val lifecycleObserver = rememberMapLifecycleObserver(mapView)
    // on below line initializing lifecycle variable.
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    // on below line adding observer for lifecycle.
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    // returning map view on below line.
    return mapView
}

@Composable
// creating a function for map lifecycle observer.
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver = remember(mapView) {
    // on below line adding different events for maps view
    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw IllegalStateException()
        }
    }
}