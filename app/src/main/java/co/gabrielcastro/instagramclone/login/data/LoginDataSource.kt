package co.gabrielcastro.instagramclone.login.data

interface LoginDataSource {
    fun login(email: String, password: String, callback: LoginCallback)
}