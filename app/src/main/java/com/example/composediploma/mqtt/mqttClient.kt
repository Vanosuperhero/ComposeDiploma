package com.example.composediploma.mqtt

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import info.mqtt.android.service.MqttAndroidClient
//import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
//import info.mqtt.android.service.MqttAndroidClient;


lateinit var mqttClient: MqttAndroidClient



fun connect(context: Context) {
    val serverURI = "tcp://10.2.173.138:1883"
    mqttClient = MqttAndroidClient(context, serverURI, "kotlin_client",)
    mqttClient.setCallback(object : MqttCallback {
        override fun messageArrived(topic: String?, message: MqttMessage?) {
            Log.d("AndroidMqttClient", "Receive message: ${message.toString()} from topic: $topic")
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
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("AndroidMqttClient", "Connection failure ${exception?.cause}")
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