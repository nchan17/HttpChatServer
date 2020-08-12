package ge.freeuni.httpchatserver.mainPage

import android.content.Context
import androidx.room.Room
import ge.freeuni.httpchatserver.database.AppDatabase
import ge.freeuni.httpchatserver.database.UserDao
import ge.freeuni.httpchatserver.model.Message
import ge.freeuni.httpchatserver.model.User


class MainModel(var context: Context) : MainContract.Model {
    private var db: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "database-name")
            .fallbackToDestructiveMigration().allowMainThreadQueries()
            .build()
    private var userDao: UserDao = db.userDao()

    override fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    override fun loadMessages(fromUser: String, toUser: String): List<Message> {
        return userDao.loadMessages(fromUser, toUser)
    }

    override fun userExists(name: String): Int {
        return userDao.userExists(name)
    }

    override fun insertAllMessages(vararg message: Message) {
        return userDao.insertAllMessages(*message)
    }

    override fun insertAllUsers(vararg user: User) {
        return userDao.insertAllUsers(*user)
    }

    override fun delete(user: User) {
        return userDao.delete(user)
    }

}