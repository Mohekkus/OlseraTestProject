package me.mohekkus.olsera_testproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import me.mohekkus.olsera_testproject.database.RetailDatabase
import me.mohekkus.olsera_testproject.database.repository.RetailRepository

@HiltAndroidApp
class OlseraTestApplication: Application() {

    val appScope = CoroutineScope(SupervisorJob())

    val database by lazy { RetailDatabase.getDatabase(this) }
    val repo by lazy { RetailRepository(database.retailDAO()) }
}