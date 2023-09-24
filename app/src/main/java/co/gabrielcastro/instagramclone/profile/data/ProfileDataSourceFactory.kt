package co.gabrielcastro.instagramclone.profile.data

import co.gabrielcastro.instagramclone.common.base.Cache
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.UserAuth
import co.gabrielcastro.instagramclone.profile.Profile

class ProfileDataSourceFactory(
    private val profileCache: Cache<UserAuth>,
    private val postsCache: Cache<List<Post>>
) {

    fun createLocalDataSource() : ProfileDataSource {
        return ProfileLocalDataSource(profileCache, postsCache)
    }

    fun createFromUser() : ProfileDataSource {
        if (profileCache.isCached()) {
            return ProfileLocalDataSource(profileCache, postsCache)
        } else {
            return ProfileFakeRemoteDataSource()
        }
    }

    fun createFromPosts() : ProfileDataSource {
        if (postsCache.isCached()) {
            return ProfileLocalDataSource(profileCache, postsCache)
        } else {
            return ProfileFakeRemoteDataSource()
        }
    }

}