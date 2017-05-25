package com.rx.android.jamspeedlibrary.utils.view

import android.widget.TextView

/**
 * Created by Jam on 2017/3/2 下午7:58.
 * Describe:
 */


import java.util.ArrayList

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet

class SpannableTextView : TextView {

    private var mPieces: List<Piece>? = null

    /**
     * Create a new instance of a this class

     * @param context
     */
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mPieces = ArrayList<Piece>()
        SpannableTextView.DEFAULT_ABSOLUTE_TEXT_SIZE = getTextSize() as Int
    }

    /**
     * Use this method to add a [SpannableTextView.BabushkaText.Piece] to a BabushkaText. Each
     * [SpannableTextView.BabushkaText.Piece] is added sequentially, so the order you call this method matters.

     * @param aPiece the Piece
     */
    fun addPiece(aPiece: Piece) {
        mPieces!!.add(aPiece)
    }

    /**
     * Adds a Piece at this specific location. The underlying data structure is a [java.util.List], so expect the
     * same type of behaviour.

     * @param aPiece the Piece to add.
     * *
     * @param location the index at which to add.
     */
    fun addPiece(aPiece: Piece, location: Int) {
        mPieces!!.add(location, aPiece)
    }

    /**
     * Replaces the Piece at the specified location with this new Piece. The underlying data structure is a
     * [java.util.List], so expect the same type of behaviour.

     * @param newPiece the Piece to insert.
     * *
     * @param location the index at which to insert.
     */
    fun replacePieceAt(location: Int, newPiece: Piece) {
        mPieces!!.set(location, newPiece)
    }

    /**
     * Removes the Piece at this specified location. The underlying data structure is a [java.util.List], so
     * expect the same type of behaviour.

     * @param location the index of the Piece to remove
     */
    fun removePiece(location: Int) {
        mPieces!!.remove(location)
    }

    /**
     * Clear all the Pieces, same as reset()
     */
    fun clearPiece() {
        mPieces!!.clear()
    }

    /**
     * Get a specific [SpannableTextView.BabushkaText.Piece] in position index.

     * @param location position of Piece (0 based)
     * *
     * @return Piece o null if invalid index
     */
    fun getPiece(location: Int): Piece? {
        if (location >= 0 && location < mPieces!!.size()) {
            return mPieces!![location]
        }

        return null
    }

    /**
     * Call this method when you're done adding [SpannableTextView.BabushkaText.Piece]s and want this TextView to
     * display the final, styled version of it's String contents.

     * You MUST also call this method whenever you make a modification to the text of a Piece that has already been
     * displayed.
     */
    fun display() {

        // generate the final string based on the pieces
        val builder = StringBuilder()
        for (aPiece in mPieces!!) {
            builder.append(aPiece.text)
        }

        // apply spans
        var cursor = 0
        val finalString = SpannableString(builder.toString())
        for (aPiece in mPieces!!) {
            applySpannablesTo(aPiece, finalString, cursor, cursor + aPiece.text!!.length())
            cursor += aPiece.text!!.length()
        }

        // set the styled text
        setText(finalString)
    }

    private fun applySpannablesTo(aPiece: Piece, finalString: SpannableString, start: Int, end: Int) {

        if (aPiece.subscript) {
            finalString.setSpan(SubscriptSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        if (aPiece.superscript) {
            finalString.setSpan(SuperscriptSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        if (aPiece.strike) {
            finalString.setSpan(StrikethroughSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        if (aPiece.underline) {
            finalString.setSpan(UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        // style
        finalString.setSpan(StyleSpan(aPiece.style), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // absolute text size
        finalString.setSpan(AbsoluteSizeSpan(aPiece.textSize), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // relative text size
        finalString.setSpan(RelativeSizeSpan(aPiece.textSizeRelative), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // text color
        finalString.setSpan(ForegroundColorSpan(aPiece.textColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // background color
        if (aPiece.backgroundColor != -1) {
            finalString.setSpan(BackgroundColorSpan(aPiece.backgroundColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    /**
     * Resets the styling of this view and sets it's content to an empty String.
     */
    fun reset() {
        mPieces = ArrayList<Piece>()
        setText("")
    }

    /**
     * Change text color of all pieces of textview.
     */
    fun changeTextColor(textColor: Int) {
        for (mPiece in mPieces!!) {
            mPiece.setTextColor(textColor)
        }
        display()
    }

    /**
     * A Piece represents a part of the text that you want to style. Say for example you want this BabushkaText to
     * display "Hello World" such that "Hello" is displayed in Bold and "World" is displayed in Italics. Since these
     * have different styles, they are both separate Pieces.

     * You create a Piece by using it's [SpannableTextView.BabushkaText.Piece.Builder]

     */
    class Piece(builder: Piece.Builder) {

        private var text: String? = null

        private var textColor: Int = 0

        private val textSize: Int

        private val backgroundColor: Int

        private val textSizeRelative: Float

        private val style: Int

        private val underline: Boolean

        private val superscript: Boolean

        private val strike: Boolean

        private val subscript: Boolean

        init {
            this.text = builder.text
            this.textSize = builder.textSize
            this.textColor = builder.textColor
            this.backgroundColor = builder.backgroundColor
            this.textSizeRelative = builder.textSizeRelative
            this.style = builder.style
            this.underline = builder.underline
            this.superscript = builder.superscript
            this.subscript = builder.subscript
            this.strike = builder.strike
        }

        /**
         * Sets the text of this Piece. If you're creating a new Piece, you should do so using it's
         * [SpannableTextView.BabushkaText.Piece.Builder].

         * Use this method if you want to modify the text of an existing Piece that is already displayed. After doing
         * so, you MUST call `display()` for the changes to show up.

         * @param text the text to display
         */
        fun setText(text: String) {
            this.text = text
        }

        /**
         * Sets the text color of this Piece. If you're creating a new Piece, you should do so using it's
         * [SpannableTextView.BabushkaText.Piece.Builder].

         * Use this method if you want to change the text color of an existing Piece that is already displayed. After
         * doing so, you MUST call `display()` for the changes to show up.

         * @param color of text (it is NOT android Color resources ID, use getResources().getColor(R.color.colorId) for
         * *            it)
         */
        fun setTextColor(textColor: Int) {
            this.textColor = textColor
        }

        /**
         * Builder of Pieces
         */
        class Builder
        /**
         * Creates a new Builder for this Piece.

         * @param text the text of this Piece
         */
        (// required
                private val text: String) {

            // optional
            private var textSize = DEFAULT_ABSOLUTE_TEXT_SIZE

            private var textColor = Color.BLACK

            private var backgroundColor = -1

            private var textSizeRelative = DEFAULT_RELATIVE_TEXT_SIZE

            private var style = Typeface.NORMAL

            private var underline = false

            private var strike = false

            private var superscript = false

            private var subscript = false

            /**
             * Sets the absolute text size.

             * @param textSize text size in pixels
             * *
             * @return a Builder
             */
            fun textSize(textSize: Int): Builder {
                this.textSize = textSize
                return this
            }

            /**
             * Sets the text color.

             * @param textColor the color
             * *
             * @return a Builder
             */
            fun textColor(textColor: Int): Builder {
                this.textColor = textColor
                return this
            }

            /**
             * Sets the background color.

             * @param backgroundColor the color
             * *
             * @return a Builder
             */
            fun backgroundColor(backgroundColor: Int): Builder {
                this.backgroundColor = backgroundColor
                return this
            }

            /**
             * Sets the relative text size.

             * @param textSizeRelative relative text size
             * *
             * @return a Builder
             */
            fun textSizeRelative(textSizeRelative: Float): Builder {
                this.textSizeRelative = textSizeRelative
                return this
            }

            /**
             * Sets a style to this Piece.

             * @param style see [android.graphics.Typeface]
             * *
             * @return a Builder
             */
            fun style(style: Int): Builder {
                this.style = style
                return this
            }

            /**
             * Underlines this Piece.

             * @return a Builder
             */
            fun underline(): Builder {
                this.underline = true
                return this
            }

            /**
             * Strikes this Piece.

             * @return a Builder
             */
            fun strike(): Builder {
                this.strike = true
                return this
            }

            /**
             * Sets this Piece as a superscript.

             * @return a Builder
             */
            fun superscript(): Builder {
                this.superscript = true
                return this
            }

            /**
             * Sets this Piece as a subscript.

             * @return a Builder
             */
            fun subscript(): Builder {
                this.subscript = true
                return this
            }

            /**
             * Creates a [SpannableTextView.BabushkaText.Piece] with the customized parameters.

             * @return a Piece
             */
            fun build(): Piece {
                return Piece(this)
            }
        }
    }

    companion object {

        // some default params
        private var DEFAULT_ABSOLUTE_TEXT_SIZE: Int = 0

        private val DEFAULT_RELATIVE_TEXT_SIZE = 1f
    }
}
