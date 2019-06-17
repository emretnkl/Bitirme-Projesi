package com.example.letmeknow.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.letmeknow.R
import com.example.letmeknow.activity.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*

@Suppress("UNREACHABLE_CODE")
class SignInFragment : Fragment() {

    lateinit var mAuthStateListener : FirebaseAuth.AuthStateListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_sign_in, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initMyAuthStateListener()

        val activity = (requireActivity() as AppCompatActivity)

        val signUpFromSignIn = view.findViewById<TextView>(R.id.sign_up_t)

        signUpFromSignIn.setOnClickListener {
            Navigation.findNavController(
                this.activity!!,
                R.id.nav_host_fragment
            ).navigate(R.id.toSignUp)
        }


        signInBtn?.setOnClickListener {

            if (email_text.text.isNotEmpty() && password_text.text.isNotEmpty())
            {
                progressBarGoster()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email_text.text.toString(),password_text.text.toString())
                    .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                        override fun onComplete(p0: Task<AuthResult>) {

                            if (p0.isSuccessful){
                                progressBarGizle()
                                Toast.makeText(activity,"Giriş bilgileri doğru: "+FirebaseAuth.getInstance().currentUser?.email,Toast.LENGTH_SHORT).show()

                            }else{
                                progressBarGizle()
                                Toast.makeText(activity,"Hatalı Giriş:"+p0.exception?.message,Toast.LENGTH_SHORT).show()


                            }
                        }

                    }
                    )

            }
            else{
                Toast.makeText(activity,"Boş alanları doldurunuz!",Toast.LENGTH_SHORT).show()


            }
        }



    }

    private fun progressBarGoster()
    {
        progressBarLogin.visibility=View.VISIBLE

    }

    private fun progressBarGizle()
     {
        progressBarLogin.visibility=View.INVISIBLE

    }

    private fun initMyAuthStateListener(){

        mAuthStateListener= object : FirebaseAuth.AuthStateListener{

            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var kullanici=p0.currentUser

                if (kullanici!=null){

                    if (kullanici.isEmailVerified){

                        Toast.makeText(activity,"Hoşgeldiniz.",Toast.LENGTH_SHORT).show()
                        var intent=Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()

                    } else{

                        Toast.makeText(activity,"Mail adresinizi doğrulamadan giriş yapamazsınız.",Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }

    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener)
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener)
    }



}
