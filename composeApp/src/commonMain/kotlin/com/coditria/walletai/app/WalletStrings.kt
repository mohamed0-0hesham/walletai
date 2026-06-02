package com.coditria.walletai.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import com.coditria.walletai.resources.Res
import com.coditria.walletai.resources.account
import com.coditria.walletai.resources.account_section
import com.coditria.walletai.resources.accounts_cards
import com.coditria.walletai.resources.add
import com.coditria.walletai.resources.agree_terms
import com.coditria.walletai.resources.ai
import com.coditria.walletai.resources.ai_understanding
import com.coditria.walletai.resources.amount
import com.coditria.walletai.resources.analysis
import com.coditria.walletai.resources.app_name
import com.coditria.walletai.resources.appearance
import com.coditria.walletai.resources.appearance_sub
import com.coditria.walletai.resources.arabic
import com.coditria.walletai.resources.auto_suggestions
import com.coditria.walletai.resources.biometrics
import com.coditria.walletai.resources.biometrics_sub
import com.coditria.walletai.resources.cancel
import com.coditria.walletai.resources.category
import com.coditria.walletai.resources.change_password
import com.coditria.walletai.resources.change_password_sub
import com.coditria.walletai.resources.create_account
import com.coditria.walletai.resources.create_sub
import com.coditria.walletai.resources.create_title
import com.coditria.walletai.resources.currency
import com.coditria.walletai.resources.dark
import com.coditria.walletai.resources.date
import com.coditria.walletai.resources.egyptian_pound
import com.coditria.walletai.resources.email
import com.coditria.walletai.resources.english
import com.coditria.walletai.resources.expense
import com.coditria.walletai.resources.expense_breakdown
import com.coditria.walletai.resources.expenses
import com.coditria.walletai.resources.expenses_unit
import com.coditria.walletai.resources.financial_health
import com.coditria.walletai.resources.first_name
import com.coditria.walletai.resources.forgot_password
import com.coditria.walletai.resources.goals
import com.coditria.walletai.resources.good_morning
import com.coditria.walletai.resources.have_account
import com.coditria.walletai.resources.help_center
import com.coditria.walletai.resources.income
import com.coditria.walletai.resources.installments
import com.coditria.walletai.resources.language
import com.coditria.walletai.resources.language_sub
import com.coditria.walletai.resources.last_name
import com.coditria.walletai.resources.light
import com.coditria.walletai.resources.limits_budgets
import com.coditria.walletai.resources.listening
import com.coditria.walletai.resources.live
import com.coditria.walletai.resources.mobile
import com.coditria.walletai.resources.monthly
import com.coditria.walletai.resources.months_word
import com.coditria.walletai.resources.more
import com.coditria.walletai.resources.nav_home
import com.coditria.walletai.resources.nav_reports
import com.coditria.walletai.resources.nav_settings
import com.coditria.walletai.resources.nav_transactions
import com.coditria.walletai.resources.new_transaction
import com.coditria.walletai.resources.no_account
import com.coditria.walletai.resources.notifications
import com.coditria.walletai.resources.notifications_sub
import com.coditria.walletai.resources.or_sign_in_with
import com.coditria.walletai.resources.password
import com.coditria.walletai.resources.password_placeholder
import com.coditria.walletai.resources.password_strength_good
import com.coditria.walletai.resources.per_month_suffix
import com.coditria.walletai.resources.preferences
import com.coditria.walletai.resources.pressure_map
import com.coditria.walletai.resources.pro_membership
import com.coditria.walletai.resources.profile
import com.coditria.walletai.resources.profile_sub
import com.coditria.walletai.resources.recent_transactions
import com.coditria.walletai.resources.remaining
import com.coditria.walletai.resources.remember_me
import com.coditria.walletai.resources.reports
import com.coditria.walletai.resources.save_transaction
import com.coditria.walletai.resources.say_in_arabic
import com.coditria.walletai.resources.security_privacy
import com.coditria.walletai.resources.set_monthly_budget
import com.coditria.walletai.resources.settings
import com.coditria.walletai.resources.sign_in
import com.coditria.walletai.resources.sign_out
import com.coditria.walletai.resources.sign_up
import com.coditria.walletai.resources.smart_insights
import com.coditria.walletai.resources.support
import com.coditria.walletai.resources.tab_active
import com.coditria.walletai.resources.tab_calendar
import com.coditria.walletai.resources.tab_done
import com.coditria.walletai.resources.tagline
import com.coditria.walletai.resources.tell_me_about_tx
import com.coditria.walletai.resources.terms_privacy
import com.coditria.walletai.resources.total_balance
import com.coditria.walletai.resources.total_remaining_debt
import com.coditria.walletai.resources.transfer
import com.coditria.walletai.resources.two_factor
import com.coditria.walletai.resources.two_factor_sub
import com.coditria.walletai.resources.upcoming_installments
import com.coditria.walletai.resources.verdict_good
import com.coditria.walletai.resources.version_line
import com.coditria.walletai.resources.version_loading
import com.coditria.walletai.resources.view_all
import com.coditria.walletai.resources.voice
import com.coditria.walletai.resources.voice_assistant
import com.coditria.walletai.resources.welcome_back
import com.coditria.walletai.resources.welcome_back_sub
import com.coditria.walletai.resources.week
import com.coditria.walletai.resources.year
import com.coditria.walletai.resources.month
import com.coditria.walletai.resources.transcribing
import org.jetbrains.compose.resources.stringResource

