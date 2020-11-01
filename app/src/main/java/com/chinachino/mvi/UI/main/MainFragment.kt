package com.chinachino.mvi.UI.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chinachino.mvi.R
import com.chinachino.mvi.UI.main.state.MainStateEvent
import com.chinachino.mvi.UI.main.state.MainViewState
import com.chinachino.mvi.utils.DataState

class MainFragment : Fragment(){
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?: throw Exception("Invalid Activity")

        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(viewLifecycleOwner, Observer {dataState ->

            //handle data
            handleData(dataState)

            //handle error
            handleError(dataState)

            //handle loading
            handleLoading(dataState)
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState->
            viewState.blogPosts?.let {
                println("debug :: BlogPost ---> $it")
            }
            viewState.user?.let {
                println("debug :: User ---> $it")
            }
        })
    }

    private fun handleLoading(dataState: DataState<MainViewState>) {
        TODO("Not yet implemented")
    }

    private fun handleError(dataState: DataState<MainViewState>) {
        TODO("Not yet implemented")
    }

    private fun handleData(dataState: DataState<MainViewState>) {
        dataState.data?.let {mainViewState ->
            mainViewState.user?.let {
                viewModel.setUserData(it)
            }
            mainViewState.blogPosts?.let {
                viewModel.setBlogPostData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_get_blog -> triggerGetBlogPost()

            R.id.action_get_user -> triggerGetUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetUser() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }

    private fun triggerGetBlogPost() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent())
    }
}