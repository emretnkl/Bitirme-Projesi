package com.example.letmeknow.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.letmeknow.R
import com.example.letmeknow.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_create_event.*

class CreateEventFragment : Fragment() {

    lateinit var _db: DatabaseReference
    var mAuth:FirebaseAuth= FirebaseAuth.getInstance()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _db = FirebaseDatabase.getInstance().reference



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_event, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mAuth.currentUser==null)
        {
            etkinlikKaydetBtn.visibility=View.INVISIBLE

        }
        else{
            etkinlikKaydetBtn.visibility=View.VISIBLE

        }

        //val etkinlikAdiDegisken=etkinlikAdiKayit.toString()
        //val etkinlikAciklamaDegisken=etkinlikAciklamaKayit.toString()
        //val etkinlikAdresDegisken=etkinlikAdresKayit.toString()
        //val etkinlikOrganizatorDegisken=etkinlikOrganizatorKayit.toString()





        etkinlikKaydetBtn.setOnClickListener {
            addTask()
            Toast.makeText(activity,"Etkinlik oluşturuldu.",Toast.LENGTH_SHORT).show()
        }
    }
    fun addTask(){

        val task = Task.create()
        task.etkinlikAdi = etkinlikAdiKayit.text.toString()
        task.etkinlikAciklama = etkinlikAciklamaKayit.text.toString()
        task.etkinlikFoto = "https://blogmedia.evbstatic.com/wp-content/uploads/wpmulti/sites/8/2018/01/15155312/iStock-667709450.jpg"
        task.adres = etkinlikAdresKayit.text.toString()
        task.tarih = etkinlikTarihKayit.text.toString()

        mAuth.currentUser?.let {
            task.organizator = it.displayName
        }
        val newTask = _db.child("Etkinlikler").push()
        task.objectId = newTask.key
        newTask.setValue(task)

    }






}
