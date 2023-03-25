package top.krasus1966.contactlist.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import top.krasus1966.contactlist.domain.Contact


@Dao
interface ContactDAO {
    // 新增或更新
    @Upsert
    suspend fun saveContact(data: Contact)

    // 删除
    @Delete
    suspend fun deleteContact(data: Contact)

    // 查询，写SQL
    @Query("SELECT * FROM ks_contact ORDER BY name ASC")
    fun queryContactByName(): Flow<List<Contact>>

    @Query("SELECT * FROM ks_contact ORDER BY nickName ASC")
    fun queryContactByNickName(): Flow<List<Contact>>

    @Query("SELECT * FROM ks_contact ORDER BY phoneNumber ASC")
    fun queryContactByPhoneNumber(): Flow<List<Contact>>

    // 使用 :args 可以在sql语句中传递数据，与mybatis中的#{}相同
//    @Query("SELECT * FROM ks_contact ORDER BY  :column  ASC")
//    fun queryContactBySortType(column:String) : Flow<List<Contact>>
}