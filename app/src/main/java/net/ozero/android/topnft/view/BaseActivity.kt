package net.ozero.android.topnft.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import net.ozero.android.topnft.R
import net.ozero.android.topnft.viewmodel.base.BaseViewModel

abstract class BaseActivity<T: BaseViewModel, VBinding: ViewBinding> : AppCompatActivity() {

    protected val viewModel by lazy { getViewModelInstance() }

    protected lateinit var binding: VBinding

    abstract fun getViewModelInstance(): T

    abstract fun getViewBinding(): VBinding

    abstract fun initView()

    abstract fun initObserving()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TopNFT)
        binding = getViewBinding()
        setContentView(binding.root)
        initView()
        viewModel.onViewInitialized()
        initObserving()
        observe(viewModel.throwableError) { handleThrowable(it) }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.onViewDestroyed()
    }

    protected fun handleThrowable(t: Throwable) {
        showToast(t.message)
    }

    protected fun showToast(message: String?) {
        message?.let { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
    }

    protected fun <T> observe(field: MutableLiveData<T>, onChanged: (T) -> Unit) {
        field.observe(this) { onChanged(it) }
    }
}