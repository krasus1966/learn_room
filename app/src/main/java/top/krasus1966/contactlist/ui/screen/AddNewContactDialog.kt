package top.krasus1966.contactlist.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.krasus1966.contactlist.event.ContactEvent
import top.krasus1966.contactlist.event.ContactState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactDialog(
    state: ContactState, onEvent: (ContactEvent) -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(modifier = modifier,
        onDismissRequest = { onEvent(ContactEvent.HideDialog) },
        title = { Text(text = "添加联系人") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(value = state.name,
                    onValueChange = { onEvent(ContactEvent.SetName(it)) },
                    placeholder = {
                        Text(text = "姓名")
                    })
                TextField(value = state.nickName,
                    onValueChange = { onEvent(ContactEvent.SetNickName(it)) },
                    placeholder = {
                        Text(text = "昵称")
                    })
                TextField(value = state.phoneNumber,
                    onValueChange = { onEvent(ContactEvent.SetPhoneNumber(it)) },
                    placeholder = {
                        Text(text = "手机号码")
                    })
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = { onEvent(ContactEvent.SaveContact) }) {
                    Text(text = "保存")
                }
            }
        }
    )
}