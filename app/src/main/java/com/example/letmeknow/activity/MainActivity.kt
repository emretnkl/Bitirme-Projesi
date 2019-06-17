package com.example.letmeknow.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.letmeknow.R
import com.example.letmeknow.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseUser



class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth =FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_app_bar_item, menu)
        val view =menu?.findItem(R.id.account_btn)
        auth.currentUser?.let {

            view?.let {
                view.isVisible=false
            }
        }
        if(auth.currentUser==null){
            view?.let {
                view.isVisible=true
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {

            R.id.account_btn -> {

                Navigation.findNavController(this,
                        R.id.nav_host_fragment
                    ).navigate(R.id.toSignIn)

            }

            R.id.search_btn -> {
            //    Navigation.findNavController(this,
              //          R.id.nav_host_fragment
                //).navigate(R.id.toSearch)
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this,"Çıkış yapıldı.",Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
