package com.grok.akm.carousell.View

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.grok.akm.carousell.R
import com.grok.akm.carousell.Utils.SortType
import com.grok.akm.carousell.ViewModel.FragmentViewModel
import com.grok.akm.carousell.ViewModel.ViewModelFactory
import com.grok.akm.carousell.di.MyApplication
import com.grok.akm.carousell.di.SortPreferences
import javax.inject.Inject

class SortingDialogFragment : DialogFragment(), RadioGroup.OnCheckedChangeListener {

    private var recent: RadioButton? = null
    private var popular: RadioButton? = null

    private var sortingOptionsGroup: RadioGroup? = null

    @Inject
    lateinit var pref: SortPreferences

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var fragmentViewModel: FragmentViewModel? = null

    companion object {
        fun newInstance(): SortingDialogFragment {
            return SortingDialogFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApplication).appComponent!!.doInjection(this)

        fragmentViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(FragmentViewModel::class.java)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.sorting_options, null)

        recent = dialogView.findViewById<View>(R.id.recent) as RadioButton
        popular = dialogView.findViewById<View>(R.id.popular) as RadioButton
        sortingOptionsGroup = dialogView.findViewById<View>(R.id.sorting_group) as RadioGroup
        setChecked()
        sortingOptionsGroup?.setOnCheckedChangeListener(this)

        val dialog = Dialog(activity!!)
        dialog.setContentView(dialogView)
        dialog.setTitle(R.string.sort_by)
        dialog.show()
        return dialog
    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {

        when (checkedId) {
            R.id.recent -> {
                fragmentViewModel?.setStatusLiveData(SortType.RECENT)
                pref.setSelectedOption(SortType.RECENT)
                onDetach()
                dismiss()
            }

            R.id.popular -> {
                fragmentViewModel?.setStatusLiveData(SortType.POPULAR)
                pref.setSelectedOption(SortType.POPULAR)
                onDetach()
                dismiss()
            }

        }

    }

    private fun setChecked() {
        val selectedOption = pref.selectedOption
        if (selectedOption == SortType.RECENT.value) {
            recent?.isChecked = true

        } else if (selectedOption == SortType.POPULAR.value) {
            popular?.isChecked = true

        }
    }
}