package com.liujc.retrofitmock

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.android.apimock.ApiMock
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @ClassName: MainActivity
 * @author: liujc
 * @date: 2019/1/16
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apimock.setOnClickListener {
            ApiMock.getApiService("http://test.com/", ApiService::class.java)
                    .getversion()
                    .subscribe(
                            { versinfon -> Toast.makeText(this@MainActivity, versinfon.data.toString(), Toast.LENGTH_SHORT).show() },
                            { throwable -> Toast.makeText(this@MainActivity, "onFailure:" + throwable.localizedMessage, Toast.LENGTH_SHORT).show() }
                    )
        }
    }
}
