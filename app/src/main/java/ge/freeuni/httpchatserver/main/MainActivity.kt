package ge.freeuni.httpchatserver.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import ge.freeuni.httpchatserver.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainContract.View {
    private var serverUp = false
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        presenter = MainPresenter(this, this)
        serverButton.setOnClickListener {
            serverUp = if (!serverUp) {
                presenter.serverUp()
                true
            } else {
                presenter.serverDown()
                false
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun informServerUp() {
        serverTextView.text = getString(R.string.server_running)
        serverButton.text = getString(R.string.stop_server)
    }

    override fun informServerDown() {
        serverTextView.text = getString(R.string.server_down)
        serverButton.text = getString(R.string.start_server)
    }
}