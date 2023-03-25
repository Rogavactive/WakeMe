package ge.rogavactive.wakeme.navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import ge.rogavactive.common.DarkGray

@Composable
fun RowScope.AnimatedBottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val tintColorAnimated by animateColorAsState(
        targetValue = if (selected) Color.Black else DarkGray,
        animationSpec = TweenSpec(500, 0, LinearOutSlowInEasing)
    )
    Box(
        modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = null
            )
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = tintColorAnimated,
        )
    }

}