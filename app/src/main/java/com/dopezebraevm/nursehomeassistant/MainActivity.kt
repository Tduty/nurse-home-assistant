package com.dopezebraevm.nursehomeassistant

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import com.dopezebraevm.nursehomeassistant.view.MainFragment
import com.dopezebraevm.nursehomeassistant.view.auth.LoginFirstStepFragment
import com.dopezebraevm.nursehomeassistant.view.dairy.DairyFragment
import com.dopezebraevm.nursehomeassistant.view.help.HelpFragment
import com.dopezebraevm.nursehomeassistant.view.plan.CreatePlanFragment
import com.dopezebraevm.nursehomeassistant.view.task.NewTaskVO
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        const val DAIRY_TAG = "dairy"
        const val TASKS_TAG = "tasks"
        const val HELP_TAG = "help"
    }

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (App.get(this).prefHelper.isNotFirstStart()) {
            showNavFragment(MainFragment(), TASKS_TAG)
            bottom_navigation.selectedItemId = R.id.action_tasks
        } else showFragment(LoginFirstStepFragment())

        bottom_navigation.setOnNavigationItemSelectedListener { item -> onSelectedItem(item) }
    }

    fun showBottomNavigationBar() {
        bottom_navigation.visibility = View.VISIBLE
    }

    fun hideBottomNavigationBar() {
        bottom_navigation.visibility = View.GONE
    }

    private fun onSelectedItem(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_dairy -> showNavFragment(DairyFragment.newInstance(), DAIRY_TAG)
            R.id.action_tasks -> showNavFragment(MainFragment.newInstance(), TASKS_TAG)
            R.id.action_help -> showNavFragment(HelpFragment(), HELP_TAG)
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun closeFragment(newTaskVO: NewTaskVO) {
        supportFragmentManager.popBackStack()
        val fragments = supportFragmentManager.fragments
        fragments.forEach {
            when (it) {
                is MainFragment -> {
                    it.addTask(newTaskVO)
                    return@forEach
                }
                is CreatePlanFragment -> {
                    it.addTask(newTaskVO)
                    return@forEach
                }
            }
        }
    }

    fun closeFragment() {
        supportFragmentManager.popBackStack()
    }

    fun showFragment(fragment: Fragment, tag: String? = null) {
        val transaction = supportFragmentManager.beginTransaction()
        val currentFragment = supportFragmentManager.findFragmentById(R.id.content_frame)
        setupAnimation(fragment)
        transaction.add(R.id.content_frame, fragment, tag)
        transaction.addToBackStack(null)
        try {
            transaction.commit()
        } catch (ignored: IllegalStateException) { }
    }

    private fun showNavFragment(fragment: Fragment, tag: String) {
        if (tag == currentFragment?.tag) return
        val transaction = supportFragmentManager.beginTransaction()
        if (currentFragment?.isVisible == true) {
            currentFragment?.let { transaction.hide(it) }
        }
        var newFragment = supportFragmentManager.findFragmentByTag(tag)
        if (newFragment == null) {
            newFragment = fragment
            transaction.add(R.id.content_frame, newFragment, tag)
        } else if (newFragment is DairyFragment) {
            newFragment.addData()
        }
        currentFragment = newFragment
        transaction.show(newFragment)
        try {
            transaction.commitNow()
        } catch (ignored: java.lang.IllegalStateException) {
        }
    }

    private fun setupAnimation(fragment: Fragment) {
        val enterFade = Slide(Gravity.END)
        enterFade.startDelay = 80
        enterFade.duration = 200
        fragment.enterTransition = enterFade
    }

    private fun isSameFragment(
        newFragment: Fragment,
        newTag: String?,
        oldFragment: Fragment?
    ): Boolean {
        return if (newTag != null) newTag == oldFragment?.tag
        else oldFragment?.javaClass == newFragment.javaClass
    }
}