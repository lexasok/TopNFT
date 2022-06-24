package net.ozero.android.topnft.view

import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import net.ozero.android.topnft.databinding.ActivityMainBinding
import net.ozero.android.topnft.viewmodel.main.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun getViewModelInstance(): MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        binding.inputSearch.doOnTextChanged { text, _, _, _ -> viewModel.onQueryChanged(text.toString()) }
    }

    override fun initObserving() {
        observe(viewModel.nfts) {
            val strBuilder = StringBuilder()
            it.forEach { nft ->  strBuilder.append("${nft.id}, ") }
            binding.textNfts.text = strBuilder.toString()
        }
    }

}