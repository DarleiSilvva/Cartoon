package vm.caatsoft.cartoon.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vm.caatsoft.cartoon.R
import vm.caatsoft.cartoon.databinding.ActivityMainBinding
import vm.caatsoft.cartoon.controller.MainController
import vm.caatsoft.cartoon.graphql.GetCharacterQuery
import vm.caatsoft.cartoon.di.AppComponent
import vm.caatsoft.cartoon.di.AppModule
import vm.caatsoft.cartoon.di.DaggerAppComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainController.ViewContract {

    @Inject
    lateinit var controller: MainController

    private lateinit var binding: ActivityMainBinding
    private lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initInjectionDependency()
        setListeners()
    }

    private fun initInjectionDependency() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }

    private fun setListeners() {
        binding.buttonDiscover.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                controller.fetchRandomCharacter()
            }
        }
    }

    override fun showLoading() {
        binding.textCharacterInfo.text = getString(R.string.loading)
    }

    override fun hideLoading() {}

    override fun showCharacter(character: GetCharacterQuery.Character) {
        binding.textCharacterInfo.text = buildString {
            append(character.name)
            append("\n")
            append(character.species)
            append("\n")
            append(character.status)
            append("\n")
            append(character.gender)
        }
        Glide.with(this)
            .load(character.image)
            .into(binding.imageCharacter)
    }

    override fun showError(message: String) {
        binding.textCharacterInfo.text = message
    }
}
