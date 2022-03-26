package com.subhambnikash.wifitest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.net.wifi.WifiNetworkSuggestion
import android.net.wifi.hotspot2.PasspointConfiguration
import android.os.Build
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var button2: Button
    lateinit var button: Button
    lateinit var webView: WebView


    private lateinit var callback:ConnectivityManager.NetworkCallback

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button=findViewById(R.id.button)
        webView=findViewById(R.id.webView)
        button2=findViewById(R.id.button2)
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        button.setOnClickListener {


//            val suggestion2 = WifiNetworkSuggestion.Builder()
//                .setSsid("POCO X3")
//                .setWpa2Passphrase("12345678sbb")
//                .setIsAppInteractionRequired(true) // Optional (Needs location permission)
//                .build()
//
//
//
////            val passpointConfig = PasspointConfiguration(); // configure passpointConfig to include a valid Passpoint configuration
////            val suggestion4 = WifiNetworkSuggestion.Builder()
////                .setPasspointConfig(passpointConfig)
////                .setIsAppInteractionRequired(true) // Optional (Needs location permission)
////                .build();
//
//            val suggestionsList = listOf(suggestion2);
//
//            val wifiManager = this.getSystemService(Context.WIFI_SERVICE) as WifiManager;
//            wifiManager.removeNetworkSuggestions(suggestionsList)
//            val status = wifiManager.addNetworkSuggestions(suggestionsList);
//            if (status != WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
//                Toast.makeText(this,"enter",Toast.LENGTH_SHORT).show()
//                val status = wifiManager.addNetworkSuggestions(suggestionsList);
//            }
//
//            // Optional (Wait for post connection broadcast to one of your suggestions)
//            val intentFilter = IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//            val broadcastReceiver = object : BroadcastReceiver() {
//                override fun onReceive(context: Context, intent: Intent) {
//                    if (!intent.action.equals(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)) {
//                        return;
//                    }
//                    // do post connect processing here
//                }
//            };
//            this.registerReceiver(broadcastReceiver, intentFilter)


            callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    connectivityManager.bindProcessToNetwork(network)
                }
                override fun onUnavailable() {
                    super.onUnavailable()
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    connectivityManager.bindProcessToNetwork(null)
                    connectivityManager.unregisterNetworkCallback(callback)
                }
            }



            val networkSpecifier = WifiNetworkSpecifier.Builder()
                .setSsid("BiswaNetwork")
                .setWpa2Passphrase("12345678")
                .build()

            val networkRequest: NetworkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .setNetworkSpecifier(networkSpecifier)
                .build()
            connectivityManager.requestNetwork(networkRequest, callback)


        }

        button2.setOnClickListener {
                 // connectivityManager.unregisterNetworkCallback(callback)
            val webSettings: WebSettings = webView.settings
            webSettings.javaScriptEnabled = true
            webView.settings.loadWithOverviewMode = true
            webView.webViewClient = WebViewClient()
            webView.loadUrl("http://192.168.137.1/chartphp/index.php")
        }





    }
}