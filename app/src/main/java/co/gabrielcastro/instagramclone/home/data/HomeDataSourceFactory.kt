package co.gabrielcastro.instagramclone.home.data

import co.gabrielcastro.instagramclone.common.base.Cache
import co.gabrielcastro.instagramclone.common.model.Post

class  HomeDataSourceFactory(
    private val feedCache: Cache<List<Post>>
) {

    fun createLocalDataSource() : HomeDataSource {
        return HomeLocalDataSource(feedCache)
    }

    fun createRemoteDataSource() : HomeDataSource {
        return FirebaseHomeDataSource()
    }

    fun createFromFeed() : HomeDataSource {
        if (feedCache.isCached()) {
            return HomeLocalDataSource(feedCache)
        }
        return FirebaseHomeDataSource()//HomeFakeRemoteDataSource()
    }

}