package com.coditria.walletai.data.preview

import com.coditria.walletai.domain.model.AiInsight
import com.coditria.walletai.domain.model.AiSuggestion
import com.coditria.walletai.domain.model.BalanceSummary
import com.coditria.walletai.domain.model.CategoryBreakdown
import com.coditria.walletai.domain.model.FinancialHealth
import com.coditria.walletai.domain.model.HeatmapMonth
import com.coditria.walletai.domain.model.Installment
import com.coditria.walletai.domain.model.InstallmentSummary
import com.coditria.walletai.domain.model.InstallmentVisualStyle
import com.coditria.walletai.domain.model.InsightTone
import com.coditria.walletai.domain.model.Money
import com.coditria.walletai.domain.model.Transaction
import com.coditria.walletai.domain.model.TransactionCategory
import com.coditria.walletai.domain.model.TransactionType
import com.coditria.walletai.domain.model.User
import com.coditria.walletai.domain.repository.AiSuggestionRepository
import com.coditria.walletai.domain.repository.AnalyticsRepository
import com.coditria.walletai.domain.repository.BalanceRepository
import com.coditria.walletai.domain.repository.InstallmentRepository
import com.coditria.walletai.domain.repository.TransactionRepository
import com.coditria.walletai.domain.repository.UserRepository

/**
 * Hard-coded fake repositories for `@Preview` composables and unit tests.
 * Bypasses the SQLDelight stack so previews render synchronously without a
 * driver or context. Production code never touches this file —
 * [com.coditria.walletai.data.di.DataGraph.sqlDelight] is the production wire.
 */
internal object PreviewFakes {

    fun userRepository(): UserRepository = object : UserRepository {
        override suspend fun currentUser() = User(
            id = "u1",
            firstName = "هشام",
            lastName = "محمد",
            email = "hisham@walletai.eg",
            avatarInitial = "ه",
            tier = "Pro",
        )
    }

    fun balanceRepository(): BalanceRepository = object : BalanceRepository {
        override suspend fun summary() = BalanceSummary(
            total = Money(23_450, "ج.م"),
            income = Money(25_000, "ج.م"),
            expenses = Money(16_800, "ج.م"),
            netDelta = Money(8_200, "ج.م"),
        )
    }

    fun transactionRepository(): TransactionRepository = object : TransactionRepository {
        private val list = listOf(
            Transaction("t1", "مواصلات", "اليوم · 8:30 ص", Money(20, "ج.م"),
                TransactionType.Expense, TransactionCategory.Transport),
            Transaction("t2", "قهوة وفطار", "اليوم · 9:10 ص", Money(35, "ج.م"),
                TransactionType.Expense, TransactionCategory.Food),
            Transaction("t3", "Freelance · مشروع", "أمس · 4:20 م", Money(2_000, "ج.م"),
                TransactionType.Income, TransactionCategory.Income),
        )

        override suspend fun recent(limit: Int) = list.take(limit)
        override suspend fun add(transaction: Transaction) = Unit
    }

    fun installmentRepository(): InstallmentRepository = object : InstallmentRepository {
        private val items = listOf(
            Installment("i1", "قسط الأيفون", "بنك الإمارات · 14 شهر",
                24, 10, Money(1_485, "ج.م"), "⏰ مستحق غداً",
                isWarning = true, style = InstallmentVisualStyle.Brand),
            Installment("i3", "قرض شخصي", "بنك القاهرة · 18 شهر",
                24, 6, Money(965, "ج.م"), "📅 15 مايو",
                style = InstallmentVisualStyle.Brand),
            Installment("i4", "لابتوب Dell", "2B Computer · 4 شهور",
                12, 8, Money(800, "ج.م"), "📅 20 مايو",
                style = InstallmentVisualStyle.Accent),
        )
        private val upcomingItems = listOf(
            Installment("i1", "قسط الأيفون", "باقي 14 شهر · مستحق غداً",
                24, 10, Money(1_485, "ج.م"), "⏰ مستحق غداً",
                isWarning = true, style = InstallmentVisualStyle.Brand),
            Installment("i2", "جمعية عمتي", "دورك بعد 4 شهور · 15 مايو",
                12, 8, Money(10_000, "ج.م"), "📅 15 مايو",
                style = InstallmentVisualStyle.Accent),
        )

        override suspend fun upcoming(limit: Int) = upcomingItems.take(limit)
        override suspend fun all() = items
        override suspend fun summary() = InstallmentSummary(
            totalRemaining = Money(42_180, "ج.م"),
            monthly = Money(3_250, "ج.م"),
            remainingMonths = 14,
        )
        override suspend fun pressureHeatmap(year: Int): List<HeatmapMonth> {
            val pressures = listOf(1, 2, 2, 3, 4, 3, 2, 2, 1, 2, 3, 4)
            val labels = listOf(
                "ينا", "فبر", "مار", "أبر", "ماي", "يون",
                "يول", "أغس", "سبت", "أكت", "نوف", "ديس",
            )
            return pressures.mapIndexed { i, level ->
                HeatmapMonth(
                    label = labels[i],
                    amountLabel = ((level + 1) * 1_200).toString(),
                    pressure = level,
                )
            }
        }
    }

    fun analyticsRepository(): AnalyticsRepository = object : AnalyticsRepository {
        override suspend fun expenseBreakdown() = listOf(
            CategoryBreakdown("أقساط", 35, 0xFF5B6CFF, 0xFF8E62FF),
            CategoryBreakdown("أكل", 25, 0xFFF0A24A, 0xFFF26B7A),
            CategoryBreakdown("مواصلات", 18, 0xFF3DD2C0, 0xFF5B6CFF),
            CategoryBreakdown("فواتير", 13, 0xFF22C58B, 0xFF3DD2C0),
            CategoryBreakdown("أخرى", 9, 0xFF7A819E, 0xFF7A819E),
        )

        override suspend fun financialHealth() = FinancialHealth(
            score = 78,
            verdict = "جيد",
            description = "صرفت 67% من الدخل، نسبة الإدخار جيدة. حاول تقلل مصاريف الأكل بره.",
        )

        override suspend fun aiInsights() = listOf(
            AiInsight(
                title = "توقع المصاريف",
                description = "بالمعدل الحالي، هتصرف 17,500 ج.م الشهر الجاي · زيادة +4%",
                tone = InsightTone.Brand,
                highlight = true,
            ),
            AiInsight(
                title = "مصاريف الأكل ارتفعت",
                description = "صرفت +23% هذا الشهر مقارنة بالشهر اللي فات",
                tone = InsightTone.Bad,
            ),
            AiInsight(
                title = "تحسن في الإدخار",
                description = "إدخارك زاد بنسبة 15% · استمر بنفس الوتيرة!",
                tone = InsightTone.Good,
            ),
        )
    }

    fun aiSuggestionRepository(): AiSuggestionRepository = object : AiSuggestionRepository {
        override suspend fun suggestionFor(utterance: String) = AiSuggestion(
            parsedText = utterance.ifBlank { "صرفت 35 جنيه فطار من بتاع الكشري" },
            amount = "35",
            currency = "ج.م",
            category = TransactionCategory.Food,
            categoryLabel = "أكل وشرب",
            date = "اليوم · 9:10 ص",
            account = "كاش",
            confidence = 0.92f,
        )
    }
}
