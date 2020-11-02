package com.chinachino.mvi.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.chinachino.mvi.R
import com.chinachino.mvi.ui.DataStateListener
import com.chinachino.mvi.utils.DataState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),DataStateListener {

    lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        showMainFragment()
    }
    private fun showMainFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment(),"MainFragment")
            .commit()
    }

    override fun onDataStateChanged(dataState: DataState<*>?) {
        handleDataStateChanged(dataState)
    }

    private fun handleDataStateChanged(dataState: DataState<*>?) {
        dataState?.let {
            showProgressBar(dataState.loading)

            dataState.message?.let {message ->
                println("DEBUGE :: $message")
                showToastBar(message)
            }
        }

    }

    private fun showToastBar(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBar(isVisible: Boolean) {
        if (isVisible){
            progress_bar.visibility = View.VISIBLE
        }else{
            progress_bar.visibility = View.INVISIBLE
        }
    }
}