/**
 * Snapshot of all simple (no-arg) UI strings for the current locale.
 * Populated by [rememberWalletStrings] which uses Compose Multiplatform Resources
 * (`Res.string.*`) for the lookup. Locale is driven by [ProvideAppLocale].
 *
 * Parameterized strings (e.g. `Res.string.linked_accounts`, `Res.string.out_of`,
 * `Res.string.net_this_month`, `Res.string.paid_of_total`) are resolved at the
 * call site via `stringResource(resource, args...)` because they require
 * runtime arguments.
 */
@Immutable
data class WalletStrings(
    // Splash
    val appName: String,
    val tagline: String,
    val versionLoading: String,
    // Auth shared
    val email: String,
    val password: String,
    val signIn: String,
    val signUp: String,
    val createAccount: String,
    val rememberMe: String,
    val forgotPassword: String,
    val orSignInWith: String,
    val noAccount: String,
    val haveAccount: String,
    val welcomeBack: String,
    val welcomeBackSub: String,
    val createTitle: String,
    val createSub: String,
    val firstName: String,
    val lastName: String,
    val mobile: String,
    val passwordPlaceholder: String,
    val passwordStrength: String,
    val agreeTerms: String,
    // Dashboard
    val goodMorning: String,
    val totalBalance: String,
    val income: String,
    val expenses: String,
    val add: String,
    val voice: String,
    val transfer: String,
    val goals: String,
    val analysis: String,
    val more: String,
    val viewAll: String,
    val week: String,
    val month: String,
    val year: String,
    val upcomingInstallments: String,
    val recentTransactions: String,
    // Add transaction
    val newTransaction: String,
    val expense: String,
    val tellMeAboutTx: String,
    val aiUnderstanding: String,
    val amount: String,
    val ai: String,
    val autoSuggestions: String,
    val category: String,
    val date: String,
    val account: String,
    val cancel: String,
    val saveTransaction: String,
    // Voice
    val voiceAssistant: String,
    val listening: String,
    val sayInArabic: String,
    val live: String,
    val transcribing: String,
    // Installments
    val installments: String,
    val totalRemainingDebt: String,
    val monthly: String,
    val remaining: String,
    val tabActive: String,
    val tabCalendar: String,
    val tabDone: String,
    val pressureMap: String,
    val perMonth: String,
    val monthsWord: String,
    // Reports
    val reports: String,
    val financialHealth: String,
    val verdictGood: String,
    val expenseBreakdown: String,
    val smartInsights: String,
    val expensesUnit: String,
    // Settings
    val settings: String,
    val accountSection: String,
    val profile: String,
    val profileSub: String,
    val accountsCards: String,
    val limitsBudgets: String,
    val setMonthlyBudget: String,
    val preferences: String,
    val appearance: String,
    val appearanceSub: String,
    val light: String,
    val dark: String,
    val language: String,
    val languageSub: String,
    val arabic: String,
    val english: String,
    val currency: String,
    val egyptianPound: String,
    val notifications: String,
    val notificationsSub: String,
    val securityPrivacy: String,
    val changePassword: String,
    val changePasswordSub: String,
    val biometrics: String,
    val biometricsSub: String,
    val twoFactor: String,
    val twoFactorSub: String,
    val support: String,
    val helpCenter: String,
    val termsPrivacy: String,
    val signOut: String,
    val versionLine: String,
    val proMembership: String,
    // Bottom nav
    val navHome: String,
    val navTransactions: String,
    val navReports: String,
    val navSettings: String,
)

