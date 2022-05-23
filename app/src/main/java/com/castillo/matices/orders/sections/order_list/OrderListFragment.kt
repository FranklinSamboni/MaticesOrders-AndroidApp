package com.castillo.matices.orders.sections.order_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.castillo.matices.orders.R
import com.castillo.matices.orders.databinding.FragmentOrderListBinding
import com.castillo.matices.orders.sections.add_order.AddOrderActivity
import com.castillo.matices.orders.sections.order_detail.OrderDetailActivity

/**
 * A simple [Fragment] subclass.
 * Use the [OrderListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class OrderListFragment : Fragment(), OrderAdapter.OnOrderClickListener {

    private val ADD_ORDER_ACTIVITY_RESULT_CODE = 1

    private lateinit var binding: FragmentOrderListBinding
    private lateinit var adapter: OrderAdapter
    private var orderListViewModel =
        OrderListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentOrderListBinding.inflate(inflater, container, false)
        val view = binding.root

        setHasOptionsMenu(true)
        setupRecyclerView()

        return view
    }

    override fun onResume() {
        super.onResume()
        getOrders()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.order_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_order -> {
                navigateToAddOrder()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onOrderClick(position: Int) {
        Log.e("TAG","MESSAGE $position")
        val order = adapter.orders[position]
        val activity = Intent(activity, OrderDetailActivity::class.java)
        activity.putExtra("order", order)
        startActivity(activity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === ADD_ORDER_ACTIVITY_RESULT_CODE) {
            getOrders()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun navigateToAddOrder() {
        val activity = Intent(activity, AddOrderActivity::class.java)
        startActivityForResult(activity, ADD_ORDER_ACTIVITY_RESULT_CODE)
    }

    private fun setupRecyclerView() {
        adapter = OrderAdapter(
            requireContext(),
            listOf(),
            this
        )
        binding.ordersRecyclerView.adapter = adapter
        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getOrders() {
        orderListViewModel.getOrders { orders, error ->
            activity?.runOnUiThread {
                if (!error.isEmpty()) {
                    val toast = Toast.makeText(activity, error, Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    adapter.updateOrders(orders)
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
         * @return A new instance of fragment OrderListFragment.
         */
        @JvmStatic
        fun newInstance() =
            OrderListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}