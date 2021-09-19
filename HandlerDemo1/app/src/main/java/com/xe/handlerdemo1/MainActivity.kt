package com.xe.handlerdemo1

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View

/**
 * Created by Administrator on 2021/9/19.
 */
class MainActivity: AppCompatActivity() {
    var mH1: Handler? = null
    var mH2: Handler? = null
    var mH3: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initH1()
        initH2()
        initH3();
    }
    class MyHandler: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            Log.d("MainActivity","MyHandler所在当前线程为：" + Thread.currentThread().name)
        }
    }
    fun initH3() {
        mH3 = MyHandler()
    }

    fun initH2() {
        mH2 = Handler(MyCallback())
    }

    class MyCallback: Handler.Callback {
        override fun handleMessage(msg: Message?): Boolean {
            Log.d("MainActivity","MyCallback所在当前线程为：" + Thread.currentThread().name)
            return true
        }
    }
    fun initH1() {
        mH1 = Handler()
    }
    class MyR1: Runnable {
        override fun run() {
            Log.d("MainActivity","MyR1所在当前线程为：" + Thread.currentThread().name)
        }
    }
    inner class T1: Thread() {
        override fun run() {
            super.run()
            mH1!!.post(MyR1())
        }
    }
    inner class T2: Thread() {
        override fun run() {
            super.run()
            mH2!!.sendEmptyMessage(0)
        }
    }
    inner class T3: Thread() {
        override fun run() {
            super.run()
            mH3!!.sendEmptyMessage(0)
        }
    }
    fun onClick(v: View) {
        when(v.id) {
            R.id.btn_1 -> {
               var t1: T1 = T1();
                t1.start()
            }
            R.id.btn_2 -> {
                var t2: T2 = T2();
                t2.start()
            }
            R.id.btn_3 -> {
                var t3: T3 = T3();
                t3.start()
            }
        }
    }
}