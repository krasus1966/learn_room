package top.krasus1966.contactlist.config

import androidx.room.Database
import androidx.room.RoomDatabase
import top.krasus1966.contactlist.dao.ContactDAO
import top.krasus1966.contactlist.domain.Contact

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase : RoomDatabase() {
    abstract val dao: ContactDAO
}