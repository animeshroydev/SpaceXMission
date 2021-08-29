package com.animesh.roy.spacexmission

import DetailsQuery
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import id.voela.actrans.AcTrans
import java.util.*

class DetailsActivity : AppCompatActivity() {

    var userId: String = ""
    val imageList = ArrayList<SlideModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        getIncomingIntent()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        val textView: TextView = findViewById(R.id.textDescription)
        val missionName: TextView = findViewById(R.id.missionName)
        val isSuccessful: TextView = findViewById(R.id.isSuccessful)
        val launchSite: TextView = findViewById(R.id.launchSite)
        val dateOfLaunch: TextView = findViewById(R.id.dateOfLaunch)
        val rocketName: TextView = findViewById(R.id.rocketName)


        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(DetailsQuery(userId)).toDeferred().await()
            } catch (e: ApolloException) {
                Log.d("LaunchActivity", "Failure", e)
                null
            }

            val launches = response?.data?.launch
            if (launches != null && !response.hasErrors()) {

                textView.text = launches.details
                carouselSlideEffect(launches)
                missionName.text = launches.mission_name
                isSuccessful.text = "Successful? " + launches.launch_success.toString()
                launchSite.text = launches.launch_site?.site_name
                dateOfLaunch.text = launches.launch_date_local.toString()
                rocketName.text = launches.rocket?.rocket_name
            }
        }
    }


    private fun getIncomingIntent() {
        if (intent.hasExtra("id_value")) {
            userId = intent.getStringExtra("id_value").toString()
            Log.d("IdVal ", userId.toString())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AcTrans.Builder(this).performSlideToLeft()
    }

    private fun carouselSlideEffect(launches: DetailsQuery.Launch) {
        // Log.d("Corusal1 ", launches.links?.flickr_images?.get(0).toString()) // **
        for (i in 0..(launches.links?.flickr_images?.size.toString()).toInt() - 1) {
            imageList.add(
                SlideModel(
                    launches.links?.flickr_images?.get(i).toString(),
                    launches.mission_name
                )
            )
        }

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
    }

}