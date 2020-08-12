package ge.freeuni.httpchatserver.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ge.freeuni.httpchatserver.model.Message
import ge.freeuni.httpchatserver.model.User

@Database(entities = [User::class, Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}