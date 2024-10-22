import androidx.compose.ui.graphics.vector.ImageVector
import fitnesstracker.FitnessTracker24px
import kotlin.collections.List as ____KtList

public object FitnessTracker

private var __AllIcons: ____KtList<ImageVector>? = null

public val FitnessTracker.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(FitnessTracker24px)
    return __AllIcons!!
  }
