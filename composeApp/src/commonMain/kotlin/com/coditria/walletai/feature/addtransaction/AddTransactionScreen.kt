package com.coditria.walletai.feature.addtransaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.coditria.walletai.app.AppLocale
import com.coditria.walletai.app.LocalWalletStrings
import androidx.compose.runtime.rememberCoroutineScope
import com.coditria.walletai.data.di.DataGraph
import com.coditria.walletai.domain.model.TransactionType
import com.coditria.walletai.feature.common.WalletPreviewHarness
import com.coditria.walletai.feature.common.WalletIconCheck
import com.coditria.walletai.feature.common.WalletIconClose
import com.coditria.walletai.feature.common.WalletIconCoffee
import com.coditria.walletai.feature.common.WalletIconSparkles
import com.coditria.walletai.feature.common.WalletPhoneStatusBar
import com.walletai.core.designsystem.components.AmountDisplaySize
import com.walletai.core.designsystem.components.ChipStyle
import com.walletai.core.designsystem.components.SegmentItem
import com.walletai.core.designsystem.components.SegmentStyle
import com.walletai.core.designsystem.components.WalletAiInputCard
import com.walletai.core.designsystem.components.WalletAiSuggestionRow
import com.walletai.core.designsystem.components.WalletAmountDisplay
import com.walletai.core.designsystem.components.WalletChip
import com.walletai.core.designsystem.components.WalletConfidenceBar
import com.walletai.core.designsystem.components.WalletGhostButton
import com.walletai.core.designsystem.components.WalletPrimaryButton
import com.walletai.core.designsystem.components.WalletSegmentedControl
import com.walletai.core.designsystem.components.WalletTextField
import com.coditria.walletai.designsystem.components.WalletTopBar
import androidx.compose.ui.text.input.KeyboardType
import com.walletai.core.designsystem.theme.WalletTheme

