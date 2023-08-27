package co.gabrielcastro.instagramclone.common.base

import co.gabrielcastro.instagramclone.login.data.FakeDataSource
import co.gabrielcastro.instagramclone.login.data.LoginRepository
import co.gabrielcastro.instagramclone.profile.data.ProfileFakeRemoteDataSource
import co.gabrielcastro.instagramclone.profile.data.ProfileRepository
import co.gabrielcastro.instagramclone.register.data.FakeRegisterDataSource
import co.gabrielcastro.instagramclone.register.data.RegisterRepository
import co.gabrielcastro.instagramclone.splash.data.FakeLocalDataSource
import co.gabrielcastro.instagramclone.splash.data.SplashRepository

object DependencyInjector {

    fun splashRepository() : SplashRepository {
        return SplashRepository(FakeLocalDataSource())
    }
    fun loginRepository() : LoginRepository {
        return LoginRepository(FakeDataSource())
    }

    fun registerEmailRepository() : RegisterRepository {
        return RegisterRepository(FakeRegisterDataSource())
    }

    fun profileRepository() : ProfileRepository {
        return ProfileRepository(ProfileFakeRemoteDataSource())
    }

}