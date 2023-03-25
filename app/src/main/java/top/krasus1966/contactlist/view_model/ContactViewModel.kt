package top.krasus1966.contactlist.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.krasus1966.contactlist.dao.ContactDAO
import top.krasus1966.contactlist.domain.Contact
import top.krasus1966.contactlist.domain.SortType
import top.krasus1966.contactlist.event.ContactEvent
import top.krasus1966.contactlist.event.ContactState

@OptIn(ExperimentalCoroutinesApi::class)
class ContactViewModel(
    private val dao: ContactDAO
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _state = MutableStateFlow(ContactState())

    private val _contacts = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.NAME -> dao.queryContactByName()
            SortType.NICK_NAME -> dao.queryContactByNickName()
            SortType.PHONE_NUMBER -> dao.queryContactByPhoneNumber()
        }
//        dao.queryContactBySortType(sortType.column)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

     val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    fun onEvent(event: ContactEvent) {
        when (event) {
            ContactEvent.SaveContact -> {
                val name = state.value.name
                val nickName = state.value.nickName
                val phoneNumber = state.value.phoneNumber

                if (name.isBlank() || nickName.isBlank() || phoneNumber.isBlank()) {
                    return
                }
                viewModelScope.launch {
                    dao.saveContact(
                        Contact(
                            name = name,
                            nickName = nickName,
                            phoneNumber = phoneNumber
                        )
                    )
                }
                _state.update {
                    it.copy(
                        isAddingContact = false,
                        name = "",
                        nickName = "",
                        phoneNumber = ""
                    )
                }
            }

            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(event.contact)
                }
            }

            is ContactEvent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is ContactEvent.SetNickName -> {
                _state.update {
                    it.copy(
                        nickName = event.nickName
                    )
                }
            }

            is ContactEvent.SetPhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }

            is ContactEvent.SortContacts -> {
                _sortType.value = event.sortType
            }

            ContactEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = true
                    )
                }
            }

            ContactEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = false
                    )
                }
            }

        }
    }
}