package co.gabrielcastro.instagramclone.common.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserAuth(
    val uuid: String,
    val name: String,
    val email: String,
    val password: String,
    val postCount: Int = 0,
    val followingCount: Int = 0,
    val followersCount: Int = 0,
) : Parcelable
