package ge.freeuni.httpchatserver.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ge.freeuni.httpchatserver.model.Message
import ge.freeuni.httpchatserver.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM messages WHERE fromUser LIKE :fromUser AND toUser Like :toUser")
    fun loadMessages(fromUser :String, toUser: String): List<Message>

    @Insert
    fun insertAllUsers(vararg users: User)

    @Query("SELECT COUNT(*) FROM users WHERE name LIKE :name")
    fun userExists(name :String): Int

    @Insert
    fun insertAllMessages(vararg message: Message)

    @Delete
    fun delete(user: User)
}