package top.krasus1966.contactlist.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ks_contact")
data class Contact(
    val name: String,
    val nickName: String,
    val phoneNumber: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)