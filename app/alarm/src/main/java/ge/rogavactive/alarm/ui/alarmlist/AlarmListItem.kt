package ge.rogavactive.alarm.ui.alarmlist

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import ge.rogavactive.domain.alarm.model.AlarmListItemData
import ge.rogavactive.common.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlarmListItem(
    data: AlarmListItemData,
    onClick: () -> Unit,
    onRemove: () -> Unit,
    onDuplicate: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        RevealSwipe(
            modifier = Modifier
                .height(height = 80.dp)
                .fillMaxWidth(),
            directions = setOf(
                RevealDirection.StartToEnd,
                RevealDirection.EndToStart
            ),
            hiddenContentEnd = {
                IconButton(onClick = onRemove, modifier = Modifier.align(Alignment.CenterVertically)) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(id = R.string.delete)
                    )
                }
            },
            hiddenContentStart = {
                IconButton(onClick = onDuplicate, modifier = Modifier.align(Alignment.CenterVertically)) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = stringResource(R.string.duplicate)
                    )
                }
            }
        ) {
            Card(onClick = onClick) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(width = 1.dp, color = Color.Black),
                ) {
                    Row {
                        Text(text = data.message)
                    }
                    Row {
                        Text(text = data.time.toString())
                    }
                }
            }
        }
    }
}