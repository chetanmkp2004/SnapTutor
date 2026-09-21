package com.snaptutor.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.snaptutor.app.data.local.dao.ProgressDao
import com.snaptutor.app.data.local.dao.QuestionDao
import com.snaptutor.app.data.local.dao.QuizDao
import com.snaptutor.app.data.local.entities.ProgressEntity
import com.snaptutor.app.data.local.entities.QuestionEntity
import com.snaptutor.app.data.local.entities.QuizEntity

/**
 * Room database scaffold.
 * Not yet used by real repositories in this phase — fake implementations
 * use in-memory data. This scaffold ensures the Room schema compiles
 * and is ready for real repo implementations.
 */
@Database(
    entities = [
        QuestionEntity::class,
        ProgressEntity::class,
        QuizEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun progressDao(): ProgressDao
    abstract fun quizDao(): QuizDao
}
