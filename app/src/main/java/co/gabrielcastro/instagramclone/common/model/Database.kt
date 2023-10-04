package co.gabrielcastro.instagramclone.common.model

import android.net.Uri
import java.io.File
import java.util.UUID

object Database {
    val usersAuth = mutableListOf<UserAuth>()
    val posts = hashMapOf<String, MutableSet<Post>>()
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, MutableSet<String>>()

    var sessionAuth: UserAuth? = null

    init {
//        val userA = UserAuth(
//            UUID.randomUUID().toString(),
//            "User A",
//            "a@a.com",
//            "123123123",
//            null
//        )
//
//        usersAuth.add(userA)
//        sessionAuth = usersAuth.first()

    }
}