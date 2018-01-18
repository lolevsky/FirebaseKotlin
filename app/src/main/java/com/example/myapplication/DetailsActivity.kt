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

    private lateinit var content: Update

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ditails)
        initActionBar()

        content = unwrap<Update>(intent.extras.getParcelable("content"))

        val database = FirebaseDatabase.getInstance()

        when (content.updateType) {
            Update.TYPE.DHT -> {
                val myRef1 = database.getReference("logDHT")
                myRef1.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        content.logDHT = dataSnapshot.child("" + content.logDHT?.id).getValue(LogDHT::class.java)

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
                        content.logSEN = dataSnapshot.child("" + content.logSEN?.id).getValue(LogSEN::class.java)

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
        param1.text = "ELD1 = ${content.logSEN?.eld1}"
        param2.text = "ELD2 = ${content.logSEN?.eld2}"
        param3.text = "Time = ${content.logSEN?.time}"

        updateRedBackground()
    }

    fun updateViewDHT() {
        title_text.text = "DHT";
        param1.text = "Temperature = ${content.logDHT?.temperature}"
        param2.text = "Humidity = ${content.logDHT?.humidity}"
        param3.text = "Time = ${content.logDHT?.time}"

        updateRedBackground()
    }

    fun updateRedBackground() {
        val root = title_text.getRootView()

        if (content.logSEN?.eld1 == 1 || content.logSEN?.eld2 == 1) {
            root.setBackgroundColor(Color.RED)
        } else {
            root.setBackgroundColor(Color.WHITE)
        }
    }

    fun initActionBar() {
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
