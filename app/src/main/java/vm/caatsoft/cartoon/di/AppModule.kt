package vm.caatsoft.cartoon.di

import dagger.Module
import dagger.Provides
import vm.caatsoft.cartoon.controller.MainController
import vm.caatsoft.cartoon.model.GraphQLClient
import vm.caatsoft.cartoon.view.MainActivity
import javax.inject.Singleton

@Module
class AppModule(private val mainActivity: MainActivity) {

    @Provides
    @Singleton
    fun provideViewContract(): MainController.ViewContract {
        return mainActivity
    }
}

