package co.gabrielcastro.instagramclone.login.data

class LoginRepository(private val dataSource: LoginDataSource) {

    fun login(email: String, password: String, callback: LoginCallback) {
        // vai ser responsavel por decidir o que fazer com estes dados
        // servidor ou local database
        dataSource.login(email, password , callback)
    }
}