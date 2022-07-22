package com.example.composediploma.mqtt

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private var _pvBef = mutableStateOf(0.0)
    var pvBef = _pvBef
    private var _pv = mutableStateOf(0.0)
    var pv = _pv
    private var _pv2 = mutableStateOf(0.0)
    var pv2 = _pv2
    private var _pv1Bef = mutableStateOf(0.0)
    var pv1Bef = _pv1Bef
    private var _pv1 = mutableStateOf(0.0)
    var pv1 = _pv1
    private var _fromGrid = mutableStateOf(0.0)
    var fromGrid = _fromGrid
    private var _toGrid = mutableStateOf(0.0)
    var toGrid = _toGrid

    private var _pv1_voltage = mutableStateOf("")
    var pv1_voltage = _pv1_voltage
    private var _pv2_voltage = mutableStateOf("")
    var pv2_voltage = _pv2_voltage
    private var _pv1_current = mutableStateOf("")
    var pv1_current = _pv1_current
    private var _pv2_current = mutableStateOf("")
    var pv2_current = _pv2_current
    private var _grid_voltage = mutableStateOf("")
    var grid_voltage = _grid_voltage
    private var _grid_current = mutableStateOf("")
    var grid_current = _grid_current
    private var _grid_frequency = mutableStateOf("")
    var grid_frequency = _grid_frequency

    var fsta = mutableStateOf(false)
    var fstc = mutableStateOf(false)
    var fatc = mutableStateOf(false)
    var fgtc = mutableStateOf(false)

    private var _connected = mutableStateOf(false)
    var connected = _connected
    private lateinit var mqttClient: MqttAndroidClient

    fun connect(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val serverURI = "tcp://192.168.1.132:1883"
//            val serverURI = "tcp://broker.emqx.io:1883"
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
                        "mpei/der/solar_river/output_power/value" -> _pvBef.value = message.toString().toDouble()/1000
                        "mpei/der/solar_river/pv1_input_power/value" -> _pv1Bef.value = message.toString().toDouble()/1000
                        "mpei/der/solar_river/pv2_input_power/value" -> _pv2.value = message.toString().toDouble()/1000
                        "mpei/der/solar_river/pv1_voltage/value" -> _pv1_voltage.value = message.toString()
                        "mpei/der/solar_river/pv2_voltage/value" -> _pv2_voltage.value = message.toString()
                        "mpei/der/solar_river/pv1_current/value" -> _pv1_current.value = message.toString()
                        "mpei/der/solar_river/pv2_current/value" -> _pv2_current.value = message.toString()
                        "mpei/der/solar_river/grid_voltage/value" -> _grid_voltage.value = message.toString()
                        "mpei/der/solar_river/grid_current/value" -> _grid_current.value = message.toString()
                        "mpei/der/solar_river/grid_frequency/value" -> _grid_frequency.value = message.toString()
                    }
                    _pv1.value = pv1Bef.value.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
                    _pv.value = pvBef.value.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
                    _fromGrid.value = (pv1.value-pv.value).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
                    _toGrid.value = _fromGrid.value*-1



                    //Задаем условия для смены фона
                    fgtc.value = pv.value <= 0 && fromGrid.value > 0 && pv1.value > 0 && toGrid.value <= 0
                    fatc.value = pv.value > 0 && fromGrid.value > 0 && pv1.value > 0 && toGrid.value <= 0
                    fstc.value = pv.value > 0 && fromGrid.value <= 0 && toGrid.value <= 0 && pv1.value > 0
                    fsta.value = toGrid.value > 0 && pv.value > 0 && fromGrid.value <= 0 && pv1.value > 0

                    Log.d("view", "fsta-${fsta.value}, fstc-${fstc.value}, fatc-${fatc.value}, fgtc-${fgtc.value}")
                }
                override fun connectionLost(cause: Throwable?) {
                    _connected.value = false
                    Toast.makeText(context,"Соединение потеряно",Toast.LENGTH_SHORT).show()
                    Log.d("AndroidMqttClient", "Connection lost ${cause.toString()}")
                }
                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                }
            })
            val options = MqttConnectOptions()
            try {
                mqttClient.connect(options, null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Toast.makeText(context,"Соединение установлено",Toast.LENGTH_SHORT).show()
                        Log.d("AndroidMqttClient", "Connection success")
                        _connected.value = true
                        subscribe("mpei/der/weather_station/WS1AVG/value")
                        subscribe("mpei/der/weather_station/TA/value")
                        subscribe("mpei/der/weather_station/PA/value")
                        subscribe("mpei/der/weather_station/RH/value")
                        subscribe("mpei/der/weather_station/PR24H/value")
                        subscribe("mpei/der/weather_station/V/value")
                        subscribe("mpei/der/solar_river/output_power/value")
//                        вместо абб пока одна панель:
                        subscribe("mpei/der/solar_river/pv1_input_power/value")
                        subscribe("mpei/der/solar_river/pv2_input_power/value")
                        subscribe("mpei/der/solar_river/pv1_voltage/value")
                        subscribe("mpei/der/solar_river/pv2_voltage/value")
                        subscribe("mpei/der/solar_river/pv1_current/value")
                        subscribe("mpei/der/solar_river/pv2_current/value")
                        subscribe("mpei/der/solar_river/grid_voltage/value")
                        subscribe("mpei/der/solar_river/grid_current/value")
                        subscribe("mpei/der/solar_river/grid_frequency/value")
                    }
                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Toast.makeText(context,"Соединение не установлено",Toast.LENGTH_SHORT).show()
                        Log.d("AndroidMqttClient", "Connection failure ${exception?.cause}")
                        _connected.value = false
                    }
                })
            } catch (e: MqttException) {
                e.printStackTrace()
            }
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