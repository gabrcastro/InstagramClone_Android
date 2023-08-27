package co.gabrielcastro.instagramclone.common.model

import java.util.UUID

object Database {
    val usersAuth = hashSetOf<UserAuth>()
    val photos = hashSetOf<Photo>()
    val posts = hashMapOf<String, Set<Post>>()

    var sessionAuth: UserAuth? = null

    init {
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "user.a@gmail.com", "User A", "123123123"))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "user.b@gmail.com", "User B", "123123123"),)

        sessionAuth = usersAuth.first()
    }
}