@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel,
    onSave: () -> Unit,
    onCancel: () -> Unit,
) {
    val state = viewModel.state
    val s = LocalWalletStrings.current
    val segmentStyle = when (state.type) {
        TransactionType.Expense -> SegmentStyle.Bad
        TransactionType.Income -> SegmentStyle.Good
        TransactionType.Transfer -> SegmentStyle.Default
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WalletTheme.colors.bg)
            .verticalScroll(rememberScrollState()),
    ) {
        WalletPhoneStatusBar()
        WalletTopBar(
            title = s.newTransaction,
            onBack = onCancel,
            backIcon = { WalletIconClose(color = WalletTheme.colors.ink2) },
        )
        Spacer(Modifier.height(8.dp))
        WalletSegmentedControl(
            items = listOf(
                SegmentItem("expense", s.expense),
                SegmentItem("income", s.income),
                SegmentItem("transfer", s.transfer),
            ),
            selectedKey = when (state.type) {
                TransactionType.Expense -> "expense"
                TransactionType.Income -> "income"
                TransactionType.Transfer -> "transfer"
            },
            onSelect = { key ->
                viewModel.onTypeChange(
                    when (key) {
                        "expense" -> TransactionType.Expense
                        "income" -> TransactionType.Income
                        else -> TransactionType.Transfer
                    },
                )
            },
            style = segmentStyle,
            modifier = Modifier.padding(horizontal = 18.dp),
        )
        Spacer(Modifier.height(16.dp))

        // Input + amount card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .clip(WalletTheme.shapes.medium)
                .background(WalletTheme.colors.surface)
                .padding(18.dp),
        ) {
            Text(s.tellMeAboutTx,
                style = WalletTheme.typography.label, color = WalletTheme.colors.muted)
            Spacer(Modifier.height(8.dp))
            WalletAiInputCard(
                parsedText = state.suggestion.parsedText,
                label = s.aiUnderstanding,
                badgeIcon = { WalletIconSparkles(color = Color.White) },
            )
            Spacer(Modifier.height(18.dp))
            Text(s.amount,
                style = WalletTheme.typography.label, color = WalletTheme.colors.muted)
            Spacer(Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
            ) {
                WalletAmountDisplay(
                    amount = state.suggestion.amount,
                    currency = state.suggestion.currency,
                    size = AmountDisplaySize.Hero,
                )
            }
        }
        Spacer(Modifier.height(14.dp))

        // AI suggestions card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .clip(WalletTheme.shapes.medium)
                .background(WalletTheme.colors.surface)
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                WalletChip(text = s.ai, style = ChipStyle.AiBadge)
                Text(s.autoSuggestions,
                    style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = WalletTheme.colors.ink)
            }
            Spacer(Modifier.height(12.dp))
            WalletAiSuggestionRow(
                label = s.category,
                showDivider = false,
                trailingContent = {
                    WalletChip(
                        text = state.suggestion.categoryLabel,
                        style = ChipStyle.Warn,
                        leadingIcon = { WalletIconCoffee(size = 12.dp, color = WalletTheme.colors.warnDeep) },
                    )
                },
            )
            WalletAiSuggestionRow(label = s.date, value = state.suggestion.date)
            WalletAiSuggestionRow(label = s.account, value = state.suggestion.account)
            Spacer(Modifier.height(8.dp))
            WalletConfidenceBar(
                confidence = state.suggestion.confidence,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Spacer(Modifier.height(14.dp))

        // Manual entry card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .clip(WalletTheme.shapes.medium)
                .background(WalletTheme.colors.surface)
                .padding(16.dp),
        ) {
            Text(
                text = s.tellMeAboutTx,
                style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = WalletTheme.colors.ink,
            )
            Spacer(Modifier.height(12.dp))
            WalletTextField(
                label = s.aiUnderstanding,
                value = state.suggestion.parsedText,
                onValueChange = viewModel::onParsedTextChange,
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                WalletTextField(
                    label = s.amount,
                    value = state.suggestion.amount,
                    onValueChange = viewModel::onAmountChange,
                    placeholder = "0",
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(2f),
                )
                WalletTextField(
                    label = "Currency",
                    value = state.suggestion.currency,
                    onValueChange = viewModel::onCurrencyChange,
                    placeholder = "EGP",
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(Modifier.height(10.dp))
            WalletTextField(
                label = s.category,
                value = state.suggestion.categoryLabel,
                onValueChange = viewModel::onCategoryLabelChange,
            )
            Spacer(Modifier.height(10.dp))
            WalletTextField(
                label = s.date,
                value = state.suggestion.date,
                onValueChange = viewModel::onDateChange,
            )
            Spacer(Modifier.height(10.dp))
            WalletTextField(
                label = s.account,
                value = state.suggestion.account,
                onValueChange = viewModel::onAccountChange,
            )
        }
        Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            WalletGhostButton(
                text = s.cancel,
                onClick = onCancel,
                modifier = Modifier.weight(1f),
            )
            WalletPrimaryButton(
                text = s.saveTransaction,
                onClick = { viewModel.save(onSave) },
                modifier = Modifier.weight(1f),
                trailingIcon = { WalletIconCheck(color = Color.White) },
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Preview
@Composable
private fun AddTransactionScreenArabicPreview() {
    WalletPreviewHarness(locale = AppLocale.Arabic) {
        val data = remember { DataGraph.previewFakes() }
        val scope = rememberCoroutineScope()
        val vm = remember {
            AddTransactionViewModel(data.aiSuggestionRepository, data.transactionRepository, scope)
        }
        AddTransactionScreen(viewModel = vm, onSave = {}, onCancel = {})
    }
}

@Preview
@Composable
private fun AddTransactionScreenEnglishPreview() {
    WalletPreviewHarness(locale = AppLocale.English) {
        val data = remember { DataGraph.previewFakes() }
        val scope = rememberCoroutineScope()
        val vm = remember {
            AddTransactionViewModel(data.aiSuggestionRepository, data.transactionRepository, scope)
        }
        AddTransactionScreen(viewModel = vm, onSave = {}, onCancel = {})
    }
}
