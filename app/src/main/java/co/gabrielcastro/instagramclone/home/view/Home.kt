package co.gabrielcastro.instagramclone.home.view

import co.gabrielcastro.instagramclone.common.base.BasePresenter
import co.gabrielcastro.instagramclone.common.base.BaseView

interface Home {

	interface Presenter: BasePresenter {
	}
	interface View: BaseView<Presenter> {
	}

}