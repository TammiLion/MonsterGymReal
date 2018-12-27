package com.tammidev.monstergym.monstergym.feature

import android.animation.Animator
import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.media.MediaPlayer
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager
import android.view.View
import com.tammidev.monstergym.monstergym.feature.utils.*
import com.tammidev.monstergym.monstergym.logic.Gym
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, MainViewModelFactory()).get(MainViewModel::class.java)

        lootBtn.setOnClickListener { onLootButtonClicked() }
    }

    private fun onLootButtonClicked() {
        disableLootButton()
        playButtonSound()
        val animationStartDelay = getDelayIfNotInStartPosition()
        setViewsInStartPosition()
        val drawableRes = getRandomMonsterImage()

        shakePreviewImageAndRevealMonster(animationStartDelay, drawableRes, animationEndListener {
            enableLootButton()
            showRevealedMonsterInfo()
        })
    }

    private fun playButtonSound() {
        val mp = MediaPlayer.create(this, R.raw.button1)
        mp.seekTo(350)
        mp.start()
    }

    private fun showRevealedMonsterInfo() {
        setConstraintLayoutSecondState()
        monsterDescription.visibility = View.VISIBLE
        monsterName.visibility = View.VISIBLE
    }

    private fun setConstraintLayoutBeginState() {
        doConstraintLayoutAnimation(R.layout.activity_main)
    }

    private fun setConstraintLayoutSecondState() {
        doConstraintLayoutAnimation(R.layout.activity_main_second_state)
    }

    private fun doConstraintLayoutAnimation(@LayoutRes layoutRes: Int) {
        val mConstraintSet1 = ConstraintSet()
        val mConstraintSet2 = ConstraintSet()
        val mConstraintLayout: ConstraintLayout = findViewById(R.id.rootContainer)
        mConstraintSet2.clone(this, layoutRes)
        mConstraintSet1.clone(mConstraintLayout)
        TransitionManager.beginDelayedTransition(mConstraintLayout)
        mConstraintSet2.applyTo(mConstraintLayout)
    }

    private fun disableLootButton() {
        lootBtn.isEnabled = false
    }

    private fun enableLootButton() {
        lootBtn.isEnabled = true
    }

    //We want to delay the shake animation when the preview image is not visible yet. It's easier on the eyes that way.
    private fun getDelayIfNotInStartPosition() = if (monsterImage.visibility == View.VISIBLE) 500L else 0L

    private fun setViewsInStartPosition() {
        monsterDescription.visibility = View.GONE
        monsterName.visibility = View.GONE
        monsterImage.visibility = View.INVISIBLE
        previewImage.visibility = View.VISIBLE
        setConstraintLayoutBeginState()
    }

    private fun getRandomMonsterImage() = MonsterImage.values().random().drawable

    private fun shakePreviewImageAndRevealMonster(startDelay: Long, drawableRes: Int, allAnimationsEndedListener: Animator.AnimatorListener) {
        previewImage
                .shake()
                .setStartDelay(startDelay)
                .setListener(animationEndListener {
                    blinkScreenHelperView.fadeIn().setListener(animationEndListener {
                        hidePreviewImage()
                        revealMonster(drawableRes, allAnimationsEndedListener)
                        blinkScreenHelperView.fadeOut(duration = QUARTER_SECOND).setListener(animationEndListener {
                            blinkScreenHelperView.visibility = View.GONE
                        })
                    }).start()
                }).start()
    }

    private fun revealMonster(drawableRes: Int, allAnimationsEndedListener: Animator.AnimatorListener) {
        monsterImage.setImageResource(drawableRes)
        monsterImage
                .fadeIn()
                .setListener(allAnimationsEndedListener)
                .start()
    }

    private fun hidePreviewImage() {
        previewImage.visibility = View.GONE
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }

}

class MainViewModel : ViewModel() {

    val text: MutableLiveData<String> = MutableLiveData()
    val gym: Gym = Gym()

    init {
        init()

    }

    @SuppressLint("CheckResult") //TODO remove when we do something useful in subscribe
    fun init() {
        gym.test().trainerWon()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    text.value = if (it.won) "Whoohoo! " + it.challenger.name + " won!" else it.challenger.name + " lost :("
                }
    }
}
