package com.example.composediploma.mqtt

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.math.RoundingMode
import kotlin.math.roundToInt


class MQTTViewModel : ViewModel() {



    private var _ta = mutableStateOf("")
    var ta = _ta
    private var _ws1avg = mutableStateOf("")
    var ws1avg = _ws1avg
    private var _pa = mutableStateOf("")
    var pa = _pa
    private var _rh = mutableStateOf("")
    var rh = _rh
    private var _pr24h = mutableStateOf("")
    var pr24h = _pr24h
    private var _v = mutableStateOf("")
    var v = _v

    private var _pv = mutableStateOf(0.0)
    var pv = _pv
    private var _consumerBef = mutableStateOf(0.0)
    var consumerBef = _consumerBef
    private var _consumer = mutableStateOf(0.0)
    var consumer = _consumer
    private var _fromGrid = mutableStateOf(0.0)
    var fromGrid = _fromGrid
    private var _toGrid = mutableStateOf(0.0)
    var toGrid = _toGrid
    
    var fsta = mutableStateOf(false)
    var fstc = mutableStateOf(false)
    var fatc = mutableStateOf(false)
    var fgtc = mutableStateOf(false)

    private var _connect = mutableStateOf(false)
    private lateinit var mqttClient: MqttAndroidClient

    fun connect(context: Context) {
        val serverURI = "tcp://192.168.1.132:1883"
        mqttClient = MqttAndroidClient(context, serverURI, "kotlin_client",)
        mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d("AndroidMqttClient", "Receive message: ${message.toString()} from topic: $topic")
                when (topic){
                    "mpei/der/weather_station/TA/value" -> _ta.value = message.toString()
                    "mpei/der/weather_station/WS1AVG/value" -> _ws1avg.value = message.toString()
                    "mpei/der/weather_station/PA/value" -> _pa.value = message.toString()
                    "mpei/der/weather_station/RH/value" -> _rh.value = message.toString()
                    "mpei/der/weather_station/PR24H/value" -> _pr24h.value = message.toString()
                    "mpei/der/weather_station/V/value" -> _v.value = message.toString()
                    "mpei/der/solar_river/output_power/value" -> _pv.value = message.toString().toDouble()/1000
                    "mpei/der/solar_river/pv1_input_power/value" -> _consumerBef.value = message.toString().toDouble()/1000
                }
                consumer.value = consumerBef.value.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
                _fromGrid.value = (consumer.value-pv.value).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
                _toGrid.value = _fromGrid.value*-1
                Log.d("view", "fsta-${fsta.value}, fstc-${fstc.value}, fatc-${fatc.value}, fgtc-${fgtc.value}")
            }

            override fun connectionLost(cause: Throwable?) {
                Log.d("AndroidMqttClient", "Connection lost ${cause.toString()}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })
        val options = MqttConnectOptions()
        try {
            mqttClient.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("AndroidMqttClient", "Connection success")
                    _connect.value = true
                    subscribe("mpei/der/weather_station/WS1AVG/value")
                    subscribe("mpei/der/weather_station/TA/value")
                    subscribe("mpei/der/weather_station/PA/value")
                    subscribe("mpei/der/weather_station/RH/value")
                    subscribe("mpei/der/weather_station/PR24H/value")
                    subscribe("mpei/der/weather_station/V/value")
                    subscribe("mpei/der/solar_river/output_power/value")
//                    в место абб пока одна панель:
                    subscribe("mpei/der/solar_river/pv1_input_power/value")

                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("AndroidMqttClient", "Connection failure ${exception?.cause}")
                    _connect.value = false
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun subscribe(topic: String, qos: Int = 1) {
        try {
            mqttClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("AndroidMqttClient", "Subscribed to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("AndroidMqttClient", "Failed to subscribe $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

//    fun unsubscribe(topic: String) {
//        try {
//            mqttClient.unsubscribe(topic, null, object : IMqttActionListener {
//                override fun onSuccess(asyncActionToken: IMqttToken?) {
//                    Log.d("AndroidMqttClient", "Unsubscribed to $topic")
//                }
//
//                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
//                    Log.d("AndroidMqttClient", "Failed to unsubscribe $topic")
//                }
//            })
//        } catch (e: MqttException) {
//            e.printStackTrace()
//        }
//    }
//
//    fun publish(topic: String, msg: String, qos: Int = 1, retained: Boolean = false) {
//        try {
//            val message = MqttMessage()
//            message.payload = msg.toByteArray()
//            message.qos = qos
//            message.isRetained = retained
//            mqttClient.publish(topic, message, null, object : IMqttActionListener {
//                override fun onSuccess(asyncActionToken: IMqttToken?) {
//                    Log.d("AndroidMqttClient", "$msg published to $topic")
//                }
//
//                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
//                    Log.d("AndroidMqttClient", "Failed to publish $msg to $topic")
//                }
//            })
//        } catch (e: MqttException) {
//            e.printStackTrace()
//        }
//    }
//
//    fun disconnect() {
//        try {
//            mqttClient.disconnect(null, object : IMqttActionListener {
//                override fun onSuccess(asyncActionToken: IMqttToken?) {
//                    Log.d("AndroidMqttClient", "Disconnected")
//                }
//
//                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
//                    Log.d("AndroidMqttClient", "Failed to disconnect")
//                }
//            })
//        } catch (e: MqttException) {
//            e.printStackTrace()
//        }
//    }
}