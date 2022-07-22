package com.example.composediploma.mqtt

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.BoardiesITSolutions.AndroidMySQLConnector.*
import com.BoardiesITSolutions.AndroidMySQLConnector.Exceptions.InvalidSQLPacketException
import com.BoardiesITSolutions.AndroidMySQLConnector.Exceptions.MySQLConnException
import com.BoardiesITSolutions.AndroidMySQLConnector.Exceptions.MySQLException
import com.madrapps.plot.line.DataPoint
import java.io.IOException


class MySqlViewModel : ViewModel() {

    private var _energyToday = mutableStateOf(0.0)
    var energyToday = _energyToday
    private var _listOfDataPointSes = mutableStateOf(listOf(DataPoint(0f,0f)))
    var listOfDataPointSes = _listOfDataPointSes
    private var _listOfDataPointMeteo = mutableStateOf(listOf(DataPoint(0f,0f)))
    var listOfDataPointMeteo = _listOfDataPointMeteo

    private var _connected = mutableStateOf(false)
    var connected = _connected

    var mysqlConnection: Connection = Connection("62.109.30.150", "ivan_mpei", "ivA4231n", 3306, "mpei_ses", object :IConnectionInterface{
        override fun actionCompleted() {
            _connected.value = true
            Log.d("mysqlConnection","complete")
        }

        override fun handleInvalidSQLPacketException(ex: InvalidSQLPacketException?) {
            _connected.value = false
            Log.d("mysqlConnection","invalid ${ex}")
        }

        override fun handleMySQLException(ex: MySQLException?) {
            _connected.value = false
            Log.d("mysqlConnection","msql ${ex}")
        }

        override fun handleIOException(ex: IOException?) {
            _connected.value = false
            Log.d("mysqlConnection","io ${ex}")
        }

        override fun handleMySQLConnException(ex: MySQLConnException?) {
            _connected.value = false
            Log.d("mysqlConnection","conn ${ex}")
        }

        override fun handleException(exception: Exception?) {
            _connected.value = false
            Log.d("mysqlConnection","handle ${exception}")
        }
    })

