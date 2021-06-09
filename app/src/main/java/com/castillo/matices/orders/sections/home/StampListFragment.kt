package com.castillo.matices.orders.sections.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.castillo.matices.orders.R
import com.castillo.matices.orders.databinding.FragmentStampListBinding
import com.castillo.matices.orders.models.Product
import com.castillo.matices.orders.models.Stamp
import com.castillo.matices.orders.sections.add_products.AddProductActivity
import com.castillo.matices.orders.sections.add_stamp.AddStampActivity


/**
 * A simple [Fragment] subclass.
 * Use the [StampListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StampListFragment : Fragment(), StampAdapter.OnStampClickListener {

    private val ADD_STAMP_ACTIVITY_RESULT_CODE = 1

    private lateinit var binding: FragmentStampListBinding
    private lateinit var adapter: StampAdapter
    private var stampListViewModel = StampListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentStampListBinding.inflate(inflater, container, false)
        val view = binding.root

        setHasOptionsMenu(true)
        setupRecyclerView()

        return view
    }

    override fun onResume() {
        super.onResume()
        getStamps()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.stamp_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_stamp -> {
                navigateToAddStamp(null)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onStampClick(position: Int) {
        Log.e("TAG","MESSAGE $position")
        val stamp = adapter.stamps[position]
        navigateToAddStamp(stamp)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === ADD_STAMP_ACTIVITY_RESULT_CODE) {
            getStamps()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun navigateToAddStamp(stamp: Stamp?) {
        val activity = Intent(activity, AddStampActivity::class.java)
        activity.putExtra("stamp", stamp)
        startActivityForResult(activity, ADD_STAMP_ACTIVITY_RESULT_CODE)
    }

    private fun setupRecyclerView() {
        adapter = StampAdapter(requireContext(), listOf(), this)
        binding.stampsRecyclerView.adapter = adapter
        binding.stampsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getStamps() {
        stampListViewModel.getStamps { orders, error ->
            activity?.runOnUiThread {
                if (!error.isEmpty()) {
                    val toast = Toast.makeText(activity, error, Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    adapter.updateStamps(orders)
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StampListFragment.
         */
        @JvmStatic
        fun newInstance() =
            StampListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}