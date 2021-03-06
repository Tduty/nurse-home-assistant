package com.dopezebraevm.nursehomeassistant.view.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.dopezebraevm.nursehomeassistant.BaseFragment
import com.dopezebraevm.nursehomeassistant.MainActivity
import com.dopezebraevm.nursehomeassistant.R
import com.dopezebraevm.nursehomeassistant.data.AppData
import com.dopezebraevm.nursehomeassistant.view.MainFragment
import com.dopezebraevm.nursehomeassistant.view.plan.CreatePlanFragment
import com.dopezebraevm.nursehomeassistant.view.plan.CreatePlanFragment.Companion.LOGIN_MODE
import kotlinx.android.synthetic.main.fragment_login_third_step.*

class LoginThirdStepFragment : BaseFragment(R.layout.fragment_login_third_step) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_fio_doctor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                AppData.account.fioDoctor = p0.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        et_phone_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                AppData.account.phoneNumberDoctor = p0.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        btn_next.setOnClickListener {
            (activity as MainActivity).showFragment(CreatePlanFragment.newInstance(LOGIN_MODE))
        }
        btn_skip.setOnClickListener {
            (activity as MainActivity).showFragment(CreatePlanFragment.newInstance(LOGIN_MODE))
        }
    }
}