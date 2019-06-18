@file:Suppress("UNREACHABLE_CODE")

package com.example.letmeknow.fragment


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.letmeknow.R
import com.example.letmeknow.datasnapshotconverter.DataSnapshotConverter
import com.example.letmeknow.model.Task
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    lateinit var eventsAdapter: EventsAdapter
    private val eventList: MutableList<Task> = mutableListOf()
    lateinit var _db: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = (requireActivity() as AppCompatActivity)
        activity.setSupportActionBar(bottom_app_bar)

        val fabBtn = view.findViewById<FloatingActionButton>(R.id.floating_action_btn)
        val eventRv = view.findViewById<RecyclerView>(R.id.event_rv)

        fabBtn.setOnClickListener {
            Navigation.findNavController(
                this.activity!!,
                R.id.nav_host_fragment
            ).navigate(R.id.toCreateEvent)
        }

        eventsAdapter = EventsAdapter()
        eventRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        eventRv.adapter = eventsAdapter

//        Log.e("MainFragment", "falan filan")
//        val retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl("https://letmeknow-dacf2.firebaseio.com")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//
//        val apiEvents = retrofit.create(ApiEvents::class.java)
//
//        apiEvents.getEvents()
//            .subscribeOn(Schedulers.io())
//            .unsubscribeOn(Schedulers.computation())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ eventsAdapter.setEvents(it.data) },
//                {
//                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
//                })

        var _taskListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w("MainActivity", "loadItem:onCancelled", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                loadTaskList(p0)
            }
        }

        _db = FirebaseDatabase.getInstance().reference
        _db.orderByKey().addValueEventListener(_taskListener)

        eventRv.layoutManager = LinearLayoutManager(context)

        bottom_app_bar.replaceMenu(R.menu.bottom_app_bar_item)
    }

    private fun loadTaskList(dataSnapshot: DataSnapshot) {
        Log.d("MainActivity", "loadTaskList")

        DataSnapshotConverter().getEtkinlikler()
            .doOnNext {
                it?.let {
                    Log.i("MyLogger",it.etkinlikAdi)
                    eventList!!.add(it)
                    eventsAdapter.notifyDataSetChanged()
                }
            }
            .doOnError {

            }
            .subscribe()

        //Check if current database contains any collection


    }

    inner class EventsAdapter : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
            return EventsViewHolder(layoutInflater.inflate(R.layout.event_item, parent, false))
        }

        override fun getItemCount(): Int {
            return eventList.size
        }

        override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
            holder.bindModel(eventList[position])
        }

        fun setEvents(data: List<Task>) {
            eventList.addAll(data)
            notifyDataSetChanged()
        }

        inner class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bindModel(event: Task) {

                val eventName = itemView.findViewById<TextView>(R.id.event_name)
                val eventOrganizator = itemView.findViewById<TextView>(R.id.event_organizator)
                val eventAddress = itemView.findViewById<TextView>(R.id.event_address)
                val eventDate = itemView.findViewById<TextView>(R.id.event_date)
                val eventImage = itemView.findViewById<ImageView>(R.id.event_image)

                eventName.text = event.etkinlikAdi
                eventOrganizator.text = event.organizator
                eventAddress.text = event.adres
                eventDate.text = event.tarih

                Glide.with(itemView)
                    .load(event.etkinlikFoto)
                    // .placeholder(R.drawable.abc_btn_check_material)
                    .into(eventImage)

                itemView.setOnClickListener {
                    val intent: Intent = Intent(itemView.context, DetailFragment::class.java)
                    intent.putExtra("Event", event)
                    itemView.context.startActivity(intent)
                }

            }
        }
    }
}
