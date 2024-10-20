import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Topic")
data class Topic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "topic") val topic: String,
    @ColumnInfo(name = "subtopic") val subtopic: String
)
