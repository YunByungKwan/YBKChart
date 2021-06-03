package com.kwancorp.ybkchart.piechart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.kwancorp.ybkchart.data.Item
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class PieChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val PI: Float = 3.141592F
        private const val RADIAN = PI / 180F
        private const val CIRCLE_COLOR_DEFAULT = "#FF6096"
        private const val SIDE_COLOR_DEFAULT = "#FF286A"
        private const val BORDER_COLOR_DEFAULT = "#000000"
        private const val STROKE_WIDTH_DEFAULT = 5F
    }

    private var itemList = listOf<Item>()

    private val circleRectF = RectF()
    private val sideRectF = RectF()

    private val circlePaint = Paint()
    private val borderPaint = Paint()
    private val sidePaint = Paint()
    private val textPaint = Paint()

    private var pieHeight = 0F
    private var padding = 0F

    init {
        circlePaint.apply {
            this.color = Color.parseColor(CIRCLE_COLOR_DEFAULT)
            this.style = Paint.Style.FILL
        }

        sidePaint.apply {
            this.color = Color.parseColor(SIDE_COLOR_DEFAULT)
            this.style = Paint.Style.STROKE
        }

        borderPaint.apply {
            this.color = Color.parseColor(BORDER_COLOR_DEFAULT)
            this.strokeWidth = STROKE_WIDTH_DEFAULT
            this.style = Paint.Style.STROKE
        }

        textPaint.apply {
            this.color = Color.BLACK
            this.textSize = 50F
            this.textAlign = Paint.Align.CENTER
        }

        padding = borderPaint.strokeWidth + 2F
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        circleRectF.apply {
            this.left = 0F + pieHeight + (padding + borderPaint.strokeWidth)
            this.top = 0F + pieHeight + (padding + borderPaint.strokeWidth)
            this.right = right.toFloat() - left.toFloat() - pieHeight - (padding + borderPaint.strokeWidth)
            this.bottom = bottom.toFloat() - top.toFloat() - pieHeight - (padding + borderPaint.strokeWidth)
        }

        sideRectF.apply {
            this.left = 0F + pieHeight + (padding + borderPaint.strokeWidth)
            this.top = 0F + pieHeight + (padding + borderPaint.strokeWidth)
            this.right = right.toFloat() - left.toFloat() - pieHeight - (padding + borderPaint.strokeWidth)
            this.bottom = bottom.toFloat() - top.toFloat() - pieHeight - (padding + borderPaint.strokeWidth)
        }
    }

    override fun dispatchDraw(canvas: Canvas?) {
    }

    override fun onDraw(canvas: Canvas?) {
        drawPieChart(canvas)
        drawTextOfPieChart(canvas)
    }

    /** Draw a pie chart. */
    private fun drawPieChart(canvas: Canvas?) {
        drawSideOfPieChart(canvas)
        drawTopOfPieChart(canvas)
    }

    /** Draw the side of the pie chart. */
    private fun drawSideOfPieChart(canvas: Canvas?) {
        drawBottomBorderOfSideOfPieChart(canvas)
        fillSideOfPieChartWithArc(canvas)
        drawBorderOfSideOfPieChart(canvas)
    }

    /** Draw the top of the pie chart. */
    private fun drawTopOfPieChart(canvas: Canvas?) {
        fillTopOfPieChartWithArc(canvas)
        drawBorderOfTopOfPieChart(canvas)
    }

    private fun drawTextOfPieChart(canvas: Canvas?) {
        var offset = 0F
        for(item in itemList) {
            val radius = (circleRectF.bottom - circleRectF.top) / 2F
            val centerX = getCenterX(circleRectF)
            val centerY = getCenterY(circleRectF)
            val startX = centerX + (radius*2/3 * cos((offset + item.value/2) * RADIAN))
            val startY = centerY + (radius*2/3 * sin((offset + item.value/2) * RADIAN))
            canvas?.drawText(item.title, startX, startY, textPaint)

            offset += item.value
        }
    }

    /** Fill the side of the pie chart with an arc. */
    private fun fillSideOfPieChartWithArc(canvas: Canvas?) {
        sidePaint.apply {

        }
        for(j in 0 until pieHeight.toInt()) {
            var offset = 0F
            for(item in itemList) {
                canvas?.drawArc(sideRectF, offset, item.value, false, sidePaint)
                offset += item.value
            }
            sideRectF.apply {
                this.top++
                this.bottom++
            }
        }
    }

    /** Draw the border of the side of the pie chart. */
    private fun drawBorderOfSideOfPieChart(canvas: Canvas?) {
        var offset = 0F
        //val centerX = circleRectF.left + ((circleRectF.right - circleRectF.left) / 2F)
        //val centerY = circleRectF.top + ((circleRectF.bottom - circleRectF.top) / 2F)
        val centerX = getCenterX(circleRectF)
        val centerY = getCenterY(circleRectF)
        val a = (circleRectF.right - circleRectF.left) / 2F
        val b = (circleRectF.bottom - circleRectF.top) / 2F

        canvas?.drawLine(circleRectF.left, centerY, circleRectF.left, centerY + pieHeight, borderPaint)
        canvas?.drawLine(circleRectF.right, centerY, circleRectF.right, centerY + pieHeight, borderPaint)

        for(item in itemList) {
            val alpha = offset * RADIAN
            val beta = (offset + item.value) * RADIAN

            val alphaSign = if(offset in 90.0..270.0) -1F else 1F
            val betaSign = if(offset + item.value in 90.0..270.0) -1F else 1F

            val sX1 = centerX + alphaSign * (1 / sqrt((1/(a * a)) + ((tan(alpha) * tan(alpha)) / (b * b))))
            val sY1 = centerY + alphaSign * (tan(alpha) / sqrt((1/(a * a)) + ((tan(alpha) * tan(alpha)) / (b * b))))
            val sX2 = centerX + betaSign * (1 / sqrt((1/(a * a)) + ((tan(beta) * tan(beta)) / (b * b))))
            val sY2 = centerY + betaSign * (tan(beta) / sqrt((1/(a * a)) + ((tan(beta) * tan(beta)) / (b * b))))

            canvas?.drawLine(sX1, sY1, sX1, sY1 + pieHeight, borderPaint)
            canvas?.drawLine(sX2, sY2, sX2, sY2 + pieHeight, borderPaint)
            offset += item.value
        }
    }

    private fun drawBottomBorderOfSideOfPieChart(canvas: Canvas?) {
        val borderWidth = borderPaint.strokeWidth.toInt()
        sideRectF.apply {
            this.top += pieHeight
            this.bottom += pieHeight
        }
        for(i in 0 until borderWidth) {
            var offset = 0F
            for(item in itemList) {
                canvas?.drawArc(sideRectF, offset, item.value, false, borderPaint)
                offset += item.value
            }
            sideRectF.apply {
                this.top++
                this.bottom++
            }
        }
        sideRectF.apply {
            this.top -= (pieHeight + borderWidth)
            this.bottom -= (pieHeight + borderWidth)
        }
    }

    /** Fill the top of the pie chart with an arc. */
    private fun fillTopOfPieChartWithArc(canvas: Canvas?) {
        var offset = 0F
        for(item in itemList) {
            canvas?.drawArc(circleRectF, offset, item.value, true, circlePaint)
            offset += item.value
        }
    }

    /** Draw the border of the top of the pie chart. */
    private fun drawBorderOfTopOfPieChart(canvas: Canvas?) {
        var offset = 0F
        for(item in itemList) {
            canvas?.drawArc(circleRectF, offset, item.value, true, borderPaint)
            offset += item.value
        }
    }

    private fun getCenterX(circleRectF: RectF): Float = circleRectF.left + ((circleRectF.right - circleRectF.left) / 2F)

    private fun getCenterY(circleRectF: RectF): Float = circleRectF.top + ((circleRectF.bottom - circleRectF.top) / 2F)

    /** Set the item of the pie chart. */
    fun setItems(list: MutableList<Item>) {
        var valueSum = 0F
        list.forEach { item ->
            valueSum += item.value
        }
        list.forEach { item ->
            item.value = item.value / valueSum * 360F
        }
        this.itemList = list
    }

    fun setCircleColor(color: Int) {
        circlePaint.color = color
    }

    fun setSideColor(color: Int) {
        sidePaint.color = color
    }

    fun setBorderWidth(stroke: Float) {
        borderPaint.strokeWidth = stroke
    }

    fun setBorderColor(color: Int) {
        borderPaint.color = color
    }

    fun setItemTextSize(size: Float) {
        textPaint.textSize = size
    }

    fun setItemTextColor(color: Int) {
        textPaint.color = color
    }

    fun setHeight(pieHeight: Float) {
        this.pieHeight = pieHeight
    }
}