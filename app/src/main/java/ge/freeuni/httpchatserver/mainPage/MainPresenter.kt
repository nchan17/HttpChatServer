package ge.freeuni.httpchatserver.mainPage

import android.content.Context
import com.sun.net.httpserver.HttpServer
import java.io.IOException
import java.net.InetSocketAddress
import java.util.concurrent.Executors
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import ge.freeuni.httpchatserver.mainPage.MainContract.Model
import ge.freeuni.httpchatserver.model.Message
import ge.freeuni.httpchatserver.model.User
import org.json.JSONObject
import java.io.InputStream
import java.util.*

class MainPresenter(var context: Context, mainView: MainContract.View) : MainContract.Presenter {
    private val port = 5000
    private var mHttpServer: HttpServer? = null
    private val model: Model = MainModel(context)
    private val mainView: MainContract.View = mainView

    init{
        val user1 = User("NinoTest", "write", null)
        val user2 = User("TestUser", "check", null)
        if(model.userExists(user1.name) == 1 || model.userExists(user2.name) == 1){
            model.insertAllUsers(user1, user2)
            val message1 = Message(user1.name, user2.name, "efefhjefhjefejk ekjfefhew ewkfhekjfhe ", 12.01)
            val message2 = Message(user2.name, user1.name, "efefhdeeeeeeeefhjefejk eeeeeeekfhekjfhe ", 12.01)
            val message3 = Message(user1.name, user2.name, "efefhjefhjefejk ekjfefhew ewkfhekjfhe ", 12.01)
            val message4 = Message(user2.name, user1.name, "efefhdeeeeeeeefhjefejk eeeeeeekfhekjfhe ", 12.01)
            val message5 = Message(user2.name, user1.name, "efefhdeeeeeeeefhjefejk eeeeeeekfhekjfhe ", 12.01)
            val message6 = Message(user1.name, user2.name, "efefhjefhjefejk efefhdeeeeeeeefhjefejk ewkfhekjfhe ", 12.01)
            val message7 = Message(user1.name, user2.name, "efefhdeeeeeeeefhjefejk ekjfefhew ewkfhekjfhe ", 12.01)
            model.insertAllMessages(message1, message2, message3, message4, message5, message6, message7)
        }

    }

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