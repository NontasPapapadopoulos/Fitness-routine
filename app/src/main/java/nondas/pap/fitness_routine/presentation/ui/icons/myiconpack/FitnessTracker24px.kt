package nondas.pap.fitness_routine.presentation.ui.icons.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import nondas.pap.fitness_routine.presentation.ui.icons.FitnessDiary

public val FitnessDiary.FitnessTracker24px: ImageVector
    get() {
        if (_fitnessTracker24px != null) {
            return _fitnessTracker24px!!
        }
        _fitnessTracker24px = Builder(name = "FitnessTracker24px", defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(360.0f, 880.0f)
                lineTo(312.0f, 721.0f)
                quadTo(297.0f, 705.0f, 288.5f, 684.0f)
                quadTo(280.0f, 663.0f, 280.0f, 640.0f)
                lineTo(280.0f, 320.0f)
                quadTo(280.0f, 296.0f, 288.5f, 275.0f)
                quadTo(297.0f, 254.0f, 312.0f, 238.0f)
                lineTo(360.0f, 80.0f)
                lineTo(600.0f, 80.0f)
                lineTo(646.0f, 237.0f)
                quadTo(662.0f, 254.0f, 671.0f, 275.0f)
                quadTo(680.0f, 296.0f, 680.0f, 320.0f)
                lineTo(680.0f, 640.0f)
                quadTo(680.0f, 664.0f, 671.5f, 685.0f)
                quadTo(663.0f, 706.0f, 647.0f, 723.0f)
                lineTo(600.0f, 880.0f)
                lineTo(360.0f, 880.0f)
                close()
                moveTo(419.0f, 800.0f)
                lineTo(540.0f, 800.0f)
                lineTo(552.0f, 760.0f)
                lineTo(407.0f, 760.0f)
                lineTo(419.0f, 800.0f)
                close()
                moveTo(400.0f, 680.0f)
                lineTo(560.0f, 680.0f)
                quadTo(577.0f, 680.0f, 588.5f, 668.5f)
                quadTo(600.0f, 657.0f, 600.0f, 640.0f)
                lineTo(600.0f, 320.0f)
                quadTo(600.0f, 303.0f, 588.5f, 291.5f)
                quadTo(577.0f, 280.0f, 560.0f, 280.0f)
                lineTo(400.0f, 280.0f)
                quadTo(383.0f, 280.0f, 371.5f, 291.5f)
                quadTo(360.0f, 303.0f, 360.0f, 320.0f)
                lineTo(360.0f, 640.0f)
                quadTo(360.0f, 657.0f, 371.5f, 668.5f)
                quadTo(383.0f, 680.0f, 400.0f, 680.0f)
                close()
                moveTo(407.0f, 200.0f)
                lineTo(552.0f, 200.0f)
                lineTo(540.0f, 160.0f)
                lineTo(419.0f, 160.0f)
                lineTo(407.0f, 200.0f)
                close()
                moveTo(479.0f, 760.0f)
                lineTo(479.0f, 760.0f)
                lineTo(479.0f, 760.0f)
                lineTo(479.0f, 760.0f)
                close()
                moveTo(479.0f, 200.0f)
                lineTo(479.0f, 200.0f)
                lineTo(479.0f, 200.0f)
                lineTo(479.0f, 200.0f)
                close()
            }
        }
        .build()
        return _fitnessTracker24px!!
    }

private var _fitnessTracker24px: ImageVector? = null
