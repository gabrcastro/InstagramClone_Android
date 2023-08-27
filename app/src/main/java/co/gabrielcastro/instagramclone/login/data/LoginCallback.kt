package co.gabrielcastro.instagramclone.login.data

import co.gabrielcastro.instagramclone.common.model.UserAuth

interface LoginCallback {
    fun onSuccess(userAuth: UserAuth)
    fun onFailure(message: String)
    fun onComplete()
}