package com.example.db

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.food_app.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao():MealDao

    companion object {
        var INSTANCE : MealDatabase?= null
        @Synchronized
        fun getInstance(context:Context):MealDatabase{
            if (INSTANCE==null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meals.db"
                    /* i need to rebuild database and remain old db */
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MealDatabase
        }
    }
}