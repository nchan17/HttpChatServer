package ge.freeuni.httpchatserver

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey val name: String,
    val about: String?,
    val img: Int?
) : Parcelable {
    override fun toString(): String {
        return "$name $about ${img.toString()} \n"
    }
}

@Parcelize
@Entity(
    tableName = "messages", foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["name"],
        childColumns = ["fromUser"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = User::class,
        parentColumns = ["name"],
        childColumns = ["toUser"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Message(
    val fromUser: String,
    val toUser: String,
    val message: String?,
    val time: Double
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "$id $fromUser $toUser $message ${time.toString()} \n"
    }
}