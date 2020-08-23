package com.example.config_management

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailViewActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var idextras = intent.getIntExtra("id", -1)
        var oldparamextra = intent.getStringExtra("oldparam")

        println(idextras)

        val pagerAdapter = DetailPagerAdapter(supportFragmentManager, idextras, oldparamextra!!)

        setContentView(R.layout.fragment_detail)
        viewpagerdetail.adapter = pagerAdapter

        textView5.text = idextras.toString()

        if (oldparamextra=="Domains"){
            textView6.text = "Associated Features -> "
        }
        else{
            textView6.text = "Domains Associated to -> "

        }
    }

}

class DetailPagerAdapter(fm: FragmentManager, idinclass:Int, name:String) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return 1
    }

    private val id = idinclass
    private val name = name

    override fun getItem(position: Int): Fragment {
        return DetailFragment.newInstance(id,name)
    }

}