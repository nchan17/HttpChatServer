package ge.freeuni.httpchatserver

import android.app.Application
import androidx.room.Room

class MyApplication : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, AppDatabase::class.java, "database-name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}