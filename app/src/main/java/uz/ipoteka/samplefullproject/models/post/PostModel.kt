package uz.ipoteka.samplefullproject.models.post

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class PostModel(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
):Parcelable