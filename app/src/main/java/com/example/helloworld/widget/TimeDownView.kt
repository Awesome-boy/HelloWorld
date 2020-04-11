package com.example.helloworld.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class TimeDownView: View{
    //环形的圆的画笔
    private var mPaintRing:Paint= Paint(Paint.ANTI_ALIAS_FLAG)
    //圆形背景的画笔
    private var mPaintCircle:Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    //时间文字的画笔
    private var mPaintTimeText:Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    //阴影大小
    private var mShadowSize=10f
    //圆环宽度
    private var mRingWidth=20f
    //圆环的矩形
    private var mRectRing: RectF? = null

    private  var mAnimator:ValueAnimator?=null
    private var mProgress = 10f //进度
    private var mAngle = 90F
    private var mTextBaseY = 0F//文字居中的位置
    var mTimeCountDownCallBack:TimeCountDownCallBack? = null
    constructor(context: Context?) : super(context){
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }
    private fun init() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        setLayerType(LAYER_TYPE_SOFTWARE,null) //关闭硬件加速
        mPaintCircle.color=Color.WHITE
        mPaintCircle.style=Paint.Style.FILL
        mPaintCircle.setShadowLayer(mShadowSize,0f,0f,Color.BLACK)
        mPaintRing.color=Color.BLUE
        mPaintRing.style=Paint.Style.STROKE
        mPaintRing.strokeWidth=mRingWidth
        mPaintTimeText.color=Color.BLACK
        mPaintTimeText.textAlign=Paint.Align.CENTER

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRingWidth=width/10f
        mPaintTimeText.strokeWidth=mRingWidth
        mPaintTimeText.textSize=(width-mShadowSize-mRingWidth)/2
        val ringMargen= mRingWidth/3*2
        mRectRing = RectF(ringMargen+mShadowSize,ringMargen+mShadowSize,width-ringMargen-mShadowSize,height-ringMargen-mShadowSize)
        val fontMetrics = mPaintTimeText.fontMetrics
        // 计算文字高度
        val fontHeight = fontMetrics.bottom - fontMetrics.top
       // 计算文字baseline 让文字垂直居中
        mTextBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom
        startAnim()
    }

    private fun startAnim() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if (mAnimator!=null){
            mAnimator!!.cancel()
        }
        mAnimator = ValueAnimator.ofFloat(11f, 1f)
        mAnimator!!.duration = 10000L
        //设置动画差值器，让动画为匀速动画
        mAnimator!!.interpolator = LinearInterpolator()
        mAnimator!!.addUpdateListener {
            mProgress = it.animatedValue as Float
            invalidate()
        }
        mAnimator!!.repeatMode = ValueAnimator.RESTART
        mAnimator!!.repeatCount = ValueAnimator.INFINITE
        mAnimator!!.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
            }
            override fun onAnimationRepeat(animation: Animator?) {
                super.onAnimationRepeat(animation)
                //回调倒计时重复的事件
                if(mTimeCountDownCallBack!=null){
                    mTimeCountDownCallBack!!.onRepeat()
                }
            }
        })
        mAnimator!!.start()
    }
    /**
     * 结束动画
     */
    fun stopAnim(){
        if(mAnimator!=null){
            mAnimator!!.cancel()
        }
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAnimator?.cancel()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val centerX:Float = (width / 2).toFloat()
        val centerY:Float = (height / 2).toFloat()
//        画圆形背景
        canvas!!.drawCircle(centerX,centerY,Math.min(centerX,centerY)-mShadowSize,mPaintCircle)
        //画圆环
        canvas.drawArc(mRectRing!!,0F - mAngle,-36F*(mProgress-1),false,mPaintRing)
        canvas.drawText("${mProgress.toInt()}",centerX,mTextBaseY,mPaintTimeText)
    }
    /**
     * 倒计时结束的时候的回调
     */
    interface TimeCountDownCallBack{
        fun onRepeat()
    }
}