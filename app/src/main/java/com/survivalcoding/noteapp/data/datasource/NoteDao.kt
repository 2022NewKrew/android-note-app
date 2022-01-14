package com.survivalcoding.noteapp.data.datasource

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NoteDao {

    @Query("SELECT * FROM Note")
    abstract fun selectAll(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id == :id")
    abstract suspend fun selectById(id: Int): Note?


    fun selectAllOrderBy(columnName: String, isAsc: Boolean): Flow<List<Note>> {
        val query = "SELECT * FROM Note ORDER BY $columnName ${if(isAsc) "ASC" else "DESC"}"
        return rawQueryNotes(SimpleSQLiteQuery(query))
    }

    @RawQuery(observedEntities = [Note::class])
    protected abstract fun rawQueryNotes(query: SupportSQLiteQuery): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsert(vararg note: Note)

    @Delete
    abstract suspend fun delete(vararg note: Note)
}