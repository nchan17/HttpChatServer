package ge.freeuni.httpchatserver.main

import android.content.Context
import com.sun.net.httpserver.HttpServer
import java.io.IOException
import java.net.InetSocketAddress
import java.util.concurrent.Executors
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import ge.freeuni.httpchatserver.main.MainContract.Model
import org.json.JSONObject
import java.io.InputStream
import java.util.*

class MainPresenter(var context: Context, mainView: MainContract.View) : MainContract.Presenter {
    private val port = 5000
    private var mHttpServer: HttpServer? = null
    private val model: Model = MainModel(context)
    private val mainView: MainContract.View = mainView

    override fun serverUp() {
        try {
            mHttpServer = HttpServer.create(InetSocketAddress(port), 0)
            mHttpServer!!.executor = Executors.newCachedThreadPool()

            mHttpServer!!.createContext("/", rootHandler)
            mHttpServer!!.createContext("/index", rootHandler)
            mHttpServer!!.createContext("/introduce", introduceHandler)
            // Handle /messages endpoint
            mHttpServer!!.createContext("/messages", messageHandler)
            mHttpServer!!.start()//startServer server;
            mainView.informServerUp()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun serverDown() {
        if (mHttpServer != null) {
            mHttpServer!!.stop(0)
            mainView.informServerDown()
        }
    }


    private fun streamToString(inputStream: InputStream): String {
        val s = Scanner(inputStream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }

    private fun sendResponse(httpExchange: HttpExchange, responseText: String) {
        httpExchange.sendResponseHeaders(200, responseText.length.toLong())
        val os = httpExchange.responseBody
        os.write(responseText.toByteArray())
        os.close()
    }


    // Handler for root endpoint
    private val rootHandler = HttpHandler { exchange ->
        run {
            // Get request method
            when (exchange!!.requestMethod) {
                "GET" -> {
                    sendResponse(exchange, "true")
                }

            }
        }

    }

    // Handler for introduction endpoint
    private val introduceHandler = HttpHandler { exchange ->
        run {
            // Get request method
            when (exchange!!.requestMethod) {
                "GET" -> {
                    sendResponse(exchange, "true")
                }

            }
        }

    }

    private val messageHandler = HttpHandler { httpExchange ->
        run {
            when (httpExchange!!.requestMethod) {
                "GET" -> {
                    // Get all messages
                    sendResponse(httpExchange, "Would be all messages stringified json")
                }
                "POST" -> {
                    val inputStream = httpExchange.requestBody

                    val requestBody = streamToString(inputStream)
                    val jsonBody = JSONObject(requestBody)
                    // save message to database

                    //for testing
                    sendResponse(httpExchange, jsonBody.toString())

                }

            }
        }
    }

}