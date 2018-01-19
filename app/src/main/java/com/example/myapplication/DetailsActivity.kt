package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_ditails.*
import org.parceler.Parcels.unwrap


class DetailsActivity : AppCompatActivity() {
    private val TAG = DetailsActivity::class.java.simpleName

    private lateinit var content: MyData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ditails)
        initActionBar()

        content = unwrap<MyData>(intent.extras.getParcelable("content"))

        val database = FirebaseDatabase.getInstance()

        when (content.getType()) {
            MyData.TYPE.DHT -> {
                val myRef1 = database.getReference("logDHT")
                myRef1.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val log: LogDHT? = dataSnapshot.child("" + content.getDataId()).getValue(LogDHT::class.java)
                        log?.id = content.getDataId()
                        content = log as MyData

                        updateViewDHT()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(TAG, "Failed to read value DHT.", error.toException())
                    }
                })

                updateViewDHT()
            }
            else -> {
                val myRef1 = database.getReference("logSEN")
                myRef1.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val log: LogSEN? = dataSnapshot.child("" + content.getDataId()).getValue(LogSEN::class.java)
                        log?.id = content.getDataId()
                        content = log as MyData

                        updateViewSEN()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(TAG, "Failed to read value DHT.", error.toException())
                    }
                })

                updateViewSEN()
            }
        }
    }

    fun updateViewSEN() {
        title_text.text = "SEN";
        param1.text = "ELD1 = ${content.getParam1()}"
        param2.text = "ELD2 = ${content.getParam2()}"
        param3.text = "Time = ${content.getDataTime()}"

        updateRedBackground()
    }

    fun updateViewDHT() {
        title_text.text = "DHT";
        param1.text = "Temperature = ${content.getParam1()}"
        param2.text = "Humidity = ${content.getParam2()}"
        param3.text = "Time = ${content.getDataTime()}"

        updateRedBackground()
    }

    private fun updateRedBackground() {
        val root = title_text.rootView

        if (content.getParam1() == "1" || content.getParam2() == "1") {
            root.setBackgroundColor(Color.RED)
        } else {
            root.setBackgroundColor(Color.WHITE)
        }
    }

    private fun initActionBar() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