@Composable
fun rememberWalletStrings(): WalletStrings = WalletStrings(
    appName = stringResource(Res.string.app_name),
    tagline = stringResource(Res.string.tagline),
    versionLoading = stringResource(Res.string.version_loading),
    email = stringResource(Res.string.email),
    password = stringResource(Res.string.password),
    signIn = stringResource(Res.string.sign_in),
    signUp = stringResource(Res.string.sign_up),
    createAccount = stringResource(Res.string.create_account),
    rememberMe = stringResource(Res.string.remember_me),
    forgotPassword = stringResource(Res.string.forgot_password),
    orSignInWith = stringResource(Res.string.or_sign_in_with),
    noAccount = stringResource(Res.string.no_account),
    haveAccount = stringResource(Res.string.have_account),
    welcomeBack = stringResource(Res.string.welcome_back),
    welcomeBackSub = stringResource(Res.string.welcome_back_sub),
    createTitle = stringResource(Res.string.create_title),
    createSub = stringResource(Res.string.create_sub),
    firstName = stringResource(Res.string.first_name),
    lastName = stringResource(Res.string.last_name),
    mobile = stringResource(Res.string.mobile),
    passwordPlaceholder = stringResource(Res.string.password_placeholder),
    passwordStrength = stringResource(Res.string.password_strength_good),
    agreeTerms = stringResource(Res.string.agree_terms),
    goodMorning = stringResource(Res.string.good_morning),
    totalBalance = stringResource(Res.string.total_balance),
    income = stringResource(Res.string.income),
    expenses = stringResource(Res.string.expenses),
    add = stringResource(Res.string.add),
    voice = stringResource(Res.string.voice),
    transfer = stringResource(Res.string.transfer),
    goals = stringResource(Res.string.goals),
    analysis = stringResource(Res.string.analysis),
    more = stringResource(Res.string.more),
    viewAll = stringResource(Res.string.view_all),
    week = stringResource(Res.string.week),
    month = stringResource(Res.string.month),
    year = stringResource(Res.string.year),
    upcomingInstallments = stringResource(Res.string.upcoming_installments),
    recentTransactions = stringResource(Res.string.recent_transactions),
    newTransaction = stringResource(Res.string.new_transaction),
    expense = stringResource(Res.string.expense),
    tellMeAboutTx = stringResource(Res.string.tell_me_about_tx),
    aiUnderstanding = stringResource(Res.string.ai_understanding),
    amount = stringResource(Res.string.amount),
    ai = stringResource(Res.string.ai),
    autoSuggestions = stringResource(Res.string.auto_suggestions),
    category = stringResource(Res.string.category),
    date = stringResource(Res.string.date),
    account = stringResource(Res.string.account),
    cancel = stringResource(Res.string.cancel),
    saveTransaction = stringResource(Res.string.save_transaction),
    voiceAssistant = stringResource(Res.string.voice_assistant),
    listening = stringResource(Res.string.listening),
    sayInArabic = stringResource(Res.string.say_in_arabic),
    live = stringResource(Res.string.live),
    transcribing = stringResource(Res.string.transcribing),
    installments = stringResource(Res.string.installments),
    totalRemainingDebt = stringResource(Res.string.total_remaining_debt),
    monthly = stringResource(Res.string.monthly),
    remaining = stringResource(Res.string.remaining),
    tabActive = stringResource(Res.string.tab_active),
    tabCalendar = stringResource(Res.string.tab_calendar),
    tabDone = stringResource(Res.string.tab_done),
    pressureMap = stringResource(Res.string.pressure_map),
    perMonth = stringResource(Res.string.per_month_suffix),
    monthsWord = stringResource(Res.string.months_word),
    reports = stringResource(Res.string.reports),
    financialHealth = stringResource(Res.string.financial_health),
    verdictGood = stringResource(Res.string.verdict_good),
    expenseBreakdown = stringResource(Res.string.expense_breakdown),
    smartInsights = stringResource(Res.string.smart_insights),
    expensesUnit = stringResource(Res.string.expenses_unit),
    settings = stringResource(Res.string.settings),
    accountSection = stringResource(Res.string.account_section),
    profile = stringResource(Res.string.profile),
    profileSub = stringResource(Res.string.profile_sub),
    accountsCards = stringResource(Res.string.accounts_cards),
    limitsBudgets = stringResource(Res.string.limits_budgets),
    setMonthlyBudget = stringResource(Res.string.set_monthly_budget),
    preferences = stringResource(Res.string.preferences),
    appearance = stringResource(Res.string.appearance),
    appearanceSub = stringResource(Res.string.appearance_sub),
    light = stringResource(Res.string.light),
    dark = stringResource(Res.string.dark),
    language = stringResource(Res.string.language),
    languageSub = stringResource(Res.string.language_sub),
    arabic = stringResource(Res.string.arabic),
    english = stringResource(Res.string.english),
    currency = stringResource(Res.string.currency),
    egyptianPound = stringResource(Res.string.egyptian_pound),
    notifications = stringResource(Res.string.notifications),
    notificationsSub = stringResource(Res.string.notifications_sub),
    securityPrivacy = stringResource(Res.string.security_privacy),
    changePassword = stringResource(Res.string.change_password),
    changePasswordSub = stringResource(Res.string.change_password_sub),
    biometrics = stringResource(Res.string.biometrics),
    biometricsSub = stringResource(Res.string.biometrics_sub),
    twoFactor = stringResource(Res.string.two_factor),
    twoFactorSub = stringResource(Res.string.two_factor_sub),
    support = stringResource(Res.string.support),
    helpCenter = stringResource(Res.string.help_center),
    termsPrivacy = stringResource(Res.string.terms_privacy),
    signOut = stringResource(Res.string.sign_out),
    versionLine = stringResource(Res.string.version_line),
    proMembership = stringResource(Res.string.pro_membership),
    navHome = stringResource(Res.string.nav_home),
    navTransactions = stringResource(Res.string.nav_transactions),
    navReports = stringResource(Res.string.nav_reports),
    navSettings = stringResource(Res.string.nav_settings),
)

val LocalWalletStrings = staticCompositionLocalOf<WalletStrings> {
    error("LocalWalletStrings not provided. Wrap content in ProvideAppLocale + LocalWalletStrings provides rememberWalletStrings().")
}