    fun state(date:String) {
        var statement = mysqlConnection.createStatement()
        var getEnergyToday =
            statement.executeQuery("\n" +
                    "SELECT `energy_today(кВт*ч)` FROM mpei_ses.invertor_dump WHERE DATE(mpei_ses.invertor_dump.DATE) = '$date' ORDER BY mpei_ses.invertor_dump.DATE DESC LIMIT 1;", object : IResultInterface {

                override fun executionComplete(resultSet: ResultSet?) {
                    val energyToday = resultSet?.nextRow?.getDouble("energy_today(ÐºÐ\u0092Ñ\u0082*Ñ\u0087)")
                    Log.d("mysqlConnection", "result ${energyToday}")
                    if (energyToday != null) {
                        _energyToday.value = energyToday
                    }
                }

                override fun handleInvalidSQLPacketException(ex: InvalidSQLPacketException?) {
                    Log.d("mysqlConnection", "inv ${ex}")
                }

                override fun handleMySQLException(ex: MySQLException?) {
                    Log.d("mysqlConnection", "my ${ex}")
                }

                override fun handleIOException(ex: IOException?) {
                    Log.d("mysqlConnection", "io ${ex}")
                }

                override fun handleMySQLConnException(ex: MySQLConnException?) {
                    Log.d("mysqlConnection", "conn ${ex}")
                }

                override fun handleException(ex: Exception?) {
                    Log.d("mysqlConnection", "handle ${ex}")
                }
            })
//        var listOfDataPoint = mutableListOf<DataPoint>()
//        var getRangeOfPower =
            statement.executeQuery("\n" +
                    "SELECT DATE(mpei_ses.invertor_dump.DATE) AS date, HOUR(mpei_ses.invertor_dump.DATE) AS hour, AVG(`output_power(Вт)`) FROM mpei_ses.invertor_dump WHERE DATE(mpei_ses.invertor_dump.DATE) BETWEEN '$date' AND '$date' GROUP BY 1, 2", object : IResultInterface {
//                SELECT DATE(mpei_meteo.meteo_dump_solar_temp_press.DATE) AS date,
//                HOUR(mpei_meteo.meteo_dump_solar_temp_press.DATE) AS hour, AVG(SR_1M) as sr1_avg FROM
//                mpei_meteo.meteo_dump_solar_temp_press WHERE
//                date
//                BETWEEN '2022-04-01' AND '2022-04-03'
//                group by 1, 2
                override fun executionComplete(resultSetRange: ResultSet?) {
                    val columnNameDay = resultSetRange?.fields?.get(0)?.columnName
                    val columnNameHour = resultSetRange?.fields?.get(1)?.columnName
                    val columnNamePower = resultSetRange?.fields?.get(2)?.columnName


//                    val row = resultSetRange?.nextRow?.getString("$columnName")
//                    Log.d("mysqlConnection", "result ${resultSetRange?.fields?.get(0)?.columnType} ${LocalDate.parse("2022-01-06").toD)}")

//                    val value: String = this.getString(column)
//                    return value.toInt()

                    val numRows = resultSetRange?.numRows
//                    val listOfDays = mutableListOf<String>()
                    val listOfHours = mutableListOf<Int>()
                    val listOfPower = mutableListOf<Double>()

                    val listOfData = mutableListOf<DataPoint>()
//                    val listOfDataPower = mutableListOf<DataPoint>()
                    Log.d("mysqlConnection", "inv ${date}")
                    Log.d("mysqlConnection", "inv ${numRows}")
                    Log.d("mysqlConnection", "inv ${columnNamePower} $columnNameDay $columnNameHour")



                    if(numRows != null) {
                        for (i in 1..numRows) {
                            val row = resultSetRange.nextRow
                            val power = row.getDouble("$columnNamePower")
                            val hour = row.getInt("$columnNameHour")
//                            listOfPower.add(row.getDouble("$columnNamePower"))
//                            listOfData.add(row.getString("$columnNameDay"))
//                            listOfHours.add(row.getInt("$columnNameHour"))
                            listOfData.add(DataPoint(hour.toFloat(),power.toFloat()))
                        }
                        Log.d("mysqlConnection", "${listOfPower} ${listOfHours} $listOfData")
                    }
                        _listOfDataPointSes.value = listOfData
                }

                override fun handleInvalidSQLPacketException(ex: InvalidSQLPacketException?) {
                    Log.d("mysqlConnection", "inv ${ex}")
                }

                override fun handleMySQLException(ex: MySQLException?) {
                    Log.d("mysqlConnection", "my ${ex}")
                }

                override fun handleIOException(ex: IOException?) {
                    Log.d("mysqlConnection", "io ${ex}")
                }

                override fun handleMySQLConnException(ex: MySQLConnException?) {
                    Log.d("mysqlConnection", "conn ${ex}")
                }

                override fun handleException(ex: Exception?) {
                    Log.d("mysqlConnection", "handle ${ex}")
                }
            })

        statement.executeQuery("\n" +
                "SELECT DATE(mpei_meteo.meteo_dump_solar_temp_press.DATE) AS date, HOUR(mpei_meteo.meteo_dump_solar_temp_press.DATE) AS hour, AVG(`SR_1M`) FROM mpei_meteo.meteo_dump_solar_temp_press WHERE DATE(mpei_meteo.meteo_dump_solar_temp_press.DATE) BETWEEN '$date' AND '$date' GROUP BY 1, 2", object : IResultInterface {
            //                SELECT DATE(mpei_meteo.meteo_dump_solar_temp_press.DATE) AS date,
//                HOUR(mpei_meteo.meteo_dump_solar_temp_press.DATE) AS hour, AVG(SR_1M) as sr1_avg FROM
//                mpei_meteo.meteo_dump_solar_temp_press WHERE
//                date
//                BETWEEN '2022-04-01' AND '2022-04-03'
//                group by 1, 2
            override fun executionComplete(resultSetRange: ResultSet?) {
                val columnNameDay = resultSetRange?.fields?.get(0)?.columnName
                val columnNameHour = resultSetRange?.fields?.get(1)?.columnName
                val columnNamePower = resultSetRange?.fields?.get(2)?.columnName


//                    val row = resultSetRange?.nextRow?.getString("$columnName")
//                    Log.d("mysqlConnection", "result ${resultSetRange?.fields?.get(0)?.columnType} ${LocalDate.parse("2022-01-06").toD)}")

//                    val value: String = this.getString(column)
//                    return value.toInt()

                val numRows = resultSetRange?.numRows
//                    val listOfDays = mutableListOf<String>()
                val listOfHours = mutableListOf<Int>()
                val listOfPower = mutableListOf<Double>()

                val listOfData = mutableListOf<DataPoint>()
//                    val listOfDataPower = mutableListOf<DataPoint>()
                Log.d("mysqlConnection", "met ${date}")
                Log.d("mysqlConnection", "met ${numRows}")
                Log.d("mysqlConnection", "met ${columnNamePower} $columnNameDay $columnNameHour")



                if(numRows != null) {
                    for (i in 1..numRows) {
                        val row = resultSetRange.nextRow
                        val power = row.getDouble("$columnNamePower")
                        val hour = row.getInt("$columnNameHour")
//                            listOfPower.add(row.getDouble("$columnNamePower"))
//                            listOfData.add(row.getString("$columnNameDay"))
//                            listOfHours.add(row.getInt("$columnNameHour"))
                        if (hour in 4..20) {
                            listOfData.add(DataPoint(hour.toFloat(), power.toFloat()))
                        }
                    }
                    Log.d("mysqlConnection", "${listOfPower} ${listOfHours} $listOfData")
                }
                _listOfDataPointMeteo.value = listOfData
            }

            override fun handleInvalidSQLPacketException(ex: InvalidSQLPacketException?) {
                Log.d("mysqlConnection", "inv ${ex}")
            }

            override fun handleMySQLException(ex: MySQLException?) {
                Log.d("mysqlConnection", "my ${ex}")
            }

            override fun handleIOException(ex: IOException?) {
                Log.d("mysqlConnection", "io ${ex}")
            }

            override fun handleMySQLConnException(ex: MySQLConnException?) {
                Log.d("mysqlConnection", "conn ${ex}")
            }

            override fun handleException(ex: Exception?) {
                Log.d("mysqlConnection", "handle ${ex}")
            }
        })
    }
}

