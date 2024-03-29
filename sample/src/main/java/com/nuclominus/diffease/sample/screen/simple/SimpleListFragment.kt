package com.nuclominus.diffease.sample.screen.simple

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nuclominus.diffease.base.ListObserver
import com.nuclominus.diffease.sample.R
import com.nuclominus.diffease.sample.data.MultiMock
import com.nuclominus.diffease.sample.databinding.FragmentSampleBinding
import com.nuclominus.diffease.sample.extensions.showSnackBar
import com.nuclominus.diffease.sample.screen.core.MockViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleListFragment : Fragment(R.layout.fragment_sample) {

    private val viewBinding by viewBinding(FragmentSampleBinding::bind)
    private val vm: MockViewModel by viewModels()

    private val adapter by lazy {
        SimpleDiffAdapter(object : ListObserver<MultiMock>() {
            override fun onItemClicked(item: MultiMock) {
                showSnackBar(
                    view = viewBinding.root,
                    text = "You click on ${getString(item.name)}"
                )
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    private fun initObservers() {
        vm.data.observe(viewLifecycleOwner) { data ->
            adapter.update(data, true)
        }
    }

    private fun initViews() {
        with(viewBinding) {
            listWidget.recyclerView.adapter = adapter

            btnShuffle.setOnClickListener {
                vm.shuffle()
            }
        }
    }
}