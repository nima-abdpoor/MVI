package com.chinachino.mvi.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chinachino.mvi.R
import com.chinachino.mvi.model.BlogPost
import com.chinachino.mvi.ui.DataStateListener
import com.chinachino.mvi.ui.main.state.MainStateEvent
import com.chinachino.mvi.ui.main.state.MainViewState
import com.chinachino.mvi.utils.DataState
import com.chinachino.mvi.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() , BlogListAdapter.Interaction{
    val TAG = "MainFragment"

    lateinit var viewModel: MainViewModel
    lateinit var dataStateListener: DataStateListener
    lateinit var blogListAdapter: BlogListAdapter

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
        initRecyclerView()
    }
    private fun initRecyclerView(){
        val topSpacingItemDecoration =  TopSpacingItemDecoration(30)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(topSpacingItemDecoration)
            blogListAdapter = BlogListAdapter(this@MainFragment)
            adapter = blogListAdapter
        }
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(viewLifecycleOwner, Observer {dataState ->
            //handle loading and message
            dataStateListener.onDataStateChanged(dataState)

            //handle data
            handleData(dataState)


        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState->
            viewState.blogPosts?.let {list->
                println("debug :: BlogPost ---> $list")
                blogListAdapter.submitList(list)
            }
            viewState.user?.let {
                println("debug :: User ---> $it")
            }
        })
    }


    private fun handleData(dataState: DataState<MainViewState>) {
        dataState.data?.let {event ->
            event.getContentIfNotHandled()?.let {mainViewState->
                mainViewState.user?.let {
                    viewModel.setUserData(it)
                }
                mainViewState.blogPosts?.let {
                    viewModel.setBlogPostData(it)
                }
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        }catch (e : ClassCastException){
            println("DEBUGE : $context must implement DataStateListener!!")
        }
    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        Log.d(TAG, "onItemSelected: $position Clicked")
        Log.d(TAG, "onItemSelected: $item Clicked")
    }
}