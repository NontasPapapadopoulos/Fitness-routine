package com.example.fitness_routine.data.datasource

import com.example.fitness_routine.data.cache.dao.DailyReportDao
import javax.inject.Inject

interface DailyRoutineDataSource {



}




class DailyRoutineDataSourceImpl @Inject constructor(
    dao: DailyReportDao
): DailyRoutineDataSource {



}