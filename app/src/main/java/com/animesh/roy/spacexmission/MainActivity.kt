package com.animesh.roy.spacexmission

import ListQuery
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val b: Boolean = isNetworkAvailable()
        Log.d("NetworkAv", b.toString())

        if (b.toString() == "false") {
            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(
                applicationContext,
                "You aren't connected to the Internet!",
                duration
            )
            toast.show()
        }


        recyclerView = findViewById(R.id.recyclerView)

        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(ListQuery()).toDeferred().await()
            } catch (e: ApolloException) {
                Log.d("LaunchList", "Failure", e)
                null
            }

            val launches = response?.data?.launchesPast?.filterNotNull()
            if (launches != null && !response.hasErrors()) {


                val launches = response?.data?.launchesPast?.filterNotNull()
                if (launches != null && !response.hasErrors()) {

                    var controller: LayoutAnimationController? = null
                    controller =
                        AnimationUtils.loadLayoutAnimation(
                            recyclerView.context,
                            R.anim.layout_slide_from_right
                        );

                    val adapter = ListAdapter(launches, context = applicationContext)
                    recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    recyclerView.adapter = adapter

                    recyclerView.setLayoutAnimation(controller);
                    recyclerView.getAdapter()?.notifyDataSetChanged();
                    recyclerView.scheduleLayoutAnimation();


                }
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_sort_mission -> Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT)
                .show()
            R.id.sort_by_date -> Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT)
                .show()
            else -> {
            }
        }
        return true
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}

