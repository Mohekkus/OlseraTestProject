package me.mohekkus.olsera_testproject.database

import android.content.Context
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.mohekkus.olsera_testproject.database.dao.RetailDAO
import me.mohekkus.olsera_testproject.database.entity.RetailEntity

@Database(entities = [RetailEntity::class], version = 1, exportSchema = false)
public abstract class RetailDatabase: RoomDatabase() {

    abstract fun retailDAO(): RetailDAO

    companion object {
        @Volatile
        private var INSTANCE: RetailDatabase? = null

        fun getDatabase(context: Context) : RetailDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RetailDatabase::class.java,
                    "Retail Database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }

    private class RetailDBCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.retailDAO())
                }
            }

        }

        suspend fun populateDatabase(wordDao: RetailDAO) {
            // Delete all content here.
            wordDao.deleteAll()

        }

    }
}