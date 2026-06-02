package com.coditria.walletai.data.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Seeds the SQLDelight database with the prototype's demo content the first
 * time the app launches against an empty schema. Idempotent — checks counts
 * before inserting so re-running on an already-populated DB is a no-op.
 *
 * Replace this with a real onboarding/import flow when the demo data goes away.
 */
class DatabaseSeeder(private val db: WalletDatabase) {

    suspend fun seedIfEmpty() = withContext(Dispatchers.Default) {
        seedUser()
        seedBalance()
        seedTransactions()
        seedInstallments()
        seedHeatmap()
        seedAnalytics()
    }

    private fun seedUser() {
        if (db.userQueries.count().executeAsOne() > 0) return
        db.userQueries.upsert(
            id = "u1",
            firstName = "هشام",
            lastName = "محمد",
            email = "hisham@walletai.eg",
            avatarInitial = "ه",
            tier = "Pro",
        )
    }

    private fun seedBalance() {
        if (db.balanceQueries.count().executeAsOne() > 0) return
        db.balanceQueries.upsertSummary(
            totalMinor = 23_450,
            incomeMinor = 25_000,
            expensesMinor = 16_800,
            netDeltaMinor = 8_200,
            currency = "ج.م",
        )
    }

    private fun seedTransactions() {
        if (db.walletTransactionQueries.count().executeAsOne() > 0) return
        val rows = listOf(
            Triple("t1", "مواصلات", "اليوم · 8:30 ص") to TxSeed(20, "expense", "Transport", 3),
            Triple("t2", "قهوة وفطار", "اليوم · 9:10 ص") to TxSeed(35, "expense", "Food", 2),
            Triple("t3", "Freelance · مشروع", "أمس · 4:20 م") to TxSeed(2_000, "income", "Income", 1),
        )
        rows.forEach { (head, seed) ->
            db.walletTransactionQueries.upsert(
                id = head.first,
                title = head.second,
                subtitle = head.third,
                amountMinor = seed.amount,
                currency = "ج.م",
                type = seed.type,
                category = seed.category,
                createdAtEpochMs = seed.sort.toLong(),
            )
        }
    }

    private fun seedInstallments() {
        if (db.installmentQueries.count().executeAsOne() > 0) return

        // Distinct items, with two display flags so the dashboard "upcoming"
        // and the installments list can pull different subsets from one table.
        val seeds = listOf(
            InstSeed("i1", "قسط الأيفون", "بنك الإمارات · 14 شهر",
                24, 10, 1_485, "⏰ مستحق غداً", isWarning = true,
                style = "brand", showInUpcoming = false, showInAllList = true, sortOrder = 0),
            InstSeed("i1u", "قسط الأيفون", "باقي 14 شهر · مستحق غداً",
                24, 10, 1_485, "⏰ مستحق غداً", isWarning = true,
                style = "brand", showInUpcoming = true, showInAllList = false, sortOrder = 0),
            InstSeed("i2", "جمعية عمتي", "دورك بعد 4 شهور · 15 مايو",
                12, 8, 10_000, "📅 15 مايو", isWarning = false,
                style = "accent", showInUpcoming = true, showInAllList = false, sortOrder = 1),
            InstSeed("i3", "قرض شخصي", "بنك القاهرة · 18 شهر",
                24, 6, 965, "📅 15 مايو", isWarning = false,
                style = "brand", showInUpcoming = false, showInAllList = true, sortOrder = 2),
            InstSeed("i4", "لابتوب Dell", "2B Computer · 4 شهور",
                12, 8, 800, "📅 20 مايو", isWarning = false,
                style = "accent", showInUpcoming = false, showInAllList = true, sortOrder = 3),
        )
        seeds.forEach {
            db.installmentQueries.upsert(
                id = it.id,
                name = it.name,
                provider = it.provider,
                totalMonths = it.totalMonths.toLong(),
                paidMonths = it.paidMonths.toLong(),
                monthlyAmountMinor = it.monthlyMinor,
                currency = "ج.م",
                nextDueLabel = it.nextDueLabel,
                isWarning = if (it.isWarning) 1L else 0L,
                style = it.style,
                showInUpcoming = if (it.showInUpcoming) 1L else 0L,
                showInAllList = if (it.showInAllList) 1L else 0L,
                sortOrder = it.sortOrder.toLong(),
            )
        }
        db.installmentQueries.upsertSummary(
            totalRemainingMinor = 42_180,
            monthlyMinor = 3_250,
            currency = "ج.م",
            remainingMonths = 14,
        )
    }

