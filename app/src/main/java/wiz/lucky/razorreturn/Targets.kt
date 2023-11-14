package wiz.lucky.razorreturn

sealed class Targets(val route: String) {

    object First : Targets("first")
    object Second : Targets("second")
    object Third : Targets("third")
    object Fourth : Targets("fourth")
    object Sixth : Targets("sixth")
    object Sevens : Targets("sevens")

}