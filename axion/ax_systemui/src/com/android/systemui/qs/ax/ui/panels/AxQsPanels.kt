/*
 * Copyright 2025-2026 AxionOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.qs.ax.ui.panels

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import com.android.compose.animation.Expandable
import com.android.compose.animation.scene.ContentScope
import com.android.compose.gesture.gesturesDisabled
import com.android.systemui.common.ui.compose.Icon as SystemUiIcon
import com.android.systemui.common.ui.compose.PagerDots
import com.android.systemui.compose.modifiers.sysuiResTag
import com.android.systemui.lifecycle.rememberViewModel
import com.android.systemui.qs.ax.res.R
import com.android.systemui.qs.ax.shared.model.AxQsTokens
import com.android.systemui.qs.ax.ui.grid.LocalAxQsCellConfig
import com.android.systemui.qs.ax.ui.header.AxQuickSettingsDate
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsForegroundServicesButtonViewModel
import com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel
import com.android.systemui.res.R as SysuiR
import com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel

@Composable
internal fun ContentScope.AxQsDateHeader(
    toolbarViewModel: ToolbarViewModel,
    shadeHeaderViewModel: ShadeHeaderViewModel,
    showEdit: Boolean,
    showDate: Boolean = true,
    isFullyVisible: () -> Boolean,
    editButtonProgress: () -> Float,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (showDate) {
                AxQuickSettingsDate(viewModel = shadeHeaderViewModel)
            }
        }
        AxQsHeaderActions(
            viewModel = toolbarViewModel,
            isFullyVisible = isFullyVisible,
            editButtonProgress = editButtonProgress,
            showEdit = showEdit,
            modifier = Modifier.offset(x = 6.dp),
        )
    }
}

@Composable
internal fun ContentScope.AxQsHeaderActions(
    viewModel: ToolbarViewModel,
    isFullyVisible: () -> Boolean,
    editButtonProgress: () -> Float,
    showEdit: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (showEdit) {
            AxEditButton(viewModel, isFullyVisible, editButtonProgress)
        }
        FooterIconButton(
            model = viewModel.settingsButtonViewModel,
            containerColor = LocalAxQsCellConfig.current.backgroundColor(),
            modifier =
                Modifier.sysuiResTag("settings_button_container").minimumInteractiveComponentSize(),
        )
        FooterIconButton(
            model = viewModel.powerButtonViewModel,
            containerColor = LocalAxQsCellConfig.current.backgroundColor(),
            modifier = Modifier.sysuiResTag("pm_lite").minimumInteractiveComponentSize(),
        )
    }
}

@Composable
internal fun AxQsPagerIndicator(pagerState: PagerState, modifier: Modifier = Modifier) {
    if (pagerState.pageCount > 1) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            PagerDots(
                pagerState = pagerState,
                activeColor = MaterialTheme.colorScheme.primary,
                nonActiveColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                showArrows = false,
            )
        }
    }
}

@Composable
private fun AxEditButton(
    viewModel: ToolbarViewModel,
    isFullyVisible: () -> Boolean,
    editButtonProgress: () -> Float,
) {
    val editModeButtonViewModel =
        rememberViewModel("AxQsActions") { viewModel.editModeButtonViewModelFactory.create() }
    if (!editModeButtonViewModel.isEditButtonVisible) return
    val editEnabled by remember(editButtonProgress, isFullyVisible) {
        derivedStateOf {
            editButtonProgress() >= EDIT_BUTTON_ENABLE_THRESHOLD && isFullyVisible()
        }
    }
    Box(
        modifier =
            Modifier.graphicsLayer {
                val progress = editButtonProgress().coerceIn(0f, 1f)
                val minScale = AxQsTokens.Animation.TILE_REVEAL_MIN_SCALE
                val scale = minScale + (1f - minScale) * progress
                alpha = progress
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin.Center
            }
                .then(
                    if (editEnabled) {
                        Modifier
                    } else {
                        Modifier.gesturesDisabled().clearAndSetSemantics {}
                    }
                )
    ) {
        Expandable(
            color = LocalAxQsCellConfig.current.backgroundColor(),
            shape = CircleShape,
            onClick = { editModeButtonViewModel.onButtonClick() },
            modifier =
                Modifier.sysuiResTag("qs_edit_mode_button").minimumInteractiveComponentSize(),
            useModifierBasedImplementation = true,
        ) {
            Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(
                        SysuiR.string.accessibility_quick_settings_edit
                    ),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(18.dp),
                )
            }
        }
    }
}

@Composable
private fun FooterIconButton(
    model: FooterActionsButtonViewModel?,
    containerColor: Color,
    modifier: Modifier = Modifier,
) {
    if (model == null) return
    Expandable(
        color = containerColor,
        shape = CircleShape,
        onClick = model.onClick,
        modifier = modifier,
        useModifierBasedImplementation = true,
    ) {
        Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
            SystemUiIcon(
                icon = model.icon,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

private const val EDIT_BUTTON_ENABLE_THRESHOLD = 0.99f
