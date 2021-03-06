/**
MIT License

Copyright (c) 2016 Shaun Reich <sreich02@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.ore.infinium

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.kotcrab.vis.ui.widget.*
import com.ore.infinium.util.scrollToBottom
import kotlinx.coroutines.experimental.channels.ProducerJob
import ktx.actors.onClick
import ktx.vis.table


class LoadingScreen(private val client: OreClient,
                    private val ownStage: Stage,
                    rootTable: VisTable) : VisTable() {

    val loadingMeter = VisProgressBar(0f, 100f, 1f, false)

    val airAmountLabel = VisLabel()

    //   val progressText: VisTextArea
    val progressScroll: VisScrollPane
    val progressScrollTable: VisTable = VisTable()

    val progressElements = mutableListOf<VisLabel>()

    val fixedCenterTable: VisTable
    val progressLabelPercent = VisLabel("percent")
    val loadingLabel = VisLabel("Creating World...")

    init {

        fixedCenterTable =
                table {
                    add(loadingLabel).padRight(1f)
                    row()
                    add(loadingMeter).padBottom(8f)
                    row()
                    add(progressLabelPercent).padBottom(8f)
                }

        add(fixedCenterTable).center()
//hack        add(progressScroll).expand().fill().colspan(1)
        row()

        progressScroll = VisScrollPane(progressScrollTable)
                .apply {
                    setFadeScrollBars(false)
                    setScrollBarPositions(true, true)
                }
//        progressScroll = scroll {
//            add(progressScrollTable)
//            setFadeScrollBars(false)
//            setScrollBarPositions(true, true)
//            scrollPercentY = 100f
//        }
        //scrollPane {  }

        add(progressScroll).fill().size(700f, 600f).padBottom(8f)

        row()

        val mainTable = table {
            add(fixedCenterTable)

        }
        val cancelButton = VisTextButton("Cancel")
        cancelButton.onClick { _, _ -> cancelClicked() }
        add(cancelButton)

        //progressScroll.setScrollingDisabled(true, true)

//        rootTable.add(this).expand().fill() //.padBottom(5f)//.size(500f, 200f)

        setBackground("grey-panel")
        this.layout()
    }

    private fun cancelClicked() {
        throw NotImplementedError("function not yet implemented")
    }

    fun addNewProgressLine(text: String) {
        val element = VisLabel(text)
        progressElements.add(element)

        progressScrollTable.row().left()//.pad(1f)

        progressScrollTable.apply {
            add(element).expandX().fill()
        }

        element.layout()
        progressScrollTable.layout()
        progressScroll.scrollToBottom()
        this.layout()
    }

    fun progressReceived(progress: String, worldGenJob: ProducerJob<String>) {
        loadingMeter.value += 1
        if (loadingMeter.value >= 100) {
            loadingMeter.value -= 100
        }
        progressLabelPercent.label = "${loadingMeter.value}%"

        addNewProgressLine(progress)
    }

    fun progressComplete() {
        addNewProgressLine("finished!")
    }
}

private var Label.label: String
    get() = text.toString()
    set(str) = setText(str)

