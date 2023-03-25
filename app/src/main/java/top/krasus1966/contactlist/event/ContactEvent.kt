package top.krasus1966.contactlist.event

import top.krasus1966.contactlist.domain.Contact
import top.krasus1966.contactlist.domain.SortType

sealed interface ContactEvent {
    object SaveContact : ContactEvent
    data class SetName(val name: String) : ContactEvent
    data class SetNickName(val nickName: String) : ContactEvent
    data class SetPhoneNumber(val phoneNumber: String) : ContactEvent
    object ShowDialog : ContactEvent
    object HideDialog : ContactEvent
    data class SortContacts(val sortType: SortType) : ContactEvent
    data class DeleteContact(val contact: Contact) : ContactEvent
}