    private fun seedHeatmap() {
        if (db.installmentQueries.countHeatmap().executeAsOne() > 0) return
        val pressures = listOf(1, 2, 2, 3, 4, 3, 2, 2, 1, 2, 3, 4)
        val labels = listOf(
            "ينا", "فبر", "مار", "أبر", "ماي", "يون",
            "يول", "أغس", "سبت", "أكت", "نوف", "ديس",
        )
        pressures.forEachIndexed { index, level ->
            db.installmentQueries.upsertHeatmap(
                year = 2026,
                monthIndex = (index + 1).toLong(),
                label = labels[index],
                pressureLevel = level.toLong(),
                totalMinor = (level + 1) * 1_200L,
                currency = "ج.م",
            )
        }
    }

    private fun seedAnalytics() {
        seedBreakdown()
        seedHealth()
        seedInsights()
    }

    private fun seedBreakdown() {
        if (db.analyticsQueries.countBreakdown().executeAsOne() > 0) return
        val rows = listOf(
            BreakdownSeed("أقساط", 35, 0xFF5B6CFF, 0xFF8E62FF, 0),
            BreakdownSeed("أكل", 25, 0xFFF0A24A, 0xFFF26B7A, 1),
            BreakdownSeed("مواصلات", 18, 0xFF3DD2C0, 0xFF5B6CFF, 2),
            BreakdownSeed("فواتير", 13, 0xFF22C58B, 0xFF3DD2C0, 3),
            BreakdownSeed("أخرى", 9, 0xFF7A819E, 0xFF7A819E, 4),
        )
        rows.forEach {
            db.analyticsQueries.upsertBreakdown(
                name = it.name,
                percent = it.percent.toLong(),
                gradientStart = it.start,
                gradientEnd = it.end,
                sortOrder = it.sortOrder.toLong(),
            )
        }
    }

    private fun seedHealth() {
        db.analyticsQueries.upsertHealth(
            score = 78,
            maxScore = 100,
            verdict = "جيد",
            description = "صرفت 67% من الدخل، نسبة الإدخار جيدة. حاول تقلل مصاريف الأكل بره.",
        )
    }

    private fun seedInsights() {
        if (db.analyticsQueries.countInsights().executeAsOne() > 0) return
        val rows = listOf(
            InsightSeed(
                "توقع المصاريف",
                "بالمعدل الحالي، هتصرف 17,500 ج.م الشهر الجاي · زيادة +4%",
                "brand", true, 0,
            ),
            InsightSeed(
                "مصاريف الأكل ارتفعت",
                "صرفت +23% هذا الشهر مقارنة بالشهر اللي فات",
                "bad", false, 1,
            ),
            InsightSeed(
                "تحسن في الإدخار",
                "إدخارك زاد بنسبة 15% · استمر بنفس الوتيرة!",
                "good", false, 2,
            ),
        )
        rows.forEach {
            db.analyticsQueries.upsertInsight(
                title = it.title,
                description = it.description,
                tone = it.tone,
                highlight = if (it.highlight) 1L else 0L,
                sortOrder = it.sortOrder.toLong(),
            )
        }
    }

    private data class TxSeed(val amount: Long, val type: String, val category: String, val sort: Int)
    private data class InstSeed(
        val id: String, val name: String, val provider: String,
        val totalMonths: Int, val paidMonths: Int, val monthlyMinor: Long,
        val nextDueLabel: String, val isWarning: Boolean, val style: String,
        val showInUpcoming: Boolean, val showInAllList: Boolean, val sortOrder: Int,
    )
    private data class BreakdownSeed(
        val name: String, val percent: Int, val start: Long, val end: Long, val sortOrder: Int,
    )
    private data class InsightSeed(
        val title: String, val description: String, val tone: String,
        val highlight: Boolean, val sortOrder: Int,
    )
}
