package top.krasus1966.contactlist.event

import top.krasus1966.contactlist.domain.Contact
import top.krasus1966.contactlist.domain.SortType

data class ContactState(
    val contacts: List<Contact> = emptyList(),
    val name: String = "",
    val nickName: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.NAME
)
