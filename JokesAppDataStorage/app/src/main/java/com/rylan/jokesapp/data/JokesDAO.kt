package com.rylan.jokesapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.database.getStringOrNull
import com.rylan.jokesapp.models.JokeModel

class JokesDAO (
    context: Context,

    factory: SQLiteDatabase.CursorFactory?,

    DATABASE_NAME: String = "jokes_db"
) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, 1) {

    val JOKES_TABLE: String = "jokes_table"
    val ID_COL: String = "id"
    val JOKE_QUESTION_COLUMN: String = "joke_question"
    val JOKE_ANSWER_COLUMN: String = "joke_answer"


    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + JOKES_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                JOKE_QUESTION_COLUMN + " TEXT," +
                JOKE_ANSWER_COLUMN + " TEXT)"
                )

        db?.execSQL(query)
        Log.d("jokesDBHelper", "Created new db")
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + JOKES_TABLE)

        onCreate(db)
    }

    fun getById(id: Int): JokeModel? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $JOKES_TABLE WHERE $ID_COL = ?", arrayOf(id.toString()))

        var joke: JokeModel? = null
        with(cursor){
            while(moveToNext()){
                try {
                    joke = JokeModel(
                        getInt(getColumnIndexOrThrow(ID_COL)),
                        getString(getColumnIndexOrThrow(JOKE_QUESTION_COLUMN)),
                        getString(getColumnIndexOrThrow(JOKE_ANSWER_COLUMN)),
                        false
                    )
                } catch (e: Exception){
                    Log.d("jokesDBHelper", "Error getting joke by id: $e")
                }
            }
        }
        db.close()
        return joke
    }

    fun getAll() : MutableList<JokeModel> {
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $JOKES_TABLE", null)

        val jokesList = mutableListOf<JokeModel>()

        with(cursor) {
            while (moveToNext()) {
                val joke = JokeModel(
                    getInt(getColumnIndexOrThrow(ID_COL)),
                    getString(getColumnIndexOrThrow(JOKE_QUESTION_COLUMN)),
                    getString(getColumnIndexOrThrow(JOKE_ANSWER_COLUMN)),
                    false
                )
                jokesList.add(joke)
            }
        }

        db.close()
        return jokesList
    }

    fun getWithSearch(searchTerm: String): MutableList<JokeModel> {
        val db = this.readableDatabase

        val searchWithWild = "%$searchTerm%"

        val cursor = db.rawQuery(
            "SELECT * FROM $JOKES_TABLE WHERE $JOKE_QUESTION_COLUMN LIKE ? OR $JOKE_ANSWER_COLUMN LIKE ?",
            arrayOf(searchWithWild, searchWithWild)
        )

        val jokesList = mutableListOf<JokeModel>()

        with(cursor) {
            while (moveToNext()) {
                val joke = JokeModel(
                    getInt(getColumnIndexOrThrow(ID_COL)),
                    getString(getColumnIndexOrThrow(JOKE_QUESTION_COLUMN)),
                    getString(getColumnIndexOrThrow(JOKE_ANSWER_COLUMN)),
                    false
                )
                jokesList.add(joke)
            }
        }

        db.close()
        return jokesList
    }

    fun deleteById(id: Int) : Boolean {
        val db = this.writableDatabase

        val result = db.delete(JOKES_TABLE, "$ID_COL = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    fun updateById(id: Int, joke: JokeModel) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(JOKE_QUESTION_COLUMN, joke.question)
        values.put(JOKE_ANSWER_COLUMN, joke.answer)

        val result = db.update(JOKES_TABLE, values, "$ID_COL = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    fun addOne(joke: JokeModel): JokeModel?{
        val db = this.writableDatabase

        val values = ContentValues()

        values.put(JOKE_QUESTION_COLUMN, joke.question)
        values.put(JOKE_ANSWER_COLUMN, joke.answer)

        val newRowId = db.insert(JOKES_TABLE, null, values)
        Log.d("jokesDBHelper", "Added new joke to db" + joke.question + " " + joke.answer)

        db.close()

        return getById(newRowId.toInt())
    }

}