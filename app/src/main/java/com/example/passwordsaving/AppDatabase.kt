import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passwordsaving.data.Content

@Dao
interface TopicDao {
    @Insert
    fun insertTopic(topic: Topic): Long

    @Query("SELECT * FROM Topic")
    fun getAllTopics(): LiveData<List<Topic>>
}

@Dao
interface ContentDao {
    @Insert
    fun insertContent(content: Content)
}

@Database(entities = [Topic::class, Content::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
    abstract fun contentDao(): ContentDao
}