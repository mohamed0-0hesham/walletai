package com.coditria.walletai.data

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
import com.coditria.walletai.domain.repository.WalletRepository

class InMemoryWalletRepository : WalletRepository {

    override fun currentUser() = User(
        id = "u1",
        firstName = "هشام",
        lastName = "محمد",
        email = "hisham@walletai.eg",
        avatarInitial = "ه",
    )

    override fun balanceSummary() = BalanceSummary(
        total = Money(23_450, "ج.م"),
        income = Money(25_000, "ج.م"),
        expenses = Money(16_800, "ج.م"),
        netDelta = Money(8_200, "ج.م"),
    )

    override fun recentTransactions() = listOf(
        Transaction("t1", "مواصلات", "اليوم · 8:30 ص", Money(20, "ج.م"),
            TransactionType.Expense, TransactionCategory.Transport),
        Transaction("t2", "قهوة وفطار", "اليوم · 9:10 ص", Money(35, "ج.م"),
            TransactionType.Expense, TransactionCategory.Food),
        Transaction("t3", "Freelance · مشروع", "أمس · 4:20 م", Money(2_000, "ج.م"),
            TransactionType.Income, TransactionCategory.Income),
    )

    override fun upcomingInstallments() = listOf(
        Installment("i1", "قسط الأيفون", "باقي 14 شهر · مستحق غداً",
            totalMonths = 24, paidMonths = 10, monthlyAmount = Money(1_485, "ج.م"),
            nextDueLabel = "⏰ مستحق غداً", isWarning = true,
            style = InstallmentVisualStyle.Brand),
        Installment("i2", "جمعية عمتي", "دورك بعد 4 شهور · 15 مايو",
            totalMonths = 12, paidMonths = 8, monthlyAmount = Money(10_000, "ج.م"),
            nextDueLabel = "📅 15 مايو", style = InstallmentVisualStyle.Accent),
    )

    override fun allInstallments() = listOf(
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

    override fun installmentSummary() = InstallmentSummary(
        totalRemaining = Money(42_180, "ج.م"),
        monthly = Money(3_250, "ج.م"),
        remainingMonths = 14,
    )

    override fun heatmap() = listOf(
        "ينا" to 1, "فبر" to 2, "مار" to 2, "أبر" to 3, "ماي" to 4, "يون" to 3,
        "يول" to 2, "أغس" to 2, "سبت" to 1, "أكت" to 2, "نوف" to 3, "ديس" to 4,
    ).map { (label, level) ->
        HeatmapMonth(label = label, amountLabel = "${(level + 1) * 1200}", pressure = level)
    }

    override fun expenseBreakdown() = listOf(
        CategoryBreakdown("أقساط", 35, 0xFF5B6CFF, 0xFF8E62FF),
        CategoryBreakdown("أكل", 25, 0xFFF0A24A, 0xFFF26B7A),
        CategoryBreakdown("مواصلات", 18, 0xFF3DD2C0, 0xFF5B6CFF),
        CategoryBreakdown("فواتير", 13, 0xFF22C58B, 0xFF3DD2C0),
        CategoryBreakdown("أخرى", 9, 0xFF7A819E, 0xFF7A819E),
    )

    override fun financialHealth() = FinancialHealth(
        score = 78,
        verdict = "جيد",
        description = "صرفت 67% من الدخل، نسبة الإدخار جيدة. حاول تقلل مصاريف الأكل بره.",
    )

    override fun aiInsights() = listOf(
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

    override fun aiSuggestionForUtterance(utterance: String) = AiSuggestion(
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
