package com.mehmetpetek.veriparkimkb.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mehmetpetek.veriparkimkb.R
import com.mehmetpetek.veriparkimkb.model.req.ReqHandsake
import com.mehmetpetek.veriparkimkb.model.res.ResHandshake
import com.mehmetpetek.veriparkimkb.utils.Constants
import com.mehmetpetek.veriparkimkb.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        imkbSenetEndeks_button.setOnClickListener {
            startActivity(Intent(this, StockViewActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        callHandShakeApi()
        observeHandShakeLiveData()
    }

    private fun callHandShakeApi() {
        viewModel.getHandshake(ReqHandsake(
            deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID),
            systemVersion = Build.VERSION.SDK_INT.toString(),
            platformName = Constants.Android,
            deviceModel = Build.MODEL,
            manifacturer = Build.MANUFACTURER))
    }

    private fun observeHandShakeLiveData() {
        viewModel.handshake.observe(this,{ hndshk ->
            ResHandshake.apply {
                aesIV = hndshk.aesIV
                aesKey = hndshk.aesKey
                authorization = hndshk.authorization
            }

            imkbSenetEndeks_button.isEnabled = true

            Log.d("HandShakeLiveData", "aesIV: ${hndshk.aesIV} || aesKey: ${hndshk.aesKey} || authorization: ${hndshk.authorization} ")
        })

        viewModel.error.observe(this,{ error ->
            Toast.makeText(this,getString(R.string.app_name) + " $error",Toast.LENGTH_LONG).show()
            Log.e("HandShakeLiveData", "Error: $error")
        })
    }
}