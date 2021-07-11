package com.sribnyak.draw

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity

const val WHITE = 0xff_00_00_00.toInt()
const val YELLOW = 0xff_ff_dd_00.toInt()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainView(this))
    }
}

class MainView(context: Context) : SurfaceView(context) {

    private val paths = Array(4) { Path() }

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = YELLOW
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    for (path in paths)
                        path.reset()
                    paths[0].moveTo(event.x, event.y)
                    paths[1].moveTo(event.x, height - event.y)
                    paths[2].moveTo(width - event.x, event.y)
                    paths[3].moveTo(width - event.x, height - event.y)
                }
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                    paths[0].lineTo(event.x, event.y)
                    paths[1].lineTo(event.x, height - event.y)
                    paths[2].lineTo(width - event.x, event.y)
                    paths[3].lineTo(width - event.x, height - event.y)
                }
            }
            val canvas = holder.lockCanvas()
            canvas.drawColor(WHITE)
            for (path in paths)
                canvas.drawPath(path, paint)
            holder.unlockCanvasAndPost(canvas)
            return true
        }
        return super.onTouchEvent(event)
    }
}