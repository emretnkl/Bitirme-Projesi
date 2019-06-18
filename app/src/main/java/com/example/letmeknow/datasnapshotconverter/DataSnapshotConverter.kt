package com.example.letmeknow.datasnapshotconverter

import android.util.Log
import com.example.letmeknow.model.CurrentUser
import com.example.letmeknow.model.Task
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class DataSnapshotConverter {


    var mDatabase = FirebaseDatabase.getInstance()
    var dbReferenceEtkinlikler = mDatabase.getReference("/Etkinlikler")


    fun getEtkinlikler(): Observable<Task?> {
        return Observable.create<DataSnapshot> { emitter ->
            dbReferenceEtkinlikler.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    emitter.onNext(p0)
                    emitter.onComplete()
                }

            })
        }
            .flatMap {
                Observable.fromIterable(it.children)
            }
            .map {
                it.getValue(Task::class.java)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

    }


    fun pushCurrentUserOnTheEtkinlikler(key: String, currentUser: CurrentUser) {
        dbReferenceEtkinlikler = mDatabase.getReference("Etkinlikler/$key")
        dbReferenceEtkinlikler.child("currentUser").push().setValue(currentUser).addOnFailureListener {
            Log.i("MyLogger", it.localizedMessage)
        }

    }


}