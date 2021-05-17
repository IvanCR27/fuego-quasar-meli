package com.fuego.quasar.core.service.location

import com.fuego.quasar.common.constants.Constant
import com.fuego.quasar.common.dto.Position
import com.fuego.quasar.common.exception.FuegoQuasarException
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver
import org.springframework.stereotype.Service
import com.lemmingapex.trilateration.TrilaterationFunction
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer
import org.springframework.http.HttpStatus

@Service
class LocationService {

    fun getLocation(distances: List<Double>): Position {

        if (distances.size < Constant.MIN_DISTANCES_SIZE) {
            throw FuegoQuasarException(
                code = "404",
                message = "Must have at least 3 distances",
                status = HttpStatus.NOT_FOUND
            )
        }

        val positions = Array(3) { DoubleArray(2) }
        positions[0][0] = Constant.KENOBI.x
        positions[0][1] = Constant.KENOBI.y
        positions[1][0] = Constant.SKYWALKER.x
        positions[1][1] = Constant.SKYWALKER.y
        positions[2][0] = Constant.SATO.x
        positions[2][1] = Constant.SATO.y

        val trilerationFunction = TrilaterationFunction(positions, distances.toDoubleArray())
        val nSolver = NonLinearLeastSquaresSolver(trilerationFunction, LevenbergMarquardtOptimizer())

        val points = nSolver.solve().point.toArray()

        return Position(x = points[0], y = points[1])
    }
}