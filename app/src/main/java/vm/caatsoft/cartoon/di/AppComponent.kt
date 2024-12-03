package vm.caatsoft.cartoon.di

import dagger.Component
import vm.caatsoft.cartoon.view.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}

