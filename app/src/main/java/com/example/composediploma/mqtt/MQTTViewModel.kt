package com.example.composediploma.mqtt

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*


class MQTTViewModel : ViewModel() {

    private var _message = mutableStateOf("")
    var message = _message

    private var _connect = mutableStateOf(false)
    var connect = _connect

    private lateinit var mqttClient: MqttAndroidClient

    fun connect(context: Context) {
        val serverURI = "tcp://192.168.1.132:1883"
        mqttClient = MqttAndroidClient(context, serverURI, "kotlin_client",)
        mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d("AndroidMqttClient", "Receive message: ${message.toString()} from topic: $topic")
                _message.value = message.toString()
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
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("AndroidMqttClient", "Connection failure ${exception?.cause}")
                    _connect.value = false
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
//        return mqttClient.connect().isComplete
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

    fun unsubscribe(topic: String) {
        try {
            mqttClient.unsubscribe(topic, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("AndroidMqttClient", "Unsubscribed to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("AndroidMqttClient", "Failed to unsubscribe $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic: String, msg: String, qos: Int = 1, retained: Boolean = false) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("AndroidMqttClient", "$msg published to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("AndroidMqttClient", "Failed to publish $msg to $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        try {
            mqttClient.disconnect(null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("AndroidMqttClient", "Disconnected")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("AndroidMqttClient", "Failed to disconnect")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}