package co.gabrielcastro.instagramclone.common.model

import android.net.Uri
import java.io.File
import java.util.UUID

object Database {
    val usersAuth = mutableListOf<UserAuth>()
    val posts = hashMapOf<String, MutableSet<Post>>()
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, Set<String>>()

    var sessionAuth: UserAuth? = null

    init {
        val userA = UserAuth(
            UUID.randomUUID().toString(),
            "User A",
            "user.a@gmail.com",
            "123123123",
            Uri.fromFile(
                File("/storage/emulated/0/Android/media/co.gabrielcastro.instagramclone/Instagram/2023-09-23-21-11-14-134.jpg")),
        )
            val userB = UserAuth(
            UUID.randomUUID().toString(),
            "User B",
            "user.b@gmail.com",
            "123123123",
            Uri.fromFile(
                File("/storage/emulated/0/Android/media/co.gabrielcastro.instagramclone/Instagram/2023-09-23-21-11-14-134.jpg")),
        )

            usersAuth.add(userA)
        usersAuth.add(userB)

        followers[userA.uuid] = hashSetOf()
        posts[userA.uuid] = hashSetOf()
        feeds[userA.uuid] = hashSetOf()

        followers[userB.uuid] = hashSetOf()
        posts[userB.uuid] = hashSetOf()
        feeds[userB.uuid] = hashSetOf()

        feeds[userA.uuid]?.addAll(
            arrayListOf(
                Post(UUID.randomUUID().toString(), Uri.fromFile(
                    File("/storage/emulated/0/Android/media/co.gabrielcastro.instagramclone/Instagram/2023-09-23-21-11-14-134.jpg")),
                    "desc",
                    System.currentTimeMillis(),
                    userA
                ),
            )
        )

        feeds[userA.uuid]?.toList()?.let {
            feeds[userB.uuid]?.addAll(it)
        }

        sessionAuth = usersAuth.first()
    }
}