package com.example.letmeknow.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.letmeknow.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        signUpBtn.setOnClickListener {
            if (email_kaydol.text.isNotEmpty() && password_kaydol.text.isNotEmpty() && password_kaydol_tekrar.text.isNotEmpty())
            {
                if (password_kaydol.text.toString().equals(password_kaydol_tekrar.text.toString()))
                {
                    yeniUyeKayit(email_kaydol.text.toString(),password_kaydol.text.toString())

                }
                else
                {
                    Toast.makeText(activity,"Şifreler aynı değil!",Toast.LENGTH_SHORT).show()
                }


            }
            else
            {
                Toast.makeText(activity,"Boş alanları doldurunuz.",Toast.LENGTH_SHORT).show()

            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun yeniUyeKayit(mail: String, sifre: String)
    {
        progressBarGoster()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,sifre)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {
                    if (p0.isSuccessful)
                    {
                        Toast.makeText(activity,"Üye Kaydedildi!"+FirebaseAuth.getInstance().currentUser?.uid,Toast.LENGTH_SHORT).show()
                        onayMailiGonder()
                        FirebaseAuth.getInstance().signOut()
                    }else{
                        Toast.makeText(activity,"Üye kaydedilirken sorun oluştu!"+p0.exception?.message,Toast.LENGTH_SHORT).show()
                    }

                }
            })
        progressBarGizle()



    }

    private fun onayMailiGonder()
    {
        var kullanici = FirebaseAuth.getInstance().currentUser
        if (kullanici != null){
            kullanici.sendEmailVerification()
                .addOnCompleteListener(object : OnCompleteListener<Void>{
                    override fun onComplete(p0: Task<Void>) {

                        if (p0.isSuccessful){

                            Toast.makeText(activity,"Mail kutunuzu kontrol edin, mailinizi onaylayın.",Toast.LENGTH_SHORT).show()

                        }else{

                            Toast.makeText(activity,"Mail atılırken sorun oluştu!"+p0.exception?.message,Toast.LENGTH_SHORT).show()

                        }
                    }


                })
        }

    }
    private fun progressBarGoster()
    {
        progressBar.visibility=View.VISIBLE

    }

    private fun progressBarGizle()
    {
        progressBar.visibility=View.INVISIBLE

    }






}
