package com.example.jetfitnessapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jetfitnessapp.data.local.dao.FitnessDao
import com.example.jetfitnessapp.data.local.entity.Converters
import com.example.jetfitnessapp.data.local.entity.ProgressRoom

@Database(

    entities = [ProgressRoom::class],
    version = 1,
    exportSchema = false

)
@TypeConverters( Converters::class )
abstract class FitnessDatabase : RoomDatabase() {

    abstract fun fitnessDao(): FitnessDao

    companion object {

        @Volatile
        private var Instance: FitnessDatabase? = null

        fun getDatabase( context: Context): FitnessDatabase {

            return Instance ?: synchronized( this ) {

                Room.databaseBuilder(

                    context,
                    FitnessDatabase::class.java,
                    "workout_progress"

                ).fallbackToDestructiveMigrationFrom()
                    .build()
                    .also { Instance = it }

            }

        }

    }

}