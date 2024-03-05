package com.lilong.kknetwork

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import com.lilong.kknetwork.databinding.ActivityMainBinding
import com.lilong.kknetwork.network.apiServiceRx
import com.lilong.kknetwork.network.repository.HttpRequestCoroutine
import com.lilong.kknetwork.network.response.AmapResponse
import com.lilong.kknetwork.network.response.Regeocode
import com.lilong.network.requestKotlin
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        requestAddress()
//        requestAddressRx()
    }


    private fun requestAddressRx() {
        //北京市朝阳区阜通东大街6号转换后经纬度：116.480881,39.989410
        val map = hashMapOf<String, String>()
        //固定key
        map.put("key", "ca6ca4ad54fff4ea7c1e070ebf7d83d5")
//        map.put("location","116.480881,39.989410")
        //小数点后不能超过6位
        map.put("location", "106.743723,29.640969")
        apiServiceRx.getAddress(map).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe (object :Observer<AmapResponse<Regeocode>>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }

                override fun onNext(it: AmapResponse<Regeocode>) {
                    Log.d("TAG", "onNext:$it ")
                }

            })
    }

    /**
     * 逆地址编码
     */
    private fun requestAddress() {
        //北京市朝阳区阜通东大街6号转换后经纬度：116.480881,39.989410
        val map = hashMapOf<String, String>()
        //固定key
        map.put("key", "ca6ca4ad54fff4ea7c1e070ebf7d83d5")
//        map.put("location","116.480881,39.989410")
        //小数点后不能超过6位
        map.put("location", "106.743723,29.640969")
        val job = requestKotlin(block = { HttpRequestCoroutine.getAddress(map) }, success = {
            Log.d("TAG", "requestAddress:$it ")
//            findViewById<TextView>(R.id.tv_text).text = it?.formatted_address
        }, error = {
        }, true, dialogBefore = {
//            showLoadingExt()
        }, dialogSuccess = {
//            dismissLoadingExt()
        }, dialogError = {
//            dismissLoadingExt()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}