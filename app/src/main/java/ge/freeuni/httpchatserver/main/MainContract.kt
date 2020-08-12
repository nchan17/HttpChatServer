package ge.freeuni.httpchatserver.main

import ge.freeuni.httpchatserver.model.Message
import ge.freeuni.httpchatserver.model.User

interface MainContract{
    interface View{
        fun informServerUp()
        fun informServerDown()
    }

    interface Presenter{
        fun serverUp()
        fun serverDown()
    }

    interface Model{
        fun loadMessages(fromUser :String, toUser: String): List<Message>
        fun userExists(name :String): Int
        fun getAllUsers(): List<User>
        fun insertAllMessages(vararg message: Message)
        fun delete(user: User)
    }
}