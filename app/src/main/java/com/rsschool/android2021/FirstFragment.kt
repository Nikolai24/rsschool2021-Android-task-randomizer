package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var listener: ActionGenerate? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionGenerate
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val min: EditText = view.findViewById(R.id.min_value)
        val max: EditText = view.findViewById(R.id.max_value)
        var minInt = 0
        var maxInt = 0

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        generateButton?.setOnClickListener {
            if (min.text.toString() != "" && max.text.toString() != "")
            if (min.text.toString().toLong() <= Int.MAX_VALUE && max.text.toString().toLong() <= Int.MAX_VALUE){
                minInt = min.text.toString().toInt()
                maxInt = max.text.toString().toInt()
            }
            if (minInt < maxInt) {
                listener?.onActionGenerate(minInt, maxInt)
            } else {
                Toast.makeText(activity, "Входные данные невалидны", Toast.LENGTH_SHORT).show()
            }
        }
    }

    interface ActionGenerate {
        fun onActionGenerate(min: Int, max: Int) {
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}